package com.example.eg09batch.base.application.repository;

import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;

public interface BatchJobExecutionCustomRepository {
    BatchJobExecution selectLastSuccessByJobName(String jobName);
}