package com.example.eg09batch.base.application.service;

import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;

public interface BatchJobService {

    BatchJobExecution findLastComplete(String jobName);

    boolean isRunning(String jobName);

}
