package com.weather.mlib.model.win.classification

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 逻辑回归的计算可以转化成数据集的特征向量与系数θ共同完成，然后求得其加权和最为最终的判断的结果
  * 最终逻辑回归问题又称为对系数θ的求值问题
  */
object WinLogisticRegressionWithSGDModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinLogisticRegressionWithSGDModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\WinC.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))
        //使用逻辑回归模型训练
        val model = LogisticRegressionWithSGD.train(trainingData, 50)

        val labelsAndPredictions = testData.map(f => {
            val predicts = model.predict(f.features)
            (f.label, predicts)
        })



        //误差评估
        ShowErrorUtil.showError(labelsAndPredictions)
//        MulticlassMetrics Precision: 0.9428571428571428
//        Test Mean Squared Error = 0.05714285714285714
//        Error Diff = 0.05714285714285714
//        Error rate = 0.05714285714285714


        //保存模型
        //model.save(sc, "C:\\Users\\admin\\Desktop\\WinLogisticRegressionWithSGDModel")

        sc.stop()
    }
}
