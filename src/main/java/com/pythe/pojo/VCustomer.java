package com.pythe.pojo;

import java.util.Date;

public class VCustomer {
    private Long customerId;

    private String phoneNum;

    private Integer type;

    private String name;

    private String description;

    private Integer level;

    private Double amount;

    private Double inAmount;

    private Double outAmount;

    private Integer carStatus;

    private String carId;

    private Date startTime;

    private Date stopTime;

    private String openId;

    private String unionId;

    private String recordId;

    private String deviceId;

    private Long qrId;

    private String xcxOpenId;

    private Double giving;

    private Double price;

    private Integer pStatus;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public Double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId == null ? null : carId.trim();
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Long getQrId() {
        return qrId;
    }

    public void setQrId(Long qrId) {
        this.qrId = qrId;
    }

    public String getXcxOpenId() {
        return xcxOpenId;
    }

    public void setXcxOpenId(String xcxOpenId) {
        this.xcxOpenId = xcxOpenId == null ? null : xcxOpenId.trim();
    }

    public Double getGiving() {
        return giving;
    }

    public void setGiving(Double giving) {
        this.giving = giving;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }
}