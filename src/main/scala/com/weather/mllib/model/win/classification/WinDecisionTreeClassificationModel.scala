package com.weather.mllib.model.win.classification

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 决策树和朴素贝叶斯不受数据标准化的影响
  * ID3（它偏向于具有大量值的属性） 衍生 C4.5(entropy) CART(Gini)
  * 属性选择度量又称分裂规则，因为它们决定给定节点上的元组如何分裂。
  * 具有最好度量得分的属性被选作给定元组的分裂属性。
  * 比较流行的属性选择度量有--信息增益、信息增益率和Gini指标。
  */
object WinDecisionTreeClassificationModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinDecisionTreeClassificationModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\Weather\\WeatherData\\MLlib\\WinC.txt")
        //按比例随机切分为训练数据和测试数据两部分（交叉验证）
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //属性对的格式
        //月份是分类型特征所以用0->12表示，坑爹的是它是从0开始计数的所以只能强行13
        //把月份标识成分类型数据准确率没提升反而略微下降
        // (影响不大，我猜是就算不指明分类型特征也会把连续的特征按maxBins分类的)
        //val categoricalFeaturesInfo = Map[Int, Int](0->12)
        val categoricalFeaturesInfo = Map[Int, Int]()
        //类别数量
        val numClasses = 2
        //计算信息增益的计算准则（不纯度的度量方式）
        val impurity = "gini"
        //树的深度
        val maxDepth = 6
        //连续性特征最大切分的数据集合数量
        val maxBins = 32

        //决策树模型训练
        val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo, impurity,
            maxDepth, maxBins)

        val labelsAndPredictions = testData.map { point =>
            val prediction = model.predict(point.features)
            (point.label, prediction)
        }

        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.9808612440191388
//        Test Mean Squared Error = 0.01913875598086126
//        Error Diff = 0.019138755980861243
//        Error rate = 0.019138755980861243


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinDecisionTreeClassificationModel")

        sc.stop()
    }


}
