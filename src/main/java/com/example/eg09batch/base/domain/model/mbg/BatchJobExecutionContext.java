package com.example.eg09batch.base.domain.model.mbg;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table batch_job_execution_context
 */
@Data
public class BatchJobExecutionContext implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column batch_job_execution_context.job_execution_id
     *
     * @mbg.generated
     */
    private Long jobExecutionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column batch_job_execution_context.short_context
     *
     * @mbg.generated
     */
    private String shortContext;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column batch_job_execution_context.serialized_context
     *
     * @mbg.generated
     */
    private String serializedContext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table batch_job_execution_context
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}