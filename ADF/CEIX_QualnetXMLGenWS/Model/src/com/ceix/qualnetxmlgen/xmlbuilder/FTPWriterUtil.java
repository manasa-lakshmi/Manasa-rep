package com.ceix.qualnetxmlgen.xmlbuilder;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.InputStream;

import java.util.Calendar;
import java.util.logging.Level;

import oracle.adf.share.logging.ADFLogger;


/**This utility class will write XML File in Consol ftp.
 * @author Romesh Soni
 */
public class FTPWriterUtil {

    private static ADFLogger _logger =
        ADFLogger.createADFLogger(FTPWriterUtil.class);

    private String SFTP_HOST;
    private int SFTP_PORT;
    private String SFTP_USER;
    private String SFTP_PASS;
    
 
    public FTPWriterUtil(
    String SFTP_HOST ,
    int SFTP_PORT ,
    String SFTP_USER ,
    String SFTP_PASS 
        ) {
        super();
        this.SFTP_HOST=SFTP_HOST;
        this.SFTP_PASS=SFTP_PASS;
        this.SFTP_PORT=SFTP_PORT;
        this.SFTP_USER=SFTP_USER;
        
    }
    public void writeFileToFTP(String SFTP_WORKING_DIR, String FILE_NAME,
                              InputStream fileToWrite) throws Exception {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        InputStream in_stream = null;

        try {
            _logger.log(Level.FINER,
                        "Opening SFTP connection and trying to write XML File.");

//            String proxyhost = System.getProperty("https.proxyHost");
//            String proxyport = System.getProperty("https.proxyPort");
//            ProxyHTTP proxy =
//                new ProxyHTTP(proxyhost, Integer.parseInt(proxyport));

            JSch jsch = new JSch();
            session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
            session.setPassword(SFTP_PASS);
            //session.setProxy(proxy);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTP_WORKING_DIR);

            channelSftp.put(fileToWrite, FILE_NAME);


            _logger.log(Level.FINER, "File has been written successfully.");


        } catch (JSchException e) {
            _logger.log(Level.FINER,
                        "JSchException occurred during writing file to SFTP server due to " +
                        e.getMessage());
            throw e;

        } catch (SftpException e) {
            _logger.log(Level.FINER,
                        "SftpException occurred during writing file to SFTP server due to " +
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

public static String getTimestampForFileName() {
    
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
    int day = now.get(Calendar.DAY_OF_MONTH);
    int hour = now.get(Calendar.HOUR_OF_DAY);
    int minute = now.get(Calendar.MINUTE);
    int second = now.get(Calendar.SECOND);
    int millis = now.get(Calendar.MILLISECOND);
    return month+"_"+day+"_"+year+"_"+hour+"_"+minute+"_"+second+"_"+millis;
}

public static String getCompleteFileName(String ftpFileName) {
    return ftpFileName+getTimestampForFileName()+".xml";
}

}
