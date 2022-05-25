package com.example.eg09batch.dataSync.application.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class JobStartTasklet implements Tasklet {

    public JobStartTasklet() {
        log.debug("**** 初期化しました。*****");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("ジョブを開始します。");
        log.info("共通のログを出力します。");
        log.info("StepContribution: " + stepContribution.toString());
        log.info("ChunkContext: " + chunkContext.toString());

//        stepContribution.setExitStatus(ExitStatus.COMPLETED);
//        stepContribution.setExitStatus(ExitStatus.FAILED);
//        stepContribution.setExitStatus(ExitStatus.STOPPED);

        return RepeatStatus.FINISHED;
    }
}
