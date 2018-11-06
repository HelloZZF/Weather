package com.weather.syrtweather

import com.weather.conf.ConfigurationManager
import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.SyRT_WeatherImpl
import com.weather.domain.SyRealTime
import com.weather.util.SparkUtil
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by admin on 2018/11/5.
  */
object SyRealTimeWeather_Ops {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
            .setAppName(Constants.SPARK_APP_NAME_SYRT)
        SparkUtil.setMaster(conf)
        val ssc = new StreamingContext(conf, Seconds(5))
        val sc = ssc.sparkContext
        sc.setLogLevel("ERROR")

        val kafkaParams = Map("metadata.broker.list" -> ConfigurationManager.getProperty(Constants.KAFKA_METADATA_BROKER_LIST))
        val topics = Set(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS))
        //获取kafka中对应字段的数据,默认从kafka获得的数据key为空
        val syRealTimeDStream:InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
        syRealTimeDStream.print()
        syRealTimeDStream.transform(rdd => {
            rdd.map(tuple => {
                val factor = tuple._2.split(" ")
                val sw = new SyRT_WeatherImpl()
                sw.insert(new SyRealTime(factor(0),factor(1),factor(2).toInt,factor(3).toInt,factor(4).toInt
                ,factor(5).toFloat,factor(6).toFloat,factor(7).toFloat,factor(8).toFloat,factor(9).toFloat
                ,factor(10).toFloat,factor(11).toFloat,factor(12).toFloat,factor(13).toFloat,factor(14).toFloat
                ,factor(15).toFloat,factor(16).toFloat,factor(17),factor(18).toFloat,factor(19),factor(20).toFloat,factor(21).toFloat))
            })
            null
        })

        ssc.start()
        ssc.awaitTermination()
        //正常状况下ssc.stop()执行不到
        ssc.stop()

    }

}
