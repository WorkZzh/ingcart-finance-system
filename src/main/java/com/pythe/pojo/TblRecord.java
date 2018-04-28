package com.pythe.pojo;

import java.util.Date;

public class TblRecord {
    private String id;

    private Long customerId;

    private String carId;

    private Integer status;

    private Date startTime;

    private Date stopTime;

    private Double longitdeStart;

    private Double latitudeStart;

    private Double longitudeStop;

    private Double latitudeStop;

    private Double amount;

    private Double refundAmount;

    private Double givingAmount;

    private Long managerId;

    private Integer type;

    private String outTradeNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId == null ? null : carId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Double getLongitdeStart() {
        return longitdeStart;
    }

    public void setLongitdeStart(Double longitdeStart) {
        this.longitdeStart = longitdeStart;
    }

    public Double getLatitudeStart() {
        return latitudeStart;
    }

    public void setLatitudeStart(Double latitudeStart) {
        this.latitudeStart = latitudeStart;
    }

    public Double getLongitudeStop() {
        return longitudeStop;
    }

    public void setLongitudeStop(Double longitudeStop) {
        this.longitudeStop = longitudeStop;
    }

    public Double getLatitudeStop() {
        return latitudeStop;
    }

    public void setLatitudeStop(Double latitudeStop) {
        this.latitudeStop = latitudeStop;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getGivingAmount() {
        return givingAmount;
    }

    public void setGivingAmount(Double givingAmount) {
        this.givingAmount = givingAmount;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }
}