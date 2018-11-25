package com.weather.realtimedata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weather.util.FileUtil;
import com.weather.util.HttpUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by admin on 2018/11/6.
 */
public class GetSyRTWeatherToLocal extends Thread{
    private ArrayList<String> timelist;
    private int flag = 0;
    private int index = 0;

    public GetSyRTWeatherToLocal() {
        timelist = new ArrayList<>();
        //getWeatherData();
    }
    //循环返回当前日期的每个小时和前一天的每个小时
    public String TimeList() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        //这里的月份是从0开始计数的所以要加一
        int month = c.get(Calendar.MONTH) + 1;
        String monthStr = add0(month);
        int date = c.get(Calendar.DATE);
        String dateStr = add0(date);
        //如果是UTC时间的话要减去8小时的时差
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if ( hour >= 9) {
            hour = hour - 9;
        }
        String hourStr = add0(hour);

        for (int i = 0; i <= 23; i++) {
            int predate = c.get(Calendar.DATE) - 1;
            String predateStr = add0(predate);
            String pretime = year + "" + monthStr + "" + predateStr + "" + add0(i) + "0000";
            timelist.add(pretime);
        }

        for (int i = 0; i <= hour; i++) {
            String time = year + "" + monthStr + "" + dateStr + "" + add0(i) + "0000";
            timelist.add(time);
        }


        return flag == timelist.size() ? timelist.get(flag - timelist.size()) : timelist.get(flag++);
    }

    public String getWeatherData(String time) {

        //气象局坑爹啊，这个api只能获取当前时间之前7/8个小时的数据,也有可能是UTC世界统一时间
        String json =  HttpUtil.getHttpContent("http://api.data.cma.cn:8090/api?userId=542954102812qDCbb&pwd=4D5Qx9Z" +
                "&dataFormat=json" +
                "&interfaceId=getSurfEleByTimeRangeAndStaID" +
                "&dataCode=SURF_CHN_MUL_HOR" +
                "&timeRange=["+ time + "," + time + "]" +
                "&staIDs=59945" +
                "&elements=Station_Id_C,Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_Min,RHU,RHU_Min,PRE_1h," +
                "WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,tigan,VIS");
        JSONObject jsonAll = JSON.parseObject(json);
        if (!jsonAll.getString("returnCode").equals("0")) {
            return null;
        }
        JSONArray jsonDS = JSON.parseArray(jsonAll.get("DS").toString());
        JSONObject jsonFactor = JSON.parseObject(jsonDS.get(0).toString());

        String radjson = HttpUtil.getHttpContent("http://api.data.cma.cn:8090/api?userId=542954295229kWWZi&pwd=GpuYWv6" +
                "&dataFormat=json" +
                "&interfaceId=getRadiEleByTimeRangeAndStaID" +
                "&dataCode=RADI_CHN_MUL_HOR2400" +
                "&timeRange=["+ time + "," + time + "]" +
                "&staIds=59945" +
                "&elements=V14311,V14320");
        JSONObject radjsonAll = JSON.parseObject(radjson);
        String rad = "0";
        if (radjsonAll.getString("returnCode").equals("0")) {
            JSONArray radjsonDS = JSON.parseArray(radjsonAll.get("DS").toString());
            JSONObject radjsonFactor = JSON.parseObject(radjsonDS.get(0).toString());
            rad = radjsonFactor.getString("V14311");
        }


        String result = "sanya" + " " +jsonFactor.getString("Year") + " " + jsonFactor.getString("Mon") + " " +
                jsonFactor.getString("Day") + " " + jsonFactor.getString("Hour") + " " +
                jsonFactor.getString("PRS") + " " + jsonFactor.getString("PRS_Sea") + " " +
                jsonFactor.getString("PRS_Max") + " " + jsonFactor.getString("PRS_Min") + " " +
                jsonFactor.getString("TEM") + " " + jsonFactor.getString("TEM_Max") + " " +
                jsonFactor.getString("TEM_Min") + " " + jsonFactor.getString("tigan") + " " +
                jsonFactor.getString("RHU") + " " + jsonFactor.getString("RHU_Min") + " " +
                jsonFactor.getString("PRE_1h") + " " + jsonFactor.getString("WIN_S_Max") + " " +
                jsonFactor.getString("WIN_D_S_Max") + " " + jsonFactor.getString("WIN_S_Avg_2mi") + " " +
                jsonFactor.getString("WIN_D_Avg_2mi") + " " + jsonFactor.getString("VIS")
                + " " + rad;
        return result;

    }

    public String add0(int i) {
        String s = i + "";
        if (i < 10) {
            s = "0" + i;
        }
        return s;
    }

    public void run() {
        while(true) {
            System.out.println("running...");
            FileUtil.writeToFile(getWeatherData(TimeList()),"/root/flume-data/syrt-weather/","syrtWeather-" + index++);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动Kafka Producer
     * @param args
     */
    public static void main(String[] args) {
        GetSyRTWeatherToLocal producer = new GetSyRTWeatherToLocal();
//		String data = producer.getWeatherData();
//		System.out.println(data);
        producer.start();
    }
}
