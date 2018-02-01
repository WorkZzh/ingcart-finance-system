package com.pythe.pojo;

import java.util.Date;

public class TblStore {
    private String id;

    private String name;

    private Double longitude;

    private Double latitude;

    private String locationName;

    private Date created;

    private String description;

    private Integer status;

    private Integer bagNum;

    private Integer inBagNum;

    private Integer outBagNum;

    private String storeHours;

    private String dealer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName == null ? null : locationName.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBagNum() {
        return bagNum;
    }

    public void setBagNum(Integer bagNum) {
        this.bagNum = bagNum;
    }

    public Integer getInBagNum() {
        return inBagNum;
    }

    public void setInBagNum(Integer inBagNum) {
        this.inBagNum = inBagNum;
    }

    public Integer getOutBagNum() {
        return outBagNum;
    }

    public void setOutBagNum(Integer outBagNum) {
        this.outBagNum = outBagNum;
    }

    public String getStoreHours() {
        return storeHours;
    }

    public void setStoreHours(String storeHours) {
        this.storeHours = storeHours == null ? null : storeHours.trim();
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }
}