package com.pythe.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblHoldRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TblHoldRecordExample() {
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

        public Criteria andCarIdIsNull() {
            addCriterion("car_id is null");
            return (Criteria) this;
        }

        public Criteria andCarIdIsNotNull() {
            addCriterion("car_id is not null");
            return (Criteria) this;
        }

        public Criteria andCarIdEqualTo(String value) {
            addCriterion("car_id =", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotEqualTo(String value) {
            addCriterion("car_id <>", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThan(String value) {
            addCriterion("car_id >", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThanOrEqualTo(String value) {
            addCriterion("car_id >=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThan(String value) {
            addCriterion("car_id <", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThanOrEqualTo(String value) {
            addCriterion("car_id <=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLike(String value) {
            addCriterion("car_id like", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotLike(String value) {
            addCriterion("car_id not like", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdIn(List<String> values) {
            addCriterion("car_id in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotIn(List<String> values) {
            addCriterion("car_id not in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdBetween(String value1, String value2) {
            addCriterion("car_id between", value1, value2, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotBetween(String value1, String value2) {
            addCriterion("car_id not between", value1, value2, "carId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Long value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Long value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Long value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Long value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Long value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Long> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Long> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Long value1, Long value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Long value1, Long value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeIsNull() {
            addCriterion("hold_start_time is null");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeIsNotNull() {
            addCriterion("hold_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeEqualTo(Date value) {
            addCriterion("hold_start_time =", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeNotEqualTo(Date value) {
            addCriterion("hold_start_time <>", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeGreaterThan(Date value) {
            addCriterion("hold_start_time >", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("hold_start_time >=", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeLessThan(Date value) {
            addCriterion("hold_start_time <", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("hold_start_time <=", value, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeIn(List<Date> values) {
            addCriterion("hold_start_time in", values, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeNotIn(List<Date> values) {
            addCriterion("hold_start_time not in", values, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeBetween(Date value1, Date value2) {
            addCriterion("hold_start_time between", value1, value2, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("hold_start_time not between", value1, value2, "holdStartTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeIsNull() {
            addCriterion("hold_stop_time is null");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeIsNotNull() {
            addCriterion("hold_stop_time is not null");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeEqualTo(Date value) {
            addCriterion("hold_stop_time =", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeNotEqualTo(Date value) {
            addCriterion("hold_stop_time <>", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeGreaterThan(Date value) {
            addCriterion("hold_stop_time >", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("hold_stop_time >=", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeLessThan(Date value) {
            addCriterion("hold_stop_time <", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeLessThanOrEqualTo(Date value) {
            addCriterion("hold_stop_time <=", value, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeIn(List<Date> values) {
            addCriterion("hold_stop_time in", values, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeNotIn(List<Date> values) {
            addCriterion("hold_stop_time not in", values, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeBetween(Date value1, Date value2) {
            addCriterion("hold_stop_time between", value1, value2, "holdStopTime");
            return (Criteria) this;
        }

        public Criteria andHoldStopTimeNotBetween(Date value1, Date value2) {
            addCriterion("hold_stop_time not between", value1, value2, "holdStopTime");
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