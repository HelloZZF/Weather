package com.weather.dao.impl.syrt_weather;

import com.weather.dao.syrt_weather.HourseWeatherDao;
import com.weather.domain.Hourse;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Created by admin on 2018/11/10.
 */
public class HourseWeatherImpl implements HourseWeatherDao {
    @Override
    public void insert(Hourse hourse) {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String fields = "asn,month,ave_tem,ave_max_tem,ave_min_tem,max_tem_more_30,max_tem_more_35,ave_rhu,pre_20_20," +
                "pre_more_25,max_conti_pre,ave_win,max_win_more_10,max_win_more_12";
        String sql = "insert into hourse_weather (" +fields+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, hourse.getAsn(),hourse.getMonth(),hourse.getAve_tem(),hourse.getAve_max_tem(),hourse.getAve_min_tem(),hourse.getMax_tem_more_30(),
                    hourse.getMax_tem_more_35(),hourse.getAve_rhu(),hourse.getPre_20_20(),hourse.getPre_more_25(),
                    hourse.getMax_conti_pre(),hourse.getAve_win(),hourse.getMax_win_more_10(),hourse.getMax_win_more_12());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
