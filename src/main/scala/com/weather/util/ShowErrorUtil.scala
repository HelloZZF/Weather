package com.weather.util

import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.rdd.RDD

/**
  * Created by admin on 2018/11/20.
  */
object ShowErrorUtil {

    def showError(test : RDD[Tuple2[Double,Double]]) = {

        test.foreach(f => {
            println("真实值：" + f._1 + " 预测值:" + f._2)
        })

        //多分类评价器
        val metrics = new MulticlassMetrics(test)
        val precision = metrics.precision
        println("MulticlassMetrics Precision: " + precision)

        //均方误差
        val testMSE = test.map{ case (v, p) => math.pow(v - p, 2) }.mean()
        println("Test Mean Squared Error = " + testMSE)

        //输出调试数据
        //println("Learned regression tree model:\n" + model.toDebugString)

        //预测值和真实值的差值
        val testDiff = test.map{ case (v, p) => math.abs(v - p) }.sum() / test.count()
        println("Error Diff = " + testDiff)

        //错误率
        val testErr = test.filter(r => r._1 != r._2).count.toDouble / test.count()
        println("Error rate = " + testErr)


    }
}
