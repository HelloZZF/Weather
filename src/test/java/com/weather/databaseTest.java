package com.weather;

import com.weather.dao.impl.syrt_weather.Crops_WeatherImpl;
import com.weather.dao.impl.syrt_weather.Ssd_WeatherImpl;
import com.weather.dao.impl.syrt_weather.SyRT_WeatherImpl;
import com.weather.dao.syrt_weather.Crops_WeatherDao;
import com.weather.dao.syrt_weather.Ssd_WeatherDao;
import com.weather.domain.Crops;
import com.weather.domain.Ssd;
import com.weather.domain.SyRealTime;

/**
 * Created by admin on 2018/11/4.
 */
public class databaseTest {
    private String province;
    private int month;
    private float ave_tem;
    private float ave_rhu;
    private float pre_20_20;
    private float max_win;
    private float ssd;
    private float ssd_month;

    public static void main(String[] args) {
        Ssd ssd = new Ssd("hainan",2,1.0f,2.9f,2.9f,2.9f,2.9f,2.9f);
        Ssd_WeatherDao dao = new Ssd_WeatherImpl();
        dao.insert(ssd);
    }
}
