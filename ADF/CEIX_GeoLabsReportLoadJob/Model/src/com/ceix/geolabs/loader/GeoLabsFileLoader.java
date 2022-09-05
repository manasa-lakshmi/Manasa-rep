package com.ceix.geolabs.loader;


import com.ceix.common.util.ArchiveFTPFileUtil;
import com.ceix.common.util.SFTPCredentials;
import com.ceix.geolabs.loader.util.ConversionUtil;
import com.ceix.geolabs.model.view.CEIXOrdQualLabResltVOImpl;
import com.ceix.geolabs.model.view.CEIXOrdQualLabResltVORowImpl;
import com.ceix.geolabs.model.view.CEIXStsDeliveryLinesVOImpl;
import com.ceix.geolabs.model.view.CEIXStsRailSchedulesVOImpl;
import com.ceix.geolabs.model.view.CEIXStsRailSchedulesVORowImpl;
import com.ceix.geolabs.model.view.CEIXStsDeliveryLinesVORowImpl;
import com.ceix.geolabs.model.view.WSResultPVOImpl;
import com.ceix.geolabs.model.view.WSResultPVORowImpl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.SQLException;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;

import org.apache.commons.lang3.exception.ExceptionUtils;
 

/**This class will handle all the steps needed to read the geo labs file from ftp
 * and load it in dbaas.
 * @author Romesh Soni
 */
public class GeoLabsFileLoader {

    private static ADFLogger _logger = 
                ADFLogger.createADFLogger(GeoLabsFileLoader.class); 
    
    //private static final String INTEGRATION_USER = "oicintegrationuser";
    /*private static final String SFTP_HOST = "cnxftp.consolenergy.com";
    private static final int SFTP_PORT = 22;
    private static final String SFTP_USER = "CEIX_OracleERP";
    private static final String SFTP_PASS = "0r@cleERP";*/
    private static final String SFTP_ROOT_DIR="CEIX_OracleERP/INTF/Inbound/OM/GeoLabs/";
    private static final String SFTP_WORKING_DIR =
        SFTP_ROOT_DIR+"In/";
    //private static final String FILE_NAME = "geo_lab_report.csv";
    private static final String FILE_NAME_PREFIX = "BLY_";
    private static final String ARCHIVE_DIR=SFTP_ROOT_DIR+"Archive/";
    
    private static final String SFTP_ROOT_DIR_ITM="CEIX_OracleERP/INTF/Inbound/OM/ItmannLabs/";
  //  private static final String SFTP_ROOT_DIR_ITM="/home/ceixoicuser/CEIX_OracleERP/INTF/Inbound/OM/ItmannLabs/";
    private static final String SFTP_WORKING_DIR_ITM =
        SFTP_ROOT_DIR_ITM+"In/";
    //private static final String FILE_NAME = "geo_lab_report.csv";
    private static final String FILE_NAME_PREFIX_ITM = "ITM_";
    private static final String ARCHIVE_DIR_ITM=SFTP_ROOT_DIR_ITM+"Archive/";
    
    //you will process one file in this run. This file name will help you to archive the file once it is processed.
    private String fileNameBeingProcessed=null;
                                               
    private int PROCESSED_COUNT=0;
    
    private static final String RPT_SAMPLE_CSV_DATE_FORMAT="MM/dd/yyyy";


    private  CEIXOrdQualLabResltVOImpl vo;
    private CEIXStsRailSchedulesVOImpl stsVO;
    private CEIXStsDeliveryLinesVOImpl stsDelVO;
    
    
    public GeoLabsFileLoader() {
        super();
    }
    
    
    public List<ViewRowImpl> load(WSResultPVOImpl resultVo
                                  ,CEIXOrdQualLabResltVOImpl vo
                                  ,CEIXStsRailSchedulesVOImpl stsVO) {
        
        _logger.info(">>load");
        
        
        List<ViewRowImpl> lstResult= new ArrayList<ViewRowImpl>();
        this.vo=vo;
        this.stsVO = stsVO;

        try 
        {
             //Iterator<GeoLabReportBean> geoLabReportBeanIterator =readCSVFileFromLocalMachine();
            
            SFTPCredentials sftpCredentials= new SFTPCredentials();
            Iterator<GeoLabReportBean> geoLabReportBeanIterator 
                =readCSVFileFromFTP(sftpCredentials);
           


            if(rowsFoundInCSV(geoLabReportBeanIterator)) {
                _logger.log(Level.FINER,"CSV File found. Processing it.");
                _logger.log(Level.SEVERE,"at Line 121 CSV File found. Processing it.");


                //populate CSV data in VO.
                processCSVData(geoLabReportBeanIterator);
                //if rows found, save them in DBAAS
                persistRecords();
                //archive the file
                archiveTheFile(sftpCredentials);
                //prepare the success response
                prepareSuccessResponse(resultVo,lstResult);
                
            }
            else
                _logger.log(Level.FINER,"No records found in CSV");
            
            
            
            
        } catch (Exception e) {
            _logger.log(Level.SEVERE,"Error while : "+ e.getMessage()); 
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
    
    public List<ViewRowImpl> loadItmann(WSResultPVOImpl resultVo
                                  ,CEIXOrdQualLabResltVOImpl vo
                                  ,CEIXStsDeliveryLinesVOImpl stsVO) {
        
        _logger.info(">>loadItmann");
        _logger.log(Level.SEVERE," at 168 inside loadItmann");
        //int rowCount = vo.getRowCount();
        
        List<ViewRowImpl> lstResult= new ArrayList<ViewRowImpl>();
        this.vo=vo;
        this.stsDelVO = stsVO;

        try 
        {
             //Iterator<GeoLabReportBean> geoLabReportBeanIterator =readCSVFileFromLocalMachine();
            
            SFTPCredentials sftpCredentials= new SFTPCredentials();
            _logger.log(Level.SEVERE,"Before Read");
            Iterator<GeoLabReportBean> geoLabReportBeanIterator 
                =readCSVFileFromFTPItmann(sftpCredentials);
            _logger.log(Level.SEVERE,"at 174 after read file.");
           


            if(rowsFoundInCSV(geoLabReportBeanIterator)) {
                _logger.log(Level.SEVERE,"at 179  geoLabReportBeanIterator."+ geoLabReportBeanIterator);
                _logger.log(Level.FINER,"CSV File found. Processing it.");
                _logger.log(Level.SEVERE,"at 179 CSV File found. Processing it.");
                _logger.log(Level.SEVERE,"at 181 Before processCSVDataItmann.");

                //populate CSV data in VO.
            //    processCSVData(geoLabReportBeanIterator);
               processCSVDataItmann(geoLabReportBeanIterator);
               
                _logger.log(Level.SEVERE,"at 187 After processCSVDataItmann.");
                _logger.log(Level.SEVERE,"at 188 Before persistRecords.");
                //if rows found, save them in DBAAS
                persistRecords();
                _logger.log(Level.SEVERE,"at 191 After persistRecords.");
                //archive the file
              //  archiveTheFile(sftpCredentials);
              _logger.log(Level.SEVERE,"at 194 Before archiveTheFileItmann.");
               archiveTheFileItmann(sftpCredentials);
                _logger.log(Level.SEVERE,"at 196 After  archiveTheFileItmann.");
                //prepare the success response
                _logger.log(Level.SEVERE,"at 184 Before prepareSuccessResponse.");
                prepareSuccessResponse(resultVo,lstResult);
                _logger.log(Level.SEVERE,"at 200 After  prepareSuccessResponse.");
                
            }
            else
                _logger.log(Level.FINER,"No records found in CSV");
            
            
            
            
        } catch (Exception e) {
            _logger.log(Level.SEVERE,"Error while : "+ e.getMessage()); 
            if(e instanceof SftpException  ){
                
                _logger.log(Level.SEVERE,"at 213 Inside     if(e instanceof SftpException  )");
                SftpException sftpE = (SftpException)e;
                if(sftpE.id== ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    prepareFileNotFoundResponse(resultVo, lstResult);    
                }
                else
                {
                    _logger.log(Level.SEVERE,"at 220 Inside   else  if(sftpE.id== ChannelSftp.SSH_FX_NO_SUCH_FILE)");
                prepareFailedResponse(resultVo, lstResult, e);
                }
            }
            else{
                _logger.log(Level.SEVERE,"at 225 Inside   else   if(e instanceof SftpException  )");
            prepareFailedResponse(resultVo, lstResult, e);
            }
            
        } 
        
        _logger.info("<<loadItmann");
        
        return lstResult;
    }
    
    private void prepareFileNotFoundResponse(WSResultPVOImpl resultVo,
                                               List<ViewRowImpl> lstResult) {
        
        WSResultPVORowImpl resultRow =
            (WSResultPVORowImpl)resultVo.createRow();
        resultRow.setStatus("SUCCESS");
        resultRow.setMessage("No file found in remote directory TESTING!!!!!!!!.");
        lstResult.add(resultRow);
        
    }
    
    private void archiveTheFile(SFTPCredentials sftpCredentials)
    throws JSchException, SftpException,
                                                                        Exception {
                
        ArchiveFTPFileUtil arUtil = new ArchiveFTPFileUtil();
        arUtil.archiveFileWithTimestamp(SFTP_WORKING_DIR, fileNameBeingProcessed, ARCHIVE_DIR
                                        ,sftpCredentials);
        
    }
    private void archiveTheFileItmann(SFTPCredentials sftpCredentials)
    throws JSchException, SftpException,
                                                                        Exception {
                
        ArchiveFTPFileUtil arUtil = new ArchiveFTPFileUtil();
        _logger.log(Level.SEVERE,"at 261 inside archiveTheFileItmann");
        arUtil.archiveFileWithTimestamp(SFTP_WORKING_DIR_ITM, fileNameBeingProcessed, ARCHIVE_DIR_ITM
                                        ,sftpCredentials);
        
    }
    private void prepareSuccessResponse(WSResultPVOImpl resultVo
                                        ,List<ViewRowImpl> lstResult) {
        
        WSResultPVORowImpl resultRow = (WSResultPVORowImpl)resultVo.createRow();
        _logger.log(Level.SEVERE,"at 270 inside prepareSuccessResponse");
        resultRow.setStatus("SUCCESS");
        resultRow.setMessage("File processed successfully. Number of records processed: "
                             +PROCESSED_COUNT);
        _logger.log(Level.SEVERE,"at 274 inside prepareSuccessResponse,  File processed successfully. Number of records processed: " + 
         +PROCESSED_COUNT);
        lstResult.add(resultRow);

    }
    private void prepareFailedResponse(WSResultPVOImpl resultVo
                                        ,List<ViewRowImpl> lstResult, Exception e) {
        
        WSResultPVORowImpl resultRow = (WSResultPVORowImpl)resultVo.createRow();
        _logger.log(Level.SEVERE,"at 283 inside prepareFailedResponse");
        String stackTrace = "";
        stackTrace = stackTraceToString(e);
        /*if(e!=null) {
            stackTrace = stackTraceToString(e);
            if(stackTrace!=null&&stackTrace.length()>2500)
            stackTrace=stackTrace.substring(0,2500);
            
        }*/
        resultRow.setStatus("FAILED");
        resultRow.setMessage("Processing Error. "+ stackTrace
                             +PROCESSED_COUNT);
        _logger.log(Level.SEVERE,"at 295, Processing Error. "+ stackTrace
                             +PROCESSED_COUNT);
        lstResult.add(resultRow);

    }

  
    
     private  Iterator<GeoLabReportBean> readCSVFileFromFTP(SFTPCredentials sftpCredentials)
     throws Exception {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        InputStream in_stream =null;

        try {
            _logger.log(Level.FINER,"Opening SFTP connection and trying to read CSV File.");
            _logger.log(Level.SEVERE," at 319 Opening SFTP connection and trying to read CSV File.");
            
            /*String proxyhost = System.getProperty("https.proxyHost");
            String proxyport= System.getProperty("https.proxyPort");
            ProxyHTTP  proxy = new ProxyHTTP(proxyhost,Integer.parseInt(proxyport));*/
           
            
            
            JSch jsch = new JSch();

            
            session = jsch.getSession(sftpCredentials.getSFTP_USER()
                                      , sftpCredentials.getSFTP_HOST()
                                      , sftpCredentials.getSFTP_PORT());
            session.setPassword(sftpCredentials.getSFTP_PASS());

            //session.setProxy(proxy);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setTimeout(60000);
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.cd(SFTP_WORKING_DIR);

            //in_stream = channelSftp.get(FILE_NAME);

            Vector filelist = channelSftp.ls(SFTP_WORKING_DIR);
            _logger.log(Level.FINER,"total files found: "+ filelist.size());
                        for(int i=0; i<filelist.size();i++){
                        
                            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
                            String fileName = entry.getFilename();
                            //skip linux files
                            if(fileName.startsWith("."))
                                continue;
                            _logger.log(Level.FINER,"fileName: "+ fileName);
                          
                            if(fileName.startsWith(FILE_NAME_PREFIX)) {
                                
                                  channelSftp.cd(SFTP_WORKING_DIR);
                                  in_stream = channelSftp.get(fileName);
                                  fileNameBeingProcessed=fileName;
                                  //break the loop to process one file in one run
                                  break;
                              }
                        }
            
            if(in_stream!=null) {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                int bytesRead = 0;
                byte[] buff = new byte[8000];
                while ((bytesRead = in_stream.read(buff)) != -1) {
                                        bao.write(buff, 0, bytesRead);
                    }
                byte[] data = bao.toByteArray();

                ByteArrayInputStream bin = new ByteArrayInputStream(data);

                /*Iterator<GeoLabReportBean> geoLabReportBeanIterator=
                createLabReportBeanIterator(in_stream);*/
                Iterator<GeoLabReportBean> geoLabReportBeanIterator=
                            createLabReportBeanIterator(bin);            
                
                _logger.log(Level.FINER,"File has been read successfully.");
                
                return geoLabReportBeanIterator;
                
            }
            else {
                //file not found
                SftpException e2 = new SftpException(ChannelSftp.SSH_FX_NO_SUCH_FILE,"No file found");
                throw e2;
            }


        }  catch (JSchException e) {
            _logger.log(Level.FINER,"JSchException occurred during reading file from SFTP server due to " +
                               e.getMessage());
            throw e;
            
        } catch (SftpException e) {
            _logger.log(Level.FINER,"SftpException occurred during reading file from SFTP server due to " +
                               e.getMessage());
            throw e;
        } finally {
            if(in_stream!=null) 
            in_stream.close();        
            if(channel!=null)
            channel.disconnect();
            if(session!=null)
            session.disconnect(); 
        }

        
    }

    private  Iterator<GeoLabReportBean> readCSVFileFromFTPItmann(SFTPCredentials sftpCredentials)
    throws Exception {
       Session session = null;
       Channel channel = null;
       ChannelSftp channelSftp = null;
       InputStream in_stream =null;

       try {
           _logger.log(Level.FINER,"Opening SFTP connection and trying to read CSV File.");
           _logger.log(Level.SEVERE," at 397 ");
           _logger.log(Level.SEVERE,"Opening SFTP connection and trying to read CSV File.");
           
           /*String proxyhost = System.getProperty("https.proxyHost");
           String proxyport= System.getProperty("https.proxyPort");
           ProxyHTTP  proxy = new ProxyHTTP(proxyhost,Integer.parseInt(proxyport));*/
          
           
           
           JSch jsch = new JSch();

           
           session = jsch.getSession(sftpCredentials.getSFTP_USER()
                                     , sftpCredentials.getSFTP_HOST()
                                     , sftpCredentials.getSFTP_PORT());
           session.setPassword(sftpCredentials.getSFTP_PASS());
           
           _logger.log(Level.SEVERE," at 415 "
                                    +"sftpCredentials.getSFTP_USER():"+sftpCredentials.getSFTP_USER()
                                    + "sftpCredentials.getSFTP_HOST():"+sftpCredentials.getSFTP_HOST()
                                    +" sftpCredentials.getSFTP_PORT():"+ sftpCredentials.getSFTP_PORT()
                                    + "sftpCredentials.getSFTP_PASS():"+sftpCredentials.getSFTP_PASS());

           //session.setProxy(proxy);
           java.util.Properties config = new java.util.Properties();
           config.put("StrictHostKeyChecking", "no");
           session.setTimeout(60000);
           session.setConfig(config);
           session.connect();
           _logger.log(Level.SEVERE," at 419 ");
           channel = session.openChannel("sftp");
           channel.connect();
           channelSftp = (ChannelSftp)channel;
           //channelSftp.cd(SFTP_WORKING_DIR);

           //in_stream = channelSftp.get(FILE_NAME);

      //     Vector filelist = channelSftp.ls(SFTP_ROOT_DIR_ITM);
           Vector filelist = channelSftp.ls(SFTP_WORKING_DIR_ITM); 
           _logger.log(Level.SEVERE," at 428 "+"SFTP_ROOT_DIR_ITM:"+ SFTP_ROOT_DIR_ITM);
           _logger.log(Level.SEVERE," at 428 "+"SFTP_WORKING_DIR_ITM:"+ SFTP_WORKING_DIR_ITM);
           _logger.log(Level.FINER,"total files found: "+ filelist.size());
                       for(int i=0; i<filelist.size();i++){
                       
                           ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
                           String fileName = entry.getFilename();
                           //skip linux files
                           if(fileName.startsWith("."))
                               continue;
                           _logger.log(Level.FINER,"fileName: "+ fileName);
                           _logger.log(Level.SEVERE," at 438 "+ "fileName: "+ fileName);
                         
                           if(fileName.startsWith(FILE_NAME_PREFIX_ITM)) {
                               
                                 _logger.log(Level.SEVERE," at 442"+"FILE_NAME_PREFIX_ITM:"+FILE_NAME_PREFIX_ITM);
                        //         channelSftp.cd(SFTP_ROOT_DIR_ITM);
                                 channelSftp.cd(SFTP_WORKING_DIR_ITM);
                                 in_stream = channelSftp.get(fileName);
                                 _logger.log(Level.SEVERE," in_stream:"+in_stream);
                                 fileNameBeingProcessed=fileName;
                                 _logger.log(Level.SEVERE," fileNameBeingProcessed:"+fileNameBeingProcessed);
                                 //break the loop to process one file in one run
                                 break;
                             }
                       }
           
           if(in_stream!=null) {
               ByteArrayOutputStream bao = new ByteArrayOutputStream();
               int bytesRead = 0;
               byte[] buff = new byte[8000];
               while ((bytesRead = in_stream.read(buff)) != -1) {
                                       bao.write(buff, 0, bytesRead);
                   }
               byte[] data = bao.toByteArray();

               ByteArrayInputStream bin = new ByteArrayInputStream(data);

               /*Iterator<GeoLabReportBean> geoLabReportBeanIterator=
               createLabReportBeanIterator(in_stream);*/
               Iterator<GeoLabReportBean> geoLabReportBeanIterator=
                           createLabReportBeanIterator(bin);            
               
               _logger.log(Level.FINER,"File has been read successfully.");
               _logger.log(Level.SEVERE,"at 468 File has been read successfully.");
               
               return geoLabReportBeanIterator;
               
           }
           else {
               //file not found
               SftpException e2 = new SftpException(ChannelSftp.SSH_FX_NO_SUCH_FILE,"No file found");
               throw e2;
           }


       }  catch (JSchException e) {
           _logger.log(Level.FINER,"JSchException occurred during reading file from SFTP server due to " +
                              e.getMessage());
           _logger.log(Level.SEVERE,"JSchException occurred during reading file from SFTP server due to " +
                              e.getMessage());
           throw e;
           
       } catch (SftpException e) {
           _logger.log(Level.FINER,"SftpException occurred during reading file from SFTP server due to " +
                              e.getMessage());
           _logger.log(Level.SEVERE,"SftpException occurred during reading file from SFTP server due to " +
                              e.getMessage());
           throw e;
       } finally {
           if(in_stream!=null) 
           in_stream.close();        
           if(channel!=null)
           channel.disconnect();
           if(session!=null)
           session.disconnect(); 
       }

       
    }


    private  Iterator<GeoLabReportBean> readCSVFileFromLocalMachine() throws FileNotFoundException {
 
        String FILE_NAME2 = "D:/"+ fileNameBeingProcessed;
        InputStream inputStream = new FileInputStream(FILE_NAME2);
        Iterator<GeoLabReportBean> geoLabReportBeanIterator=
        createLabReportBeanIterator(inputStream);
        return geoLabReportBeanIterator;
     
    }
    
    private Iterator<GeoLabReportBean> createLabReportBeanIterator(InputStream inputStream) {
        CsvToBean csv = new CsvToBean();
        _logger.log(Level.SEVERE,"at 553 inside createLabReportBeanIterator");
        CsvToBean<GeoLabReportBean> csvToBean =
            new CsvToBeanBuilder(new InputStreamReader (inputStream)).withType(GeoLabReportBean.class).withIgnoreLeadingWhiteSpace(true).build();
        _logger.log(Level.SEVERE,"  csvToBean conversion result"+ csvToBean);
        _logger.log(Level.SEVERE,"at 556 after  csvToBean conversion");
        
        _logger.log(Level.SEVERE,"at 558 populating  geoLabReportBeanIterator");
        
        Iterator<GeoLabReportBean> geoLabReportBeanIterator =
            csvToBean.iterator();

        _logger.log(Level.SEVERE,"at 563 after populating  geoLabReportBeanIterator:"+geoLabReportBeanIterator);
        return geoLabReportBeanIterator;


    }

  boolean rowsFoundInCSV(Iterator<GeoLabReportBean> geoLabReportBeanIterator) {
   
      _logger.log(Level.SEVERE," at 571 inside rowsFoundInCSV");
      if(!geoLabReportBeanIterator.hasNext()) {
          _logger.log(Level.SEVERE," at 573  No rows geoLabReportBeanIterator");
          return false;
      }
      return true;
  }

    /**Process the csv data and save it inside VO
     * @param geoLabReportBeanIterator
     */
    private  void processCSVData(Iterator<GeoLabReportBean> geoLabReportBeanIterator
                                       
                                       ) throws SQLException,ParseException {
        _logger.log(Level.FINER,">>processCSVData");
         while (geoLabReportBeanIterator.hasNext()) {
             PROCESSED_COUNT++;
             GeoLabReportBean geoLabReportBean =
                 geoLabReportBeanIterator.next();
             
             String train =geoLabReportBean.getTRAIN().trim();
             Date dt = ConversionUtil.stringToDate(geoLabReportBean.getDATESAMP(),RPT_SAMPLE_CSV_DATE_FORMAT);
            String labNumber = geoLabReportBean.getRPTSAMPNO();
            labNumber=ConversionUtil.trimCSVString(labNumber);
             
             _logger.log(Level.FINER, "Processing Train, Sample Date: "+train+", "+dt );
             //if record (train+sampled date combination) already exist then update, else insert
            /*Key key = new Key(new Object[] {geoLabReportBean.getTRAIN() });
            Row[] row = (Row[]
                )this.vo.findByKey(key,1);*/
            
            CEIXOrdQualLabResltVORowImpl row =  
            getLabResultRecordById(train
                                   ,dt,labNumber);
            CEIXOrdQualLabResltVORowImpl rowLab =  
            getLabResultRecordByNum(dt,labNumber);
           CEIXStsRailSchedulesVORowImpl stsRow = 
            getSTSRailScheduleData(train
                                   ,dt);
             
            String orderNumber = null;
            if(stsRow!=null
               &&stsRow.getSalesOrderNumber()!=null
               &&!stsRow.getSalesOrderNumber().trim().equals(""))
            orderNumber = stsRow.getSalesOrderNumber();
             //if(orderNumber == null){
               //  orderNumber = "1234";
             //}
             if(row!=null || rowLab !=null) {
                 _logger.log(Level.FINER,"Record already exists.");
                 //row.setLastUpdatedBy(INTEGRATION_USER);
                 //System.out.println("I am in IF");
                 if(row!=null){
                    setAttributes(geoLabReportBean,row,orderNumber);
                 }else{
                     setAttributes(geoLabReportBean,rowLab,orderNumber);
                 }
             }
             else {
                 _logger.log(Level.FINER,"Record doesnt exists.");
                 
                 //System.out.println("I am in ELSE");
                 CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl= (CEIXOrdQualLabResltVORowImpl)vo.createRow();
                 _logger.log(Level.FINER,geoLabReportBean.getTRAIN());
                 try 
                 {
                     setAttributes(geoLabReportBean,ceixOrdQualLabResltVORowImpl,orderNumber);
                     //ceixOrdQualLabResltVORowImpl.setCreatedBy(INTEGRATION_USER);
                     vo.insertRow(ceixOrdQualLabResltVORowImpl);
                     
                     //printVORows();
                     
                 } catch (SQLException e) {
                     _logger.log(Level.SEVERE,"Error whle converting CSV data" +
                         "to target data type: "+ e.getMessage());
                                 
                     throw e;
                 } catch (ParseException e) {
                     _logger.log(Level.SEVERE,"ParseException while parsing CSV File: "+ e.getMessage()
                         );                     
                     
                     throw e;
                 }
                 
             }
            
        }
        _logger.log(Level.FINER,"<<processCSVData");
    }
    private  void processCSVDataItmann(Iterator<GeoLabReportBean> geoLabReportBeanIterator
                                       
                                       ) throws SQLException,ParseException {
        _logger.log(Level.FINER,">>processCSVDataItmann");
        _logger.log(Level.SEVERE,"at 651 >>processCSVDataItmann");
         while (geoLabReportBeanIterator.hasNext()) {
            _logger.log(Level.SEVERE,"at 666 >>Inside processCSVDataItmann,  while (geoLabReportBeanIterator.hasNext())");
             PROCESSED_COUNT++;
             GeoLabReportBean geoLabReportBean =
                 geoLabReportBeanIterator.next();
            _logger.log(Level.SEVERE,"at 670 >>before getting truck");
            _logger.log(Level.SEVERE,"at 671 >>before getting truck,geoLabReportBean value is"+geoLabReportBean);
            String train =geoLabReportBean.getTRAIN().trim();
       //     String truck =geoLabReportBean.getTRUCK().trim();
          //  _logger.log(Level.SEVERE,"at 659 >>truck"+truck);
            _logger.log(Level.SEVERE,"at 659 >>truck"+train);
             Date dt = ConversionUtil.stringToDate(geoLabReportBean.getDATESAMP(),RPT_SAMPLE_CSV_DATE_FORMAT);
            _logger.log(Level.SEVERE,"at 659 >>date"+dt);
            String labNumber = geoLabReportBean.getRPTSAMPNO();
            labNumber=ConversionUtil.trimCSVString(labNumber);
            _logger.log(Level.SEVERE,"at 664 >>labNumber"+labNumber);
             
            _logger.log(Level.FINER, "Processing Train, Sample Date: "+train+", "+dt );
        //    _logger.log(Level.FINER, "Processing Truck, Sample Date: "+truck+", "+dt );
             //if record (train+sampled date combination) already exist then update, else insert
            /*Key key = new Key(new Object[] {geoLabReportBean.getTRAIN() });
            Row[] row = (Row[]
                )this.vo.findByKey(key,1);*/
            
            CEIXOrdQualLabResltVORowImpl row =  
            getLabResultRecordById(train
                                   ,dt,labNumber);
       /*     CEIXOrdQualLabResltVORowImpl row =  
            getLabResultRecordById(truck
                                   ,dt,labNumber);*/
            CEIXOrdQualLabResltVORowImpl rowLab =  
            getLabResultRecordByNum(dt,labNumber);
            CEIXStsDeliveryLinesVORowImpl stsRow=
            getSTSDeliveryLinesData(train
                                   ,dt);
      /*    CEIXStsRailSchedulesVORowImpl stsRow = 
            getSTSRailScheduleData(truck
                                   ,dt);
             */
            String orderNumber = null;
            if(stsRow!=null
               &&stsRow.getSalesOrderNumber()!=null
               &&!stsRow.getSalesOrderNumber().trim().equals(""))
            {
                _logger.log(Level.SEVERE,"at 693 stsRow is not null.");
            orderNumber = stsRow.getSalesOrderNumber();
            }
             //if(orderNumber == null){
               //  orderNumber = "1234";
             //}
             if(row!=null || rowLab !=null) {
                 _logger.log(Level.FINER,"Record already exists.");
                 _logger.log(Level.SEVERE,"Record already exists.");
                 //row.setLastUpdatedBy(INTEGRATION_USER);
                 //System.out.println("I am in IF");
                 if(row!=null){
                     _logger.log(Level.SEVERE,"at 702 Before setAttributesItmann.");
              //     setAttributes(geoLabReportBean,row,orderNumber);
                     setAttributesItmann(geoLabReportBean,row,orderNumber);
                     _logger.log(Level.SEVERE,"at 705 After setAttributesItmann.");
                 }else{
                  //   setAttributes(geoLabReportBean,rowLab,orderNumber);
                  _logger.log(Level.SEVERE,"at 708 Before setAttributesItmann.");
                     setAttributesItmann(geoLabReportBean,rowLab,orderNumber);
                     _logger.log(Level.SEVERE,"at 710 After setAttributesItmann.");
                 }
             }
             else {
                 _logger.log(Level.FINER,"Record doesnt exists.");
                 _logger.log(Level.SEVERE,"at 718 Record doesnt exists.");
                  
                 //System.out.println("I am in ELSE");
                 _logger.log(Level.SEVERE,"at 721 About to create CEIXOrdQualLabResltVORowImpl rows.");
                 CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl= (CEIXOrdQualLabResltVORowImpl)vo.createRow();
                 _logger.log(Level.FINER,geoLabReportBean.getTRAIN());
                 _logger.log(Level.SEVERE,geoLabReportBean.getTRAIN());
          //       _logger.log(Level.FINER,geoLabReportBean.getTRUCK());
                 _logger.log(Level.SEVERE,"at 725 geoLabReportBean.getTRAIN():"+geoLabReportBean.getTRAIN());
                 try 
                 {
                     _logger.log(Level.SEVERE,"at 728 inside try to call setAttributes");
                  //   setAttributes(geoLabReportBean,ceixOrdQualLabResltVORowImpl,orderNumber);               
                     setAttributesItmann(geoLabReportBean,ceixOrdQualLabResltVORowImpl,orderNumber);
                     //ceixOrdQualLabResltVORowImpl.setCreatedBy(INTEGRATION_USER);
                     _logger.log(Level.SEVERE,"at 731 Inserts new row");
                     vo.insertRow(ceixOrdQualLabResltVORowImpl);
                     _logger.log(Level.SEVERE,"at 733 after Inserts new row");
                     
                     //printVORows();
                     
                 } catch (SQLException e) {
                     _logger.log(Level.SEVERE,"Error whle converting CSV data" +
                         "to target data type: "+ e.getMessage());
                                 
                     throw e;
                 } catch (ParseException e) {
                     _logger.log(Level.SEVERE,"ParseException while parsing CSV File: "+ e.getMessage()
                         );                     
                     
                     throw e;
                 }
                 
             }
            
        }
        _logger.log(Level.FINER,"<<processCSVDataItmann");
        _logger.log(Level.SEVERE," at 767<<processCSVDataItmann");
    }


    public CEIXOrdQualLabResltVORowImpl getLabResultRecordById
    (String trainNo,oracle.jbo.domain.Date reportDate,String labNumber) {
            _logger.log(Level.FINER,"Checking if record alreaady exists.");
            
            boolean exists=false;
            CEIXOrdQualLabResltVORowImpl row =null;
            vo.setNamedWhereClauseParam("trainNo", trainNo); 

            vo.setNamedWhereClauseParam("rptSampleDt", reportDate);
            vo.setNamedWhereClauseParam("labNumber", labNumber);
            //System.out.println("labNumber:"+labNumber);
                
            vo.setApplyViewCriteriaName("FindByTrainAndSamplDtVOCriteria");
            vo.executeQuery(); //executeVO with Criteria
            long rowCount = vo.getEstimatedRowCount();
            if(rowCount>0) {
                row = (CEIXOrdQualLabResltVORowImpl)vo.next();
                exists=true;
            }
                
            //System.out.println(exists);
            //remove the criteria
            //vo.removeViewCriteria("FindByTrainAndSamplDtVOCriteria");
            //vo.executeQuery();    
            
            return row;
        }
    public CEIXOrdQualLabResltVORowImpl getLabResultRecordByNum
    (oracle.jbo.domain.Date reportDate,String labNumber) {
            _logger.log(Level.FINER,"Checking if record alreaady exists.");
            
            boolean exists=false;
            CEIXOrdQualLabResltVORowImpl row =null; 
            vo.setNamedWhereClauseParam("rptSampleDt", reportDate);
            vo.setNamedWhereClauseParam("labNumber", labNumber);
            //System.out.println("labNumber:"+labNumber);
            vo.setApplyViewCriteriaName("FindByLabNumAndSamplDtVOCriteria");
            vo.executeQuery(); //executeVO with Criteria
            long rowCount = vo.getEstimatedRowCount();
            if(rowCount>0) {
                row = (CEIXOrdQualLabResltVORowImpl)vo.next();
                exists=true;
                //System.out.println("Labnumber exists");
            }
                
            //System.out.println(exists);
            //remove the criteria
            //vo.removeViewCriteria("FindByTrainAndSamplDtVOCriteria");
            //vo.executeQuery();    
            
            return row;
        }
    public CEIXStsRailSchedulesVORowImpl getSTSRailScheduleData
    (String trainNo,oracle.jbo.domain.Date reportDate) {
            _logger.log(Level.FINER,"Checking if STS Rail schedule record exists.");
            
            boolean exists=false;
            CEIXStsRailSchedulesVORowImpl row =null;
            stsVO.setNamedWhereClauseParam("trainNum", trainNo); 

            stsVO.setNamedWhereClauseParam("loadDt", reportDate);
            
            stsVO.setApplyViewCriteriaName("SearchByTrainAndLoadDateVOCriteria");

            stsVO.executeQuery(); 
            long rowCount = stsVO.getEstimatedRowCount();
            if(rowCount>0) {
                row = (CEIXStsRailSchedulesVORowImpl)stsVO.next();
                exists=true;
            }
             
            return row;
        }
    public CEIXStsDeliveryLinesVORowImpl getSTSDeliveryLinesData
    (String trainNo,oracle.jbo.domain.Date reportDate) {
            _logger.log(Level.FINER,"Checking if STS Delivery Lines  record exists.");
            _logger.log(Level.SEVERE,"Checking if STS Delivery Lines  record exists.");
            
            boolean exists=false;
        //    CEIXStsRailSchedulesVORowImpl row =null;
            CEIXStsDeliveryLinesVORowImpl row =null;
            stsDelVO.setNamedWhereClauseParam("trainnum", trainNo); 

            stsDelVO.setNamedWhereClauseParam("loaddt", reportDate);
            
            stsDelVO.setApplyViewCriteriaName("SearchByTruckAndLoadDateVOCriteria");

            stsDelVO.executeQuery(); 
            long rowCount = stsDelVO.getEstimatedRowCount();
            if(rowCount>0) {
                row = (CEIXStsDeliveryLinesVORowImpl)stsDelVO.next();
                exists=true;
            }
             
            return row;
        }
    
    private  void setAttributes(
                                      GeoLabReportBean geoLabReportBean
                                      ,CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl
                                      ,String orderNumber
                                      ) throws SQLException,
                                                                                ParseException {
        
        
        
        _logger.log(Level.SEVERE,"at 839 Inside setattributes");
        //order number got from sts rail schedules table.
        ceixOrdQualLabResltVORowImpl.setOrderNumber(orderNumber);
        ceixOrdQualLabResltVORowImpl.setCars(ConversionUtil.stringToNumber(geoLabReportBean.getCARS()));
    
        ceixOrdQualLabResltVORowImpl.setTrain(ConversionUtil.trimCSVString(geoLabReportBean.getTRAIN()));
        ceixOrdQualLabResltVORowImpl.setDatesamp(ConversionUtil.stringToDate(geoLabReportBean.getDATESAMP(), RPT_SAMPLE_CSV_DATE_FORMAT)  );
        ceixOrdQualLabResltVORowImpl.setYymm(ConversionUtil.trimCSVString(geoLabReportBean.getYYMM()));
        ceixOrdQualLabResltVORowImpl.setCars(ConversionUtil.stringToNumber(geoLabReportBean.getCARS()));
        
        ceixOrdQualLabResltVORowImpl.setRptsampno(ConversionUtil.trimCSVString(geoLabReportBean.getRPTSAMPNO()));
        ceixOrdQualLabResltVORowImpl.setSampmethod(ConversionUtil.trimCSVString(geoLabReportBean.getSAMPMETHOD()));
        ceixOrdQualLabResltVORowImpl.setFsi(ConversionUtil.stringToNumber(geoLabReportBean.getFSI()));
        ceixOrdQualLabResltVORowImpl.setTm(ConversionUtil.stringToNumber(geoLabReportBean.getTM()));
        ceixOrdQualLabResltVORowImpl.setAshAsr(ConversionUtil.stringToNumber(geoLabReportBean.getASH_ASR()));
        ceixOrdQualLabResltVORowImpl.setAshDry(ConversionUtil.stringToNumber(geoLabReportBean.getASH_DRY()));
        ceixOrdQualLabResltVORowImpl.setVolAsr(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_ASR()));
        ceixOrdQualLabResltVORowImpl.setVolDry(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_DRY()));
        ceixOrdQualLabResltVORowImpl.setVolDaf(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_DAF()));
        ceixOrdQualLabResltVORowImpl.setSulAsr(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_ASR()));
        ceixOrdQualLabResltVORowImpl.setSulDry(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_DRY()));
        ceixOrdQualLabResltVORowImpl.setSulDaf(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_DAF()));
        ceixOrdQualLabResltVORowImpl.setFcAsr(ConversionUtil.stringToNumber(geoLabReportBean.getFC_ASR()));
        ceixOrdQualLabResltVORowImpl.setFcDry(ConversionUtil.stringToNumber(geoLabReportBean.getFC_DRY()));
        ceixOrdQualLabResltVORowImpl.setFcDaf(ConversionUtil.stringToNumber(geoLabReportBean.getFC_DAF()));
        ceixOrdQualLabResltVORowImpl.setBtuAsr(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_ASR()));
        ceixOrdQualLabResltVORowImpl.setBtuDry(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_DRY()));
        ceixOrdQualLabResltVORowImpl.setBtuDaf(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_DAF()));

        //ceixOrdQualLabResltVORowImpl.setCreationdate(geoLabReportBean.getCREATIONDATE());
        //ceixOrdQualLabResltVORowImpl.setLastupdatedate(geoLabReportBean.getLASTUPDATEDATE());
        //ceixOrdQualLabResltVORowImpl.setCreatedby(geoLabReportBean.getCREATEDBY());
        //ceixOrdQualLabResltVORowImpl.setLastupdatedby(geoLabReportBean.getLASTUPDATEDBY());
        ceixOrdQualLabResltVORowImpl.setAftiRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTI_RED()));
        ceixOrdQualLabResltVORowImpl.setAftsRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTS_RED()));
        ceixOrdQualLabResltVORowImpl.setAfthRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTH_RED()));
        ceixOrdQualLabResltVORowImpl.setAftfRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTF_RED()));
        ceixOrdQualLabResltVORowImpl.setChlorine(ConversionUtil.stringToNumber(geoLabReportBean.getCHLORINE()));
        ceixOrdQualLabResltVORowImpl.setMercury(ConversionUtil.stringToNumber(geoLabReportBean.getMERCURY()));
        ceixOrdQualLabResltVORowImpl.setMinelocation("Bailey");
        ceixOrdQualLabResltVORowImpl.setLbss(ConversionUtil.stringToNumber(geoLabReportBean.getLBSS()));
        ceixOrdQualLabResltVORowImpl.setLbsso2(ConversionUtil.stringToNumber(geoLabReportBean.getLBSSO2()));
        ceixOrdQualLabResltVORowImpl.setSampleWt(ConversionUtil.trimCSVString(geoLabReportBean.getSAMPLEWT()));
        _logger.log(Level.SEVERE,"at 882 Done with  setattributes");
    }
    private  void setAttributesItmann(
                                      GeoLabReportBean geoLabReportBean
                                      ,CEIXOrdQualLabResltVORowImpl ceixOrdQualLabResltVORowImpl
                                      ,String orderNumber
                                      ) throws SQLException,
                                                                                ParseException {
        
        
        
        
        //order number got from sts rail schedules table.
        _logger.log(Level.SEVERE,"at 886 Inside  setAttributesItmann.");
        ceixOrdQualLabResltVORowImpl.setOrderNumber(orderNumber);
       // ceixOrdQualLabResltVORowImpl.setCars(ConversionUtil.stringToNumber(geoLabReportBean.getCARS()));
    
         ceixOrdQualLabResltVORowImpl.setTrain(ConversionUtil.trimCSVString(geoLabReportBean.getTRAIN()));
    //    ceixOrdQualLabResltVORowImpl.setTruck(ConversionUtil.trimCSVString(geoLabReportBean.getTRUCK()));
        ceixOrdQualLabResltVORowImpl.setDatesamp(ConversionUtil.stringToDate(geoLabReportBean.getDATESAMP(), RPT_SAMPLE_CSV_DATE_FORMAT)  );
        ceixOrdQualLabResltVORowImpl.setYymm(ConversionUtil.trimCSVString(geoLabReportBean.getYYMM()));
    //    ceixOrdQualLabResltVORowImpl.setCars(ConversionUtil.stringToNumber(geoLabReportBean.getCARS()));
        
        ceixOrdQualLabResltVORowImpl.setRptsampno(ConversionUtil.trimCSVString(geoLabReportBean.getRPTSAMPNO()));
        ceixOrdQualLabResltVORowImpl.setSampmethod(ConversionUtil.trimCSVString(geoLabReportBean.getSAMPMETHOD()));
        ceixOrdQualLabResltVORowImpl.setFsi(ConversionUtil.stringToNumber(geoLabReportBean.getFSI()));
        ceixOrdQualLabResltVORowImpl.setTm(ConversionUtil.stringToNumber(geoLabReportBean.getTM()));
        ceixOrdQualLabResltVORowImpl.setAshAsr(ConversionUtil.stringToNumber(geoLabReportBean.getASH_ASR()));
        ceixOrdQualLabResltVORowImpl.setAshDry(ConversionUtil.stringToNumber(geoLabReportBean.getASH_DRY()));
        ceixOrdQualLabResltVORowImpl.setVolAsr(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_ASR()));
        ceixOrdQualLabResltVORowImpl.setVolDry(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_DRY()));
        ceixOrdQualLabResltVORowImpl.setVolDaf(ConversionUtil.stringToNumber(geoLabReportBean.getVOL_DAF()));
        ceixOrdQualLabResltVORowImpl.setSulAsr(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_ASR()));
        ceixOrdQualLabResltVORowImpl.setSulDry(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_DRY()));
        ceixOrdQualLabResltVORowImpl.setSulDaf(ConversionUtil.stringToNumber(geoLabReportBean.getSUL_DAF()));
        ceixOrdQualLabResltVORowImpl.setFcAsr(ConversionUtil.stringToNumber(geoLabReportBean.getFC_ASR()));
        ceixOrdQualLabResltVORowImpl.setFcDry(ConversionUtil.stringToNumber(geoLabReportBean.getFC_DRY()));
        ceixOrdQualLabResltVORowImpl.setFcDaf(ConversionUtil.stringToNumber(geoLabReportBean.getFC_DAF()));
        ceixOrdQualLabResltVORowImpl.setBtuAsr(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_ASR()));
        ceixOrdQualLabResltVORowImpl.setBtuDry(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_DRY()));
        ceixOrdQualLabResltVORowImpl.setBtuDaf(ConversionUtil.stringToNumber(geoLabReportBean.getBTU_DAF()));

        //ceixOrdQualLabResltVORowImpl.setCreationdate(geoLabReportBean.getCREATIONDATE());
        //ceixOrdQualLabResltVORowImpl.setLastupdatedate(geoLabReportBean.getLASTUPDATEDATE());
        //ceixOrdQualLabResltVORowImpl.setCreatedby(geoLabReportBean.getCREATEDBY());
        //ceixOrdQualLabResltVORowImpl.setLastupdatedby(geoLabReportBean.getLASTUPDATEDBY());
        ceixOrdQualLabResltVORowImpl.setAftiRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTI_RED()));
        ceixOrdQualLabResltVORowImpl.setAftsRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTS_RED()));
        ceixOrdQualLabResltVORowImpl.setAfthRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTH_RED()));
        ceixOrdQualLabResltVORowImpl.setAftfRed(ConversionUtil.stringToNumber(geoLabReportBean.getAFTF_RED()));
        ceixOrdQualLabResltVORowImpl.setChlorine(ConversionUtil.stringToNumber(geoLabReportBean.getCHLORINE()));
        ceixOrdQualLabResltVORowImpl.setMercury(ConversionUtil.stringToNumber(geoLabReportBean.getMERCURY()));
        
        ceixOrdQualLabResltVORowImpl.setLbss(ConversionUtil.stringToNumber(geoLabReportBean.getLBSS()));
        ceixOrdQualLabResltVORowImpl.setLbsso2(ConversionUtil.stringToNumber(geoLabReportBean.getLBSSO2()));
        ceixOrdQualLabResltVORowImpl.setSampleWt(ConversionUtil.trimCSVString(geoLabReportBean.getSAMPLEWT()));
        ceixOrdQualLabResltVORowImpl.setTonnage(ConversionUtil.trimCSVString(geoLabReportBean.getTonnage()));
        ceixOrdQualLabResltVORowImpl.setPerrecovery(ConversionUtil.stringToNumber(geoLabReportBean.getPER_RECOVERY()));
      //  ceixOrdQualLabResltVORowImpl.setPerRecovery(ConversionUtil.trimCSVString(geoLabReportBean.getPER_RECOVERY()));
        ceixOrdQualLabResltVORowImpl.setMinelocation("Itmann");
        //geoLabReportBean.getMINELOCATION()));                                                                                                              
        ceixOrdQualLabResltVORowImpl.setRommoisture(ConversionUtil.stringToNumber(geoLabReportBean.getROM_MOISTURE()));
        ceixOrdQualLabResltVORowImpl.setRawash(ConversionUtil.stringToNumber(geoLabReportBean.getRAWASH()));
        ceixOrdQualLabResltVORowImpl.setRawTons(ConversionUtil.stringToNumber(geoLabReportBean.getRAW_TONS()));
        ceixOrdQualLabResltVORowImpl.setCleanTons(ConversionUtil.stringToNumber(geoLabReportBean.getCLEAN_TONS()));
        ceixOrdQualLabResltVORowImpl.setOxidation(ConversionUtil.stringToNumber(geoLabReportBean.getOXIDATION()));
        _logger.log(Level.SEVERE,"at 935 Done with  setAttributesItmann.");
                                   
    }


    /**Save the records in database.
     */
    private void persistRecords() {
        
        
        //RowSetIterator iterator = vo.createRowSetIterator(null);
        //iterator.reset();
        /*if(!iterator.hasNext()) {
            _logger.log(Level.FINER,"No rows found in iterator.");
            return;
        }*/
        //iterator.reset();
        _logger.log(Level.FINER,"Committing records");
        _logger.log(Level.SEVERE,"at 962 Committing records");
        //printVORows();
        vo.getDBTransaction().commit();
        _logger.log(Level.FINER,"Commit successful");
        _logger.log(Level.SEVERE," at 966 Commit successful");
        //int rowCount = vo.getRowCount();
        //_logger.log(Level.FINER,"Records saved successfully: "+ rowCount);
        //iterator.closeRowSetIterator();
        

        
    }

    /**VERY EXPEENSIVE CALL. ONLY CALL THIS FUNCTION ON DEV MACHINE.
     */
//    private void printVORows() {
//        
//       /* if(!vo.hasNext()) {
//            _logger.log(Level.FINER,"No rows in VO");
//            return;
//        }*/
//            
//        
//        RowSetIterator iterator = vo.createRowSetIterator(null);
//        iterator.reset();
//        while(iterator.hasNext()) {
//            CEIXOrdQualLabResltVORowImpl row = (CEIXOrdQualLabResltVORowImpl)
//                iterator.next();
//            _logger.log(Level.FINER,"Rows found in iterator."+row.getTrain());
//            
//        }
//        iterator.reset();
//    }

    private static String stackTraceToString(Throwable e) {
                    
                    Throwable rootcause = ExceptionUtils.getRootCause(e);
                    if(rootcause==null)
                    {
                            rootcause= e;
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
    
}
