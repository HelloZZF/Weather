package com.weather.countryweather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
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
public class testHBase {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "master,slave01,slave02");
        Job job = Job.getInstance(config, testHBase.class.getName());

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Put.class);

        //只有map没有reduce，所以设置reduce的数目为0
        job.setNumReduceTasks(0);

        //设置数据的输入路径,没有使用参数，直接在程序中写入HDFS的路径
        FileInputFormat.setInputPaths(job, new Path("/output/weather/hainan_weather/part-r-00000"));


        //驱动函数
        TableMapReduceUtil.initTableReducerJob("country_weather",null, job);
        //TableMapReduceUtil.addDependencyJars(job);

        job.setJarByClass(MyMapper.class);
        boolean bo = job.waitForCompletion(true);
        if (bo == true) {
            System.out.println("666");
        }


    }

    private static class MyMapper extends Mapper<LongWritable, Text, NullWritable, Put> {
        private Admin admin;
        private Connection connection;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration conf = HBaseConfiguration.create();
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            Put put = new Put(key.toString().getBytes());
            context.write(NullWritable.get(), put);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //设置表名
            TableName tb_name = TableName.valueOf("testHBase");
            HTableDescriptor desc = new HTableDescriptor(tb_name);
            HColumnDescriptor cf = new HColumnDescriptor("cf4");
            //设置列族
            desc.addFamily(cf);
            admin.createTable(desc);
        }
    }
}
