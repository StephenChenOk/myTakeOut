package com.chen.fy.mytakeout.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 店家信息的实体类, bean类
 */
public class StoreInfo extends LitePalSupport{

    //每一个账单信息的唯一标志
    private int id;
    //图标
    private String logoName;
    //店名
    private String name;
    //评分
    private double grade;
    //月售
    private int sales;
    //商品类型
    private String type;
    //配送时间
    private int time;
    //店家距离
    private double distance;
    //人均
    private int average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logoName;
    }

    public void setLogo(String logoName) {
        this.logoName = logoName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

}
