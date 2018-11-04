package com.weather.constants;

/**
 * 用于存放项目开发过程中使用到的这种常量字段，避免手写导致出错
 *
 */
public interface Constants {
    /**
     * spark作业运行的模式
     */
    String SPARK_JOB_RUN_MODE = "spark.job.run.mode";
    /**
     * 关于数据库的常量字段
     */
    String JDBC_DRIVER_CLASS_NAME = "driverClassName";
    String JDBC_URL = "url";
    String JDBC_USERNAME = "username";
    String JDBC_PASSWORD = "password";
    /**
     * 当前数据库连接池的初始化大小
     */
    String JDBC_INITIAL_SIZE = "initialSize";
    /**
     * 当前数据库连接池的最大的活跃连接数据 默认：50
     */
    String JDBC_MAX_ACTIVE = "maxActive";
    /**
     * 当前数据库连接池的最大空闲数
     */
    String JDBC_MAX_IDLE = "maxIdle";
    /**
     * 当等待的时间
     */
    String JDBC_MAX_WAIT = "maxWait";


    //执行的spark作业的类型
    String SPARK_LOCAL_TASKID_SESSION = "spark.local.taskid.session";
    String SPARK_LOCAL_TASKID_PAGE = "spark.local.taskid.page";
    String SPARK_LOCAL_TASKID_PRODUCT = "spark.local.taskid.product";

    String KAFKA_METADATA_BROKER_LIST = "kafka.metadata.broker.list";
    String KAFKA_TOPICS = "kafka.topics";
    /**
     * 数据中的缺省值
     */
    float FACTOR_DEFAULT_VALUE_FOT = 999999.0000f;
    String FACTOR_DEFAULT_VALUE_STR = "999999.0000";
    /**
     * 区站号字段名
     */
    String ASN_FIELD_NAME = "V01301";
    /**
     * adcode 文件名
     */
    String ADCODE_FILE_NAME = "adcode";




}
