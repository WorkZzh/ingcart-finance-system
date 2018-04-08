package com.pythe.pojo;

import java.util.ArrayList;
import java.util.List;

public class VCatalogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VCatalogExample() {
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