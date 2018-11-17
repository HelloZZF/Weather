package com.weather;

import com.weather.dao.impl.syrt_weather.CropsCondition_WeatherImpl;
import com.weather.dao.impl.syrt_weather.Crops_WeatherImpl;
import com.weather.dao.impl.syrt_weather.Ssd_WeatherImpl;
import com.weather.dao.impl.syrt_weather.SyRT_WeatherImpl;
import com.weather.dao.syrt_weather.CropsCondition_WeatherDao;
import com.weather.dao.syrt_weather.Crops_WeatherDao;
import com.weather.dao.syrt_weather.Ssd_WeatherDao;
import com.weather.domain.Crops;
import com.weather.domain.CropsCondition;
import com.weather.domain.Ssd;
import com.weather.domain.SyRealTime;

/**
 * Created by admin on 2018/11/4.
 */
public class databaseTest {


    public static void main(String[] args) {
        CropsCondition cc = new CropsCondition();
        CropsCondition_WeatherDao ssw = new CropsCondition_WeatherImpl();
//        cc = ssw.query("'苹果'");
//        System.out.println(cc.toString());
        System.out.println(ssw.queryAll());
    }
}
