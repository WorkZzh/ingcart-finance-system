package com.pythe.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VTeasurerRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VTeasurerRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNull() {
            addCriterion("phone_num is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNotNull() {
            addCriterion("phone_num is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumEqualTo(String value) {
            addCriterion("phone_num =", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotEqualTo(String value) {
            addCriterion("phone_num <>", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThan(String value) {
            addCriterion("phone_num >", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThanOrEqualTo(String value) {
            addCriterion("phone_num >=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThan(String value) {
            addCriterion("phone_num <", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThanOrEqualTo(String value) {
            addCriterion("phone_num <=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLike(String value) {
            addCriterion("phone_num like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotLike(String value) {
            addCriterion("phone_num not like", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIn(List<String> values) {
            addCriterion("phone_num in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotIn(List<String> values) {
            addCriterion("phone_num not in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumBetween(String value1, String value2) {
            addCriterion("phone_num between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotBetween(String value1, String value2) {
            addCriterion("phone_num not between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("created is null");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("created is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedEqualTo(Date value) {
            addCriterion("created =", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotEqualTo(Date value) {
            addCriterion("created <>", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThan(Date value) {
            addCriterion("created >", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("created >=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThan(Date value) {
            addCriterion("created <", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThanOrEqualTo(Date value) {
            addCriterion("created <=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedIn(List<Date> values) {
            addCriterion("created in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotIn(List<Date> values) {
            addCriterion("created not in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedBetween(Date value1, Date value2) {
            addCriterion("created between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotBetween(Date value1, Date value2) {
            addCriterion("created not between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andC1IdIsNull() {
            addCriterion("c1_id is null");
            return (Criteria) this;
        }

        public Criteria andC1IdIsNotNull() {
            addCriterion("c1_id is not null");
            return (Criteria) this;
        }

        public Criteria andC1IdEqualTo(String value) {
            addCriterion("c1_id =", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdNotEqualTo(String value) {
            addCriterion("c1_id <>", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdGreaterThan(String value) {
            addCriterion("c1_id >", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdGreaterThanOrEqualTo(String value) {
            addCriterion("c1_id >=", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdLessThan(String value) {
            addCriterion("c1_id <", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdLessThanOrEqualTo(String value) {
            addCriterion("c1_id <=", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdLike(String value) {
            addCriterion("c1_id like", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdNotLike(String value) {
            addCriterion("c1_id not like", value, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdIn(List<String> values) {
            addCriterion("c1_id in", values, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdNotIn(List<String> values) {
            addCriterion("c1_id not in", values, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdBetween(String value1, String value2) {
            addCriterion("c1_id between", value1, value2, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1IdNotBetween(String value1, String value2) {
            addCriterion("c1_id not between", value1, value2, "c1Id");
            return (Criteria) this;
        }

        public Criteria andC1NameIsNull() {
            addCriterion("c1_name is null");
            return (Criteria) this;
        }

        public Criteria andC1NameIsNotNull() {
            addCriterion("c1_name is not null");
            return (Criteria) this;
        }

        public Criteria andC1NameEqualTo(String value) {
            addCriterion("c1_name =", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameNotEqualTo(String value) {
            addCriterion("c1_name <>", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameGreaterThan(String value) {
            addCriterion("c1_name >", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameGreaterThanOrEqualTo(String value) {
            addCriterion("c1_name >=", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameLessThan(String value) {
            addCriterion("c1_name <", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameLessThanOrEqualTo(String value) {
            addCriterion("c1_name <=", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameLike(String value) {
            addCriterion("c1_name like", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameNotLike(String value) {
            addCriterion("c1_name not like", value, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameIn(List<String> values) {
            addCriterion("c1_name in", values, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameNotIn(List<String> values) {
            addCriterion("c1_name not in", values, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameBetween(String value1, String value2) {
            addCriterion("c1_name between", value1, value2, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC1NameNotBetween(String value1, String value2) {
            addCriterion("c1_name not between", value1, value2, "c1Name");
            return (Criteria) this;
        }

        public Criteria andC2IdIsNull() {
            addCriterion("c2_id is null");
            return (Criteria) this;
        }

        public Criteria andC2IdIsNotNull() {
            addCriterion("c2_id is not null");
            return (Criteria) this;
        }

        public Criteria andC2IdEqualTo(String value) {
            addCriterion("c2_id =", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdNotEqualTo(String value) {
            addCriterion("c2_id <>", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdGreaterThan(String value) {
            addCriterion("c2_id >", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdGreaterThanOrEqualTo(String value) {
            addCriterion("c2_id >=", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdLessThan(String value) {
            addCriterion("c2_id <", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdLessThanOrEqualTo(String value) {
            addCriterion("c2_id <=", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdLike(String value) {
            addCriterion("c2_id like", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdNotLike(String value) {
            addCriterion("c2_id not like", value, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdIn(List<String> values) {
            addCriterion("c2_id in", values, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdNotIn(List<String> values) {
            addCriterion("c2_id not in", values, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdBetween(String value1, String value2) {
            addCriterion("c2_id between", value1, value2, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2IdNotBetween(String value1, String value2) {
            addCriterion("c2_id not between", value1, value2, "c2Id");
            return (Criteria) this;
        }

        public Criteria andC2NameIsNull() {
            addCriterion("c2_name is null");
            return (Criteria) this;
        }

        public Criteria andC2NameIsNotNull() {
            addCriterion("c2_name is not null");
            return (Criteria) this;
        }

        public Criteria andC2NameEqualTo(String value) {
            addCriterion("c2_name =", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameNotEqualTo(String value) {
            addCriterion("c2_name <>", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameGreaterThan(String value) {
            addCriterion("c2_name >", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameGreaterThanOrEqualTo(String value) {
            addCriterion("c2_name >=", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameLessThan(String value) {
            addCriterion("c2_name <", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameLessThanOrEqualTo(String value) {
            addCriterion("c2_name <=", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameLike(String value) {
            addCriterion("c2_name like", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameNotLike(String value) {
            addCriterion("c2_name not like", value, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameIn(List<String> values) {
            addCriterion("c2_name in", values, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameNotIn(List<String> values) {
            addCriterion("c2_name not in", values, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameBetween(String value1, String value2) {
            addCriterion("c2_name between", value1, value2, "c2Name");
            return (Criteria) this;
        }

        public Criteria andC2NameNotBetween(String value1, String value2) {
            addCriterion("c2_name not between", value1, value2, "c2Name");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}