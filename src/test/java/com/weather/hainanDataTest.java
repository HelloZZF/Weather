package com.weather;

/**
 * Created by admin on 2018/11/2.
 */
public class hainanDataTest {
    public static void main(String[] args) {
        String str = "59758 2011 5 18.6000 999007 993.9000 21.2000 1007.1000 35.4000 126.2000 1000.4000 2.8000 26.9000 26.9000 77.0000 23.9000 31.5000 13 43.0000 173.2000 10.8000 999006 33.9000 999999.0000 ";
        String[] field = str.trim().split(" ");
        //年
        String year = field[1];
        System.out.println(year);
        //月
        String month = field[2];
        System.out.println(month);
        //平均温度
        String avetem = field[12];
        System.out.println(avetem);
        //最高温度
        String maxtem = field[8];
        System.out.println(maxtem);
        //20-20降水量
        String pre = field[9];
        System.out.println(pre);
        //相对湿度
        String rhu = field[14];
        System.out.println(rhu);
        //日照时数
        String ssd = field[19];
        System.out.println(ssd);
        //极大风速
        String win = field[3];
        System.out.println(win);
        System.out.println("_____________________");
        float nan = 0.0f/0;
        System.out.println(nan);
        if (Float.isNaN(nan)) {
            System.out.println("yes nan");
        }
    }
}
