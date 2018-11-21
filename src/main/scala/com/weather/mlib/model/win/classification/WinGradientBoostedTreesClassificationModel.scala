package com.weather.mlib.model.win.classification

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.tree.GradientBoostedTrees
import org.apache.spark.mllib.tree.configuration.BoostingStrategy
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/20.
  */
object WinGradientBoostedTreesClassificationModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinGradientBoostedTreesClassificationModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\WinC.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))

        //使用梯度提升树模型训练
        //创建算法类型
        val boostingStrategy = BoostingStrategy.defaultParams("Classification")
        //设置迭代次数
        boostingStrategy.setNumIterations(3)
        //设置分类数目
        boostingStrategy.treeStrategy.setNumClasses(2)
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
//        MulticlassMetrics Precision: 0.9179487179487179
//        Test Mean Squared Error = 0.08205128205128204
//        Error Diff = 0.08205128205128205
//        Error rate = 0.08205128205128205


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinGradientBoostedTreesClassificationModel")

        sc.stop()
    }
}
