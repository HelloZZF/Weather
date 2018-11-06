package com.weather.dao.impl.syrt_weather;

import com.weather.dao.syrt_weather.SyRT_WeatherDao;
import com.weather.domain.SyRealTime;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by admin on 2018/11/4.
 */
public class SyRT_WeatherImpl implements SyRT_WeatherDao{
    @Override
    public void insert(SyRealTime st) {
        try {
            QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
            String fields = "city,year,month,day,hour,prs,prs_sea,prs_max,prs_min,tem,tem_max,tem_min,tigan," +
                    "rhu,rhu_min,pre_1h,win_s_max,win_d_s_max,win_s_avg_2mi,win_d_avg_2mi,vis,radiation";
            String sql = "insert into syrt_weather (" + fields + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            qr.update(sql, st.getCity(),st.getYear(),st.getMonth(),st.getDay(),st.getHour(),
                st.getPrs(),st.getPrs_sea(),st.getPrs_max(),st.getPrs_min(),
                    st.getTem(),st.getTem_max(),st.getTem_min(),st.getTigan(),
                    st.getRhu(),st.getRhu_min(),st.getPre_1h(),
                    st.getWin_s_max(),st.getWin_d_s_max(),st.getWin_s_avg_2mi(),st.getWin_d_avg_2mi(),
                    st.getVis(),st.getRadiation());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
