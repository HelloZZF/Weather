package com.weather.mlib.model

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object CropsDecisionTreeClassificationModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(CropsDecisionTreeClassificationModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\crops.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //属性对的格式
        val categoricalFeaturesInfo = Map[Int, Int]()
        //计算信息增益的计算准则
        val impurity = "variance"
        //树的深度
        //只有深度在8以上预测值才会偏向于1不然为0.9几
        val maxDepth = 12
        //连续性特征最大切分的数据集合数量
        val maxBins = 32

        //决策树模型训练
        val model = DecisionTree.trainRegressor(trainingData, categoricalFeaturesInfo, impurity,
            maxDepth, maxBins)

        val labelsAndPredictions = testData.map { point =>
            val prediction = model.predict(point.features)
            (point.label, prediction)
        }

        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.4841002369114752
//        Test Mean Squared Error = 0.006257685098207834
//        Error Diff = 0.01257976443921329
//        Error rate = 0.5158997630885248

        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\CropsDecisionTreeClassificationModel")

        sc.stop()
    }


}
