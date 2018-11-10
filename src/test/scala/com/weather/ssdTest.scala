package com.weather

/**
  * Created by admin on 2018/11/10.
  */
object ssdTest {
//    private String province;
//    private int month;
//    private float ave_tem;
//    private float ave_rhu;
//    private float pre_20_20;
//    private float max_win;
//    private float ssd;
//    private float ssd_month;
    def main(args: Array[String]): Unit = {
        val str = "59758 2015 9 18.7000 999004 995.4000 23.8000 1008.1000 34.5000 394.7000 1001.6000 2.8000 28.4000 31.3000 82.0000 25.8000 32.1000 14 64.0000 233.8000 11.1000 999004 144.7000 999999.0000 ";
        val list = str.trim.split(" ")
        val month = list(2)
        println(month)
        val ave_tem = list(12)
        println(ave_tem)
        val ave_rhu = list(14)
        println(ave_rhu)
        val pre_20_20 = list(9)
        println(pre_20_20)
        val max_win = list(20)
        println(max_win)
        val ssd = list(19)
        println(ssd)
        val ssd_month = list(18)
        println(ssd_month)

    }
}
