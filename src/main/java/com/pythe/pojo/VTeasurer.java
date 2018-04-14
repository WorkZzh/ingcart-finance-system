package com.pythe.pojo;

import java.util.Date;

public class VTeasurer {
    private Long id;

    private String phoneNum;

    private Integer level;

    private Date created;

    private Integer type;

    private String password;

    private String name;

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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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