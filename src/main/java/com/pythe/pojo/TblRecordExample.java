package com.pythe.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TblRecordExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andStopTimeIsNull() {
            addCriterion("stop_time is null");
            return (Criteria) this;
        }

        public Criteria andStopTimeIsNotNull() {
            addCriterion("stop_time is not null");
            return (Criteria) this;
        }

        public Criteria andStopTimeEqualTo(Date value) {
            addCriterion("stop_time =", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotEqualTo(Date value) {
            addCriterion("stop_time <>", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThan(Date value) {
            addCriterion("stop_time >", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("stop_time >=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThan(Date value) {
            addCriterion("stop_time <", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThanOrEqualTo(Date value) {
            addCriterion("stop_time <=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeIn(List<Date> values) {
            addCriterion("stop_time in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotIn(List<Date> values) {
            addCriterion("stop_time not in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeBetween(Date value1, Date value2) {
            addCriterion("stop_time between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotBetween(Date value1, Date value2) {
            addCriterion("stop_time not between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartIsNull() {
            addCriterion("longitde_start is null");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartIsNotNull() {
            addCriterion("longitde_start is not null");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartEqualTo(Double value) {
            addCriterion("longitde_start =", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartNotEqualTo(Double value) {
            addCriterion("longitde_start <>", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartGreaterThan(Double value) {
            addCriterion("longitde_start >", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartGreaterThanOrEqualTo(Double value) {
            addCriterion("longitde_start >=", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartLessThan(Double value) {
            addCriterion("longitde_start <", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartLessThanOrEqualTo(Double value) {
            addCriterion("longitde_start <=", value, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartIn(List<Double> values) {
            addCriterion("longitde_start in", values, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartNotIn(List<Double> values) {
            addCriterion("longitde_start not in", values, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartBetween(Double value1, Double value2) {
            addCriterion("longitde_start between", value1, value2, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLongitdeStartNotBetween(Double value1, Double value2) {
            addCriterion("longitde_start not between", value1, value2, "longitdeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartIsNull() {
            addCriterion("latitude_start is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartIsNotNull() {
            addCriterion("latitude_start is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartEqualTo(Double value) {
            addCriterion("latitude_start =", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartNotEqualTo(Double value) {
            addCriterion("latitude_start <>", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartGreaterThan(Double value) {
            addCriterion("latitude_start >", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude_start >=", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartLessThan(Double value) {
            addCriterion("latitude_start <", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartLessThanOrEqualTo(Double value) {
            addCriterion("latitude_start <=", value, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartIn(List<Double> values) {
            addCriterion("latitude_start in", values, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartNotIn(List<Double> values) {
            addCriterion("latitude_start not in", values, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartBetween(Double value1, Double value2) {
            addCriterion("latitude_start between", value1, value2, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLatitudeStartNotBetween(Double value1, Double value2) {
            addCriterion("latitude_start not between", value1, value2, "latitudeStart");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopIsNull() {
            addCriterion("longitude_stop is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopIsNotNull() {
            addCriterion("longitude_stop is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopEqualTo(Double value) {
            addCriterion("longitude_stop =", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopNotEqualTo(Double value) {
            addCriterion("longitude_stop <>", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopGreaterThan(Double value) {
            addCriterion("longitude_stop >", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude_stop >=", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopLessThan(Double value) {
            addCriterion("longitude_stop <", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopLessThanOrEqualTo(Double value) {
            addCriterion("longitude_stop <=", value, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopIn(List<Double> values) {
            addCriterion("longitude_stop in", values, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopNotIn(List<Double> values) {
            addCriterion("longitude_stop not in", values, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopBetween(Double value1, Double value2) {
            addCriterion("longitude_stop between", value1, value2, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLongitudeStopNotBetween(Double value1, Double value2) {
            addCriterion("longitude_stop not between", value1, value2, "longitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopIsNull() {
            addCriterion("latitude_stop is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopIsNotNull() {
            addCriterion("latitude_stop is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopEqualTo(Double value) {
            addCriterion("latitude_stop =", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopNotEqualTo(Double value) {
            addCriterion("latitude_stop <>", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopGreaterThan(Double value) {
            addCriterion("latitude_stop >", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude_stop >=", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopLessThan(Double value) {
            addCriterion("latitude_stop <", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopLessThanOrEqualTo(Double value) {
            addCriterion("latitude_stop <=", value, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopIn(List<Double> values) {
            addCriterion("latitude_stop in", values, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopNotIn(List<Double> values) {
            addCriterion("latitude_stop not in", values, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopBetween(Double value1, Double value2) {
            addCriterion("latitude_stop between", value1, value2, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andLatitudeStopNotBetween(Double value1, Double value2) {
            addCriterion("latitude_stop not between", value1, value2, "latitudeStop");
            return (Criteria) this;
        }

        public Criteria andBillIdIsNull() {
            addCriterion("bill_id is null");
            return (Criteria) this;
        }

        public Criteria andBillIdIsNotNull() {
            addCriterion("bill_id is not null");
            return (Criteria) this;
        }

        public Criteria andBillIdEqualTo(String value) {
            addCriterion("bill_id =", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotEqualTo(String value) {
            addCriterion("bill_id <>", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdGreaterThan(String value) {
            addCriterion("bill_id >", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdGreaterThanOrEqualTo(String value) {
            addCriterion("bill_id >=", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdLessThan(String value) {
            addCriterion("bill_id <", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdLessThanOrEqualTo(String value) {
            addCriterion("bill_id <=", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdLike(String value) {
            addCriterion("bill_id like", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotLike(String value) {
            addCriterion("bill_id not like", value, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdIn(List<String> values) {
            addCriterion("bill_id in", values, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotIn(List<String> values) {
            addCriterion("bill_id not in", values, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdBetween(String value1, String value2) {
            addCriterion("bill_id between", value1, value2, "billId");
            return (Criteria) this;
        }

        public Criteria andBillIdNotBetween(String value1, String value2) {
            addCriterion("bill_id not between", value1, value2, "billId");
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