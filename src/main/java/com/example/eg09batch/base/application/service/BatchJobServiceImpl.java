package com.example.eg09batch.base.application.service;

import com.example.eg09batch.base.application.repository.BatchJobExecutionCustomRepository;
import com.example.eg09batch.base.application.repository.mbg.BatchJobExecutionRepository;
import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;
import com.example.eg09batch.base.domain.model.mbg.BatchJobExecutionExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean isRunning(String jobName) {
        JobExecution jobExecution = jobExplorer.getLastJobExecution(jobExplorer.getLastJobInstance(jobName));
        if (jobExecution != null) {
            return jobExecution.isRunning();
        }
        return false;
    }
}
