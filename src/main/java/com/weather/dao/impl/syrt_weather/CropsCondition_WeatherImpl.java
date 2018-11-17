package com.weather.dao.impl.syrt_weather;

import com.mysql.jdbc.ResultSetImpl;
import com.weather.dao.syrt_weather.CropsCondition_WeatherDao;
import com.weather.domain.CropsCondition;
import com.weather.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2018/11/17.
 */
public class CropsCondition_WeatherImpl implements CropsCondition_WeatherDao{

    @Override
    public CropsCondition query(String name) {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String sql = "select * from crops_condition where `name`=" + name;
        try {
            return (CropsCondition) qr.query(sql, new BeanHandler(CropsCondition.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CropsCondition> queryAll() {
        QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
        String sql = "select * from crops_condition";
        try {
            return qr.query(sql, new BeanListHandler<>(CropsCondition.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
