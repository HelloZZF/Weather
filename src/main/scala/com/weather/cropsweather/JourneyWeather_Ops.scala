package com.weather.cropsweather

import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.Journey_WeatherImpl
import com.weather.domain.Journey
import com.weather.util.SparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/10.
  */
object JourneyWeather_Ops {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(JourneyWeather_Ops.getClass.getSimpleName)
        SparkUtil.setMaster(conf)
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        sc.textFile("C:/Users/admin/Desktop/cropsweather_1981-2010.txt")
            .map(line => {
                val factors:Array[String] = line.split(" ")
                (factors(0) + " " + factors(2), factors)
            })
            .filter(f => {
                val asn = f._1.split(" ")(0).equals(Constants.ASN_BAOTING)
                var default = true
                val values = f._2

                if (values(15).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(16).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(17).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(31).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(32).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(45).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(48).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(61).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(65).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(73).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(85).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(91).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(93).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                    default = false
                }
                asn && default
            })
            .map(f => {
                (f._1.split(" ")(1).toInt, f._2)
            })
            .sortByKey()
            .foreach(t => {
                val values = t._2

                var city: String = "sanya"
                var month: Int = values(2).toInt
                var ave_tem: Float = values(15).toFloat
                var ave_max_tem: Float = values(16).toFloat
                var ave_min_tem: Float = values(17).toFloat
                var max_tem_more_30: Float = values(31).toFloat
                var max_tem_more_35: Float = values(32).toFloat
                var ave_rhu: Float = values(45).toFloat
                var pre_20_20: Float = values(48).toFloat
                var pre_more_25: Float = values(61).toFloat
                var max_conti_pre: Float = values(65).toFloat
                var max_no_pre: Float = values(73).toFloat
                var ave_win: Float = values(85).toFloat
                var max_win_more_10: Float = values(91).toFloat
                var max_win_more_15: Float = values(93).toFloat

                val dao = new Journey_WeatherImpl()
                val jour = new Journey(city,month,ave_tem,ave_max_tem,ave_min_tem,
                    max_tem_more_30,max_tem_more_35,ave_rhu,pre_20_20,pre_more_25,
                    max_conti_pre,max_no_pre,ave_win,max_win_more_10,max_win_more_15)
                dao.insert(jour)


            })


        sc.stop()
    }

}
