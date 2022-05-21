package com.example.eg09batch.dataSync.application.batch.b001;

import com.example.eg09batch.common.batch.CustomIncrementer;
import com.example.eg09batch.dataSync.application.common.JobStartTasklet;
import com.example.eg09batch.dataSync.application.common.PrintOutTasklet;
import com.example.eg09batch.dataSync.application.common.WebClientTasklet;
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

//    @Autowired
//    S3PushTasklet s3PushTasklet;

    @Autowired
    WebClientTasklet webClientTasklet;

    @Bean(JOB_ID)
    public Job job() {

        Step step0 = step0();
        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(step2())
                .next(step3())
                .next(step4())
                .build();

        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new CustomIncrementer())
                .start(step0).on(ExitStatus.STOPPED.getExitCode()).to(step1())
                .from(step0).on(ExitStatus.COMPLETED.getExitCode()).to(flow1)
                .end()
//                .start(step0())
//                .next(step1())
//                .next(step2())
                .build();
    }

    private Step step0() {
        return stepBuilderFactory.get("jobStartTasklet")
                .tasklet(jobStartTasklet)
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("webClientTasklet")
                .tasklet(new PrintOutTasklet("ste1を実行します。"))
                .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new PrintOutTasklet("step2を実行します。"))
                .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new PrintOutTasklet("step3を実行します。"))
                .build();
    }

    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(new PrintOutTasklet("step4を実行します。"))
                .build();
    }

//    private Step step2() {
//        return stepBuilderFactory.get("s3PushTasklet")
//                .tasklet(s3PushTasklet)
//                .build();
//    }
}
