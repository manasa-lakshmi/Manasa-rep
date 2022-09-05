package com.ceix.geolabs.loader.util;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**Common functions for conversion between CSV read string values to
 * JBO Data types.
 * @author Romesh Soni
 */
public class ConversionUtil {

    public static String trimCSVString(String in) {

        if (in != null)
            return in.trim();
        else
            return in;

    }

    public static oracle.jbo.domain.Number stringToNumber(String in) throws SQLException {

        //if the file contains blank or null, save null in db as well.
        if (in == null || in.trim().equals(""))
            return null;

        oracle.jbo.domain.Number num = new oracle.jbo.domain.Number(in);

        return num;
    }

    public static oracle.jbo.domain.Date stringToDate(String in, String dateFormat) throws ParseException {

        if (in == null || in.trim().equals(""))
            return null;

        in=in.trim();
        DateFormat formatter;
        java.util.Date date;

        formatter = new SimpleDateFormat(dateFormat);
        date = formatter.parse(in);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        oracle.jbo.domain.Date jboDate = new oracle.jbo.domain.Date(sqlDate);

        return jboDate;
    }

    public static void main(String[] args) {

        try {
            //System.out.println(stringToNumber(""));
            System.out.println(stringToDate("12/17/2018","MM/dd/yyyy"));
        }/* catch (SQLException e) {
            e.printStackTrace();
        }*/ catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
