package com.example.eg09batch.dataSync.application.batch.b001;

import com.example.eg09batch.common.batch.CustomIncrementer;
import com.example.eg09batch.dataSync.application.common.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.eg09batch.dataSync.application.batch.b001.B001Constants.JOB_ID;
import static com.example.eg09batch.dataSync.application.batch.b001.B001Constants.TASKLET_NAME;


@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "b001")
@Slf4j
@Configuration
public class B001Config {
    B001Config(){
        log.debug("**** 初期化しました。*****");
    }

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

    @Autowired
    S3TestTasklet s3TestTasklet;

    @Autowired
    WebClientTasklet webClientTasklet;

    @Bean
    public Job job(Step step0, Step step1, Step step2, Step step3, Step step4, Step step5) {

        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(step2)
                .next(step3)
                .next(step4)
                .next(step5)
                .build();

        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new CustomIncrementer())
                .start(step0).on(ExitStatus.STOPPED.getExitCode()).to(step1)
                .from(step0).on(ExitStatus.COMPLETED.getExitCode()).to(flow1)
                .end()
                .build();
    }

    @Bean
    Step step0() {
        return stepBuilderFactory.get("jobStartTasklet")
                .tasklet(jobStartTasklet)
                .build();
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("webClientTasklet")
                .tasklet(new PrintOutTasklet("ste1を実行します。"))
                .build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new PrintOutTasklet("step2を実行します。"))
                .build();
    }

    @Bean
    Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new PrintOutTasklet("step3を実行します。"))
                .build();
    }

    @Bean
    Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(new PrintOutTasklet("step4を実行します。"))
                .build();
    }

    @Bean
    Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet(tasklet)
                .tasklet(s3TestTasklet)
                .build();
    }

}
