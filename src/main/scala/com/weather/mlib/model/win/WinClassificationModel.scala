package com.weather.mlib.model.win

import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/18.
  */
object WinClassificationModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[1]").setAppName("Win_ClassificationModel")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\winS.txt")
        //val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\winC.txt")
        //val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\winM.txt")
        //随机划分为训练数据和测试数据
        val splits = data.randomSplit(Array(0.7,0.3))
        val trainData = splits(0)
        val testData = splits(1)

        //使用随机森林分类模型进行训练
        val numClasses = 30
        //val numClasses = 2
        val categoricalFeaturesInfo = Map[Int, Int]()
        val numTrees = 5
        val featureSubsetStrategy = "auto"
        val impurity = "gini"
        val maxDepth = 4
        val maxBins = 100
        val randomForestModel = RandomForest.trainClassifier(trainData, numClasses, categoricalFeaturesInfo,
            numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
        println("RandomForest Classifier-----------------")
        val testRandomForestModel = testData.map(f => {
            val predicts = randomForestModel.predict(f.features)
            (f.label, predicts)
        })
        TestAndErrorRateLinearModel(testRandomForestModel)

        //使用决策树分类模型进行训练
        val numClassesC = 30
        //val numClassesC = 2
        val categoricalFeaturesInfoC = Map[Int, Int]()
        val impurityC = "gini"
        val maxDepthC = 5
        val maxBinsC = 32
        val decisionTreeModel = DecisionTree.trainClassifier(trainData,
            numClassesC,categoricalFeaturesInfoC,impurityC,maxDepthC,maxBinsC)
        println("DecisionTree Classifier-----------------")
        val testDecisionTreeModel = testData.map(f => {
            val predicts = decisionTreeModel.predict(f.features)
            (f.label, predicts)
        })
        TestAndErrorRateLinearModel(testDecisionTreeModel)




        sc.stop()
    }

    def TestAndErrorRateLinearModel(test : RDD[Tuple2[Double,Double]]) = {

//        test.foreach(f => {
//            println("真实值：" + f._1 + " 预测值:" + f._2)
//        })

        val metrics = new MulticlassMetrics(test)
        val precision = metrics.precision
        println("Precision: " + precision)

        val testMSE = test.map{ case (v, p) => math.pow(v - p, 2) }.mean()
        println("Test Mean Squared Error = " + testMSE)
        //println("Learned regression tree model:\n" + model.toDebugString)

        val testDiff = test.map{ case (v, p) => math.abs(v - p) }.sum() / test.count()
        println("Error Diff = " + testDiff)

        val testErr = test.filter(r => r._1 != r._2).count.toDouble / test.count()
        println("Error rate = " + testErr)


    }

}
