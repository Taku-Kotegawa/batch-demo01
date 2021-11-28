package com.example.batchdemo01.application.job.b001;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.batchdemo01.application.job.b001.B001Constants.JOB_ID;
import static com.example.batchdemo01.application.job.b001.B001Constants.TASKLET_NAME;

@Configuration
public class B001Config {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(TASKLET_NAME)
    Tasklet tasklet;

    /**
     * メソッド名でBean定義される点に注意
     *
     * @return
     */
    @Bean(JOB_ID)
    public Job job() {
        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet)
                .build();
    }
}
