package com.ceix.qualnetxmlgen.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.bind.DatatypeConverter;

public class TimeEventRequestsRestWSCall {

    private static String url =
        "https://eguc-dev2.fa.us2.oraclecloud.com:443/hcmRestApi/resources/11.13.17.11/timeEventRequests";
    private static String cpqUserName = "tmuley";
    private static String password = "Welcome1";
    private static String charset = "UTF-8";

    private static HttpURLConnection con;

    public static void initialize() {

        try {
            byte[] loginPwd = (cpqUserName + ":" + password).getBytes();
            String encryptedLogin =
                DatatypeConverter.printBase64Binary(loginPwd);
            ;
            //String encryptedLogin = new String(loginPwd);
            URL obj = new URL(url);
            con = (HttpURLConnection)obj.openConnection();

            // add reuqest header Method
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            //con.setRequestProperty("Authorization", "Basic RUJTaW50ZWdyYXRpb246ZCp5MzQyX0o3Xw==" );
            con.setRequestProperty("Authorization", "Basic " + encryptedLogin);
            System.out.println("initialized");
        } catch (ProtocolException pe) {
            // TODO: Add catch code
            pe.printStackTrace();
        } catch (MalformedURLException murle) {
            // TODO: Add catch code
            murle.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
        }

    }


    public static void callInTimeRestAPI(String empNo, String startDate,
                                   String timeToSend) throws IOException {


        initialize();
        System.out.println(String.format("Calling In Time REST API: Emp No: %s, Start Dt: %s, Time: %s",
                                         empNo, startDate, timeToSend));
        String xmlRequest = buildInTimeXMLRequest(empNo, startDate, timeToSend);
        System.out.println(xmlRequest);

        OutputStreamWriter writer = null;
        try {
            
            writer = new OutputStreamWriter(con.getOutputStream(), charset);
            writer.write(xmlRequest); // Write POST query string (if any
            // needed).
            writer.flush();
            
            System.out.println("Response code: "+ con.getResponseCode());
            
            //printResponse(con);

        } finally {
            con.disconnect();
            con=null;
            if (writer != null)
                try {
                    
                    writer.close();
                } catch (IOException logOrIgnore) {


                }
        }

    }
    
    public static void callOutTimeRestAPI(String empNo, String endDate,
                                   String timeToSend) throws IOException {


        initialize();
        System.out.println(String.format("Calling In Time REST API: Emp No: %s, Start Dt: %s, Time: %s",
                                         empNo, endDate, timeToSend));
        String xmlRequest = buildOutTimeXMLRequest(empNo, endDate, timeToSend);
        System.out.println(xmlRequest);

        OutputStreamWriter writer = null;
        try {
            
            writer = new OutputStreamWriter(con.getOutputStream(), charset);
            writer.write(xmlRequest); // Write POST query string (if any
            // needed).
            writer.flush();
            
            System.out.println("Response code: "+ con.getResponseCode());
            
            //printResponse(con);

        } finally {
            con.disconnect();
            con=null;
            if (writer != null)
                try {
                    
                    writer.close();
                } catch (IOException logOrIgnore) {


                }
        }

    }

    

    private static String buildInTimeXMLRequest(String empNo, String dateIn,
                                          String timeToSend) {

        //In Time Rest PoST
        //String xmlRequest="{\"requestNumber\":\"CEIXOUT00012777\",\"sourceId\":\"ORACLE_EVENT_GEN_PROC\",\"requestTimestamp\": \"2018-06-01T00:00:00.000+00:00\",\"timeEvents\": [{\"deviceId\": \"CEIX_TCLOCK_PLUS\",\"eventDateTime\":\"2018-06-02T03:00:00.000+00:00\",\"supplierDeviceEvent\":\"CEIX_TCLOCK_PLUS_OUT\",\"reporterId\":\"00012777\",\"reporterIdType\":\"PERSON\",\"timeEventAttributes\":[{\"name\":\"PayrollTimeType\",\"value\": \"PM Rate Code\"}]}]}";
        String dateTime = String.valueOf(System.currentTimeMillis());
        String date = dateTime.toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));
        String sysDate = dateFormat.format(cal.getTime());

        String eventDateTime = getEventDtTime(dateIn, timeToSend);
        String xmlRequest =
            "{\"requestNumber\":" + "\"" + empNo + date + "\"" +
            ",\"sourceId\": \"ORACLE_EVENT_GEN_PROC\",\"requestTimestamp\":\"" +
            sysDate +
            "T00:00:00.000+00:00\",\"timeEvents\": [{\"deviceId\": \"CEIX_TCLOCK_PLUS\",\"eventDateTime\": \"" +
            eventDateTime +
            "\",\"supplierDeviceEvent\": \"CEIX_TCLOCK_PLUS_IN\",\"reporterId\": \"" +
            empNo +
            "\",\"reporterIdType\": \"PERSON\",\"timeEventAttributes\": [{\"name\": \"PayrollTimeType\",\"value\": \"PM Rate Code\"}]}]}";

        return xmlRequest;
    }

    private static String buildOutTimeXMLRequest(String empNo, String dateOut,
                                          String timeToSend) {

        //In Time Rest PoST
        //String xmlRequest="{\"requestNumber\":\"CEIXOUT00012777\",\"sourceId\":\"ORACLE_EVENT_GEN_PROC\",\"requestTimestamp\": \"2018-06-01T00:00:00.000+00:00\",\"timeEvents\": [{\"deviceId\": \"CEIX_TCLOCK_PLUS\",\"eventDateTime\":\"2018-06-02T03:00:00.000+00:00\",\"supplierDeviceEvent\":\"CEIX_TCLOCK_PLUS_OUT\",\"reporterId\":\"00012777\",\"reporterIdType\":\"PERSON\",\"timeEventAttributes\":[{\"name\":\"PayrollTimeType\",\"value\": \"PM Rate Code\"}]}]}";
        String dateTime = String.valueOf(System.currentTimeMillis());
        String date = dateTime.toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));
        String sysDate = dateFormat.format(cal.getTime());

        String eventDateTime = getEventDtTime(dateOut, timeToSend);
        String xmlRequest =
            "{\"requestNumber\":" + "\"" + empNo + date + "\"" +
            ",\"sourceId\": \"ORACLE_EVENT_GEN_PROC\",\"requestTimestamp\":\"" +
            sysDate +
            "T00:00:00.000+00:00\",\"timeEvents\": [{\"deviceId\": \"CEIX_TCLOCK_PLUS\",\"eventDateTime\": \"" +
            eventDateTime +
            "\",\"supplierDeviceEvent\": \"CEIX_TCLOCK_PLUS_OUT\",\"reporterId\": \"" +
            empNo +
            "\",\"reporterIdType\": \"PERSON\",\"timeEventAttributes\": [{\"name\": \"PayrollTimeType\",\"value\": \"PM Rate Code\"}]}]}";

        return xmlRequest;
    }
    
    public static String getEventDtTime(String dateIn, String timeToSend) {
        String year = dateIn.substring(0, 4);
        String month = dateIn.substring(4, 6);
        String date = dateIn.substring(6, 8);
        String timePart =
            timeToSend.substring(0, 2) + ":" + timeToSend.substring(2, 4);
        String dt1 =
            year + "-" + month + "-" + date + "T" + timePart + ":00.000+00:00";
        return dt1;
    }


    private static void printResponse(HttpURLConnection con) throws IOException {

        InputStream result = con.getInputStream();
        // System.out.println(result);
        String readLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(result));
        //BufferedReader br = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(url)).openConnection()).getInputStream(), Charset.forName("UTF-8")));

        while (((readLine = br.readLine()) != null)) {
            System.out.println(readLine);
            //log.write(readLine);
        }
        
        result.close();
        

    }
}
