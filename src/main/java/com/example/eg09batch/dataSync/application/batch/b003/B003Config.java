package com.example.eg09batch.dataSync.application.batch.b003;

import com.example.eg09batch.common.batch.CustomIncrementer;
import com.example.eg09batch.dataSync.application.common.JobStartTasklet;
import com.example.eg09batch.dataSync.application.common.S3PullDeleteTasklet;
import com.example.eg09batch.dataSync.application.common.S3PushTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.eg09batch.dataSync.application.batch.b003.B003Constants.JOB_ID;

@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "b003")
@Configuration
public class B003Config {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    @Qualifier(TASKLET_NAME)
//    Tasklet tasklet;

    @Autowired
    JobStartTasklet jobStartTasklet;

    @Autowired
    S3PushTasklet s3PushTasklet;

    @Autowired
    S3PullDeleteTasklet s3PullDeleteTasklet;


    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new CustomIncrementer())
                .start(step1())
                .next(step2())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1_s3PushTasklet")
                .tasklet(s3PushTasklet)
                .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2_s3PullDeleteTasklet")
                .tasklet(s3PullDeleteTasklet)
                .build();
    }

}
