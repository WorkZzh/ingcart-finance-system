package com.pythe.pojo;

public class TblFaultType {
    private Integer type;

    private String fault;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault == null ? null : fault.trim();
    }
}