package com.example.eg09batch.base.application.service;

import com.example.eg09batch.base.application.repository.BatchJobExecutionCustomRepository;
import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BatchJobServiceImpl implements BatchJobService {

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    BatchJobExecutionCustomRepository batchJobExecutionCustomRepository;

    @Override
    public BatchJobExecution findLastComplete(String jobName) {
        return batchJobExecutionCustomRepository.selectLastSuccessByJobName(jobName);
    }

    @Override
    public LocalDateTime getLastCompleteStartTime(String jobName) {
        BatchJobExecution batchJobExecution = findLastComplete(jobName);
        if (batchJobExecution != null) {
            return batchJobExecution.getStartTime();
        }
        return null;
    }

    @Override
    public boolean isRunning(String jobName) {
        JobExecution jobExecution = jobExplorer.getLastJobExecution(jobExplorer.getLastJobInstance(jobName));
        if (jobExecution != null) {
            return jobExecution.isRunning();
        }
        return false;
    }
}
