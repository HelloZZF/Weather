package com.weather.mlib.model.crops

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * ID3（它偏向于具有大量值的属性） 衍生 C4.5(entropy) CART(Gini)
  * 属性选择度量又称分裂规则，因为它们决定给定节点上的元组如何分裂。
  * 具有最好度量得分的属性被选作给定元组的分裂属性。
  * 比较流行的属性选择度量有--信息增益、信息增益率和Gini指标。
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
        val impurity = "gini"
        //树的深度
        val maxDepth = 5
        //连续性特征最大切分的数据集合数量
        val maxBins = 32
        //分类类数
        val numclass = 2

        //决策树模型训练
        val model = DecisionTree.trainClassifier(trainingData, numclass, categoricalFeaturesInfo, impurity,
            maxDepth, maxBins)

        val labelsAndPredictions = testData.map { point =>
            val prediction = model.predict(point.features)
            (point.label, prediction)
        }

        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.9865674110835401
//        Test Mean Squared Error = 0.013432588916459844
//        Error Diff = 0.013432588916459884
//        Error rate = 0.013432588916459884

        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\CropsDecisionTreeClassificationModel")

        sc.stop()
    }


}
