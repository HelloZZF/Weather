package com.weather.mlib.model.win

import com.weather.constants.Constants
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by admin on 2018/11/18.
  */
object WinRegressorModel {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Win_Model").setMaster("local[1]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        //加载并清洗数据
        val origin = sc.textFile("C:/Users/admin/Desktop/hainan_month1951-2017.txt")
                        .map(f => {
                            val factors = f.split(" ")
                            Tuple2(factors(0),factors)
                        })
                        .filter(f => {
                            val asn = f._1.equals(Constants.ASN_HAIKOU)
                            var default = true
                            val values = f._2
                            if (values(3).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(20).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                                values(10).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(13).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
                                values(9).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
                                default = false
                            }
                            asn && default
                        })
//            .map(f => {
//                (f._2(20), " 1:" + f._2(1) + " 2:" + f._2(2))
//            })
//            .foreach(f => {
//                val res = f._1 + f._2 + "\n"
//                FileUtil.writeToFile(res, "C:/Users/admin/Desktop/", "winM.txt")
//            })
//            .map(f => {
//                if (f._2(20).toDouble >= 14.0) {
//                    ("1", " 1:" + f._2(2) + " 2:" + f._2(3) + " 3:" + f._2(10) + " 4:" + f._2(13) + " 5:" + f._2(9))
//                }else {
//                    ("0", " 1:" + f._2(2) + " 2:" + f._2(3) + " 3:" + f._2(10) + " 4:" + f._2(13) + " 5:" + f._2(9))
//                }
//            })
//            .foreach(f => {
//                val res = f._1 + f._2 + "\n"
//                FileUtil.writeToFile(res, "C:/Users/admin/Desktop/", "winC.txt")
//            })
//            .map(f => {
//                (f._2(20), " 1:" + f._2(2) + " 2:" + f._2(3) + " 3:" + f._2(10) + " 4:" + f._2(13) + " 5:" + f._2(9))
//            })
//            .foreach(f => {
//                val res = f._1 + f._2 + "\n"
//                FileUtil.writeToFile(res, "C:/Users/admin/Desktop/", "winS.txt")
//            })

        //转化为LabledPoint形式,并进行缓存以便多次调用
//        val labeledPoint = origin.map(f => {
//            LabeledPoint(f._2(20).toDouble, Vectors.dense(Array(f._2(3).toDouble,f._2(10).toDouble,f._2(13).toDouble,f._2(9).toDouble)))
//        }).cache()
        //加载风速-年-月数据
        val labeledPoint = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\winM.txt").cache()
        //按比例随机分成测试数据和训练数据
        val splits = labeledPoint.randomSplit(Array(0.7,0.3))
        val trainData = splits(0).cache()
        val testData = splits(1).cache()

        //运用三种线性回归模型训练数据
//        val numIterations = 100;
//        val linearModel = LinearRegressionWithSGD.train(trainData, numIterations)
//        val ridgeModel = RidgeRegressionWithSGD.train(trainData, numIterations)
//        val lassoModel = LassoWithSGD.train(trainData, numIterations)

        //决策树模型训练数据
        val categoricalFeaturesInfoR = Map[Int, Int]()
        val impurityR = "variance"
        val maxDepthR = 5
        val maxBinsR = 32

        //决策树回归训练
        val decisiontreeRegressorModel = DecisionTree.trainRegressor(trainData, categoricalFeaturesInfoR, impurityR,
            maxDepthR, maxBinsR)
        println("DecisionTree Regressor-----------------")
        val testdecisionRegressontreeModel = testData.map(f => {
            val feature = decisiontreeRegressorModel.predict(f.features)
            (f.label, feature)
        })
        TestAndErrorRateLinearModel(testdecisionRegressontreeModel)
        //保存模型
        //decisiontreeRegressorModel.save(sc, "C:\\Users\\admin\\Desktop\\decisiontreeRegressorModel")

        //随机森林回归训练
        val categoricalFeaturesInfoF = Map[Int, Int]()
        val impurityF = "variance"
        val maxDepthF = 4
        val numTreesF = 2
        val maxBinsF = 100
        val featureSubsetStrategyF= "auto"
        val randomForestRegressorModel = RandomForest.trainRegressor(trainData, categoricalFeaturesInfoF,
            numTreesF,featureSubsetStrategyF,impurityF,maxDepthF, maxBinsF)
        println("RandomForest Regressor-----------------")
        val testrandomForestRegressorModel = testData.map(f => {
            val feature = randomForestRegressorModel.predict(f.features)
            (f.label, feature)
        })
        TestAndErrorRateLinearModel(testrandomForestRegressorModel)
        //保存模型
        //randomForestRegressorModel.save(sc, "C:\\Users\\admin\\Desktop\\randomForestRegressorModel")

        //测试线性回归模型并输出误差
//        println("linearModel--------------------")
//        val testlinearModel = testData.map(f => {
//            val predicts = linearModel.predict(f.features)
//            (f.label, predicts)
//        })
//        TestAndErrorRateLinearModel(testlinearModel)
//        println("ridgeModel--------------------")
//        val testridgeModel = testData.map(f => {
//            val predicts = ridgeModel.predict(f.features)
//            (f.label, predicts)
//        })
//        TestAndErrorRateLinearModel(testridgeModel)
//        println("lassoModel--------------------")
//        val testlassoModel = testData.map(f => {
//            val predicts = lassoModel.predict(f.features)
//            (f.label, predicts)
//        })
//        TestAndErrorRateLinearModel(testlassoModel)





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
