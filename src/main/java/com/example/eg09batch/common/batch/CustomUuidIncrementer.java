package com.example.eg09batch.common.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.UUID;

/**
 * 前回実行時パラメータを引き継がない
 */
public class CustomUuidIncrementer implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters jobParameters) {
//        if (jobParameters==null || jobParameters.isEmpty()) {
//            return new JobParametersBuilder().addLong("run.id", 1L).toJobParameters();
//        }
//        long id = jobParameters.getLong("run.id",1L) + 1;
//        return new JobParametersBuilder().addLong("run.id", id).toJobParameters();

        return new JobParametersBuilder()
//                .addJobParameters(jobParameters)
                .addString("run.uuid", UUID.randomUUID().toString())
                .toJobParameters();
    }
}
