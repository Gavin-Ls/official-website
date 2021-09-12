package com.ow.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author lavnote
 */
public class DateUtil {
    public static final String DATE_FORMAT = "yyy-MM-dd HH:mm:ss";

    /**
     * string to date
     *
     * @param str String
     * @return Date
     */
    public static Date str2Date(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date to string
     *
     * @param date Date
     * @return String
     */
    public static String date2Str(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        System.out.println(str2Date("2018-01-12 17:07:05"));
        System.out.println(date2Str(new Date()));
    }
}
