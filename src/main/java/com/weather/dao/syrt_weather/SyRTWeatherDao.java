package com.weather.dao.syrt_weather;

import com.weather.domain.SyRealTime;

/**
 * Created by admin on 2018/11/4.
 */
public interface SyRTWeatherDao {
    void insert(SyRealTime st);
    void delectAll();
}
