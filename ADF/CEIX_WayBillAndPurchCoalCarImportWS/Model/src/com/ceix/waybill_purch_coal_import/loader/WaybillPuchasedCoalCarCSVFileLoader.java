package com.ceix.waybill_purch_coal_import.loader;


import com.ceix.common.util.ArchiveFTPFileUtil;
import com.ceix.common.util.ConversionUtil;
import com.ceix.common.util.SFTPConnectionUtil;
import com.ceix.common.util.SFTPCredentials;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsWaybillBtmoreHdrVOImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsWaybillBtmoreHdrVORowImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsWaybillBtmoreLinesVOImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsWaybillBtmoreLinesVORowImpl;
import com.ceix.waybill_purch_coal_import.model.views.WSResultPVOImpl;
import com.ceix.waybill_purch_coal_import.model.views.WSResultPVORowImpl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.SQLException;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;

import org.apache.commons.lang3.exception.ExceptionUtils;


/**This class will handle all the steps needed to read the waybill and
 * Purchased Coal Car Details file from ftp
 * and load it in dbaas.
 * @author Romesh Soni
 */

public class WaybillPuchasedCoalCarCSVFileLoader {

    private static ADFLogger _logger =
        ADFLogger.createADFLogger(WaybillPuchasedCoalCarCSVFileLoader.class);

    
    private String SFTP_WORKING_DIR =
       null;
    private  String FILE_NAME=null;

    private int PROCESSED_COUNT = 0;
    private String ARCHIVE_DIR;
    
    private static final String LOAD_DT_CSV_DATE_FORMAT = "MM/dd/yyyy";
    private CEIXStsWaybillBtmoreHdrVOImpl voCEIXStsWaybillBtmoreHdrVOImpl;
    private CEIXStsWaybillBtmoreLinesVOImpl voCEIXStsWaybillBtmoreLinesVOImpl;
    
    public List<ViewRowImpl> load(String ftpCsvFileName, WSResultPVOImpl resultVo
                    ,CEIXStsWaybillBtmoreHdrVOImpl voCEIXStsWaybillBtmoreHdrVOImpl
                    ,CEIXStsWaybillBtmoreLinesVOImpl voCEIXStsWaybillBtmoreLinesVOImpl
                    ,String SFTP_WORKING_DIR
                ,String ARCHIVE_DIR
                                  ) {
        _logger.info(">>load");
        
        this.SFTP_WORKING_DIR=SFTP_WORKING_DIR;
        this.ARCHIVE_DIR=ARCHIVE_DIR;
        this.FILE_NAME=ftpCsvFileName;
        this.voCEIXStsWaybillBtmoreHdrVOImpl = voCEIXStsWaybillBtmoreHdrVOImpl;
        this.voCEIXStsWaybillBtmoreLinesVOImpl = voCEIXStsWaybillBtmoreLinesVOImpl;


        List<ViewRowImpl> lstResult = new ArrayList<ViewRowImpl>();

        try {
            SFTPCredentials sftpCredentials= new SFTPCredentials();
            
            Iterator<WaybillFileBean> csvBeanIterator = readCSVFileFromFTP(sftpCredentials);
            // Iterator<WaybillFileBean> csvBeanIterator =readCSVFileFromLocalMachine();
            if (rowsFoundInCSV(csvBeanIterator)) {
                _logger.log(Level.FINER, "CSV File found. Processing it.");

                //populate CSV data in voCEIXStsWaybillBtmoreHdrVOImpl.
                processCSVData(csvBeanIterator);
                //if rows found, save them in DBAAS
                persistRecords();
                //archive the file
                archiveTheFile(sftpCredentials);
                //prepare the success response
                prepareSuccessResponse(resultVo, lstResult);

            } else {
                _logger.log(Level.FINER, "No records found in CSV");
                
            }
                

        } catch (Exception e) {
            //rollback
            voCEIXStsWaybillBtmoreHdrVOImpl.getDBTransaction().rollback();
            voCEIXStsWaybillBtmoreLinesVOImpl.getDBTransaction().rollback();
            _logger.log(Level.FINER,"Rolledback transaction.");
            
            _logger.log(Level.SEVERE, "Error while : " + e.getMessage());
            if(e instanceof SftpException  ){
                SftpException sftpE = (SftpException)e;
                if(sftpE.id== ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    prepareFileNotFoundResponse(resultVo, lstResult);    
                }
                else
                prepareFailedResponse(resultVo, lstResult, e);
            }
            else
            prepareFailedResponse(resultVo, lstResult, e);
        }

        _logger.info("<<load");

        return lstResult;
    }

    /**Process the csv data and save it inside VO
        * @param WaybillFileBeanIterator
        */
       private  void processCSVData(Iterator<WaybillFileBean> WaybillFileBeanIterator
                                          
                                          ) throws Exception{
           _logger.log(Level.FINER,">>processCSVData");
           
           boolean headerProcessed=false;
           boolean headerRecordWasFound=false;
           CEIXStsWaybillBtmoreHdrVORowImpl headerRow=null;
           
            while (WaybillFileBeanIterator.hasNext()) {
                PROCESSED_COUNT++;
                WaybillFileBean waybillFileBean =
                    WaybillFileBeanIterator.next();
                
               // String train =WaybillFileBean.getTRAIN().trim();
                Date dt = ConversionUtil.stringToDate(waybillFileBean.getDumped_date()
                                                      ,LOAD_DT_CSV_DATE_FORMAT);
                
               
               //process the header record just for once, because all other lines
                //will have the same header information
               if(!headerProcessed) {
                   _logger.log(Level.FINER,"Processing Unit_Train_number, Dumped_date: "
                                            +waybillFileBean.getUnit_Train_number()
                                            +", "+dt );
                                      
                     
                    headerRow=  
                   getHeaderRecordByKeys(waybillFileBean.getUnit_Train_number()
                                          ,dt);
                   
                   //set a flag which can tell us later if header was already found
                   if(headerRow!=null) {
                       headerRecordWasFound=true;
                       _logger.log(Level.FINER,"Header record already found.");
                   }
                       
                   
                   //_logger.log(Level.FINER,"headerRecordWasFound: "+ headerRecordWasFound);
                   
                   headerRow=processHeaderRecord(headerRow,waybillFileBean);
                   headerProcessed=true;
                   
                   if(!headerRecordWasFound)
                   _logger.log(Level.FINER,"header record was not found. Will create new lines");
                   
               }
                
               processLineRecord(headerRow.getDeliveryHeaderId()
                                 ,headerRecordWasFound,waybillFileBean); 
                
//              CEIXStsWaybillBtmoreLinesVORowImpl stsRow = 
//               getSTSRailScheduleData(train
//                                      ,dt);
//                
//               String orderNumber = null;
//               if(stsRow!=null
//                  &&stsRow.getSalesOrderNumber()!=null
//                  &&!stsRow.getSalesOrderNumber().trim().equals(""))
//               orderNumber = stsRow.getSalesOrderNumber();
//                
//                if(row!=null) {
//                    _logger.log(Level.FINER,"Record already exists.");
//                    setAttributes(WaybillFileBean,row,orderNumber);
//                }
//                else {
//                    _logger.log(Level.FINER,"Record doesnt exists.");
//                    
//                    CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl= (CEIXOrdQualLabResltVORowImpl)voCEIXStsWaybillBtmoreHdrVOImpl.createRow();
//                    _logger.log(Level.FINER,WaybillFileBean.getTRAIN());
//                    try 
//                    {
//                        setAttributes(WaybillFileBean,ceixOrdQualLabResltVORowImpl,orderNumber);
//                        
//                        voCEIXStsWaybillBtmoreHdrVOImpl.insertRow(ceixOrdQualLabResltVORowImpl);
//                        
//                        //printVORows();
//                        
//                    } catch (SQLException e) {
//                        _logger.log(Level.SEVERE,"Error whle converting CSV data" +
//                            "to target data type: "+ e.getMessage());
//                                    
//                        throw e;
//                    } catch (ParseException e) {
//                        _logger.log(Level.SEVERE,"ParseException while parsing CSV File: "+ e.getMessage()
//                            );                     
//                        
//                        throw e;
//                    }
//                    
//                }
               
           }
           _logger.log(Level.FINER,"<<processCSVData");
       }
   
    private CEIXStsWaybillBtmoreHdrVORowImpl getHeaderRecordByKeys(String trainNum,
                                                                   Date dt) {
           _logger.log(Level.FINER,"Searching header row by train and load dt: "+ trainNum+", "+dt);
               
           boolean exists=false;
           CEIXStsWaybillBtmoreHdrVORowImpl row =null;
           voCEIXStsWaybillBtmoreHdrVOImpl.setNamedWhereClauseParam("trainNum", trainNum); 

           voCEIXStsWaybillBtmoreHdrVOImpl.setNamedWhereClauseParam("dumpDt", dt);
           
           voCEIXStsWaybillBtmoreHdrVOImpl.setApplyViewCriteriaName("FindByTrainAndLoadDtlDtVOCriteria");

           voCEIXStsWaybillBtmoreHdrVOImpl.executeQuery(); //executeVO with Criteria
           long rowCount = voCEIXStsWaybillBtmoreHdrVOImpl.getEstimatedRowCount();
           if(rowCount>0) {
               row = (CEIXStsWaybillBtmoreHdrVORowImpl)voCEIXStsWaybillBtmoreHdrVOImpl.next();
               _logger.log(Level.FINER,"Header row found. DeliveryHeaderId: "+row.getDeliveryHeaderId());
               exists=true;
           }
           else
               _logger.log(Level.FINER,"Header row not found.");
           
           return row;

    }
    
    private CEIXStsWaybillBtmoreHdrVORowImpl processHeaderRecord( CEIXStsWaybillBtmoreHdrVORowImpl row
                                      ,WaybillFileBean waybillFileBean) throws ParseException,
                                                                                                         SQLException {
        
        Date dt = ConversionUtil.stringToDate(waybillFileBean.getDumped_date()
                                              ,LOAD_DT_CSV_DATE_FORMAT);
        
        //check if a header record already exists by train number and load date
        //if record already exists, update the record, else create new row
        if(row!=null) {
            
            //setHeaderAttributes(row,waybillFileBean);
            _logger.log(Level.FINER,"existing header row id: "+ row.getDeliveryHeaderId());
        }
        else 
        {
            
            _logger.log(Level.FINER,"Creating new header row");

            row = (CEIXStsWaybillBtmoreHdrVORowImpl)
                voCEIXStsWaybillBtmoreHdrVOImpl.createRow();
            setHeaderAttributes(row,waybillFileBean);
            voCEIXStsWaybillBtmoreHdrVOImpl.insertRow(row);
            
            _logger.log(Level.FINER,"New header row delivery header id:"+ row.getDeliveryHeaderId());
        }
        
        return row;
        
    }
    private void processLineRecord(oracle.jbo.domain.Number deliveryHeaderId
                                   , boolean headerRecordWasFound
                                   ,WaybillFileBean waybillFileBean) throws Exception {
    
    _logger.log(Level.FINER,">>processLineRecord: deliveryHeaderId: "+ deliveryHeaderId);
    
      if(!headerRecordWasFound)  {
          //no header record found, so there wont be any lines as well
          createANewLineRecord(deliveryHeaderId,waybillFileBean);
      }
      else 
      {
        //header record found, so lines may or may not be there
        //check if line already exists, if found, update it else create a new line
        //search existing line by DELIVERY_HEADER_ID and CAR_NUMBER 
        String carNumber = waybillFileBean.getCar_number();
        CEIXStsWaybillBtmoreLinesVORowImpl lineRow 
            = getLineRecordByKey(deliveryHeaderId,carNumber);
          
          if(lineRow!=null) {
              //line record found
              _logger.log(Level.FINER,"line record found");

             //10:26 setLineAttributes(deliveryHeaderId,lineRow,waybillFileBean);
             //1026: IF LINE RECORD FOUND, DONT PROCESS THE FILE ANYMORE AND ROLLBACK ANY CHANGES MADE
             SFTPCredentials sftpCredentials= new SFTPCredentials();
             archiveTheFile(sftpCredentials);
             _logger.log(Level.FINER,"File moved to archive folder.");
             throw new Exception("Line record already found for existing Delivery Header Id. Skipping the load of the file.");              
          }
          else {
              _logger.log(Level.FINER,"line record not found");
             createANewLineRecord(deliveryHeaderId,waybillFileBean);
          }
      }
        
}

    private void createANewLineRecord(oracle.jbo.domain.Number deliveryHeaderId
                                      ,WaybillFileBean waybillFileBean) throws SQLException {
    
        //no line record found
        CEIXStsWaybillBtmoreLinesVORowImpl newRow 
            = (CEIXStsWaybillBtmoreLinesVORowImpl)
            voCEIXStsWaybillBtmoreLinesVOImpl.createRow();
        
        setLineAttributes(deliveryHeaderId,newRow,waybillFileBean);
        
        voCEIXStsWaybillBtmoreLinesVOImpl.insertRow(newRow);
        
    
    }

    private void setLineAttributes(oracle.jbo.domain.Number deliveryHeaderId
                                   ,CEIXStsWaybillBtmoreLinesVORowImpl newRow,
                                   WaybillFileBean waybillFileBean) throws SQLException {
        
        //delivery header id
        newRow.setDeliveryHeaderId(deliveryHeaderId);
        
        //Type
        newRow.setType(waybillFileBean.getType());
        //Load_Sequence
        newRow.setDumpSequence(ConversionUtil.stringToNumber(waybillFileBean.getLoad_Sequence()));
        //Car_Initial
        newRow.setCarPrefix(waybillFileBean.getCar_Initial());
        newRow.setNewCarPrefix(waybillFileBean.getCar_Initial());
        //Train Number
        newRow.setTrainNumber(waybillFileBean.getUnit_Train_number());
        //Car_number
        newRow.setCarNumber(waybillFileBean.getCar_number());
        //Weight
        String carWeight=waybillFileBean.getWeight();
        String uom= waybillFileBean.getUOM();
        String calculatedCarWeight = calculatedCarWt(uom,carWeight);
        newRow.setCarWeight(calculatedCarWeight);
        newRow.setWeightInTons(calculatedCarWeight);
        //UOM, we are converting POUND to TON
        newRow.setUom("TON");
        //Sales_order_number
        newRow.setSalesOrderNumber(waybillFileBean.getSales_order_number());
        //Rail_road
        newRow.setRailRoad(waybillFileBean.getRail_road());
        String subInv = waybillFileBean.getDumped_Location();
        newRow.setSubInventory(subInv);

    }
    
    
  
    private String calculatedCarWt(String uom, String carWeight) throws SQLException {
        
        //If UOM is TONS, then Weight=Car_weight. If UOM is Pound, then Weight*.0005 = Car_weight
        if(uom!=null&&!uom.trim().equals("")) {
            
            if(uom.trim().equalsIgnoreCase("TON"))
                return carWeight;
            else {
                oracle.jbo.domain.Number w = new oracle.jbo.domain.Number(carWeight);
                return w.multiply(new oracle.jbo.domain.Number(0.0005)).toString();
                
            }
        }
      
        return carWeight;
    }
    
    private CEIXStsWaybillBtmoreLinesVORowImpl getLineRecordByKey
    (oracle.jbo.domain.Number deliveryHeaderId,String carNumber) {
        
        
        _logger.log(Level.FINER,"Searching line row by deliveryHeaderId and car Num: "
                           + deliveryHeaderId+", "+carNumber);
            
        boolean exists=false;
        CEIXStsWaybillBtmoreLinesVORowImpl row =null;
        voCEIXStsWaybillBtmoreLinesVOImpl.setNamedWhereClauseParam("deliveryHeaderId", deliveryHeaderId); 

        voCEIXStsWaybillBtmoreLinesVOImpl.setNamedWhereClauseParam("carNumber", carNumber);
        
        voCEIXStsWaybillBtmoreLinesVOImpl.setApplyViewCriteriaName("FindByDeliveryHeaderIdAndCarNumVOCriteria");

        voCEIXStsWaybillBtmoreLinesVOImpl.executeQuery(); //executeVO with Criteria
        long rowCount = voCEIXStsWaybillBtmoreLinesVOImpl.getEstimatedRowCount();
        if(rowCount>0) {
            _logger.log(Level.FINER,"Line row found.");
            row = (CEIXStsWaybillBtmoreLinesVORowImpl)voCEIXStsWaybillBtmoreLinesVOImpl.next();
            exists=true;
        }
        else
            _logger.log(Level.FINER,"Line row not found.");
        
        return row;
        
    }
    
    private void setHeaderAttributes(CEIXStsWaybillBtmoreHdrVORowImpl headerRow,
                                     WaybillFileBean waybillFileBean) throws SQLException,
                                                                             ParseException {
    
        //Unit_Train_number     Dumped_date     Dumped_Location Freight_Cost
        
        headerRow.setTrainNumber(waybillFileBean.getUnit_Train_number());
        headerRow.setDumpedDate(ConversionUtil.stringToDate(waybillFileBean.getDumped_date()
                                                            ,LOAD_DT_CSV_DATE_FORMAT));
        headerRow.setDumpLocation(waybillFileBean.getDumped_Location());
        headerRow.setFreightCost(ConversionUtil.stringToNumber(waybillFileBean.getFreight_Cost()));
    
    }
    
    boolean rowsFoundInCSV(Iterator<WaybillFileBean> csvBeanIterator) {
     
        if(!csvBeanIterator.hasNext()) {
            return false;
        }
        return true;
    }
    
    private void prepareSuccessResponse(WSResultPVOImpl resultVo,
                                        List<ViewRowImpl> lstResult) {

        WSResultPVORowImpl resultRow =
            (WSResultPVORowImpl)resultVo.createRow();
        resultRow.setStatus("SUCCESS");
        resultRow.setMessage("File processed successfully. Number of records processed: " +
                             PROCESSED_COUNT);
        lstResult.add(resultRow);

    }

    private void prepareFailedResponse(WSResultPVOImpl resultVo,
                                       List<ViewRowImpl> lstResult,
                                       Exception e) {

        WSResultPVORowImpl resultRow =
            (WSResultPVORowImpl)resultVo.createRow();

        String stackTrace = "";
        stackTrace = stackTraceToString(e);
        if (e != null) {
            stackTrace = stackTraceToString(e);
            if (stackTrace != null && stackTrace.length() > 2500)
                stackTrace = stackTrace.substring(0, 2500);

        }
        resultRow.setStatus("FAILED");
        resultRow.setMessage("Processing Error. " + stackTrace +
                             PROCESSED_COUNT);
        lstResult.add(resultRow);

    }

    private Iterator<WaybillFileBean> readCSVFileFromFTP(com.ceix.common.util.SFTPCredentials sftpCredentials) throws Exception {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        InputStream in_stream = null;
        SFTPConnectionUtil connectionUtil = new SFTPConnectionUtil();
        try {
            _logger.log(Level.FINER,
                        "Opening SFTP connection and trying to read CSV File.");

            /*String proxyhost = System.getProperty("https.proxyHost");
            String proxyport = System.getProperty("https.proxyPort");
            ProxyHTTP proxy =
                new ProxyHTTP(proxyhost, Integer.parseInt(proxyport));*/
            
            JSch jsch = new JSch();
            
            
            session = jsch.getSession(sftpCredentials.getSFTP_USER()
                                      , sftpCredentials.getSFTP_HOST()
                                      , sftpCredentials.getSFTP_PORT());
            session.setPassword(sftpCredentials.getSFTP_PASS());
            
            //session.setProxy(proxy);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            
            channelSftp=(ChannelSftp)channel;
 
            in_stream=connectionUtil.openInputStream(channelSftp, SFTP_WORKING_DIR, FILE_NAME);
            

            //channelSftp.cd(SFTP_WORKING_DIR);

            //in_stream = channelSftp.get(FILE_NAME);

//            ByteArrayOutputStream bao = new ByteArrayOutputStream();
//            int bytesRead = 0;
//            byte[] buff = new byte[8000];
//            while ((bytesRead = in_stream.read(buff)) != -1) {
//                bao.write(buff, 0, bytesRead);
//            }
//            byte[] data = bao.toByteArray();
//
//            ByteArrayInputStream bin = new ByteArrayInputStream(data);
            ByteArrayInputStream bin = connectionUtil.getByteArrayFromInputStream(in_stream);

            /*Iterator<WaybillFileBean> WaybillFileBeanIterator=
                createLabReportBeanIterator(in_stream);*/
            Iterator<WaybillFileBean> WaybillFileBeanIterator =
                createCSVBeanIterator(bin);

            _logger.log(Level.FINER, "File has been read successfully.");

            return WaybillFileBeanIterator;


        } catch (JSchException e) {
            _logger.log(Level.FINER,
                        "JSchException occurred during reading file from SFTP server due to " +
                        e.getMessage());
            throw e;

        } catch (SftpException e) {
            _logger.log(Level.FINER,
                        "SftpException occurred during reading file from SFTP server due to " +
                        e.getMessage());
            throw e;
        } finally {
            if (in_stream != null)
                in_stream.close();
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
            
        }


    }

    private Iterator<WaybillFileBean> readCSVFileFromLocalMachine() throws FileNotFoundException {

        String FILE_NAME2 = "D:/" + FILE_NAME;
        InputStream inputStream = new FileInputStream(FILE_NAME2);
        Iterator<WaybillFileBean> WaybillFileBeanIterator =
            createCSVBeanIterator(inputStream);
        return WaybillFileBeanIterator;

    }

    private Iterator<WaybillFileBean> createCSVBeanIterator(InputStream inputStream) {

        CsvToBean csv = new CsvToBean();
        CsvToBean<WaybillFileBean> csvToBean =
            new CsvToBeanBuilder(new InputStreamReader(inputStream)).withType(WaybillFileBean.class).withIgnoreLeadingWhiteSpace(true).build();

        Iterator<WaybillFileBean> WaybillFileBeanIterator =
            csvToBean.iterator();

        return WaybillFileBeanIterator;

        
    }

    private void persistRecords() {


        //RowSetIterator iterator = voCEIXStsWaybillBtmoreHdrVOImpl.createRowSetIterator(null);
        //iterator.reset();
        /*if(!iterator.hasNext()) {
                _logger.log(Level.FINER,"No rows found in iterator.");
                return;
            }*/
        //iterator.reset();
        _logger.log(Level.FINER, "Committing records");
        //printVORows();
        voCEIXStsWaybillBtmoreHdrVOImpl.getDBTransaction().commit();
        voCEIXStsWaybillBtmoreLinesVOImpl.getDBTransaction().commit();
            
        _logger.log(Level.FINER, "Commit successful");
        //int rowCount = voCEIXStsWaybillBtmoreHdrVOImpl.getRowCount();
        //_logger.log(Level.FINER,"Records saved successfully: "+ rowCount);
        //iterator.closeRowSetIterator();


    }

    private static String stackTraceToString(Throwable e) {

        Throwable rootcause = ExceptionUtils.getRootCause(e);
        if (rootcause == null) {
            rootcause = e;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(rootcause.getMessage());
        sb.append("\n");
        for (StackTraceElement element : rootcause.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();


    }


    private void archiveTheFile(com.ceix.common.util.SFTPCredentials sftpCredentials) throws JSchException, SftpException,
                                                                                             Exception {
        
        ArchiveFTPFileUtil arUtil = new ArchiveFTPFileUtil();
        
        arUtil.archiveFileWithTimestamp(SFTP_WORKING_DIR, FILE_NAME, ARCHIVE_DIR,sftpCredentials);


    }

    private void prepareFileNotFoundResponse(WSResultPVOImpl resultVo,
                                               List<ViewRowImpl> lstResult) {
        
        WSResultPVORowImpl resultRow =
            (WSResultPVORowImpl)resultVo.createRow();
        resultRow.setStatus("SUCCESS");
        resultRow.setMessage("No file found in remote directory.");
        lstResult.add(resultRow);
        
    }
}
