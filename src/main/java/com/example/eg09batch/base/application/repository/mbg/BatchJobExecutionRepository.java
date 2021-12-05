package com.example.eg09batch.base.application.repository.mbg;

import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;
import com.example.eg09batch.base.domain.model.mbg.BatchJobExecutionExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface BatchJobExecutionRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    long countByExample(BatchJobExecutionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    List<BatchJobExecution> selectByExampleWithRowbounds(BatchJobExecutionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    List<BatchJobExecution> selectByExample(BatchJobExecutionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batch_job_execution
     *
     * @mbg.generated
     */
    BatchJobExecution selectByPrimaryKey(Long jobExecutionId);
}