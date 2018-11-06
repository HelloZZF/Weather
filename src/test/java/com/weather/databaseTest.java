package com.weather;

import com.weather.dao.impl.syrt_weather.SyRT_WeatherImpl;
import com.weather.domain.SyRealTime;

/**
 * Created by admin on 2018/11/4.
 */
public class databaseTest {
    private String city;
    private String year;
    private int month;
    private int day;
    private int hour;
    private float prs;
    private float prs_sea;
    private float prs_max;
    private float prs_min;
    private float tem;
    private float tem_max;
    private float tem_min;
    private float tigan;
    private float rhu;
    private float rhu_min;
    private float pre_1h;
    private float win_s_max;
    private String win_d_s_max;
    private float win_s_avg_2mi;
    private String win_d_avg_2mi;
    private float vis;
    private float radiation;

    public static void main(String[] args) {
        SyRealTime st = new SyRealTime("sanya","2018",11,04,22,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,
                1.0f,1.0f,1.0f,1.0f,1.0f,"111",1.0f,"111",1.0f,1.0f);
        SyRT_WeatherImpl imp = new SyRT_WeatherImpl();
        imp.insert(st);
    }
}
