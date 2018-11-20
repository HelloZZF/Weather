package com.weather.util;

import com.weather.domain.Record;
import scala.Tuple3;

import java.util.ArrayList;

/**
 * 生成农作物训练数据
 */
public class MakeCropsDataUtil {

    private static int ave_tem = 25;
    private static int ave_pre = 1809;
    private static int ave_ssd = 2124;

    public static void main(String[] args) {

        ArrayList<Integer> ave_tems = new ArrayList<>();
        ArrayList<Integer> ave_pres = new ArrayList<>();
        ArrayList<Integer> ave_ssds = new ArrayList<>();
        ArrayList<Record> lable_1_list  = new ArrayList<>();
        ArrayList<Record> lable_0_list = new ArrayList<>();
        Tuple3<Integer, Integer, Boolean> tuple = new Tuple3<>(0,0,true);

        for (int i = 10; i<=40; i++) {
            ave_tems.add(i);
        }
        for (int i = 1500; i<=2200; i++) {
            ave_pres.add(i);
        }
        for (int i = 1900; i<=2300; i++) {
            ave_ssds.add(i);
        }

        for (int i = 0; i<10; i++) {

            while (lable_1_list.size() != 5000 || lable_0_list.size() != 5000) {
                Record re = new Record();
                tuple = getRange(30, ave_tems, ave_tem);
                re.setMin_tem(tuple._1());
                re.setMax_tem(tuple._2());
                re.setTem_flag(tuple._3());
                tuple = getRange(700, ave_pres, ave_pre);
                re.setMin_pre(tuple._1());
                re.setMax_pre(tuple._2());
                re.setPre_flag(tuple._3());
                tuple = getRange(400, ave_ssds, ave_ssd);
                re.setMin_ssd(tuple._1());
                re.setMax_ssd(tuple._2());
                re.setSsd_flag(tuple._3());

                if (re.isTem_flag() && re.isPre_flag() && re.isSsd_flag()) {
                    re.setLable(1);
                    if (lable_1_list.size() < 5000)
                        lable_1_list.add(re);
                }else {
                    re.setLable(0);
                    if (lable_0_list.size() < 5000)
                        lable_0_list.add(re);
                }
            }

            writeFile(lable_0_list);
            writeFile(lable_1_list);


        }


    }


    public static Tuple3<Integer, Integer, Boolean> getRange(int type, ArrayList<Integer> list, int stand) {
        boolean flag = true;
        int rd = (int)(Math.random()*type);
        int min_rd = list.get(rd);
        while (min_rd == list.get(list.size() - 1)) {
            rd = (int)(Math.random()*type);
            min_rd = list.get(rd);
        }
        //int temp=m+(int)(Math.random()*(n+1-m)); //生成从m到n的随机整数[m,n]
        rd = rd + (int)(Math.random()*(type-rd));
        int max_rd = list.get(rd);
        if (stand >= min_rd && stand <= max_rd) {
            flag = true;
        }else if (stand < min_rd || stand > max_rd){
            flag = false;
        }
        return new Tuple3<>(min_rd, max_rd, flag);
    }

    public static void writeFile(ArrayList<Record> list) {
        for (Record r :list) {
            //System.out.println(r.toString());
            FileUtil.writeToFile(r.toString(), "C:\\Users\\admin\\Desktop\\", "crops.txt");
        }
    }
}
