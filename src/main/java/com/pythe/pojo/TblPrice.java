package com.pythe.pojo;

public class TblPrice {
    private String level;

    private Double price;

    private Double giving;

    private String annotation;

    private Integer status;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getGiving() {
        return giving;
    }

    public void setGiving(Double giving) {
        this.giving = giving;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation == null ? null : annotation.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}