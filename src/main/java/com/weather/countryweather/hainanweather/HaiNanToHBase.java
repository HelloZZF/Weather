package com.weather.countryweather.hainanweather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * Created by admin on 2018/12/2.
 */
public class HaiNanToHBase {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "master,slave01,slave02");
        Job job = Job.getInstance(config, HaiNanToHBase.class.getName());

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Put.class);

        //只有map没有reduce，所以设置reduce的数目为0
        job.setNumReduceTasks(0);

        //设置数据的输入路径,没有使用参数，直接在程序中写入HDFS的路径
        FileInputFormat.setInputPaths(job, new Path("/output/weather/hainan_weather/part-r-00000"));


        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();
        //创建表
        TableName tb_name = TableName.valueOf("hainan_weather");
        HTableDescriptor desc = new HTableDescriptor(tb_name);
        HColumnDescriptor cf = new HColumnDescriptor("field");
        //设置列族
        desc.addFamily(cf);
        admin.createTable(desc);

        //驱动函数
        TableMapReduceUtil.initTableReducerJob("hainan_weather",null, job);
        TableMapReduceUtil.addDependencyJars(job);

        job.setJarByClass(MyMapper.class);
        job.waitForCompletion(true);


    }

    private static class MyMapper extends Mapper<LongWritable, Text, NullWritable, Put> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split(" ");
            Put put = new Put( key.toString().getBytes());
            put.addColumn("field".getBytes(),"province".getBytes(),splits[0].getBytes());
            put.addColumn("field".getBytes(),"year".getBytes(),splits[1].getBytes());
            put.addColumn("field".getBytes(),"month".getBytes(),splits[2].getBytes());
            put.addColumn("field".getBytes(),"avetem".getBytes(),splits[3].getBytes());
            put.addColumn("field".getBytes(),"maxtem".getBytes(),splits[4].getBytes());
            put.addColumn("field".getBytes(),"pre".getBytes(),splits[5].getBytes());
            put.addColumn("field".getBytes(),"rhu".getBytes(),splits[6].getBytes());
            put.addColumn("field".getBytes(),"ssd".getBytes(),splits[7].getBytes());
            put.addColumn("field".getBytes(),"win".getBytes(),splits[8].getBytes());
            context.write(NullWritable.get(), put);
        }
    }
}
