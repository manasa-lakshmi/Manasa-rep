package com.ceix.qualnetxmlgen.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class CompareTextFiles {

    public static void main1(String[] args) throws IOException {

        LineNumberReader timePlusRptReader =
            new LineNumberReader(new FileReader("E:\\Sample.txt"));

        LineNumberReader biRptReader =
            new LineNumberReader(new FileReader("E:\\BIReport.txt"));

        ArrayList<TimePlusVO> lstTimePlus =
            populateTimePlsReportList(timePlusRptReader);
        //System.out.println("Timeplus count: "+ lstTimePlus.size());
        ArrayList<BIReportVO> lstBI = populateBIReportList(biRptReader);
        //System.out.println("BI count: "+ lstBI.size());


        try {

            compareStartTimeAndCallWS(lstTimePlus, lstBI);

            compareEndTimeAndCallWS(lstTimePlus, lstBI);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<TimePlusVO> populateTimePlsReportList(LineNumberReader timePlusRptReader) throws IOException {

        String line = timePlusRptReader.readLine();
        ArrayList<TimePlusVO> lst = new ArrayList<TimePlusVO>();

        while (line != null) {
            //System.out.println(line);
            TimePlusVO v = new TimePlusVO();

            //System.out.println(line);
            String StartDate = line.substring(0, 8);
            //System.out.println("StartDate-" + StartDate);
            v.setStDt(StartDate);
            String EndDate = line.substring(8, 16);
            ////System.out.println("EndDate-" + EndDate);
            v.setEnDt(EndDate);
            String EmpNo = line.substring(16, 24);
            //System.out.println("EmpNo-" + EmpNo);
            v.setEmpNo(EmpNo);
            String StartTime = line.substring(24, 28);
            //System.out.println("StartTime-" + StartTime);
            v.setStTime(StartTime);
            String ENDTime = line.substring(28, 32);
            ////System.out.println("ENDTime-" + ENDTime);
            v.setEnTime(ENDTime);
            lst.add(v);
            //lst.add(hm);

            line = timePlusRptReader.readLine();
        }


        return lst;
    }

    private static ArrayList<BIReportVO> populateBIReportList(LineNumberReader biRptReader) throws IOException {


        String line = biRptReader.readLine();
        ArrayList<BIReportVO> lst = new ArrayList<BIReportVO>();
        while (line != null) {
            //System.out.println(line);
            BIReportVO v = new BIReportVO();

            //System.out.println(line);
            String StartDate = line.substring(0, 8);
            //System.out.println("StartDate-" + StartDate);
            v.setStDt(StartDate);
            String bEndDate = line.substring(9, 17);
            v.setEnDt(bEndDate);
            ////System.out.println("EndDate-" + bEndDate);
            String EmpNo = line.substring(18, 26);
            //System.out.println("EmpNo-" + EmpNo);
            v.setEmpNo(EmpNo);
            String StartTime = line.substring(27, 31);
            //System.out.println("StartTime-" + StartTime);
            v.setStTime(StartTime);
            String ENDTime = line.substring(32, 36);
            v.setEnTime(ENDTime);
            ////System.out.println("ENDTime-" + ENDTime);

            lst.add(v);
            line = biRptReader.readLine();
        }


        return lst;
    }


    private static void compareStartTimeAndCallWS(ArrayList<TimePlusVO> lstTimePlus,
                                                  ArrayList<BIReportVO> lstBI) throws ParseException {

        for (TimePlusVO tvo : lstTimePlus) {

            String timePlusEmpNum = tvo.getEmpNo();
            String timePlusStartDt = tvo.getStDt();
            for (BIReportVO bivo : lstBI) {

                String biEmpNum = bivo.getEmpNo();
                String biStartDt = bivo.getStDt();
                if (timePlusEmpNum.equals(biEmpNum)) {
                    if (timePlusStartDt.equals(biStartDt)) {

                        //System.out.println("Found matching entry for Emp and StartDt: "+ biEmpNum+", "+biStartDt);
                        String timePlustStTime = tvo.getStTime();
                        String biStTime = bivo.getStTime();

                        long differenceInMinutes =
                            calculateDiferenceInMins(biStartDt,biEmpNum, timePlusStartDt,
                                                     timePlustStTime,
                                                     biStTime);

                        /*if start time difference is more than 30 mins ..
                         * needs to take Timeplus time for Rest API ...*/
                        String timeToSend;
                        if (differenceInMinutes <= 30)
                            timeToSend = biStTime;
                        else if (differenceInMinutes >= -15)
                            timeToSend = biStTime;

                        else
                            timeToSend = timePlustStTime;

                        try {

                            TimeEventRequestsRestWSCall.callInTimeRestAPI(biEmpNum,
                                                                          biStartDt,
                                                                          timeToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }


            }

        }

    }

    private static void compareEndTimeAndCallWS(ArrayList<TimePlusVO> lstTimePlus,
                                                ArrayList<BIReportVO> lstBI) throws ParseException {

        for (TimePlusVO tvo : lstTimePlus) {

            String timePlusEmpNum = tvo.getEmpNo();
            String timePlusEndDt = tvo.getEnDt();
            for (BIReportVO bivo : lstBI) {

                String biEmpNum = bivo.getEmpNo();
                String biEndDt = bivo.getEnDt();
                if (timePlusEmpNum.equals(biEmpNum)) {
                    if (timePlusEndDt.equals(biEndDt)) {

                        //System.out.println("Found matching entry for Emp and EndDt: "+ biEmpNum+", "+biEndDt);
                        String timePlustEnTime = tvo.getEnTime();
                        String biEnTime = bivo.getEnTime();

                        long differenceInMinutes =
                            calculateDiferenceInMins(biEndDt,biEmpNum, timePlusEndDt,
                                                     timePlustEnTime,
                                                     biEnTime);

                        /*if start time difference is more than 30 mins ..
                             * needs to take Timeplus time for Rest API ...*/
                        String timeToSend;
                        if (differenceInMinutes <= 15)
                            timeToSend = biEnTime;


                        else
                            timeToSend = timePlustEnTime;
                        
                        try {

                            TimeEventRequestsRestWSCall.callOutTimeRestAPI(biEmpNum,
                                                                          biEndDt,
                                                                          timeToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }                        

                        break;
                    }

                }


            }

        }

    }

    private static long calculateDiferenceInMins(String biFileDate, String biEmpNum,
                                                 String timePlusStartDt,
                                                 String timePlustStTime,
                                                 String biStTime) throws ParseException {

        
        String timePlusDt = getEventDtTime(timePlusStartDt)+" " + timePlustStTime;
        String biDt = getEventDtTime(biFileDate) +" "+ biStTime;
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HHmm");
        Date timePlusDate = fmt.parse(timePlusDt);
        Date biDate = fmt.parse(biDt);
        
        System.out.println("bi:"+biDate);
        System.out.println("tp: "+timePlusDate);
        

        long diff = biDate.getTime() - timePlusDate.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        System.out.println("diffInMinutes:"+diffInMinutes);
        
        //long diffMinutes = diff / (60 * 1000) % 60;

        System.out.println(String.format("bi date: %s, biEmpNum: %s,time plus startDt: %s " +
                                         "timePlustStTime: %s, biStTime: %s, bi-timeplus diff: %s",
                                         biFileDate,biEmpNum, timePlusStartDt,
                                         timePlustStTime, biStTime,
                                         diffInMinutes));

        return diffInMinutes;
    }

    public static String getEventDtTime(String dateIn) {
        String year = dateIn.substring(0, 4);
        String month = dateIn.substring(4, 6);
        String date = dateIn.substring(6, 8);
        String dt1 =
            month+ "/" + date  + "/" + year;
        return dt1;
    }


public static void main(String[] args) {

        try {
            calculateDiferenceInMins("20180528","00014167","20180528","2359","0400");
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
