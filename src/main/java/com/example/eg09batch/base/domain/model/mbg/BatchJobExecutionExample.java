package com.example.eg09batch.base.domain.model.mbg;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BatchJobExecutionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public BatchJobExecutionExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andJobExecutionIdIsNull() {
            addCriterion("job_execution_id is null");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdIsNotNull() {
            addCriterion("job_execution_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdEqualTo(Long value) {
            addCriterion("job_execution_id =", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdNotEqualTo(Long value) {
            addCriterion("job_execution_id <>", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdGreaterThan(Long value) {
            addCriterion("job_execution_id >", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("job_execution_id >=", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdLessThan(Long value) {
            addCriterion("job_execution_id <", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdLessThanOrEqualTo(Long value) {
            addCriterion("job_execution_id <=", value, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdIn(List<Long> values) {
            addCriterion("job_execution_id in", values, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdNotIn(List<Long> values) {
            addCriterion("job_execution_id not in", values, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdBetween(Long value1, Long value2) {
            addCriterion("job_execution_id between", value1, value2, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andJobExecutionIdNotBetween(Long value1, Long value2) {
            addCriterion("job_execution_id not between", value1, value2, "jobExecutionId");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdIsNull() {
            addCriterion("job_instance_id is null");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdIsNotNull() {
            addCriterion("job_instance_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdEqualTo(Long value) {
            addCriterion("job_instance_id =", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdNotEqualTo(Long value) {
            addCriterion("job_instance_id <>", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdGreaterThan(Long value) {
            addCriterion("job_instance_id >", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("job_instance_id >=", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdLessThan(Long value) {
            addCriterion("job_instance_id <", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdLessThanOrEqualTo(Long value) {
            addCriterion("job_instance_id <=", value, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdIn(List<Long> values) {
            addCriterion("job_instance_id in", values, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdNotIn(List<Long> values) {
            addCriterion("job_instance_id not in", values, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdBetween(Long value1, Long value2) {
            addCriterion("job_instance_id between", value1, value2, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andJobInstanceIdNotBetween(Long value1, Long value2) {
            addCriterion("job_instance_id not between", value1, value2, "jobInstanceId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andStartTimeEqualTo(LocalDateTime value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(LocalDateTime value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(LocalDateTime value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(LocalDateTime value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<LocalDateTime> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<LocalDateTime> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
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

        public Criteria andEndTimeEqualTo(LocalDateTime value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(LocalDateTime value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(LocalDateTime value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(LocalDateTime value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<LocalDateTime> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<LocalDateTime> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andExitCodeIsNull() {
            addCriterion("exit_code is null");
            return (Criteria) this;
        }

        public Criteria andExitCodeIsNotNull() {
            addCriterion("exit_code is not null");
            return (Criteria) this;
        }

        public Criteria andExitCodeEqualTo(String value) {
            addCriterion("exit_code =", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeNotEqualTo(String value) {
            addCriterion("exit_code <>", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeGreaterThan(String value) {
            addCriterion("exit_code >", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("exit_code >=", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeLessThan(String value) {
            addCriterion("exit_code <", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeLessThanOrEqualTo(String value) {
            addCriterion("exit_code <=", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeLike(String value) {
            addCriterion("exit_code like", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeNotLike(String value) {
            addCriterion("exit_code not like", value, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeIn(List<String> values) {
            addCriterion("exit_code in", values, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeNotIn(List<String> values) {
            addCriterion("exit_code not in", values, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeBetween(String value1, String value2) {
            addCriterion("exit_code between", value1, value2, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitCodeNotBetween(String value1, String value2) {
            addCriterion("exit_code not between", value1, value2, "exitCode");
            return (Criteria) this;
        }

        public Criteria andExitMessageIsNull() {
            addCriterion("exit_message is null");
            return (Criteria) this;
        }

        public Criteria andExitMessageIsNotNull() {
            addCriterion("exit_message is not null");
            return (Criteria) this;
        }

        public Criteria andExitMessageEqualTo(String value) {
            addCriterion("exit_message =", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageNotEqualTo(String value) {
            addCriterion("exit_message <>", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageGreaterThan(String value) {
            addCriterion("exit_message >", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageGreaterThanOrEqualTo(String value) {
            addCriterion("exit_message >=", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageLessThan(String value) {
            addCriterion("exit_message <", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageLessThanOrEqualTo(String value) {
            addCriterion("exit_message <=", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageLike(String value) {
            addCriterion("exit_message like", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageNotLike(String value) {
            addCriterion("exit_message not like", value, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageIn(List<String> values) {
            addCriterion("exit_message in", values, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageNotIn(List<String> values) {
            addCriterion("exit_message not in", values, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageBetween(String value1, String value2) {
            addCriterion("exit_message between", value1, value2, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andExitMessageNotBetween(String value1, String value2) {
            addCriterion("exit_message not between", value1, value2, "exitMessage");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedIsNull() {
            addCriterion("last_updated is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedIsNotNull() {
            addCriterion("last_updated is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedEqualTo(LocalDateTime value) {
            addCriterion("last_updated =", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedNotEqualTo(LocalDateTime value) {
            addCriterion("last_updated <>", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedGreaterThan(LocalDateTime value) {
            addCriterion("last_updated >", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_updated >=", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedLessThan(LocalDateTime value) {
            addCriterion("last_updated <", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_updated <=", value, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedIn(List<LocalDateTime> values) {
            addCriterion("last_updated in", values, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedNotIn(List<LocalDateTime> values) {
            addCriterion("last_updated not in", values, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_updated between", value1, value2, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_updated not between", value1, value2, "lastUpdated");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationIsNull() {
            addCriterion("job_configuration_location is null");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationIsNotNull() {
            addCriterion("job_configuration_location is not null");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationEqualTo(String value) {
            addCriterion("job_configuration_location =", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationNotEqualTo(String value) {
            addCriterion("job_configuration_location <>", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationGreaterThan(String value) {
            addCriterion("job_configuration_location >", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationGreaterThanOrEqualTo(String value) {
            addCriterion("job_configuration_location >=", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationLessThan(String value) {
            addCriterion("job_configuration_location <", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationLessThanOrEqualTo(String value) {
            addCriterion("job_configuration_location <=", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationLike(String value) {
            addCriterion("job_configuration_location like", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationNotLike(String value) {
            addCriterion("job_configuration_location not like", value, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationIn(List<String> values) {
            addCriterion("job_configuration_location in", values, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationNotIn(List<String> values) {
            addCriterion("job_configuration_location not in", values, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationBetween(String value1, String value2) {
            addCriterion("job_configuration_location between", value1, value2, "jobConfigurationLocation");
            return (Criteria) this;
        }

        public Criteria andJobConfigurationLocationNotBetween(String value1, String value2) {
            addCriterion("job_configuration_location not between", value1, value2, "jobConfigurationLocation");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table batch_job_execution
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
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