package com.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2018/11/5.
 */
public class syrtTest {
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH0000");
        System.out.println(df.format(System.currentTimeMillis()));

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH);

        int date = c.get(Calendar.DATE);

        int hour = c.get(Calendar.HOUR_OF_DAY);

        System.out.println(year + "" + month + "" + date + "" + (hour - 8) + "0000");

        int minute = c.get(Calendar.MINUTE);

        int second = c.get(Calendar.SECOND);

        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);



    }
}
