package com.pythe.pojo;

public class TblPrice {
    private String level;

    private Double price;

    private Double giving;

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
}