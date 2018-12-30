package com.chen.fy.mytakeout.entity;


import org.litepal.crud.LitePalSupport;

public class EvaluationInfo extends LitePalSupport{

    private int id;
    private String userId;
    private String userName;
    private String storeName;
    private long evaluationLongDate;
    private float gradeEvaluation;
    private String textEvaluation;
    private String menu;

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

    public long getEvaluationLongDate() {
        return evaluationLongDate;
    }

    public void setEvaluationLongDate(long evaluationLongDate) {
        this.evaluationLongDate = evaluationLongDate;
    }

    public float getGradeEvaluation() {
        return gradeEvaluation;
    }

    public void setGradeEvaluation(float gradeEvaluation) {
        this.gradeEvaluation = gradeEvaluation;
    }

    public String getTextEvaluation() {
        return textEvaluation;
    }

    public void setTextEvaluation(String textEvaluation) {
        this.textEvaluation = textEvaluation;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
