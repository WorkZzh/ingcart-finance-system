package com.pythe.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VCarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VCarExample() {
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

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNull() {
            addCriterion("device_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNotNull() {
            addCriterion("device_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdEqualTo(String value) {
            addCriterion("device_id =", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotEqualTo(String value) {
            addCriterion("device_id <>", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThan(String value) {
            addCriterion("device_id >", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThanOrEqualTo(String value) {
            addCriterion("device_id >=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThan(String value) {
            addCriterion("device_id <", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThanOrEqualTo(String value) {
            addCriterion("device_id <=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLike(String value) {
            addCriterion("device_id like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotLike(String value) {
            addCriterion("device_id not like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIn(List<String> values) {
            addCriterion("device_id in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotIn(List<String> values) {
            addCriterion("device_id not in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdBetween(String value1, String value2) {
            addCriterion("device_id between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotBetween(String value1, String value2) {
            addCriterion("device_id not between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
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

        public Criteria andLockKeyIsNull() {
            addCriterion("lock_key is null");
            return (Criteria) this;
        }

        public Criteria andLockKeyIsNotNull() {
            addCriterion("lock_key is not null");
            return (Criteria) this;
        }

        public Criteria andLockKeyEqualTo(String value) {
            addCriterion("lock_key =", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyNotEqualTo(String value) {
            addCriterion("lock_key <>", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyGreaterThan(String value) {
            addCriterion("lock_key >", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyGreaterThanOrEqualTo(String value) {
            addCriterion("lock_key >=", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyLessThan(String value) {
            addCriterion("lock_key <", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyLessThanOrEqualTo(String value) {
            addCriterion("lock_key <=", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyLike(String value) {
            addCriterion("lock_key like", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyNotLike(String value) {
            addCriterion("lock_key not like", value, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyIn(List<String> values) {
            addCriterion("lock_key in", values, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyNotIn(List<String> values) {
            addCriterion("lock_key not in", values, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyBetween(String value1, String value2) {
            addCriterion("lock_key between", value1, value2, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockKeyNotBetween(String value1, String value2) {
            addCriterion("lock_key not between", value1, value2, "lockKey");
            return (Criteria) this;
        }

        public Criteria andLockPasswordIsNull() {
            addCriterion("lock_password is null");
            return (Criteria) this;
        }

        public Criteria andLockPasswordIsNotNull() {
            addCriterion("lock_password is not null");
            return (Criteria) this;
        }

        public Criteria andLockPasswordEqualTo(String value) {
            addCriterion("lock_password =", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordNotEqualTo(String value) {
            addCriterion("lock_password <>", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordGreaterThan(String value) {
            addCriterion("lock_password >", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("lock_password >=", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordLessThan(String value) {
            addCriterion("lock_password <", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordLessThanOrEqualTo(String value) {
            addCriterion("lock_password <=", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordLike(String value) {
            addCriterion("lock_password like", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordNotLike(String value) {
            addCriterion("lock_password not like", value, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordIn(List<String> values) {
            addCriterion("lock_password in", values, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordNotIn(List<String> values) {
            addCriterion("lock_password not in", values, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordBetween(String value1, String value2) {
            addCriterion("lock_password between", value1, value2, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLockPasswordNotBetween(String value1, String value2) {
            addCriterion("lock_password not between", value1, value2, "lockPassword");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andQrIdIsNull() {
            addCriterion("qr_id is null");
            return (Criteria) this;
        }

        public Criteria andQrIdIsNotNull() {
            addCriterion("qr_id is not null");
            return (Criteria) this;
        }

        public Criteria andQrIdEqualTo(Long value) {
            addCriterion("qr_id =", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdNotEqualTo(Long value) {
            addCriterion("qr_id <>", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdGreaterThan(Long value) {
            addCriterion("qr_id >", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("qr_id >=", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdLessThan(Long value) {
            addCriterion("qr_id <", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdLessThanOrEqualTo(Long value) {
            addCriterion("qr_id <=", value, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdIn(List<Long> values) {
            addCriterion("qr_id in", values, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdNotIn(List<Long> values) {
            addCriterion("qr_id not in", values, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdBetween(Long value1, Long value2) {
            addCriterion("qr_id between", value1, value2, "qrId");
            return (Criteria) this;
        }

        public Criteria andQrIdNotBetween(Long value1, Long value2) {
            addCriterion("qr_id not between", value1, value2, "qrId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(String value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(String value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(String value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(String value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(String value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLike(String value) {
            addCriterion("record_id like", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotLike(String value) {
            addCriterion("record_id not like", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<String> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<String> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(String value1, String value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(String value1, String value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andUserIsNull() {
            addCriterion("user is null");
            return (Criteria) this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("user is not null");
            return (Criteria) this;
        }

        public Criteria andUserEqualTo(Long value) {
            addCriterion("user =", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotEqualTo(Long value) {
            addCriterion("user <>", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThan(Long value) {
            addCriterion("user >", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThanOrEqualTo(Long value) {
            addCriterion("user >=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThan(Long value) {
            addCriterion("user <", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThanOrEqualTo(Long value) {
            addCriterion("user <=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserIn(List<Long> values) {
            addCriterion("user in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotIn(List<Long> values) {
            addCriterion("user not in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserBetween(Long value1, Long value2) {
            addCriterion("user between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotBetween(Long value1, Long value2) {
            addCriterion("user not between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andC3IdIsNull() {
            addCriterion("c3_id is null");
            return (Criteria) this;
        }

        public Criteria andC3IdIsNotNull() {
            addCriterion("c3_id is not null");
            return (Criteria) this;
        }

        public Criteria andC3IdEqualTo(String value) {
            addCriterion("c3_id =", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdNotEqualTo(String value) {
            addCriterion("c3_id <>", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdGreaterThan(String value) {
            addCriterion("c3_id >", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdGreaterThanOrEqualTo(String value) {
            addCriterion("c3_id >=", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdLessThan(String value) {
            addCriterion("c3_id <", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdLessThanOrEqualTo(String value) {
            addCriterion("c3_id <=", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdLike(String value) {
            addCriterion("c3_id like", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdNotLike(String value) {
            addCriterion("c3_id not like", value, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdIn(List<String> values) {
            addCriterion("c3_id in", values, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdNotIn(List<String> values) {
            addCriterion("c3_id not in", values, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdBetween(String value1, String value2) {
            addCriterion("c3_id between", value1, value2, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3IdNotBetween(String value1, String value2) {
            addCriterion("c3_id not between", value1, value2, "c3Id");
            return (Criteria) this;
        }

        public Criteria andC3NameIsNull() {
            addCriterion("c3_name is null");
            return (Criteria) this;
        }

        public Criteria andC3NameIsNotNull() {
            addCriterion("c3_name is not null");
            return (Criteria) this;
        }

        public Criteria andC3NameEqualTo(String value) {
            addCriterion("c3_name =", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameNotEqualTo(String value) {
            addCriterion("c3_name <>", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameGreaterThan(String value) {
            addCriterion("c3_name >", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameGreaterThanOrEqualTo(String value) {
            addCriterion("c3_name >=", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameLessThan(String value) {
            addCriterion("c3_name <", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameLessThanOrEqualTo(String value) {
            addCriterion("c3_name <=", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameLike(String value) {
            addCriterion("c3_name like", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameNotLike(String value) {
            addCriterion("c3_name not like", value, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameIn(List<String> values) {
            addCriterion("c3_name in", values, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameNotIn(List<String> values) {
            addCriterion("c3_name not in", values, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameBetween(String value1, String value2) {
            addCriterion("c3_name between", value1, value2, "c3Name");
            return (Criteria) this;
        }

        public Criteria andC3NameNotBetween(String value1, String value2) {
            addCriterion("c3_name not between", value1, value2, "c3Name");
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