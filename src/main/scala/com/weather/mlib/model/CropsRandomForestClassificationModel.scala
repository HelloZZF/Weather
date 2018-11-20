package com.weather.mlib.model

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object CropsRandomForestClassificationModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(CropsRandomForestClassificationModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\crops.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //使用随机森林分类模型进行训练
        val numClasses = 2
        val categoricalFeaturesInfo = Map[Int, Int]()
        //树的数目，每棵树会被分配到每个节点分别计算
        val numTrees = 3
        //每个节点中要计算的特征数量
        val featureSubsetStrategy = "auto"
        //计算信息增益的形式
        val impurity = "gini"
        val maxDepth = 4
        val maxBins = 100

        val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
            numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

        val labelsAndPredictions = testData.map(f => {
            val predicts = model.predict(f.features)
            (f.label, predicts)
        })


        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.9734223706176962
//        Test Mean Squared Error = 0.026577629382303583
//        Error Diff = 0.02657762938230384
//        Error rate = 0.02657762938230384

        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\CropsRandomForestClassificationModel")

        sc.stop()
    }
}
