package com.example.eg09batch.dataSync.application.common;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@Lazy
@Slf4j
@Component
@StepScope
public class S3PullDeleteTasklet implements Tasklet {

    private ResourcePatternResolver resourcePatternResolver;

//    private AmazonS3 amazonS3;

    @Value("${dataSync.s3.bucketname}")
    private String bucketname;

//    @Autowired
//    public void setupResolver(ApplicationContext applicationContext, AmazonS3 amazonS3) {
//        this.amazonS3 = amazonS3;
//        this.resourcePatternResolver = new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext);
//    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        // S3のファイル一覧を取得
        String s3ResourcePattern = "s3://" + bucketname + "/test*";
        Resource[] allTxtFilesInFolder = this.resourcePatternResolver.getResources(s3ResourcePattern);

        for (Resource resource : allTxtFilesInFolder) {
            try (InputStream inputStream = resource.getInputStream();
                 OutputStream outputStream = new FileOutputStream("c:/users/user/download/" + resource.getFilename())) {

//                IOUtils.copy(inputStream, outputStream);
                log.info("Copy s3://" + bucketname + "/" + resource.getFilename() + " to " + "c:/users/user/download/" + resource.getFilename());

//                amazonS3.deleteObject(bucketname, resource.getFilename());
                log.info("Delete s3://" + bucketname + "/" + resource.getFilename());

            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }
        }

        return RepeatStatus.FINISHED;
    }
}
