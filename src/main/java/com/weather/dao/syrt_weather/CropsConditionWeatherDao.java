package com.weather.dao.syrt_weather;

import com.weather.domain.CropsCondition;

import java.util.List;

/**
 * Created by admin on 2018/11/17.
 */
public interface CropsConditionWeatherDao {
    CropsCondition query(String name);
    List<CropsCondition> queryAll();
}
