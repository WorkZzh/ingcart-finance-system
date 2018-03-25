package com.pythe.pojo;

import java.util.Date;

public class VCar {
    private String description;

    private String deviceId;

    private Date endTime;

    private Double latitude;

    private Integer level;

    private String lockKey;

    private String lockPassword;

    private Double longitude;

    private Long qrId;

    private String recordId;

    private Date startTime;

    private Long user;

    private Integer status;

    private String c1Id;

    private String c1Name;

    private String c2Id;

    private String c2Name;

    private String c3Id;

    private String c3Name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey == null ? null : lockKey.trim();
    }

    public String getLockPassword() {
        return lockPassword;
    }

    public void setLockPassword(String lockPassword) {
        this.lockPassword = lockPassword == null ? null : lockPassword.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getQrId() {
        return qrId;
    }

    public void setQrId(Long qrId) {
        this.qrId = qrId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getC3Id() {
        return c3Id;
    }

    public void setC3Id(String c3Id) {
        this.c3Id = c3Id == null ? null : c3Id.trim();
    }

    public String getC3Name() {
        return c3Name;
    }

    public void setC3Name(String c3Name) {
        this.c3Name = c3Name == null ? null : c3Name.trim();
    }
}