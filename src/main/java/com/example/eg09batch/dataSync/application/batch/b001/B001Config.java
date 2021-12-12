package com.example.eg09batch.dataSync.application.batch.b001;

import com.example.eg09batch.common.batch.CustomIncrementer;
import com.example.eg09batch.dataSync.application.common.JobStartTasklet;
import com.example.eg09batch.dataSync.application.common.S3PushTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import static com.example.eg09batch.dataSync.application.batch.b001.B001Constants.JOB_ID;
import static com.example.eg09batch.dataSync.application.batch.b001.B001Constants.TASKLET_NAME;


@Configuration
public class B001Config {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(TASKLET_NAME)
    Tasklet tasklet;

    @Autowired
    JobStartTasklet jobStartTasklet;

    @Autowired
    S3PushTasklet s3PushTasklet;

    @Bean(JOB_ID)
    public Job job() {
        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new CustomIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(jobStartTasklet)
                .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(tasklet)
                .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(s3PushTasklet)
                .build();
    }
}
