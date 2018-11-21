package com.weather.mlib.model.data

import com.weather.constants.Constants
import com.weather.util.FileUtil
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD

/**
  * Created by admin on 2018/11/21.
  */
object WinDataPreprocessAndAnalysis {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[1]").setAppName(WinDataPreprocessAndAnalysis.getClass.getName)
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")

        //=====对数据进行预处理=====
        //对源数据进行数据清洗和数据重排并写出到文件
//        val origin = sc.textFile("C:/Users/admin/Desktop/hainan_month1951-2017.txt")
//            .map(f => {
//                val factors = f.split(" ")
//                Tuple2(factors(0),factors)
//            })
//            .filter(f => {
//                val asn = f._1.equals(Constants.ASN_HAIKOU)
//                var default = true
//                val values = f._2
//                if (values(3).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(20).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
//                    values(10).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||values(13).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)||
//                    values(9).contains(Constants.FACTOR_DEFAULT_VALUE_CONTAIN)){
//                    default = false
//                }
//                asn && default
//            })
//            .map(f => {
//                if (f._2(20).toDouble >= 14.0) {
//                    ("1", " 1:" + f._2(2).toInt + " 2:" + f._2(3) + " 3:" + f._2(10) + " 4:" + f._2(13) + " 5:" + f._2(9))
//                }else {
//                    ("0", " 1:" + f._2(2).toInt + " 2:" + f._2(3) + " 3:" + f._2(10) + " 4:" + f._2(13) + " 5:" + f._2(9))
//                }
//            })
//            .foreach(f => {
//                val res = f._1 + f._2 + "\n"
//                FileUtil.writeToFile(res, "C:/Users/admin/Desktop/", "winCC.txt")
//            })


        //加载数据
        val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\admin\\Desktop\\WinC.txt")
        //去除重复项，各个特征之间相关系数的确定
        //这里只取了各个特征的最小值，因为最大值和最小值之间肯定是有相关性的

        //相同种类不同特性
        //0 月份 1 极大风速 2 平均气压 3 水气压 4 降水量
//        dataCorrelation(1,data,"极大风速",1,"平均气压",2,1.0)
//        dataCorrelation(1,data,"极大风速",1,"水气压",3,1.0)
//        dataCorrelation(1,data,"极大风速",1,"降水量",4,1.0)
//        dataCorrelation(1,data,"平均气压",2,"水气压",3,1.0)
//        dataCorrelation(1,data,"平均气压",2,"降水量",4,1.0)
//        dataCorrelation(1,data,"水气压",3,"降水量",4,1.0)
//
//        dataCorrelation(1,data,"极大风速",1,"平均气压",2,0.0)
//        dataCorrelation(1,data,"极大风速",1,"水气压",3,0.0)
//        dataCorrelation(1,data,"极大风速",1,"降水量",4,0.0)
//        dataCorrelation(1,data,"平均气压",2,"水气压",3,0.0)
//        dataCorrelation(1,data,"平均气压",2,"降水量",4,0.0)
//        dataCorrelation(1,data,"水气压",3,"降水量",4,0.0)

        //不同种类相同特性(这里必须是两种类别的数量相等)
//        dataCorrelation(2,data,"极大风速",1,"极大风速",1)
//        dataCorrelation(2,data,"平均气压",2,"平均气压",2)
//        dataCorrelation(2,data,"水气压",3,"水气压",3)
//        dataCorrelation(2,data,"降水量",4,"降水量",4)

        //dataCorrelation(2,data,"月份",0,"月份",0)



        //=====数据分析=====
        //微观分析--均值(反应了数据的集中程度)与方差(反应的数据的离合程度)的对比分析
        //宏观分析--（整体向量距离的计算方法，曼哈顿距离and欧几里得距离）

        //适合范围特征值
        statisticsAnalysis(1.0, data, "极大风速",1)
        statisticsAnalysis(1.0, data, "平均气压",2)
        statisticsAnalysis(1.0, data, "水气压",3)
        statisticsAnalysis(1.0, data, "降水量",4)

        //不适合范围特征值
        statisticsAnalysis(0.0, data, "极大风速",1)
        statisticsAnalysis(0.0, data, "平均气压",2)
        statisticsAnalysis(0.0, data, "水气压",3)
        statisticsAnalysis(0.0, data, "降水量",4)

        //全部范围特征值
        statisticsAnalysis(2.0, data, "极大风速",1)
        statisticsAnalysis(2.0, data, "平均气压",2)
        statisticsAnalysis(2.0, data, "水气压",3)
        statisticsAnalysis(2.0, data, "降水量",4)


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
                println("相同种类有影响的 " + featureA + "_" + featureB + " 的相关系数为：" + correlation)

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
                println("相同种类无影响的 " + featureA + "_" + featureB + " 的相关系数为：" + correlation)
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
                println("有可能的 " + feature +" 的均值为：" + summary.mean)
                println("有可能的 " + feature +" 的方差为：" + summary.variance)
                println("有可能的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
                println("有可能的 " + feature +" 的欧几里得距离为：" + summary.normL2)
            }else if (flag == 0.0){
                println("不可能的 " + feature +" 的均值为：" + summary.mean)
                println("不可能的 " + feature +" 的方差为：" + summary.variance)
                println("不可能的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
                println("不可能的 " + feature +" 的欧几里得距离为：" + summary.normL2)
            }
        }else if(flag == 2){
            val summary = Statistics.colStats(data.map(f =>{
                Vectors.dense(f.features.toArray(index))
            }))
            println("全部数据的 " + feature +" 的均值为：" + summary.mean)
            println("全部数据的 " + feature +" 的方差为：" + summary.variance)
            println("全部数据的 " + feature +" 的曼哈顿距离为：" + summary.normL1)
            println("全部数据的 " + feature +" 的欧几里得距离为：" + summary.normL2)
        }


    }

}
