package com.weather.cropsweather

import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.{Hourse_WeatherImpl, Ssd_WeatherImpl}
import com.weather.domain.{Hourse, Ssd}
import com.weather.util.SparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/10.
  */
object HourseWeather_Ops {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(HourseWeather_Ops.getClass.getSimpleName)
        SparkUtil.setMaster(conf)
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        sc.textFile("C:/Users/admin/Desktop/cropsweather_1981-2010.txt")
            .map(line => {
                val factors:Array[String] = line.split(" ")
                (factors(0) + " " + factors(2), factors)
            })
            .filter(f => {
                val asn = !f._1.split(" ")(0).equals(Constants.ASN_FIELD_NAME)
                var default = true
                val values = f._2
                if (values(15).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(31).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(32).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(45).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(48).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(61).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(65).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(85).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(91).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(92).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                    default = false
                }
                asn && default
            })
            .groupByKey()
            .map(f => {
                (new SecondSortObj(f._1.split(" ")(0).toInt, f._1.split(" ")(1).toInt), f._2)
            })
            .sortByKey()
            .foreach(t => {
                val key = t._1
                val values = t._2
                var asn: String = ""
                var month: Int = 0
                var ave_tems: Float = 0
                var max_tem_more_30: Float = 0
                var max_tem_more_35: Float = 0
                var ave_rhu: Float = 0
                var pre_20_20: Float = 0
                var pre_more_25: Float = 0
                var max_conti_pre: Float = 0
                var ave_win: Float = 0
                var max_win_more_10: Float = 0
                var max_win_more_12: Float = 0
                var sum = 0;
                for (facotrs <- values) {
                    sum = sum + 1
                    asn = facotrs(0)
                    month = facotrs(2).toInt
                    ave_tems = ave_tems + facotrs(15).toFloat
                    max_tem_more_30 = max_tem_more_30 + facotrs(31).toFloat
                    max_tem_more_35 = max_tem_more_35 + facotrs(32).toFloat
                    ave_rhu = ave_rhu + facotrs(45).toFloat
                    pre_20_20 = pre_20_20 + facotrs(48).toFloat
                    pre_more_25 = pre_more_25 + facotrs(61).toFloat
                    max_conti_pre = max_conti_pre + facotrs(65).toFloat
                    ave_win = ave_win + facotrs(85).toFloat
                    max_win_more_10 = max_win_more_10 + facotrs(91).toFloat
                    max_win_more_12 = max_win_more_12 + facotrs(92).toFloat
                }
                val hourse = new Hourse()
                hourse.setAsn(asn)
                hourse.setMonth(month)
                hourse.setAve_tem(ave_tems)
                hourse.setMax_tem_more_30(max_tem_more_30)
                hourse.setMax_tem_more_35(max_tem_more_35)
                hourse.setAve_rhu(ave_rhu)
                hourse.setPre_20_20(pre_20_20)
                hourse.setPre_more_25(pre_more_25)
                hourse.setMax_conti_pre(max_conti_pre)
                hourse.setAve_win(ave_win)
                hourse.setMax_win_more_10(max_win_more_10)
                hourse.setMax_win_more_12(max_win_more_12)
                val dao = new Hourse_WeatherImpl()
                dao.insert(hourse)


            })





        sc.stop()

    }
}

class SecondSortObj(val first:Int, val second:Int) extends Serializable with Ordered[SecondSortObj]{
    override def compare(that: SecondSortObj): Int = {
        var ret = this.first - that.first
        if (ret == 0)
            ret = this.second - that.second
        ret
    }

    override def toString: String = this.first + " " + this.second
}
