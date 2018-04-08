package com.pythe.pojo;

public class TblCatalog {
    private String id;

    private String name;

    private Integer code;

    private String higherLevelId;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getHigherLevelId() {
        return higherLevelId;
    }

    public void setHigherLevelId(String higherLevelId) {
        this.higherLevelId = higherLevelId == null ? null : higherLevelId.trim();
    }
}