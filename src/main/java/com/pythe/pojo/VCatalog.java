package com.pythe.pojo;

public class VCatalog {
    private String c1Id;

    private String c1Name;

    private String c2Id;

    private String c2Name;

    public String getC1Id() {
        return c1Id;
    }

    public void setC1Id(String c1Id) {
        this.c1Id = c1Id == null ? null : c1Id.trim();
    }

    public String getC1Name() {
        return c1Name;
    }

    public void setC1Name(String c1Name) {
        this.c1Name = c1Name == null ? null : c1Name.trim();
    }

    public String getC2Id() {
        return c2Id;
    }

    public void setC2Id(String c2Id) {
        this.c2Id = c2Id == null ? null : c2Id.trim();
    }

    public String getC2Name() {
        return c2Name;
    }

    public void setC2Name(String c2Name) {
        this.c2Name = c2Name == null ? null : c2Name.trim();
    }
}