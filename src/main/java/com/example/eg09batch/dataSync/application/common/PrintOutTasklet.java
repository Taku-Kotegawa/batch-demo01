package com.example.eg09batch.dataSync.application.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@StepScope
public class PrintOutTasklet implements Tasklet {

    private String message;

    public PrintOutTasklet(String message) {
        log.debug("**** 初期化しました。*****");
        this.message = message;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        log.info(message);

        return RepeatStatus.FINISHED;
    }
}
