package com.example.eg09batch.dataSync.application.common;


import com.example.eg09batch.base.application.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Lazy
@Slf4j
@Component
@StepScope
public class S3TestTasklet implements Tasklet {

    // アーカイブする場合の保存フォルダ名
    private static final String ARCHIVE_DIR = "archive/";

    @Autowired
    S3Client s3Client;

    @Autowired
    S3Service s3Service;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        final String bucketName = "data-integration-test-bucket";
        final String prefix = "Users/";
        final String prefix2 = "Users/A";
        final String prefix3 = "Users/_IF";
        final String delimiter = "/";
        final String downloadDir = "/home/taku/Downloads/";
        final String filePath = "/home/taku/Downloads/progit.pdf";


        // アップロード
        s3Service.putS3Object(bucketName, prefix + getFileName(filePath), filePath, null);

        // ダウンロード
        List<S3Object> s3ObjectList = s3Service.getListS3Objects(bucketName, prefix);
        s3ObjectList.forEach(s3Object ->
                s3Service.getObjectToFile(bucketName, s3Object.key(), downloadDir + getFileName(s3Object.key())));

        // ファイルコピー
        s3ObjectList = s3Service.getListS3Objects(bucketName, prefix);
        s3ObjectList.forEach(s3Object ->
                s3Service.copyObject(bucketName, s3Object.key(), bucketName, getArchiveKey(s3Object.key())));

        // ファイル移動
        s3ObjectList = s3Service.getListS3Objects(bucketName, prefix);
        s3ObjectList.forEach(s3Object ->
                s3Service.moveObject(bucketName, s3Object.key(), bucketName, getArchiveKey(s3Object.key())));

        // ファイル削除
        s3ObjectList = s3Service.getListS3Objects(bucketName, prefix);
        s3ObjectList.forEach(s3Object ->
                s3Service.deleteObject(bucketName, s3Object.key()));

        // ダウンロード＋アーカイブ(移動)
        s3Service.getListS3Objects(bucketName, prefix)
                .forEach(s3Object -> {
                    s3Service.getObjectToFile(bucketName, s3Object.key(), downloadDir + getFileName(s3Object.key()));
                    s3Service.moveObject(bucketName, s3Object.key(), bucketName, getArchiveKey(s3Object.key()));
                });

        return RepeatStatus.FINISHED;
    }

    /**
     * アーカイブ先のS3オブジェクト名を返す。
     *
     * @param objectKey
     * @return アーカイブ先のファイル名(フルパス)
     */
    private String getArchiveKey(String objectKey) {
        return getDir(objectKey) + ARCHIVE_DIR + getFileName(objectKey);
    }

    private static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String getDir(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }
}
