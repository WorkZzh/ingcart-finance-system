package com.pythe.pojo;

import java.time.Duration;
import java.util.Date;

public class VOperatorRecord {
    private String id;

    private Long operatorId;

    private Long qrId;

    private Integer status;

    private Date startTime;

    private Date stopTime;

    private Double longitdeStart;

    private Double latitudeStart;

    private Double longitudeStop;

    private Double latitudeStop;

    private String description;
    
    private String duration;
    

    public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getQrId() {
        return qrId;
    }

    public void setQrId(Long qrId) {
        this.qrId = qrId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}