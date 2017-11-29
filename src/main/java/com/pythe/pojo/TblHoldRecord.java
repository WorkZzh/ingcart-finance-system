package com.pythe.pojo;

import java.util.Date;

public class TblHoldRecord {
    private Long id;

    private String carId;

    private Long customerId;

    private Date holdStartTime;

    private Date holdStopTime;

    private String recordId;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId == null ? null : carId.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getHoldStartTime() {
        return holdStartTime;
    }

    public void setHoldStartTime(Date holdStartTime) {
        this.holdStartTime = holdStartTime;
    }

    public Date getHoldStopTime() {
        return holdStopTime;
    }

    public void setHoldStopTime(Date holdStopTime) {
        this.holdStopTime = holdStopTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}