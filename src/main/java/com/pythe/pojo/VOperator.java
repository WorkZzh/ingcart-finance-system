package com.pythe.pojo;

import java.util.Date;

public class VOperator {
    private Long id;

    private String openId;

    private String unionId;

    private String phoneNum;

    private String name;

    private Integer level;

    private Date created;

    private Integer type;

    private Integer status;

    private String xcxOpenId;

    private Long managerId;

    private String c1Id;

    private String c1Name;

    private String c2Id;

    private String c2Name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getXcxOpenId() {
        return xcxOpenId;
    }

    public void setXcxOpenId(String xcxOpenId) {
        this.xcxOpenId = xcxOpenId == null ? null : xcxOpenId.trim();
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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
}