package com.weather.domain;

import java.io.Serializable;

/**
 * Created by admin on 2018/11/9.
 */
public class Crops implements Serializable{
    private String province;
    private int month;
    private float ave_tem;
    private float ave_min_tem;
    private float ave_max_tem;
    private float ave_tem_diff;
    private float min_tem_less_0;
    private float max_tem_more_30;
    private float max_tem_more_35;
    private float ave_rhu;
    private float pre_20_20;
    private float pre_more_25;
    private float pre_more_50;
    private float ave_win;
    private float max_win_more_12;
    private float max_win_more_15;

    public Crops(String province, int month, float ave_tem, float ave_min_tem, float ave_max_tem, float ave_tem_diff, float min_tem_less_0, float max_tem_more_30, float max_tem_more_35, float ave_rhu, float pre_20_20, float pre_more_25, float pre_more_50, float ave_win, float max_win_more_12, float max_win_more_15) {
        this.province = province;
        this.month = month;
        this.ave_tem = ave_tem;
        this.ave_min_tem = ave_min_tem;
        this.ave_max_tem = ave_max_tem;
        this.ave_tem_diff = ave_tem_diff;
        this.min_tem_less_0 = min_tem_less_0;
        this.max_tem_more_30 = max_tem_more_30;
        this.max_tem_more_35 = max_tem_more_35;
        this.ave_rhu = ave_rhu;
        this.pre_20_20 = pre_20_20;
        this.pre_more_25 = pre_more_25;
        this.pre_more_50 = pre_more_50;
        this.ave_win = ave_win;
        this.max_win_more_12 = max_win_more_12;
        this.max_win_more_15 = max_win_more_15;
    }

    public Crops() {

    }

    @Override
    public String toString() {
        return "Crops{" +
                "province='" + province + '\'' +
                ", month=" + month +
                ", ave_tem=" + ave_tem +
                ", ave_min_tem=" + ave_min_tem +
                ", ave_max_tem=" + ave_max_tem +
                ", ave_tem_diff=" + ave_tem_diff +
                ", min_tem_less_0=" + min_tem_less_0 +
                ", max_tem_more_30=" + max_tem_more_30 +
                ", max_tem_more_35=" + max_tem_more_35 +
                ", ave_rhu=" + ave_rhu +
                ", pre_20_20=" + pre_20_20 +
                ", pre_more_25=" + pre_more_25 +
                ", pre_more_50=" + pre_more_50 +
                ", ave_win=" + ave_win +
                ", max_win_more_12=" + max_win_more_12 +
                ", max_win_more_15=" + max_win_more_15 +
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

    public float getAve_min_tem() {
        return ave_min_tem;
    }

    public void setAve_min_tem(float ave_min_tem) {
        this.ave_min_tem = ave_min_tem;
    }

    public float getAve_max_tem() {
        return ave_max_tem;
    }

    public void setAve_max_tem(float ave_max_tem) {
        this.ave_max_tem = ave_max_tem;
    }

    public float getAve_tem_diff() {
        return ave_tem_diff;
    }

    public void setAve_tem_diff(float ave_tem_diff) {
        this.ave_tem_diff = ave_tem_diff;
    }

    public float getMin_tem_less_0() {
        return min_tem_less_0;
    }

    public void setMin_tem_less_0(float min_tem_less_0) {
        this.min_tem_less_0 = min_tem_less_0;
    }

    public float getMax_tem_more_30() {
        return max_tem_more_30;
    }

    public void setMax_tem_more_30(float max_tem_more_30) {
        this.max_tem_more_30 = max_tem_more_30;
    }

    public float getMax_tem_more_35() {
        return max_tem_more_35;
    }

    public void setMax_tem_more_35(float max_tem_more_35) {
        this.max_tem_more_35 = max_tem_more_35;
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

    public float getPre_more_25() {
        return pre_more_25;
    }

    public void setPre_more_25(float pre_more_25) {
        this.pre_more_25 = pre_more_25;
    }

    public float getPre_more_50() {
        return pre_more_50;
    }

    public void setPre_more_50(float pre_more_50) {
        this.pre_more_50 = pre_more_50;
    }

    public float getAve_win() {
        return ave_win;
    }

    public void setAve_win(float ave_win) {
        this.ave_win = ave_win;
    }

    public float getMax_win_more_12() {
        return max_win_more_12;
    }

    public void setMax_win_more_12(float max_win_more_12) {
        this.max_win_more_12 = max_win_more_12;
    }

    public float getMax_win_more_15() {
        return max_win_more_15;
    }

    public void setMax_win_more_15(float max_win_more_15) {
        this.max_win_more_15 = max_win_more_15;
    }
}
