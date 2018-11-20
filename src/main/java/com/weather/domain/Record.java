package com.weather.domain;

/**
 * 农作物训练数据domain
 */
public class Record {
    private boolean tem_flag;
    private int max_tem;
    private int min_tem;
    private boolean pre_flag;
    private int max_pre;
    private int min_pre;
    private boolean ssd_flag;
    private int max_ssd;
    private int min_ssd;
    private int lable;

    public Record() {

    }

    public Record(boolean tem_flag, int max_tem, int min_tem, boolean pre_flag, int max_pre, int min_pre, boolean ssd_flag, int max_ssd, int min_ssd, int lable) {
        this.tem_flag = tem_flag;
        this.max_tem = max_tem;
        this.min_tem = min_tem;
        this.pre_flag = pre_flag;
        this.max_pre = max_pre;
        this.min_pre = min_pre;
        this.ssd_flag = ssd_flag;
        this.max_ssd = max_ssd;
        this.min_ssd = min_ssd;
        this.lable = lable;
    }

    @Override
    public String toString() {
        //生成libSVM形式的数据
        return lable + " " + "1:" + min_tem + " " + "2:" + max_tem + " "
                + "3:" + min_pre + " " + "4:" + max_pre + " "
                + "5:" + min_ssd + " " + "6:" + max_ssd + "\n";
    }

    public boolean isTem_flag() {
        return tem_flag;
    }

    public void setTem_flag(boolean tem_flag) {
        this.tem_flag = tem_flag;
    }

    public int getMax_tem() {
        return max_tem;
    }

    public void setMax_tem(int max_tem) {
        this.max_tem = max_tem;
    }

    public int getMin_tem() {
        return min_tem;
    }

    public void setMin_tem(int min_tem) {
        this.min_tem = min_tem;
    }

    public boolean isPre_flag() {
        return pre_flag;
    }

    public void setPre_flag(boolean pre_flag) {
        this.pre_flag = pre_flag;
    }

    public int getMax_pre() {
        return max_pre;
    }

    public void setMax_pre(int max_pre) {
        this.max_pre = max_pre;
    }

    public int getMin_pre() {
        return min_pre;
    }

    public void setMin_pre(int min_pre) {
        this.min_pre = min_pre;
    }

    public boolean isSsd_flag() {
        return ssd_flag;
    }

    public void setSsd_flag(boolean ssd_flag) {
        this.ssd_flag = ssd_flag;
    }

    public int getMax_ssd() {
        return max_ssd;
    }

    public void setMax_ssd(int max_ssd) {
        this.max_ssd = max_ssd;
    }

    public int getMin_ssd() {
        return min_ssd;
    }

    public void setMin_ssd(int min_ssd) {
        this.min_ssd = min_ssd;
    }

    public int getLable() {
        return lable;
    }

    public void setLable(int lable) {
        this.lable = lable;
    }
}
