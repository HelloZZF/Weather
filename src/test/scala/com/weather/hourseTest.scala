package com.weather

/**
  * Created by admin on 2018/11/10.
  */
object hourseTest {
//    private String city;
//    private int month;
//    private float ave_tem;
//    private float ave_max_tem;
//    private float ave_min_tem;
//    private float max_tem_more_30;
//    private float max_tem_more_35;
//    private float ave_rhu;
//    private float pre_20_20;
//    private float pre_more_25;
//    private float max_conti_pre;
//    private float max_no_pre;
//    private float ave_win;
//    private float max_win_more_10;
//    private float max_win_more_15;
    def main(args: Array[String]): Unit = {
        val str = "59842 119812010 5 1007.7000 1004.1000 1014.7000 1981 4 993.3000 1986 20 0.7000 0.9000 1.7000 -1.9000 27.3000 32.3000 24.1000 40.3000 2003 7 16.1000 1984 7 8.2000 14.2000 2005 5 1.9000 2008 11 24.1000 6.4000 2.0000 0.1000 0.0000 0.0000 0.0000 0.0000 0.0000 0.0000 0.6000 0.8000 1.6000 -1.7000 82.0000 2.0000 3.0000 122.7000 118.6000 260.5000 1988 11.8000 2006 145.1000 1985 1 13.3000 9.1000 5.7000 3.6000 1.3000 0.4000 0.0000 0.0000 11.0000 179.0000 2009.0000 30 185.4000 5.0000 1985 2 27.0000 1995.0000 4 999999.0000 999999 999999 46.0000 56.1000 55.0000 67.9000 112.0000 -90.0000 2.1000 23.6000 13.0000 2005 12 16.9000 0.4000 0.2000 0.0000 0.0000 17 15.0000 999003 11.0000"
        val list = str.trim.split(" ")

        val month = list(2)
        println(month)

        val ave_tem = list(15)
        println(ave_tem)

        val ave_max_tem = list(16)
        println(ave_max_tem)

        val ave_min_tem = list(17)
        println(ave_min_tem)

        val max_tem_more_30 = list(31)
        println(max_tem_more_30)

        val max_tem_more_35 = list(32)
        println(max_tem_more_35)

        val ave_rhu = list(45)
        println(ave_rhu)

        val pre_20_20 = list(48)
        println(pre_20_20)

        val pre_more_25 = list(61)
        println(pre_more_25)

        val max_conti_pre = list(65)
        println(max_conti_pre)

        val max_no_pre = list(73)
        println(max_no_pre)

        val ave_win = list(85)
        println(ave_win)

        val max_win_more_10 = list(91)
        println(max_win_more_10)

        val max_win_more_15 = list(93)
        println(max_win_more_15)

    }

}
