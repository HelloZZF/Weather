package com.weather.domain;

import java.io.Serializable;

/**
 * Created by admin on 2018/11/4.
 */
public class SyRealTime implements Serializable{

    private String city;
    private String year;
    private int month;
    private int day;
    private int hour;
    private float prs;
    private float prs_sea;
    private float prs_max;
    private float prs_min;
    private float tem;
    private float tem_max;
    private float tem_min;
    private float tigan;
    private float rhu;
    private float rhu_min;
    private float pre_1h;
    private float win_s_max;
    private String win_d_s_max;
    private float win_s_avg_2mi;
    private String win_d_avg_2mi;
    private float vis;
    private float radiation;

    public SyRealTime(String city, String Year, int month, int day, int hour, float prs, float prs_sea, float prs_max, float prs_min, float tem, float tem_max, float tem_min, float tigan, float rhu, float rhu_min, float pre_1h, float win_s_max, String win_d_s_max, float win_s_avg_2mi, String win_d_avg_2mi, float vis, float radiation) {
        this.city = city;
        this.year = Year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.prs = prs;
        this.prs_sea = prs_sea;
        this.prs_max = prs_max;
        this.prs_min = prs_min;
        this.tem = tem;
        this.tem_max = tem_max;
        this.tem_min = tem_min;
        this.tigan = tigan;
        this.rhu = rhu;
        this.rhu_min = rhu_min;
        this.pre_1h = pre_1h;
        this.win_s_max = win_s_max;
        this.win_d_s_max = win_d_s_max;
        this.win_s_avg_2mi = win_s_avg_2mi;
        this.win_d_avg_2mi = win_d_avg_2mi;
        this.vis = vis;
        this.radiation = radiation;
    }

    @Override
public String toString() {
        return "SyRealTime{" +
                ", city='" + city + '\'' +
                ", year='" + year + '\'' +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", prs=" + prs +
                ", prs_sea=" + prs_sea +
                ", prs_max=" + prs_max +
                ", prs_min=" + prs_min +
                ", tem=" + tem +
                ", tem_max=" + tem_max +
                ", tem_min=" + tem_min +
                ", tigan=" + tigan +
                ", rhu=" + rhu +
                ", rhu_min=" + rhu_min +
                ", pre_1h=" + pre_1h +
                ", win_s_max=" + win_s_max +
                ", win_d_s_max='" + win_d_s_max + '\'' +
                ", win_s_avg_2mi=" + win_s_avg_2mi +
                ", win_d_avg_2mi='" + win_d_avg_2mi + '\'' +
                ", vis=" + vis +
                ", radiation=" + radiation +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getPrs() {
        return prs;
    }

    public void setPrs(float prs) {
        this.prs = prs;
    }

    public float getPrs_sea() {
        return prs_sea;
    }

    public void setPrs_sea(float prs_sea) {
        this.prs_sea = prs_sea;
    }

    public float getPrs_max() {
        return prs_max;
    }

    public void setPrs_max(float prs_max) {
        this.prs_max = prs_max;
    }

    public float getPrs_min() {
        return prs_min;
    }

    public void setPrs_min(float prs_min) {
        this.prs_min = prs_min;
    }

    public float getTem() {
        return tem;
    }

    public void setTem(float tem) {
        this.tem = tem;
    }

    public float getTem_max() {
        return tem_max;
    }

    public void setTem_max(float tem_max) {
        this.tem_max = tem_max;
    }

    public float getTem_min() {
        return tem_min;
    }

    public void setTem_min(float tem_min) {
        this.tem_min = tem_min;
    }

    public float getTigan() {
        return tigan;
    }

    public void setTigan(float tigan) {
        this.tigan = tigan;
    }

    public float getRhu() {
        return rhu;
    }

    public void setRhu(float rhu) {
        this.rhu = rhu;
    }

    public float getRhu_min() {
        return rhu_min;
    }

    public void setRhu_min(float rhu_min) {
        this.rhu_min = rhu_min;
    }

    public float getPre_1h() {
        return pre_1h;
    }

    public void setPre_1h(float pre_1h) {
        this.pre_1h = pre_1h;
    }

    public float getWin_s_max() {
        return win_s_max;
    }

    public void setWin_s_max(float win_s_max) {
        this.win_s_max = win_s_max;
    }

    public String getWin_d_s_max() {
        return win_d_s_max;
    }

    public void setWin_d_s_max(String win_d_s_max) {
        this.win_d_s_max = win_d_s_max;
    }

    public float getWin_s_avg_2mi() {
        return win_s_avg_2mi;
    }

    public void setWin_s_avg_2mi(float win_s_avg_2mi) {
        this.win_s_avg_2mi = win_s_avg_2mi;
    }

    public String getWin_d_avg_2mi() {
        return win_d_avg_2mi;
    }

    public void setWin_d_avg_2mi(String win_d_avg_2mi) {
        this.win_d_avg_2mi = win_d_avg_2mi;
    }

    public float getVis() {
        return vis;
    }

    public void setVis(float vis) {
        this.vis = vis;
    }

    public float getRadiation() {
        return radiation;
    }

    public void setRadiation(float radiation) {
        this.radiation = radiation;
    }
}
