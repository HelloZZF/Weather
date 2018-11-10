package com.weather.cropsweather

import java.util

import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.Crops_WeatherImpl
import com.weather.domain.Crops
import com.weather.util.SparkUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/9.
  */
object CropsWeather_Ops {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(CropsWeather_Ops.getClass.getSimpleName)
        SparkUtil.setMaster(conf)
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")


        val origin:RDD[String] = sc.textFile("C:/Users/admin/Desktop/cropsweather_1981-2010.txt")

        val pairs:RDD[Tuple2[String, Array[String]]] = origin.map(line => {
                val factors:Array[String] = line.split(" ")
                new Tuple2[String, Array[String]](factors(0) + " " + factors(2), factors)
            })

        val filpaires:RDD[Tuple2[String, Array[String]]] = pairs.filter(f => {
            val asn = !f._1.split(" ")(0).equals(Constants.ASN_FIELD_NAME)
            var default = true
            val values = f._2
            if (values(17).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(16).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                values(24).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(36).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                values(31).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(45).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                values(48).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(61).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                values(85).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(92).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                default = false
            }
            asn && default
        })

        val monpaires:RDD[Tuple2[Int, Array[String]]] = filpaires.map(t => {
            (t._1.split(" ")(1).toInt, t._2)
        })

        monpaires.groupByKey().sortByKey().foreach(t => {
            val key = t._1
            val values = t._2
            var province: String = ""
            var month: Int = 0
            var ave_tems: Float = 0
            var ave_min_tems: Float = 0
            var ave_max_tems: Float = 0
            var ave_tem_diffs: Float = 0
            var min_tem_less_0s: Float = 0
            var max_tem_more_30s: Float = 0
            var ave_rhus: Float = 0
            var pre_20_20s: Float = 0
            var pre_more_25s: Float = 0
            var ave_wins: Float = 0
            var max_win_more_12s: Float = 0
            var sum = 0;
            for (facotrs <- values) {
                sum = sum + 1
                month = facotrs(2).toInt
                ave_tems = ave_tems + facotrs(15).toFloat
                ave_min_tems = ave_min_tems + facotrs(17).toFloat
                ave_max_tems = ave_max_tems + facotrs(16).toFloat
                ave_tem_diffs = ave_tem_diffs + facotrs(24).toFloat
                min_tem_less_0s = min_tem_less_0s + facotrs(36).toFloat
                max_tem_more_30s = max_tem_more_30s + facotrs(31).toFloat
                ave_rhus = ave_rhus + facotrs(45).toFloat
                pre_20_20s = pre_20_20s + facotrs(48).toFloat
                pre_more_25s = pre_more_25s + facotrs(61).toFloat
                ave_wins = ave_wins + facotrs(85).toFloat
                max_win_more_12s = max_win_more_12s + facotrs(92).toFloat

            }
            val crops = new Crops()
            crops.setProvince("hainan")
            crops.setMonth(month)
            crops.setAve_tem(ave_tems/sum)
            crops.setAve_min_tem(ave_min_tems/sum)
            crops.setAve_max_tem(ave_max_tems/sum)
            crops.setAve_tem_diff(ave_tem_diffs/sum)
            crops.setMin_tem_less_0(min_tem_less_0s/sum)
            crops.setMax_tem_more_30(max_tem_more_30s/sum)
            crops.setAve_rhu(ave_rhus/sum)
            crops.setPre_20_20(pre_20_20s/sum)
            crops.setPre_more_25(pre_more_25s/sum)
            crops.setAve_win(ave_wins/sum)
            crops.setMax_win_more_12(max_win_more_12s/sum)

            val dao = new Crops_WeatherImpl()
            dao.insert(crops)
        })


        sc.stop()
    }

}
