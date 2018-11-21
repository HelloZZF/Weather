package com.weather;

import com.weather.dao.impl.syrt_weather.CropsConditionWeatherImpl;
import com.weather.dao.syrt_weather.CropsConditionWeatherDao;
import com.weather.domain.CropsCondition;

/**
 * Created by admin on 2018/11/4.
 */
public class databaseTest {


    public static void main(String[] args) {
        CropsCondition cc = new CropsCondition();
        CropsConditionWeatherDao ssw = new CropsConditionWeatherImpl();
//        cc = ssw.query("'苹果'");
//        System.out.println(cc.toString());
        System.out.println(ssw.queryAll());
    }
}
