package com.weather.domain;

import java.io.Serializable;

/**
 * Created by admin on 2018/11/10.
 */
public class Hourse implements Serializable{
    private String asn;
    private int month;
    private float ave_tem;
    private float ave_max_tem;
    private float ave_min_tem;
    private float max_tem_more_30;
    private float max_tem_more_35;
    private float ave_rhu;
    private float pre_20_20;
    private float pre_more_25;
    private float max_conti_pre;
    private float ave_win;
    private float max_win_more_10;
    private float max_win_more_12;

    public Hourse(String asn, int month, float ave_tem, float ave_max_tem, float ave_min_tem, float max_tem_more_30, float max_tem_more_35, float ave_rhu, float pre_20_20, float pre_more_25, float max_conti_pre, float ave_win, float max_win_more_10, float max_win_more_12) {
        this.asn = asn;
        this.month = month;
        this.ave_tem = ave_tem;
        this.ave_max_tem = ave_max_tem;
        this.ave_min_tem = ave_min_tem;
        this.max_tem_more_30 = max_tem_more_30;
        this.max_tem_more_35 = max_tem_more_35;
        this.ave_rhu = ave_rhu;
        this.pre_20_20 = pre_20_20;
        this.pre_more_25 = pre_more_25;
        this.max_conti_pre = max_conti_pre;
        this.ave_win = ave_win;
        this.max_win_more_10 = max_win_more_10;
        this.max_win_more_12 = max_win_more_12;
    }

    public Hourse(){}

    @Override
    public String toString() {
        return "Hourse{" +
                "asn='" + asn + '\'' +
                ", month=" + month +
                ", ave_tem=" + ave_tem +
                ", ave_max_tem=" + ave_max_tem +
                ", ave_min_tem=" + ave_min_tem +
                ", max_tem_more_30=" + max_tem_more_30 +
                ", max_tem_more_35=" + max_tem_more_35 +
                ", ave_rhu=" + ave_rhu +
                ", pre_20_20=" + pre_20_20 +
                ", pre_more_25=" + pre_more_25 +
                ", max_conti_pre=" + max_conti_pre +
                ", ave_win=" + ave_win +
                ", max_win_more_10=" + max_win_more_10 +
                ", max_win_more_12=" + max_win_more_12 +
                '}';
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
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

    public float getAve_max_tem() {
        return ave_max_tem;
    }

    public void setAve_max_tem(float ave_max_tem) {
        this.ave_max_tem = ave_max_tem;
    }

    public float getAve_min_tem() {
        return ave_min_tem;
    }

    public void setAve_min_tem(float ave_min_tem) {
        this.ave_min_tem = ave_min_tem;
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

    public float getMax_conti_pre() {
        return max_conti_pre;
    }

    public void setMax_conti_pre(float max_conti_pre) {
        this.max_conti_pre = max_conti_pre;
    }

    public float getAve_win() {
        return ave_win;
    }

    public void setAve_win(float ave_win) {
        this.ave_win = ave_win;
    }

    public float getMax_win_more_10() {
        return max_win_more_10;
    }

    public void setMax_win_more_10(float max_win_more_10) {
        this.max_win_more_10 = max_win_more_10;
    }

    public float getMax_win_more_12() {
        return max_win_more_12;
    }

    public void setMax_win_more_12(float max_win_more_12) {
        this.max_win_more_12 = max_win_more_12;
    }
}
