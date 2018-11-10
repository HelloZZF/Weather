package com.weather.dao.impl.syrt_weather;

import com.weather.dao.syrt_weather.Crops_WeatherDao;
import com.weather.domain.Crops;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Created by admin on 2018/11/9.
 */
public class Crops_WeatherImpl implements Crops_WeatherDao{

    @Override
    public void insert(Crops crops) {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String fields = "province,month,ave_tem,ave_min_tem,ave_max_tem,ave_tem_diff,min_tem_less_0," +
                "max_tem_more_30,ave_rhu,pre_20_20,pre_more_25,ave_win,max_win_more_12";
        String sql = "insert into crops_weather (" +fields+") values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, crops.getProvince(),crops.getMonth(),crops.getAve_tem(),crops.getAve_min_tem(),
                    crops.getAve_max_tem(),crops.getAve_tem_diff(),crops.getMin_tem_less_0(),crops.getMax_tem_more_30(),
                    crops.getAve_rhu(),crops.getPre_20_20(),crops.getPre_more_25(),crops.getAve_win(),crops.getMax_win_more_12());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
