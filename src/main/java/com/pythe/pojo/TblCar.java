package com.pythe.pojo;

import java.util.Date;

public class TblCar {
    private String id;

    private String description;

    private String deviceId;

    private Date endtime;

    private Double latitude;

    private Integer level;

    private String lockKey;

    private String lockPassword;

    private Double longitude;

    private Long qrId;

    private String recordid;

    private Date starttime;

    private Long user;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

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

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid == null ? null : recordid.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
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
}