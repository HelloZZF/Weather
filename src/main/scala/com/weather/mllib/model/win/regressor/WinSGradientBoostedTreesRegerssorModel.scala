package com.weather.mllib.model.win.regressor

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.GradientBoostedTrees
import org.apache.spark.mllib.tree.configuration.BoostingStrategy
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object WinSGradientBoostedTreesRegerssorModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinSGradientBoostedTreesRegerssorModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\Weather\\WeatherData\\MLlib\\WinS.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //使用梯度提升树模型训练
        //创建算法类型
        val boostingStrategy = BoostingStrategy.defaultParams("Regression")
        //设置迭代次数
        boostingStrategy.setNumIterations(3)
        //设置决策树高度
        boostingStrategy.treeStrategy.setMaxDepth(5)
        //设置特征格式
        //boostingStrategy.treeStrategy.setCategoricalFeaturesInfo(java.util.Map[Integer, Integer]())
        //训练模型
        val model = GradientBoostedTrees.train(trainingData, boostingStrategy)

        val labelsAndPredictions = testData.map(f => {
            val predicts = model.predict(f.features)
            (f.label, predicts)
        })



        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.0
//        Test Mean Squared Error = 2.9505054326311395
//        Error Diff = 1.266440642121692
//        Error rate = 1.0


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinSGradientBoostedTreesRegerssorModel")

        sc.stop()
    }
}
