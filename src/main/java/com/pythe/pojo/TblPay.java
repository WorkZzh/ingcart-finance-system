package com.pythe.pojo;

import java.util.Date;

public class TblPay {
    private Long id;

    private Date date;

    private String orderNum;

    private String recordid;

    private Date startTime;

    private Date stopTime;

    private Long qrid;

    private Long customerId;

    private String phoneNum;

    private String description;

    private Integer type;

    private String prepayId;

    private Double sum;

    private Double longitudeStart;

    private Double latitudeStart;

    private Double longitudeStop;

    private Double latitudeStop;

    private String billId;

    private String carId;

    private Double amount;

    private Double givingAmount;

    private Double refundAmount;

    private String customerTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid == null ? null : recordid.trim();
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

    public Long getQrid() {
        return qrid;
    }

    public void setQrid(Long qrid) {
        this.qrid = qrid;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId == null ? null : prepayId.trim();
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getLongitudeStart() {
        return longitudeStart;
    }

    public void setLongitudeStart(Double longitudeStart) {
        this.longitudeStart = longitudeStart;
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

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId == null ? null : billId.trim();
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId == null ? null : carId.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getGivingAmount() {
        return givingAmount;
    }

    public void setGivingAmount(Double givingAmount) {
        this.givingAmount = givingAmount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCustomerTag() {
        return customerTag;
    }

    public void setCustomerTag(String customerTag) {
        this.customerTag = customerTag == null ? null : customerTag.trim();
    }
}