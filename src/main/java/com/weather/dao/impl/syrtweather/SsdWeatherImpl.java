package com.weather.dao.impl.syrtweather;

import com.weather.dao.syrtweather.SsdWeatherDao;
import com.weather.domain.Ssd;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Created by admin on 2018/11/10.
 */
public class SsdWeatherImpl implements SsdWeatherDao {
    @Override
    public void insert(Ssd ssd) {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String fields = "province,month,ave_tem,ave_rhu,pre_20_20,max_win,ssd,ssd_month";
        String sql = "insert into ssd_weather (" +fields+") values(?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, ssd.getProvince(),ssd.getMonth(),ssd.getAve_tem(),ssd.getAve_rhu(),
                    ssd.getPre_20_20(),ssd.getMax_win(),ssd.getSsd(),ssd.getSsd_month());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
