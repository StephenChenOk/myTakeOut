package com.chen.fy.mytakeout.entity;

import org.litepal.crud.LitePalSupport;

public class OrderInfo extends LitePalSupport{

    private int id;
    private String userId;
    private String storeName;
    private String menuName;
    private int price;
    private long orderLongDate;
    private boolean orderType;
    private boolean evaluationType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getOrderLongDate() {
        return orderLongDate;
    }

    public void setOrderLongDate(long orderLongDate) {
        this.orderLongDate = orderLongDate;
    }

    public boolean isOrderType() {
        return orderType;
    }

    public void setOrderType(boolean orderType) {
        this.orderType = orderType;
    }

    public boolean isEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(boolean evaluationType) {
        this.evaluationType = evaluationType;
    }
}
