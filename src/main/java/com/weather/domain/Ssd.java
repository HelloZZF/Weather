package com.weather.domain;

import java.io.Serializable;

/**
 * Created by admin on 2018/11/10.
 */
public class Ssd implements Serializable{
    private String province;
    private int month;
    private float ave_tem;
    private float ave_rhu;
    private float pre_20_20;
    private float max_win;
    private float ssd;
    private float ssd_month;

    public Ssd(String province, int month, float ave_tem, float ave_rhu, float pre_20_20, float max_win, float ssd, float ssd_month) {
        this.province = province;
        this.month = month;
        this.ave_tem = ave_tem;
        this.ave_rhu = ave_rhu;
        this.pre_20_20 = pre_20_20;
        this.max_win = max_win;
        this.ssd = ssd;
        this.ssd_month = ssd_month;
    }

    public Ssd(){}

    @Override
    public String toString() {
        return "Ssd{" +
                "province='" + province + '\'' +
                ", month=" + month +
                ", ave_tem=" + ave_tem +
                ", ave_rhu=" + ave_rhu +
                ", pre_20_20=" + pre_20_20 +
                ", max_win=" + max_win +
                ", ssd=" + ssd +
                ", ssd_month=" + ssd_month +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getAve_tem() {
        return ave_tem;
    }

    public void setAve_tem(float ave_tem) {
        this.ave_tem = ave_tem;
    }

    public float getAve_rhu() {
        return ave_rhu;
    }

    public void setAve_rhu(float ave_rhu) {
        this.ave_rhu = ave_rhu;
    }

    public float getPre_20_20() {
        return pre_20_20;
    }

    public void setPre_20_20(float pre_20_20) {
        this.pre_20_20 = pre_20_20;
    }

    public float getMax_win() {
        return max_win;
    }

    public void setMax_win(float max_win) {
        this.max_win = max_win;
    }

    public float getSsd() {
        return ssd;
    }

    public void setSsd(float ssd) {
        this.ssd = ssd;
    }

    public float getSsd_month() {
        return ssd_month;
    }

    public void setSsd_month(float ssd_month) {
        this.ssd_month = ssd_month;
    }
}
