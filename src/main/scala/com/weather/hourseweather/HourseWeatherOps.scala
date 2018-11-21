package com.weather.hourseweather

import com.weather.constants.Constants
import com.weather.dao.impl.syrt_weather.HourseWeatherImpl
import com.weather.domain.Hourse
import com.weather.util.SparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/10.
  */
object HourseWeatherOps {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(HourseWeatherOps.getClass.getSimpleName)
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
                    values(16).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(17).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(32).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(45).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(48).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(61).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(65).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(85).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                    values(91).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(92).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                    default = false
                }
                asn && default
            })
//            .map(f => {
//                (f._1.split(" ")(0), f._2)
//            })
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
                var ave_max_tems: Float = 0
                var ave_min_tems: Float = 0
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
                    ave_max_tems = ave_max_tems + facotrs(16).toFloat
                    ave_min_tems = ave_min_tems + facotrs(17).toFloat
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
                hourse.setAve_tem(ave_tems/sum)
                hourse.setAve_max_tem(ave_max_tems/sum)
                hourse.setAve_min_tem(ave_min_tems/sum)
                hourse.setMax_tem_more_30(max_tem_more_30/sum)
                hourse.setMax_tem_more_35(max_tem_more_35/sum)
                hourse.setAve_rhu(ave_rhu/sum)
                hourse.setPre_20_20(pre_20_20/sum)
                hourse.setPre_more_25(pre_more_25/sum)
                hourse.setMax_conti_pre(max_conti_pre/sum)
                hourse.setAve_win(ave_win/sum)
                hourse.setMax_win_more_10(max_win_more_10/sum)
                hourse.setMax_win_more_12(max_win_more_12/sum)
                val dao = new HourseWeatherImpl()
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
