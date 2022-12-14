package com.example.eg09batch.dataSync.application.batch.b003;

import com.example.eg09batch.common.batch.FlatFileReaderFactory;
import com.example.eg09batch.dataSync.domain.dto.IF001UserCsv;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component
@StepScope
public class B003Tasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {


        FlatFileItemReader<IF001UserCsv> reader = new FlatFileReaderFactory<IF001UserCsv>(IF001UserCsv.COLUMNS, IF001UserCsv.class)
                .csvReader("IF001.csv");

        IF001UserCsv item;
        try {
            reader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());

            while ((item = reader.read()) != null) {

                System.out.println(item);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("B003Tasklet");


        return RepeatStatus.FINISHED;
    }
}
