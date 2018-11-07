package com.weather;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by admin on 2018/11/6.
 */
public class realtimeTest {

    private ArrayList<String> timelist = new ArrayList<>();

    private int flag = 0;

    public static void main(String[] args) {
        realtimeTest r = new realtimeTest();
        while (true) {
            System.out.println(r.TimeList());
        }

    }

    public String TimeList() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        //这里的月份是从0开始计数的所以要加一
        int month = c.get(Calendar.MONTH) + 1;
        String monthStr = add0(month);
        int date = c.get(Calendar.DATE);
        String dateStr = add0(date);
        //如果是UTC时间的话要减去8小时的时差
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if ( hour >= 9) {
            hour = hour - 9;
        }
        String hourStr = add0(hour);

        for (int i = 0; i <= 23; i++) {
            int predate = c.get(Calendar.DATE) - 1;
            String predateStr = add0(predate);
            String pretime = year + "" + monthStr + "" + predateStr + "" + add0(i) + "0000";
            timelist.add(pretime);
        }

        for (int i = 0; i <= hour; i++) {
            String time = year + "" + monthStr + "" + dateStr + "" + add0(i) + "0000";
            timelist.add(time);
        }


        return flag == timelist.size() ? timelist.get(flag - timelist.size()) : timelist.get(flag++);
    }

    public String add0(int i) {
        String s = i + "";
        if (i < 10) {
            s = "0" + i;
        }
        return s;
    }
}

