package com.weather.mlib.model.data

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 1、搜集数据
  * 从传统的数据库或者实时性较强的问卷调查等
  * 2、对数据进行预处理
  * 指的是对数据进行相应的处理，
  * 比如数据重排（对数据格式进行排列使之成为能够被机器识别的数据格式
  * 常用的方法有数据的向量矩阵化、数据降维和特征值提取）和数据清洗
  * 3、数据分析
  * 主要包括计算数据的最小值最大值，计算整体的平均偏差和标准偏差，以及查看数据的分布
  * 其次，标准偏差和其他分发值可以提供有关结果的稳定性和准确性等有用的信息
  * 4、模型的调整
  * 在整个数据挖掘和分析的过程中，需要不停的对使用数据模型进行调整，这里的调整指的是
  * 采用不同的算法对数据模型进行拟合分析，从而找到一个能够真正反应数据内在关系的数据分析算法
  * 5、建立模型测试数据
  * 模型根据分析数据算法的结果建立相应的分析模型，之后根据模型对部分数据进行测试，
  * 对测试结果需要及时准确地进行反馈，当一部分数据不尽人意时则需要重新更换测试模型，从而让整个测试达到最佳效果
  * 6、呈现结果
  * 数据挖掘的结果最终需要进行展示，而展示的过程尽量要求可视化为主
  */
object CropsDataPreprocessAndAnalysis {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[1]").setAppName("CropsDataPreprocessAndAnalysis")
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        //=====对数据进行预处理=====
        //因为这里是人工生成的测试数据所以在生成的时候就转换成了libSVM格式
        //并且特征值也已经确定
        //加载数据
        //将libSVM格式的数据加载到RDD[LabeledPoint]中，自动确定特征和分区的默认数目
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\crops.txt")
        //去除重复项，各个特征之间相关系数的确定
        //这里只取了各个特征的最小值，因为最大值和最小值之间肯定是有相关性的

        //相同种类不同特性
//        dataCorrelation(1,data,"温度",0,"降水量",2,1.0)
//        dataCorrelation(1,data,"温度",0,"光照",4,1.0)
//        dataCorrelation(1,data,"光照",4,"降水量",2,1.0)
//
//        dataCorrelation(1,data,"温度",0,"降水量",2,0.0)
//        dataCorrelation(1,data,"温度",0,"光照",4,0.0)
//        dataCorrelation(1,data,"光照",4,"降水量",2,0.0)

        //不同种类相同特性
//        dataCorrelation(2,data,"温度",0,"温度",0)
//        dataCorrelation(2,data,"光照",4,"光照",4)
//        dataCorrelation(2,data,"降水量",2,"降水量",2)






        //=====数据分析=====
        //微观分析--均值(反应了数据的集中程度)与方差(反应的数据的离合程度)的对比分析
        //宏观分析--（整体向量距离的计算方法，曼哈顿距离and欧几里得距离）

        //适合范围特征值
        statisticsAnalysis(1.0, data, "最小温度",0)
        statisticsAnalysis(1.0, data, "最大温度",1)
        statisticsAnalysis(1.0, data, "最小降水量",2)
        statisticsAnalysis(1.0, data, "最大降水量",3)
        statisticsAnalysis(1.0, data, "最小日照",4)
        statisticsAnalysis(1.0, data, "最大日照",5)

        //不适合范围特征值
        statisticsAnalysis(0.0, data, "最小温度",0)
        statisticsAnalysis(0.0, data, "最大温度",1)
        statisticsAnalysis(0.0, data, "最小降水量",2)
        statisticsAnalysis(0.0, data, "最大降水量",3)
        statisticsAnalysis(0.0, data, "最小日照",4)
        statisticsAnalysis(0.0, data, "最大日照",5)

        //全部范围特征值
        statisticsAnalysis(2.0, data, "最小温度",0)
        statisticsAnalysis(2.0, data, "最大温度",1)
        statisticsAnalysis(2.0, data, "最小降水量",2)
        statisticsAnalysis(2.0, data, "最大降水量",3)
        statisticsAnalysis(2.0, data, "最小日照",4)
        statisticsAnalysis(2.0, data, "最大日照",5)


        sc.stop()

    }

    def dataCorrelation(flag:Int, data:RDD[LabeledPoint], featureA:String, indexA:Int, featureB:String, indexB:Int, kind:Double = -1) = {
        //flag：1代表相同类别2代表不同类别
        if (flag == 1) {
            if (kind == 1.0) {
                val featureRDDA = data.filter(f => {
                    f.label == kind
                }).map(f =>{
                    f.features.toArray(indexA)
                })
                val featureRDDB = data.filter(f => {
                    f.label == kind
                }).map(f =>{
                    f.features.toArray(indexB)
                })
                //默认皮尔逊相关系数计算方法
                val correlation = Statistics.corr(featureRDDA,featureRDDB)
                println("相同种类适合范围的 " + featureA + "_" + featureB + " 的相关系数为：" + correlation)

            }else if(kind == 0.0) {
                val featureRDDA = data.filter(f => {
                    f.label == kind
                }).map(f =>{
                    f.features.toArray(indexA)
                })
                val featureRDDB = data.filter(f => {
                    f.label == kind
                }).map(f =>{
                    f.features.toArray(indexB)
                })
                val correlation = Statistics.corr(featureRDDA,featureRDDB)
                println("相同种类不适合范围的 " + featureA + "_" + featureB + " 的相关系数为：" + correlation)
            }
        }else if (flag == 2){
            val featureRDDA = data.filter(f => {
                f.label == 1.0
            }).map(f =>{
                f.features.toArray(indexA)
            })
            val featureRDDB = data.filter(f => {
                f.label == 0.0
            }).map(f =>{
                f.features.toArray(indexB)
            })
            val correlation = Statistics.corr(featureRDDA,featureRDDB)
            println("不同种类 " + featureA + "_" + featureB + " 的相关系数为：" + correlation)
        }
    }

    def statisticsAnalysis(flag:Double, data:RDD[LabeledPoint], feature:String, index:Int) = {
        //计算统计量
        if (flag != 2) {
            val summary = Statistics.colStats(data.filter(f => {
                f.label == flag
            }).map(f =>{
                Vectors.dense(f.features.toArray(index))
            }))
            if (flag == 1.0) {
                println("适合范围的 " + feature +" 的均值为：" + summary.mean)
                println("适合范围的  " + feature +" 的方差为：" + summary.variance)
                println("适合范围的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
                println("适合范围的 " + feature +" 的欧几里得距离为：" + summary.normL2)
            }else if (flag == 0.0){
                println("不适合范围的 " + feature +" 的均值为：" + summary.mean)
                println("不适合范围的 " + feature +" 的方差为：" + summary.variance)
                println("不适合范围的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
                println("不适合范围的 " + feature +" 的欧几里得距离为：" + summary.normL2)
            }
        }else if(flag == 2){
            val summary = Statistics.colStats(data.map(f =>{
                Vectors.dense(f.features.toArray(index))
            }))
            println("全部范围的 " + feature +" 的均值为：" + summary.mean)
            println("全部范围的 " + feature +" 的方差为：" + summary.variance)
            println("全部范围的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
            println("全部范围的 " + feature +" 的欧几里得距离为：" + summary.normL2)
        }


    }

}
