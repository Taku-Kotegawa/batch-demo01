package com.example.eg09batch.dataSync.application.batch.b002;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import static com.example.eg09batch.dataSync.application.batch.b002.B002Constants.TASKLET_NAME;


@Component(TASKLET_NAME)
@StepScope
public class B002Tasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }
}
