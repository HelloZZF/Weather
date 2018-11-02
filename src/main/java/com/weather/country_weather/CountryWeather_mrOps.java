package com.weather.country_weather;

/**
 * Created by admin on 2018/10/30.
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
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
import java.util.ArrayList;

public class CountryWeather_mrOps {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length < 3 || args == null) {
            System.err.println("Parameter Errors! Usage:<adcodepath><datapath><outputpath>");
            System.exit(-1);
        }
        String adcodepath = args[0];
        String datapath = args[1];
        String outputpath = args[2];

        String jobName = CountryWeather_mrOps.class.getName();
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, jobName);
        //不指定JarClass会出现ClassNotFoundException的错误
        job.setJarByClass(MyMapper.class);

        job.setInputFormatClass(TextInputFormat.class);
        String inputpath = adcodepath + "," + datapath;
        //多路径输入，可以传入字符串用，分割，或者传入Path数组
        //如果需要输入一个文件夹中的所有文件输入路径可以写成/opt/soft/*.txt
        FileInputFormat.setInputPaths(job, inputpath);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FactorsWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        Path path = new Path(outputpath);
        //如果输出路径已存在则删除
        path.getFileSystem(conf).delete(path, true);
        FileOutputFormat.setOutputPath(job, path);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        //设置reduce只有一个task
        job.setNumReduceTasks(1);
        job.waitForCompletion(true);

    }


    static class MyMapper extends Mapper<LongWritable, Text, Text, FactorsWritable> {

        private String filename;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            //获取输入文件的文件名
            FileSplit input = (FileSplit) context.getInputSplit();
            filename = input.getPath().getName().split("_")[0];
            //System.out.println("filename: " + filename);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //使用文件名作为key文件中的字段作为value输出
            if (filename.equals("adcode")) {
                //System.out.println("adcode value: " + value.toString());
                String[] codes = value.toString().trim().split("\\t");
                String adcode = codes[0];
                String province = codes[1];
                String longitude = codes[2];
                String latitude = codes[3];
                //System.out.println("map province: " + province);
                context.write(new Text(filename), new FactorsWritable(province, adcode, longitude, latitude,
                "", "", 0.0f, 0.0f, 0.0f, 0.0f));
            }else {
                //System.out.println("data value: " + value.toString());
                String[] factor = value.toString().trim().split(" ");
                String asn = factor[0];
                String year = factor[1];
                String month = factor[2];
                String tem = factor[12];
                String rhu = factor[14];
                String ssd = factor[19];
                String pre = factor[9];
                if (!asn.equals("V01301")&&!tem.equals("999999.0000")&&!rhu.equals("999999.0000")&&!ssd.equals("999999.0000")&&!pre.equals("999999.0000")){
                    //System.out.println("map month: " + month);
                    //使用组合key可以进行省份和月份的归并
                    context.write(new Text(filename + "_" + month), new FactorsWritable(filename, "",
                            "", "", year, month,
                            Float.valueOf(tem), Float.valueOf(rhu),
                            Float.valueOf(ssd), Float.valueOf(pre)));
                }
            }



        }

    }

    static class MyReducer extends Reducer<Text, FactorsWritable, NullWritable, Text> {
        private ArrayList<FactorsWritable> codeList;
        private ArrayList<FactorsWritable> dataList;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            codeList = new ArrayList<>();
            dataList = new ArrayList<>();
            //System.out.println("in setup");
        }

        @Override
        protected void reduce(Text key, Iterable<FactorsWritable> values, Context context) throws IOException, InterruptedException {
            //System.out.println("in reduce");
            if (key.toString().equals("adcode")) {
                //System.out.println("adcode key" + key.toString());
                //!!!实现了Writable接口的类型在foreach循环的时候只会产生一个对象
                //即这里的f是同一个对象所以最后codeList里面的数据也会只有一个对象的数据
                //这里要重新new一个对象接收f对象的数据然后在add进去才没问题
                for (FactorsWritable f :values) {
                    //System.out.println("reduce f: " + f.getProvince());
                    FactorsWritable fw = new FactorsWritable(f.getProvince(), f.getAdcode(),
                            f.getLongitude(), f.getLatitude(), f.getYear(), f.getMonth(),
                            f.getTem(), f.getRhu(), f.getSsd(), f.getPre());
                    codeList.add(fw);
                }

            }else {
                //System.out.println("province key " + key.toString());
                //计算省份每个站点每个月的平均月值
                String province = "";
                String adcode = "";
                String longitude = "";
                String latitude = "";
                String year = "";
                String month = "";
                Float tems = 0.0f;
                Float rhus = 0.0f;
                Float ssds = 0.0f;
                Float pres = 0.0f;
                int sum = 0;

                for (FactorsWritable t : values) {
                    //System.out.println("every month:" + t.toString());
                    sum++;
                    province = t.getProvince();
                    year = t.getYear();
                    month = t.getMonth();
                    tems += t.getTem();
                    rhus += t.getRhu();
                    ssds += t.getSsd();
                    pres += t.getPre();

                }
                //System.out.println("province: " + province + "=");
                FactorsWritable result = new FactorsWritable(province, adcode, longitude, latitude, year, month,
                        tems/sum, rhus/sum, ssds/sum, pres/sum);
                dataList.add(result);
            }



        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //System.out.println("in cleanup");
            if (!codeList.isEmpty()&&!dataList.isEmpty()) {
                for (FactorsWritable fw : dataList) {
                    String pro = fw.getProvince();
                    for (FactorsWritable ac : codeList) {
                        if (ac.getProvince().equals(pro)) {
                            context.write(NullWritable.get(), new Text(new FactorsWritable(
                                    ac.getProvince(),
                                    ac.getAdcode(),
                                    ac.getLongitude(),
                                    ac.getLatitude(),
                                    fw.getYear(),
                                    fw.getMonth(),
                                    fw.getTem(),
                                    fw.getRhu(),
                                    fw.getSsd(),
                                    fw.getPre()
                            ).toString()));
                        }
                    }
                }

            }
        }
    }

    static class FactorsWritable implements WritableComparable<FactorsWritable> {

        private String province;
        private String adcode;
        private String longitude;
        private String latitude;
        private String year;
        private String month;
        private Float tem;
        private Float rhu;
        private Float ssd;
        private Float pre;

        public FactorsWritable(String province, String adcode, String longitude, String latitude, String year, String month, Float tem, Float rhu, Float ssd, Float pre) {
            this.province = province;
            this.adcode = adcode;
            this.longitude = longitude;
            this.latitude = latitude;
            this.year = year;
            this.month = month;
            this.tem = tem;
            this.rhu = rhu;
            this.ssd = ssd;
            this.pre = pre;
        }

        //在反序列化时，反射机制需要调用空参构造函数，所以显示定义了一个空参构造函数
        public FactorsWritable() {
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeUTF(province);
            out.writeUTF(adcode);
            out.writeUTF(longitude);
            out.writeUTF(latitude);
            out.writeUTF(year);
            out.writeUTF(month);
            out.writeFloat(tem);
            out.writeFloat(rhu);
            out.writeFloat(ssd);
            out.writeFloat(pre);

        }


        @Override
        public void readFields(DataInput in) throws IOException {
            this.province = in.readUTF();
            this.adcode = in.readUTF();
            this.longitude = in.readUTF();
            this.latitude = in.readUTF();
            this.year = in.readUTF();
            this.month = in.readUTF();
            this.tem = in.readFloat();
            this.rhu = in.readFloat();
            this.ssd = in.readFloat();
            this.pre = in.readFloat();

        }

        @Override
        public String toString() {
            return  province + " " +
                    adcode + " " +
                    longitude + " " +
                    latitude + " " +
                    year + " " +
                    month + " " +
                    tem + " " +
                    rhu + " " +
                    ssd + " " +
                    pre + " ";
        }

        public String getProvince() {
            return province;
        }

        public String getAdcode() {
            return adcode;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getYear() {
            return year;
        }

        public String getMonth() {
            return month;
        }

        public Float getTem() {
            return tem;
        }

        public Float getRhu() {
            return rhu;
        }

        public Float getSsd() {
            return ssd;
        }

        public Float getPre() {
            return pre;
        }

        @Override
        public int compareTo(FactorsWritable o) {
            return this.getMonth().compareTo(o.getMonth());
        }
    }


}
