package com.ceix.waybill_purch_coal_import.loader;


import com.ceix.common.util.ArchiveFTPFileUtil;
import com.ceix.common.util.ConversionUtil;
import com.ceix.common.util.SFTPConnectionUtil;
import com.ceix.common.util.SFTPCredentials;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsDeliveryHeaderVOImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsDeliveryHeaderVORowImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsDeliveryLinesVOImpl;
import com.ceix.waybill_purch_coal_import.model.views.CEIXStsDeliveryLinesVORowImpl;
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

import oracle.jbo.server.ViewRowImpl;

import org.apache.commons.lang3.exception.ExceptionUtils;


/**This class will handle all the steps needed to read the Shipment
 * Coal Car Details file from ftp
 * and load it in dbaas.
 * @author Romesh Soni
 */

public class ShipmentCoalCarCSVFileLoader {

    private static ADFLogger _logger =
        ADFLogger.createADFLogger(ShipmentCoalCarCSVFileLoader.class);

    
    private String SFTP_WORKING_DIR =
       null;
    private  String FILE_NAME=null;

    private int PROCESSED_COUNT = 0;
    private String ARCHIVE_DIR;
    
    private static final String LOAD_DT_CSV_DATE_FORMAT = "MM/dd/yyyy";
    private CEIXStsDeliveryHeaderVOImpl voCEIXStsDeliveryHeaderVOImpl;
    private CEIXStsDeliveryLinesVOImpl voCEIXStsDeliveryLinesVOImpl;
    
    public List<ViewRowImpl> load(String ftpCsvFileName, WSResultPVOImpl resultVo
                    ,CEIXStsDeliveryHeaderVOImpl voCEIXStsDeliveryHeaderVOImpl
                    ,CEIXStsDeliveryLinesVOImpl voCEIXStsDeliveryLinesVOImpl
                    ,String SFTP_WORKING_DIR
                ,String ARCHIVE_DIR
                                  ) {
        _logger.info(">>load");
        
        this.SFTP_WORKING_DIR=SFTP_WORKING_DIR;
        this.ARCHIVE_DIR=ARCHIVE_DIR;
        this.FILE_NAME=ftpCsvFileName;
        this.voCEIXStsDeliveryHeaderVOImpl = voCEIXStsDeliveryHeaderVOImpl;
        this.voCEIXStsDeliveryLinesVOImpl = voCEIXStsDeliveryLinesVOImpl;


        List<ViewRowImpl> lstResult = new ArrayList<ViewRowImpl>();

        try {
            SFTPCredentials sftpCredentials= new SFTPCredentials();
            
            Iterator<ShipmentCoalCarBean> csvBeanIterator = readCSVFileFromFTP(sftpCredentials);
            // Iterator<ShipmentCoalCarBean> csvBeanIterator =readCSVFileFromLocalMachine();
            if (rowsFoundInCSV(csvBeanIterator)) {
                _logger.log(Level.FINER, "CSV File found. Processing it.");

                //populate CSV data in voCEIXStsDeliveryHeaderVOImpl.
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
            voCEIXStsDeliveryHeaderVOImpl.getDBTransaction().rollback();
            voCEIXStsDeliveryLinesVOImpl.getDBTransaction().rollback();
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
        * @param shipmentIterator
        */
       private  void processCSVData(Iterator<ShipmentCoalCarBean> shipmentIterator
                                          
                                          ) throws Exception{
           _logger.log(Level.FINER,">>processCSVData");
           
           boolean headerProcessed=false;
           boolean headerRecordWasFound=false;
           CEIXStsDeliveryHeaderVORowImpl headerRow=null;
           
            while (shipmentIterator.hasNext()) {
                PROCESSED_COUNT++;
                ShipmentCoalCarBean shipmentCoalCarBean =
                    shipmentIterator.next();
                
               // String train =ShipmentCoalCarBean.getTRAIN().trim();
               String shipDt ="\""+shipmentCoalCarBean.getShipped_date()+"\"";
                
               
               //process the header record just for once, because all other lines
                //will have the same header information
               if(!headerProcessed) {
                   _logger.log(Level.FINER,"Processing Unit_Train_number: "
                                            +shipmentCoalCarBean.getUnit_Train_number()
                                            );
                                      
                     
                    headerRow=  
                   getHeaderRecordByKeys(shipmentCoalCarBean.getUnit_Train_number()
                                          ,shipDt);
                   
                   //set a flag which can tell us later if header was already found
                   if(headerRow!=null) {
                       headerRecordWasFound=true;
                       _logger.log(Level.FINER,"Header record already found.");
                   }
                   
                  // _logger.log(Level.FINER,"headerRecordWasFound: "+ headerRecordWasFound);
                   
                   headerRow=processHeaderRecord(headerRow,shipmentCoalCarBean);
                   headerProcessed=true;
                   
                   if(!headerRecordWasFound)
                   _logger.log(Level.FINER,"header record was not found. Will create new lines");
                   
               }
                
               processLineRecord(headerRow.getDeliveryHeaderId()
                                 ,headerRecordWasFound,shipmentCoalCarBean); 
                
//            
               
           }
           _logger.log(Level.FINER,"<<processCSVData");
       }
   
    private CEIXStsDeliveryHeaderVORowImpl getHeaderRecordByKeys(String trainNum,
                                                                   String shipDt) {
           _logger.log(Level.FINER,"Searching header row by train and Shipped_date: "+ trainNum+", "+shipDt);
               
           boolean exists=false;
           CEIXStsDeliveryHeaderVORowImpl row =null;
           voCEIXStsDeliveryHeaderVOImpl.setNamedWhereClauseParam("trainNum", trainNum); 

           voCEIXStsDeliveryHeaderVOImpl.setNamedWhereClauseParam("shipdDate", shipDt);
           
           voCEIXStsDeliveryHeaderVOImpl.setApplyViewCriteriaName("FindByTrainAndLoadShipppedDtlDtVOCriteria");

           voCEIXStsDeliveryHeaderVOImpl.executeQuery(); //executeVO with Criteria
           long rowCount = voCEIXStsDeliveryHeaderVOImpl.getEstimatedRowCount();
           if(rowCount>0) {
               
               row = (CEIXStsDeliveryHeaderVORowImpl)voCEIXStsDeliveryHeaderVOImpl.next();
               _logger.log(Level.FINER,"Header row found. DeliveryHeaderId: "+row.getDeliveryHeaderId());
               exists=true;
           }
           else
               _logger.log(Level.FINER,"Header row not found.");
           
           return row;

    }
    
    private CEIXStsDeliveryHeaderVORowImpl processHeaderRecord( CEIXStsDeliveryHeaderVORowImpl row
                                      ,ShipmentCoalCarBean shipmentCoalCarBean) throws ParseException,
                                                                                                         SQLException {
        
        /*Date dt = ConversionUtil.stringToDate(shipmentCoalCarBean.getDumped_date()
                                              ,LOAD_DT_CSV_DATE_FORMAT);*/
        
//        check if a header record already exists by train number and load date
//        if record already exists, update the record, else create new row
        if(row!=null) {
            
            //setHeaderAttributes(row,shipmentCoalCarBean);
            _logger.log(Level.FINER,"existing header row id: "+ row.getDeliveryHeaderId());
        }
        else 
        {
            
            _logger.log(Level.FINER,"Creating new header row");

            row = (CEIXStsDeliveryHeaderVORowImpl)
                voCEIXStsDeliveryHeaderVOImpl.createRow();
            setHeaderAttributes(row,shipmentCoalCarBean);
            voCEIXStsDeliveryHeaderVOImpl.insertRow(row);
            
            _logger.log(Level.FINER,"New header row delivery header id:"+ row.getDeliveryHeaderId());
        }
        
        return row;
        
    }
    private void processLineRecord(oracle.jbo.domain.Number deliveryHeaderId
                                   , boolean headerRecordWasFound
                                   ,ShipmentCoalCarBean shipmentCoalCarBean) throws Exception {
    
    _logger.log(Level.FINER,">>processLineRecord: deliveryHeaderId: "+ deliveryHeaderId);
    
      if(!headerRecordWasFound)  {
          //no header record found, so there wont be any lines as well
          createANewLineRecord(deliveryHeaderId,shipmentCoalCarBean);
      }
      else 
      {
        //header record found, so lines may or may not be there
        //check if line already exists, if found, update it else create a new line
        //search existing line by DELIVERY_HEADER_ID and CAR_NUMBER 
        String carNumber = shipmentCoalCarBean.getCar_number();
        CEIXStsDeliveryLinesVORowImpl lineRow 
            = getLineRecordByKey(deliveryHeaderId,carNumber);
          
          if(lineRow!=null) {
              //line record found
              _logger.log(Level.FINER,"line record found. Throwing exception and skipping the file processing.");

             //1026: setLineAttributes(deliveryHeaderId,lineRow,shipmentCoalCarBean);
             //1026: IF LINE RECORD FOUND, DONT PROCESS THE FILE ANYMORE AND ROLLBACK ANY CHANGES MADE
             SFTPCredentials sftpCredentials= new SFTPCredentials();
             archiveTheFile(sftpCredentials);
             _logger.log(Level.FINER,"File moved to archive folder.");
             throw new Exception("Line record already found for existing Delivery Header Id. Skipping the load of the file.");
          }
          else 
          {
              _logger.log(Level.FINER,"Line record not found. Creating new line.");
             createANewLineRecord(deliveryHeaderId,shipmentCoalCarBean);
          }
      }
        
}

    private void createANewLineRecord(oracle.jbo.domain.Number deliveryHeaderId
                                      ,ShipmentCoalCarBean shipmentCoalCarBean) throws SQLException {
    
        //no line record found
        CEIXStsDeliveryLinesVORowImpl newRow 
            = (CEIXStsDeliveryLinesVORowImpl)
            voCEIXStsDeliveryLinesVOImpl.createRow();
        
        setLineAttributes(deliveryHeaderId,newRow,shipmentCoalCarBean);
        
        voCEIXStsDeliveryLinesVOImpl.insertRow(newRow);
        
    
    }

    private void setLineAttributes(oracle.jbo.domain.Number deliveryHeaderId
                                   ,CEIXStsDeliveryLinesVORowImpl newRow,
                                   ShipmentCoalCarBean shipmentCoalCarBean) throws SQLException {
        
        //delivery header id
        newRow.setDeliveryHeaderId(deliveryHeaderId);
        //Train Number
        newRow.setUnitTrainNumber(shipmentCoalCarBean.getUnit_Train_number());
        //Load_Sequence
        newRow.setSequenceNum(shipmentCoalCarBean.getLoad_Sequence());
        //Car_Initial
        newRow.setCarPrefix(shipmentCoalCarBean.getCar_Initial());
        //newRow.setNewCarPrefix(shipmentCoalCarBean.getCar_Initial());
        //Car_number
        newRow.setCarId(shipmentCoalCarBean.getCar_number());
        //Weight
        newRow.setWeightInTons(ConversionUtil.stringToNumber(shipmentCoalCarBean.getWeight()));
        //Sales_order_number
        newRow.setSalesOrderNumber(shipmentCoalCarBean.getSales_order_number());
        //TODO: Rail_road
        newRow.setRailRoad(shipmentCoalCarBean.getRail_road());
        if (shipmentCoalCarBean.getItem_Number() != null) {
                    newRow.setItemNumber(shipmentCoalCarBean.getItem_Number());
                }
                if (shipmentCoalCarBean.getDestination() != null) {
                    newRow.setDestination(shipmentCoalCarBean.getDestination().toUpperCase());
                }
                if (shipmentCoalCarBean.getLoad_Origin() != null) {
                    newRow.setLoadOrigin(shipmentCoalCarBean.getLoad_Origin().toUpperCase());
                }

    }
    
    
  
//    private String calculatedCarWt(String uom, String carWeight) throws SQLException {
//        
//        //If UOM is TONS, then Weight=Car_weight. If UOM is Pound, then Weight*.0005 = Car_weight
//        if(uom!=null&&!uom.trim().equals("")) {
//            
//            if(uom.trim().equalsIgnoreCase("TON"))
//                return carWeight;
//            else {
//                oracle.jbo.domain.Number w = new oracle.jbo.domain.Number(carWeight);
//                return w.multiply(new oracle.jbo.domain.Number(0.0005)).toString();
//                
//            }
//        }
//      
//        return carWeight;
//    }
    
    private CEIXStsDeliveryLinesVORowImpl getLineRecordByKey
    (oracle.jbo.domain.Number deliveryHeaderId,String carNumber) {
        
        
        _logger.log(Level.FINER,"Searching line row by deliveryHeaderId and car Num: "
                           + deliveryHeaderId+", "+carNumber);
            
        boolean exists=false;
        CEIXStsDeliveryLinesVORowImpl row =null;
        voCEIXStsDeliveryLinesVOImpl.setNamedWhereClauseParam("deliveryHeaderId", deliveryHeaderId); 

        voCEIXStsDeliveryLinesVOImpl.setNamedWhereClauseParam("carNumber", carNumber);
        
        voCEIXStsDeliveryLinesVOImpl.setApplyViewCriteriaName("FindByDeliveryHeaderIdAndCarNumVOCriteria");

        voCEIXStsDeliveryLinesVOImpl.executeQuery(); //executeVO with Criteria
        long rowCount = voCEIXStsDeliveryLinesVOImpl.getEstimatedRowCount();
        if(rowCount>0) {
            _logger.log(Level.FINER,"Line row found.");
            row = (CEIXStsDeliveryLinesVORowImpl)voCEIXStsDeliveryLinesVOImpl.next();
            exists=true;
        }
        else
            _logger.log(Level.FINER,"Line row not found.");
        
        return row;
        
    }
    
    private void setHeaderAttributes(CEIXStsDeliveryHeaderVORowImpl headerRow,
                                     ShipmentCoalCarBean shipmentCoalCarBean) throws SQLException,
                                                                             ParseException {
    
        headerRow.setUnitTrainNumber(shipmentCoalCarBean.getUnit_Train_number());
        String shipDt ="\""+shipmentCoalCarBean.getShipped_date()+"\"";
        //TODO:
        headerRow.setLoadDate1(shipDt);
        headerRow.setLoadDate2(shipDt);
        headerRow.setLoadDate3(shipDt);
        headerRow.setLoadDate4(shipDt);
        headerRow.setClassCol(shipmentCoalCarBean.getClass_Name());//9469- Adding CLASS NAME to STS Shipment Template - Added by Manasa Yalamarthy on Sept 8th,2023 
     
    }
    
    boolean rowsFoundInCSV(Iterator<ShipmentCoalCarBean> csvBeanIterator) {
     
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

    private Iterator<ShipmentCoalCarBean> readCSVFileFromFTP(com.ceix.common.util.SFTPCredentials sftpCredentials) throws Exception {
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

            /*Iterator<ShipmentCoalCarBean> shipmentIterator=
                createLabReportBeanIterator(in_stream);*/
            Iterator<ShipmentCoalCarBean> shipmentIterator =
                createCSVBeanIterator(bin);

            _logger.log(Level.FINER, "File has been read successfully.");

            return shipmentIterator;


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

    private Iterator<ShipmentCoalCarBean> readCSVFileFromLocalMachine() throws FileNotFoundException {

        String FILE_NAME2 = "D:/" + FILE_NAME;
        InputStream inputStream = new FileInputStream(FILE_NAME2);
        Iterator<ShipmentCoalCarBean> shipmentIterator =
            createCSVBeanIterator(inputStream);
        return shipmentIterator;

    }

    private Iterator<ShipmentCoalCarBean> createCSVBeanIterator(InputStream inputStream) {

        CsvToBean csv = new CsvToBean();
        CsvToBean<ShipmentCoalCarBean> csvToBean =
            new CsvToBeanBuilder(new InputStreamReader(inputStream)).withType(ShipmentCoalCarBean.class).withIgnoreLeadingWhiteSpace(true).build();

        Iterator<ShipmentCoalCarBean> shipmentIterator =
            csvToBean.iterator();

        return shipmentIterator;

        
    }

    private void persistRecords() {


        //RowSetIterator iterator = voCEIXStsDeliveryHeaderVOImpl.createRowSetIterator(null);
        //iterator.reset();
        /*if(!iterator.hasNext()) {
                _logger.log(Level.FINER,"No rows found in iterator.");
                return;
            }*/
        //iterator.reset();
        _logger.log(Level.FINER, "Committing records");
        //printVORows();
        voCEIXStsDeliveryHeaderVOImpl.getDBTransaction().commit();
        voCEIXStsDeliveryLinesVOImpl.getDBTransaction().commit();
            
        _logger.log(Level.FINER, "Commit successful");
        //int rowCount = voCEIXStsDeliveryHeaderVOImpl.getRowCount();
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
