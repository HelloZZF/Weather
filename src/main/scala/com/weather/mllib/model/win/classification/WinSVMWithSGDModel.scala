package com.weather.mllib.model.win.classification

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 线性支持向量机
  * 通过找到支持向量从而获得分类平面的方法，称为支持向量机。
  * 因此支持向量机的目的是，通过划分最优的平面从而使不同的类别分开
  */
object WinSVMWithSGDModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinSVMWithSGDModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\Weather\\WeatherData\\MLlib\\WinC.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))
        //使用线性支持向量机模型训练
        val model = SVMWithSGD.train(trainingData, 50)

        val labelsAndPredictions = testData.map(f => {
            val predicts = model.predict(f.features)
            (f.label, predicts)
        })



        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.8632075471698113
//        Test Mean Squared Error = 0.1367924528301887
//        Error Diff = 0.13679245283018868
//        Error rate = 0.13679245283018868


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinSVMWithSGDModel")

        sc.stop()
    }
}
