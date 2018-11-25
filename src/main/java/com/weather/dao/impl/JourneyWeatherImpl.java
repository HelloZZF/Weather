package com.weather.dao.impl;

import com.weather.dao.JourneyWeatherDao;
import com.weather.domain.Journey;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Created by admin on 2018/11/10.
 */
public class JourneyWeatherImpl implements JourneyWeatherDao {

    @Override
    public void insert(Journey jour) {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String fields = "city,month,ave_tem,ave_max_tem,ave_min_tem,max_tem_more_30,max_tem_more_35," +
                "ave_rhu,pre_20_20,pre_more_25,max_conti_pre,max_no_pre,ave_win,max_win_more_10,max_win_more_15";
        String sql = "insert into journey_weather (" +fields+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, jour.getCity(),jour.getMonth(),jour.getAve_tem(),jour.getAve_max_tem(),
                    jour.getAve_min_tem(),jour.getMax_tem_more_30(),jour.getMax_tem_more_35(),
                    jour.getAve_rhu(),jour.getPre_20_20(),jour.getPre_more_25(),jour.getMax_conti_pre(),
                    jour.getMax_no_pre(),jour.getAve_win(),jour.getMax_win_more_10(),jour.getMax_win_more_15());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
