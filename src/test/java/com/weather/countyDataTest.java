package com.weather;

/**
 * Created by admin on 2018/10/30.
 */
public class countyDataTest {
    public static void main(String[] args) {
        String s = "58457 2017 10 15.0000 62 1003.2000 7.8000 1028.2000 34.0000 88.8000 1015.5000 2.7000 19.1000 17.0000 75.5000 16.1000 23.0000 9 999999.0000 125.8000 7.6000 13 32.6000 28.0000 ";
        String[] e = s.split(" ");
        //区站号
        System.out.println(Float.valueOf(e[0]));
        //年
        System.out.println(Float.valueOf(e[1]));
        //月
        System.out.println(Float.valueOf(e[2]));
        //平均气温
        System.out.println(Float.valueOf(e[12]));
        //平均相对湿度
        System.out.println(Float.valueOf(e[14]));
        //日照时数
        System.out.println(Float.valueOf(e[19]));
        //20-20时降水量
        System.out.println(Float.valueOf(e[9]));

        //System.out.println(Float.valueOf(e[14])/2);
        System.out.println("-------");
        System.out.println(Float.valueOf("999999.0000"));
    }
}
