package com.weather.mlib.console

import java.io.{BufferedReader, InputStreamReader}

import com.weather.dao.impl.CropsConditionWeatherImpl
import com.weather.domain.CropsCondition
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConversions._

/**
  * Created by admin on 2018/11/17.
  */
object CropsConsole {

    def main(args: Array[String]): Unit = {

        val conf = new SparkConf().setAppName("Crops_Console").setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        var cc = new CropsCondition()
        val cci = new CropsConditionWeatherImpl()
        val br = new BufferedReader(new InputStreamReader(System.in))
        val model = RandomForestModel.load(sc,"C:\\Users\\admin\\Desktop\\myRandomForestClassificationModel")
        println("__WelCome Crops Console__")

        while (true) {
            println("单作物判断请输入[1]")
            println("判断数据库中的所有数据请输入[2]")
            var fun = br.readLine()
            if (fun == "1") {
                singleCrop(br, sc, cci, model)
            }else if (fun == "2") {
                allCrops(cci, model)
            }else {
                println("输入有误，请重新输入")
            }
        }


        sc.stop()

    }

    def singleCrop(br:BufferedReader, sc:SparkContext, cci:CropsConditionWeatherImpl, model:RandomForestModel):Unit = {
        println("请输入作物名称: ")
        val line = br.readLine()
        println("正在计算...")
        val name = "'" + line + "'"
        val cc = cci.query(name)
        if (cc != null) {
            val v = Vectors.dense(Array(cc.getMin_tem.toDouble,cc.getMax_tem.toDouble,cc.getMin_pre.toDouble,cc.getMax_pre.toDouble,cc.getMin_ssd.toDouble,cc.getMax_ssd.toDouble))
            val res = model.predict(v)
            if (res == 1.0) {
                println("您好！" + line + "适合在海南种植!")
            }else {
                println("Sorry~ " + line + "不适合在海南种植")
            }

        }else {
            println("Sorry~ 数据库中暂无该作物数据！\n 请重新输入")
        }
    }

    def allCrops(cci:CropsConditionWeatherImpl, model:RandomForestModel) = {
        val ccs = cci.queryAll()
        for (c:CropsCondition <- ccs) {
            val v = Vectors.dense(Array(c.getMin_tem.toDouble,c.getMax_tem.toDouble,c.getMin_pre.toDouble,c.getMax_pre.toDouble,c.getMin_ssd.toDouble,c.getMax_ssd.toDouble))
            val res = model.predict(v)
            if (res == 1.0) {
                c.setLable("适合")
            }else {
                c.setLable("不适合")
            }
            println(c.toString)
        }
    }

}
