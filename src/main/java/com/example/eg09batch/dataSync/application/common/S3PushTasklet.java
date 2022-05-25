package com.example.eg09batch.dataSync.application.common;

import com.amazonaws.util.IOUtils;
import com.example.eg09batch.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;


@Lazy
@Slf4j
@Component
public class S3PushTasklet implements Tasklet {

    public S3PushTasklet() {
        log.debug("**** 初期化しました。*****");
    }

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${dataSync.s3.bucketname}")
    private String bucketname;

    @Value("${dataSync.s3.directory}")
    private String directory;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        // 前のステップから値を取り出す
        String outputFile = (String) ExecutionContextUtils.getContext(stepContribution, "outputFile");

        if (outputFile == null) {
            outputFile = "test.csv";
        }

        String currentTime = StringUtils.format(LocalDateTime.now(), "yyyymmddhhmmss");
        String objectKey = outputFile.replace(".", "_" + currentTime + ".");
        String s3Location = "s3://" + bucketname + "/" + objectKey;

        WritableResource resource = (WritableResource) resourceLoader.getResource(s3Location);

        try (InputStream inputStream = new FileInputStream(directory + "/" + outputFile); OutputStream outputStream = resource.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            // omitted
        }

        return RepeatStatus.FINISHED;
    }
}
