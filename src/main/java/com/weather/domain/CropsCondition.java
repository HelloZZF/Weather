package com.weather.domain;

import java.io.Serializable;

/**
 * Created by admin on 2018/11/17.
 */
public class CropsCondition implements Serializable{
    private int id;
    private String name;
    private Double min_tem;
    private Double max_tem;
    private Double min_pre;
    private Double max_pre;
    private Double min_ssd;
    private Double max_ssd;
    private String lable;

    public CropsCondition(int id, String name, Double min_tem, Double max_tem, Double min_pre, Double max_pre, Double min_ssd, Double max_ssd, String lable) {
        this.id = id;
        this.name = name;
        this.min_tem = min_tem;
        this.max_tem = max_tem;
        this.min_pre = min_pre;
        this.max_pre = max_pre;
        this.min_ssd = min_ssd;
        this.max_ssd = max_ssd;
        this.lable = lable;
    }

    public CropsCondition() {

    }

    @Override
    public String toString() {
        return "[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", min_tem=" + min_tem +
                ", max_tem=" + max_tem +
                ", min_pre=" + min_pre +
                ", max_pre=" + max_pre +
                ", min_ssd=" + min_ssd +
                ", max_ssd=" + max_ssd +
                ", lable=" + lable +
                ']';
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMin_tem() {
        return min_tem;
    }

    public void setMin_tem(Double min_tem) {
        this.min_tem = min_tem;
    }

    public Double getMax_tem() {
        return max_tem;
    }

    public void setMax_tem(Double max_tem) {
        this.max_tem = max_tem;
    }

    public Double getMin_pre() {
        return min_pre;
    }

    public void setMin_pre(Double min_pre) {
        this.min_pre = min_pre;
    }

    public Double getMax_pre() {
        return max_pre;
    }

    public void setMax_pre(Double max_pre) {
        this.max_pre = max_pre;
    }

    public Double getMin_ssd() {
        return min_ssd;
    }

    public void setMin_ssd(Double min_ssd) {
        this.min_ssd = min_ssd;
    }

    public Double getMax_ssd() {
        return max_ssd;
    }

    public void setMax_ssd(Double max_ssd) {
        this.max_ssd = max_ssd;
    }
}
