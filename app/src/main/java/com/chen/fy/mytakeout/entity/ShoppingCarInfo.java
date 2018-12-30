package com.chen.fy.mytakeout.entity;

import org.litepal.crud.LitePalSupport;

/**
 * 购物车信息实体类
 */
public class ShoppingCarInfo extends LitePalSupport {

    private String userName;
    private String storeName;
    private String menuLogo;
    private String menuName;
    private int price;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMenuLogo() {
        return menuLogo;
    }

    public void setMenuLogo(String menuLogo) {
        this.menuLogo = menuLogo;
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
}
