package com.chen.fy.mytakeout.entity;

import org.litepal.crud.LitePalSupport;

/**
 * 点菜信息的bean类
 */
public class MenuInfo extends LitePalSupport{

    private int id;
    private String logoName;
    private String name;
    private int sales;
    private int zan;
    private int price;

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

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
