package com.weather.mllib.console

import java.io.{BufferedReader, InputStreamReader}

import com.weather.dao.impl.CropsConditionWeatherImpl
import com.weather.domain.CropsCondition
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/18.
  */
object WinConsole {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Win_Console").setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        var cc = new CropsCondition()
        val cci = new CropsConditionWeatherImpl()
        val br = new BufferedReader(new InputStreamReader(System.in))
        val model = DecisionTreeModel.load(sc,"C:\\Users\\admin\\Desktop\\Weather\\WeatherData\\MLlib\\WinMDecisionTreeRegressorModel")
        println("__WelCome Win Console__")
        while (true) {
            println("请输入年份: ")
            val year = br.readLine()
            println("请输入月份: ")
            val month = br.readLine()
            val res = model.predict(Vectors.dense(Array(year.toDouble,month.toDouble)))
            println("预测值为: " + res.formatted("%.2f") + " m/s")
        }



        sc.stop()
    }

}
