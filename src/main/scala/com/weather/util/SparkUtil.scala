package com.weather.util

import com.alibaba.fastjson.JSONObject
import com.weather.conf.ConfigurationManager
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/8/21.
  * 关于Spark操作的一些工具类
  */
object SparkUtil {

    //根据是否是本地执行spark作业，而进行设置
    //在测试环境和生产环境都通过spark-submit脚本进行设置
    //--master spark://server:7077
    //--deploy-mode cluster
    def setMaster(conf: SparkConf): Unit = {
        if ("local".equalsIgnoreCase(ConfigurationManager.mode)) {
            //讲道理，这个是强行解决问题，有些不妥
            conf.setMaster("local[1]")
        }
    }

    //根据是否在本地执行Spark的作业创建对应的SQLContext
    //本地执行的话创建SQLContext
    //测试生产用HiveContext
    def getSqlContext(sc: SparkContext): SQLContext = {
        if ("local".equalsIgnoreCase(ConfigurationManager.mode)) {
            new SQLContext(sc)
        }else {
            new HiveContext(sc)
        }
    }



}
