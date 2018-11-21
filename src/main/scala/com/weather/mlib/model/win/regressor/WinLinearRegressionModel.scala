package com.weather.mlib.model.win.regressor

import com.weather.util.ShowErrorUtil
import org.apache.spark.mllib.regression.{LassoWithSGD, LinearRegressionWithSGD, RidgeRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils

/**
  * Created by admin on 2018/11/21.
  */
object WinLinearRegressionModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(WinSDecisionTreeRegressorModel.getClass.getName).setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        //加载LibSVM格式的数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\WinS.txt")
        //按比例随机切分为训练数据和测试数据两部分
        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))


        val numIterations = 100;
        //线性最小二乘
        val linearModel = LinearRegressionWithSGD.train(trainingData, numIterations)
        //套索
        val ridgeModel = RidgeRegressionWithSGD.train(trainingData, numIterations)
        //岭回归
        val lassoModel = LassoWithSGD.train(trainingData, numIterations)


//        val linearModelTest = testData.map { point =>
//            val prediction = linearModel.predict(point.features)
//            (point.label, prediction)
//        }
//        ShowErrorUtil.showError(linearModelTest)

//        val ridgeModelTest = testData.map { point =>
//            val prediction = ridgeModel.predict(point.features)
//            (point.label, prediction)
//        }
//        ShowErrorUtil.showError(ridgeModelTest)

//        val lassoModelTest = testData.map { point =>
//            val prediction = lassoModel.predict(point.features)
//            (point.label, prediction)
//        }
//        ShowErrorUtil.showError(lassoModelTest)



        sc.stop()
    }
}
