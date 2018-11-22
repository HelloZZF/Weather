package com.weather.mlib.model.win.classification

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 贝叶斯公式：
  * P(A|B)=P(A)P(B|A)/P(B)
  * 朴素贝叶斯法是基于贝叶斯定理与特征条件独立假设的分类方法
  * 朴素贝叶斯分类器基于一个简单的假定：给定目标值时属性之间相互条件独立。
  * 每个待分项分配到集合中具体分类的概率是多少
  */
object WinNaiveBayesModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinNaiveBayesModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\WinC.txt")
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
//        MulticlassMetrics Precision: 0.8309859154929577
//        Test Mean Squared Error = 0.16901408450704228
//        Error Diff = 0.16901408450704225
//        Error rate = 0.16901408450704225


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinNaiveBayesModel")

        sc.stop()
    }
}
