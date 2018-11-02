package com.weather.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * Created by admin on 2018/10/30.
 */
public class wordcount {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length < 2 || args == null) {
            System.err.println("Parameter Errors! Usage:<inputpath> <outputpath>");
            System.exit(-1);
        }
        String inputpath = args[0];
        String outputpath = args[1];

        String jobName = wordcount.class.getName();
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, jobName);
        //不指定JarClass会出现ClassNotFoundException的错误
        job.setJarByClass(WordCountMapper.class);

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, inputpath);
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        Path path = new Path(outputpath);
        path.getFileSystem(conf).delete(path, true);
        FileOutputFormat.setOutputPath(job, path);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);

    }


    static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString().trim();
            String[] words = line.split(" ");
            for (String word : words) {
                context.write(new Text(word), new IntWritable(1));
            }
        }
    }

    static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable iw : values) {
                sum += iw.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }
}
