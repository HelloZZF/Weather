package com.weather.ssdweather

import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.SsdWeatherImpl
import com.weather.domain.Ssd
import com.weather.util.SparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/10.
  */
object SsdWeatherOps {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(SsdWeatherOps.getClass.getSimpleName)
        SparkUtil.setMaster(conf)
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        sc.textFile("C:/Users/admin/Desktop/ssdweather_2010-2017.txt")
            .map(line => {
                val factors:Array[String] = line.split(" ")
                new Tuple2[String, Array[String]](factors(0) + " " + factors(2), factors)
            })
            .filter(f => {
                val asn = !f._1.split(" ")(0).equals(Constants.ASN_FIELD_NAME)
                var default = true
                val values = f._2
                if (values(12).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(14).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(9).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(20).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(19).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(18).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                    default = false
                }
                asn && default
            })
            .map(t => (t._1.split(" ")(1).toInt, t._2))
            .groupByKey()
            .sortByKey()
            .foreach(t => {
                val key = t._1
                val values = t._2
                var province: String = ""
                var month: Int = 0
                var ave_tems: Float = 0
                var ave_rhus: Float = 0
                var pre_20_20s: Float = 0
                var max_wins: Float = 0
                var ssds: Float = 0
                var ssd_months: Float = 0
                var sum = 0;
                for (facotrs <- values) {
                    sum = sum + 1
                    month = facotrs(2).toInt
                    ave_tems = ave_tems + facotrs(12).toFloat
                    ave_rhus = ave_rhus + facotrs(14).toFloat
                    pre_20_20s = pre_20_20s + facotrs(9).toFloat
                    max_wins = max_wins + facotrs(20).toFloat
                    ssds = ssds + facotrs(19).toFloat
                    ssd_months = ssd_months + facotrs(18).toFloat
                }
                val ssd = new Ssd()
                ssd.setProvince("hainan")
                ssd.setMonth(month)
                ssd.setAve_tem(ave_tems/sum)
                ssd.setAve_rhu(ave_rhus/sum)
                ssd.setPre_20_20(pre_20_20s/sum)
                ssd.setMax_win(max_wins/sum)
                ssd.setSsd(ssds/sum)
                ssd.setSsd_month(ssd_months/sum)

                val dao = new SsdWeatherImpl()
                dao.insert(ssd)
            })


        sc.stop()
    }
}
