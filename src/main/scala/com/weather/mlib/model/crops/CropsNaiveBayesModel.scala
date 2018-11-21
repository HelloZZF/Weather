package com.weather.mlib.model.crops

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object CropsNaiveBayesModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(CropsNaiveBayesModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\crops.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))
        //使用朴素贝叶斯模型训练
        //平滑参数
        val lambda = 1.0
        val model = NaiveBayes.train(trainingData, 1.0)

        val labelsAndPredictions = testData.map(f => {
            val predicts = model.predict(f.features)
            (f.label, predicts)
        })



        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.7596819270694704
//        Test Mean Squared Error = 0.24031807293053087
//        Error Diff = 0.24031807293052967
//        Error rate = 0.24031807293052967

        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\CropsNaiveBayesModel")

        sc.stop()
    }
}
