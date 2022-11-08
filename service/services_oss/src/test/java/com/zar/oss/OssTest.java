package com.zar.oss;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OssTest {
    @Test
   public void getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(dateFormat.format(date));
    }
}
