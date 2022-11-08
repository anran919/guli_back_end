package com.zar.service_edu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
   public static Date getDate(String time){
       Date date = null;
       try {
           date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
       } catch (ParseException e) {
           throw new RuntimeException(e);
       }
       return date;
    }
}
