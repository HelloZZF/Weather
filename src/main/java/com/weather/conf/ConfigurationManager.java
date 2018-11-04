package com.weather.conf;

import com.weather.constants.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by admin on 2018/8/20.
 * 该类主要用于管理配置文件信息
 * 比如根据情况分别加载本地环境，测试环境，生产环境
 * 一般情况下这些配置文件都要写成单例，同时里面的所有方法都是静态
 */
public class ConfigurationManager {

    private ConfigurationManager() {}
    private static Properties properties = new Properties();
    public static String mode = null;
// 一 般情况下,如果有些代码必须在项目启动的时候就执行的时候,需要使用静态代码块,这种代码是主动执行的;
// 需要在项目启动的时候就初始化,在不创建对象的情 况下,其他程序来调用的时候,需要使用静态方法,
// 这种代码是被动执行的. 静态方法在类加载的时候 就已经加载 可以用类名直接调用
//
// 比如main方法就必须是静态的 这是程序入口
// 两者的区别就是:静态代码块是自动执行的;
// 静态方法是被调用的时候才执行的.
    //加载配置信息
    static {
        try {
            InputStream resource = ConfigurationManager.class.getClassLoader().getResourceAsStream("conf.properties");
            //加载相关配置信息
            properties.load(resource);
            //获取spark作业运行的模式
            mode = properties.getProperty(Constants.SPARK_JOB_RUN_MODE, "local");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key).trim();
    }

    public static Boolean getBooleanProperty(String key) {
        return Boolean.valueOf(properties.getProperty(key).trim());
    }

    public static Integer getIntProperty(String key) {
        return Integer.valueOf(properties.getProperty(key).trim());
    }

    public static Long getLongProperty(String key) {
        return Long.valueOf(properties.getProperty(key).trim());
    }

    
}
