package com.weather.mllib.model.win.regressor

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object WinSDecisionTreeRegressorModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinSDecisionTreeRegressorModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\Weather\\WeatherData\\MLlib\\WinS.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //属性对的格式
        //月份是分类型特征所以用0->12表示，坑爹的是它是从0开始计数的所以只能强行13
        //回归中把月份标识成分类型数据准确率没提升反而下降
        //分类中这个数据集在决策树模型中准确率也是略微下降
        //val categoricalFeaturesInfo = Map[Int, Int](0->12)
        val categoricalFeaturesInfo = Map[Int, Int]()
        //计算信息增益的计算准则（方差）
        val impurity = "variance"
        //树的深度
        val maxDepth = 5
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
//        MulticlassMetrics Precision: 0.0
//        Test Mean Squared Error = 2.971470562341849
//        Error Diff = 1.2467058276738636
//        Error rate = 1.0


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinSDecisionTreeRegressorModel")

        sc.stop()
    }


}
