package com.weather.hainan_weather;

import com.weather.constants.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by admin on 2018/11/2.
 */
public class HaiNanWeatherMrOps {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length < 2 || args == null) {
            System.err.println("Parameter Errors! Usage:<inputpath><outputpath>");
            System.exit(-1);
        }
        String inputpath = args[0];
        String outputpath = args[1];

        Configuration conf = new Configuration();
        String jobName = HaiNanWeatherMrOps.class.getName();
        Job job = Job.getInstance(conf,jobName);

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        Path inputPath = new Path(inputpath);
        FileInputFormat.addInputPath(job, inputPath);

        job.setOutputFormatClass(TextOutputFormat.class);
        Path outputPath = new Path(outputpath);
        outputPath.getFileSystem(conf).delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FactorsWritable.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setJarByClass(MyMapper.class);
        job.setNumReduceTasks(1);
        job.waitForCompletion(true);
    }

    static class MyMapper extends Mapper<LongWritable, Text, Text, FactorsWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] factors = value.toString().trim().split(" ");
            FileSplit input = (FileSplit) context.getInputSplit();
            String province = input.getPath().getName().split("_")[0];
            String asn = factors[0];
            String year = factors[1];
            String month = factors[2];
            String avetem = factors[12];
            String maxtem = factors[8];
            String pre = factors[9];
            String rhu = factors[14];
            String ssd = factors[19];
            String win = factors[3];
            if (!asn.equals(Constants.ASN_FIELD_NAME)) {
                context.write(new Text(year + "_" + month), new FactorsWritable(province,
                        year, month, Float.valueOf(avetem), Float.valueOf(maxtem),
                        Float.valueOf(pre), Float.valueOf(rhu), Float.valueOf(ssd),Float.valueOf(win)));
            }
        }
    }

    static class MyReducer extends Reducer<Text, FactorsWritable, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<FactorsWritable> values, Context context) throws IOException, InterruptedException {
            String province = "";
            String year = "";
            String month = "";
            Float avetems = 0.0f;
            Float maxtems = 0.0f;
            Float pres = 0.0f;
            Float rhus = 0.0f;
            Float ssds = 0.0f;
            Float wins = 0.0f;
            int avetems_sum = 0;
            int maxtems_sum = 0;
            int pres_sum = 0;
            int rhus_sum = 0;
            int ssds_sum = 0;
            int wins_sum = 0;
            //这里的情况是所有站点的16年的1/4/5月的ssd都没有数据,所以最后出来的结果ssd字段还是NAN
            for (FactorsWritable fw : values) {
                province = fw.getProvince();
                year = fw.getYear();
                month = fw.getMonth();
                if (!Check(fw.getAvetem())) {
                    avetems_sum++;
                    avetems += fw.getAvetem();
                }
                if (!Check(fw.getMaxtem())) {
                    maxtems_sum++;
                    maxtems += fw.getMaxtem();
                }
                if (!Check(fw.getPre())) {
                    pres_sum++;
                    pres += fw.getPre();
                }
                if (!Check(fw.getRhu())) {
                    rhus_sum++;
                    rhus += fw.getRhu();
                }
                if (!Check(fw.getSsd())) {
                    ssds_sum++;
                    ssds += fw.getSsd();
                }
                if (!Check(fw.getWin())) {
                    wins_sum++;
                    wins += fw.getWin();
                }
            }

            Float avetem = avetems/avetems_sum;
            Float maxtem = maxtems/maxtems_sum;
            Float pre = pres/pres_sum;
            Float rhu = rhus/rhus_sum;
            Float ssd = ssds/ssds_sum;
            Float win = wins/wins_sum;

            if (Check_NaN(avetem)) {
                avetem = -1f;
            }
            if (Check_NaN(maxtem)) {
                maxtem = -1f;
            }
            if (Check_NaN(pre)) {
                pre = -1f;
            }
            if (Check_NaN(rhu)) {
                rhu = -1f;
            }
            if (Check_NaN(ssd)) {
                ssd = -1f;
            }
            if (Check_NaN(win)) {
                win = -1f;
            }

            context.write(NullWritable.get(), new Text(new FactorsWritable(
                    province,year,month,
                    avetem,maxtem,pre,rhu,ssd,win
            ).toString()));
        }
        //如果某条数据的某个值为缺省值那么在算这个值的平均值的时候把它去除
        //注意：不能把这个值设为0，如果设为0即使0对数据总数没影响但是对数据条数总数有影响即sum
        //这种数据清洗的做法虽然增加了数据的准确率，但如果数据量大的话会给集群带来很大的负担
        public boolean Check(float f) {
            return f == Constants.FACTOR_DEFAULT_VALUE_FOT ? true : false;
        }
        //把NaN的数据赋值成-1,因为NaN导入不进Mysql
        public boolean Check_NaN(float f){return Float.isNaN(f) ? true : false;}

    }

    static class FactorsWritable implements WritableComparable<FactorsWritable> {
        private String province;
        private String year;
        private String month;
        private Float avetem;
        private Float maxtem;
        private Float pre;
        private Float rhu;
        private Float ssd;
        private Float win;

        public FactorsWritable(String province, String year, String month, Float avetem, Float maxtem, Float pre, Float rhu, Float ssd, Float win) {
            this.province = province;
            this.year = year;
            this.month = month;
            this.avetem = avetem;
            this.maxtem = maxtem;
            this.pre = pre;
            this.rhu = rhu;
            this.ssd = ssd;
            this.win = win;
        }

        public FactorsWritable() {

        }

        @Override
        public int compareTo(FactorsWritable o) {
            return Integer.valueOf(this.getMonth()).compareTo(Integer.valueOf(o.getMonth()));
        }

        @Override
        public void write(DataOutput out) throws IOException {
            //这里的写出顺序很关键，决定各个字段写出的顺序
            //然而即便是调用了toString最后各个字段的顺序也是按这里的顺序来
            out.writeUTF(province);
            out.writeUTF(year);
            out.writeUTF(month);
            out.writeFloat(avetem);
            out.writeFloat(maxtem);
            out.writeFloat(pre);
            out.writeFloat(rhu);
            out.writeFloat(ssd);
            out.writeFloat(win);

        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.province = in.readUTF();
            this.year = in.readUTF();
            this.month = in.readUTF();
            this.avetem = in.readFloat();
            this.maxtem = in.readFloat();
            this.pre = in.readFloat();
            this.rhu = in.readFloat();
            this.ssd = in.readFloat();
            this.win = in.readFloat();
        }

        @Override
        public String toString() {
            return  province + " " +
                    year + " " +
                    month + " " +
                    avetem + " " +
                    maxtem + " " +
                    pre + " " +
                    rhu + " " +
                    ssd + " " +
                    win;
        }

        public String getProvince() {
            return province;
        }

        public String getYear() {
            return year;
        }

        public String getMonth() {
            return month;
        }

        public Float getAvetem() {
            return avetem;
        }

        public Float getMaxtem() {
            return maxtem;
        }

        public Float getPre() {
            return pre;
        }

        public Float getRhu() {
            return rhu;
        }

        public Float getSsd() {
            return ssd;
        }

        public Float getWin() {
            return win;
        }
    }
}
