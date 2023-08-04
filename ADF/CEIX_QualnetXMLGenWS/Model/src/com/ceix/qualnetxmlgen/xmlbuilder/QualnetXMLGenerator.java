package com.ceix.qualnetxmlgen.xmlbuilder;


import com.ceix.common.util.SFTPCredentials;
import com.ceix.qualnetxmlgen.model.view.WSResultPVOImpl;
import com.ceix.qualnetxmlgen.model.view.WSResultPVORowImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.RowSet;
import oracle.jbo.XMLInterface;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;

import oracle.xml.parser.v2.XMLNode;

import org.apache.commons.lang.exception.ExceptionUtils;


/**
 * Utility class to generate the XML Output file for Quality Metrics
 * @author Romesh Soni
 */
public class QualnetXMLGenerator {
    
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(QualnetXMLGenerator.class);

    //FTP CREDENTIALS HERE...
    //private static final String SFTP_HOST = "cnxftp.consolenergy.com";
    //private static final int SFTP_PORT = 22;
    //private static final String SFTP_USER = "CEIX_OracleERP";
    //private static final String SFTP_PASS = "0r@cleERP";

    private String SFTP_WORKING_DIR;
    private String FILE_NAME;
    private boolean isDevMachine;
    
    private ViewObjectImpl vo;
    private WSResultPVOImpl resultVo;
    
    public QualnetXMLGenerator(ViewObjectImpl voIn, String sftpDir
                               , String outputFileName
                               ,boolean isDev
                               ,WSResultPVOImpl resultVo
                               ) {
        super();
        this.vo=voIn;
        this.SFTP_WORKING_DIR=sftpDir;
        this.FILE_NAME=outputFileName;
        this.isDevMachine = isDev;
        this.resultVo = resultVo;
    }
 
   public List<ViewRowImpl> generate()  {
       
       _logger.info(">>generate");
       
       List<ViewRowImpl> lstResult= new ArrayList<ViewRowImpl>();
       File file= null;
       try 
       {
           //get the file to write
           file= getTempFile();
           
           //generate the file
           writeVODataToFile(file);
           
           //put the file in ftp
            writeToFTP(file);
            
           //prepare the success response
           prepareSuccessResponse(resultVo,lstResult);
           
       }catch(Exception e) {
           _logger.log(Level.SEVERE,"Error occurred : "+ e.getMessage());        
           prepareFailedResponse(resultVo,lstResult,e);
       }
       finally {
           try
           {
               deleteTempFile(file);
           }
           catch(Exception e) {
                      _logger.log(Level.SEVERE,"Error occurred : "+ e.getMessage());        
                      prepareFailedResponse(resultVo,lstResult,e);
                  }
       }
           
           return lstResult;
   }
  
 
    private void deleteTempFile(File file) throws Exception {
        
        _logger.log(Level.FINER,"deleting temp file.");
        
        if(file!=null&&file.exists()) {
            boolean deleted = file.delete();
            
            if(!deleted) {
                Exception e = new Exception("Temp file could not be deleted. "+ file.getName());
                 throw e;
                }
                
            }
        }
            
        
        
    public static void main(String[] args) {
        
        File f = new File("D:/quality_metrics6_8_2018_14_59_53_783.xml");
        try {
            f.delete();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
    }

    private File getTempFile() throws IOException {
        
        if(isDevMachine) {
            //if its a developer machine,just write to D: drive
             return getAFile("D:/");
        }
        
        String tempDirPath=getTempDirPath();
        _logger.log(Level.FINER,"Temp dir path: "+ tempDirPath);
        
        return getAFile(tempDirPath);
  
    }
    private File getAFile(String drive) throws IOException {
        
        File file = new File(drive+FILE_NAME);
        //delete file from temp directory if already exists.
        if(file.exists()) {
            _logger.log(Level.FINER,"File already exists. Deleting it before writing new data.");
            file.delete();  
        }
          
        
        file.createNewFile();

        return file;

        
    }

    private String getTempDirPath() {
        //String dirName = System.getProperty("java.scratch.dir");
        String dirName = System.getProperty("/customer/tmp");
        //String serverName = System.getProperty("weblogic.Name");
        //String finalPath = dirName+"/"+serverName;
        String finalPath = dirName;
        return finalPath;

    }

    private void writeVODataToFile(File file) throws FileNotFoundException,
                                                     IOException {
        _logger.log(Level.FINER,">>writing to Temp dir.");
        RowSet rowSet = vo.getRowSet();
        OutputStream outputStream   = new FileOutputStream(file);
        try {
            ((XMLNode)(rowSet.writeXML(0,
                                       XMLInterface.XML_OPT_ALL_ROWS))).print(outputStream);
        } catch (IOException ioe) {
           throw ioe;
        } finally {
            outputStream.flush();
            outputStream.close();
        }
       
    }

    private void writeToFTP(File file) throws FileNotFoundException,
                                              Exception {
        
        _logger.log(Level.FINER,">>writeToFTP");
        SFTPCredentials sftpCredentials= new SFTPCredentials();
        
        FTPWriterUtil ftpWriter 
           = new  FTPWriterUtil(sftpCredentials.getSFTP_HOST()
                                ,sftpCredentials.getSFTP_PORT()
                                ,sftpCredentials.getSFTP_USER()
                                ,sftpCredentials.getSFTP_PASS());
        
        InputStream ios = new FileInputStream(file);
        
        try {
            ftpWriter.writeFileToFTP(SFTP_WORKING_DIR, FILE_NAME, ios);
        } catch (Exception e) {
            throw e;
        } finally {
            ios.close();
        }
        
        


    }
    private void prepareSuccessResponse(WSResultPVOImpl resultVo
                                        ,List<ViewRowImpl> lstResult) {
        
        WSResultPVORowImpl resultRow = (WSResultPVORowImpl)resultVo.createRow();
        resultRow.setStatus("SUCCESS");
        resultRow.setMessage("File has been generated successfully."
                             );
        lstResult.add(resultRow);

    }
    private void prepareFailedResponse(WSResultPVOImpl resultVo
                                        ,List<ViewRowImpl> lstResult, Exception e) {
        
        WSResultPVORowImpl resultRow = (WSResultPVORowImpl)resultVo.createRow();
        
        String stackTrace = "";
        stackTrace = stackTraceToString(e);
        if(e!=null) {
            stackTrace = stackTraceToString(e);
            if(stackTrace!=null&&stackTrace.length()>2500)
            stackTrace=stackTrace.substring(0,2500);
            
        }
        resultRow.setStatus("FAILED");
        resultRow.setMessage("Processing Error. "+ stackTrace
                             );
        lstResult.add(resultRow);

    }    
     
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
