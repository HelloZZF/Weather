package com.weather

/**
  * Created by admin on 2018/11/18.
  */
object winTest {
    def main(args: Array[String]): Unit = {
        //110
//        1305
//        贝碧嘉
//        Bebinca
//        琼海市潭门镇
//        2013年6月22日
//            9级，23m/s，984hPa
//            9级，23m/s，988hPa
        val str = "59855 2012 6 12.9000 999015 990.3000 23.9000 1004.3000 37.3000 513.9000 998.2000 1.6000 28.6000 30.5000 79.0000 26.0000 32.7000 17 41.0000 164.1000 6.1000 999015 124.0000 999999.0000 "
            .split(" ")
        val strr = "59855 2013 6 17.4000 999016 981.1000 23.2000 1007.9000 36.9000 162.5000 1000.8000 2.0000 28.7000 29.4000 76.0000 25.7000 33.3000 8 53.0000 210.7000 7.8000 999001 69.7000 999999.0000"
            .split(" ")
        //区站号
        println("区站号" + str(0))
        //年
        println("年" + str(1))
        //月
        println("月" + str(2))
        //极大风速
        println("极大风速" + str(3))
        //平均两分钟风速
//        println("平均两分钟风速" + str(11))
        //最大风速
        println("最大风速" + str(20))
        //平均气压
        println("平均气压" + str(10))
        //水气压
        println("水气压" + str(13))
        //20-20降水量
        println("20降水量" + str(9))
//        println("平均气温" + str(12))
//        println("平均湿度" + str(14))

        println("______________________________________________________")
        //区站号
        println("区站号" + strr(0))
        //年
        println("年" + strr(1))
        //月
        println("月" + strr(2))
        //极大风速
        println("极大风速" + strr(3))
        //平均两分钟风速
//        println("平均两分钟风速" + strr(11))
        //最大风速
        println("最大风速" + strr(20))
        //平均气压
        println("平均气压" + strr(10))
        //水气压
        println("水气压" + strr(13))
        //20-20降水量
        println("20-20降水量" + strr(9))
//        println("平均气温" + strr(12))
//        println("平均湿度" + strr(14))
    }
}
