package com.example.eg09batch.dataSync.application.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedUpload;
import software.amazon.awssdk.transfer.s3.model.FileDownload;
import software.amazon.awssdk.transfer.s3.model.Upload;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Lazy
@Slf4j
@Component
@StepScope
public class S3ListTasklet implements Tasklet {

    @Autowired
    S3Client s3Client;

    @Autowired
    S3TransferManager s3TransferManager;


    public S3ListTasklet() {
        log.info("**** 初期化しました。 ****");
    }

    private static String formatDuration(Duration duration) {
        long millis = duration.toMillis();
        return String.format("%02d:%02d:%02d.%03d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis));
    }

    /**
     * S3にファイルをアップロードする。
     *
     * @param s3Client   S3Clinet
     * @param bucketName バケット名
     * @param objectKey  ファイル名
     * @param localPath  ローカルファイル名(フルパス)
     * @param metadata   S3オブジェクトにセットするメタデータ
     */
    public void putS3Object(S3Client s3Client, String bucketName, String objectKey, String localPath, Map<String, String> metadata) {

        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Upload Start {} to S3://{}/{}", localPath, bucketName, objectKey);

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

        PutObjectResponse response = s3Client.putObject(putOb, new File(localPath).toPath());

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Upload End, Elapsed Time: {}", formatDuration(duration));
    }


    /**
     * S3オブジェクトをコピーする
     *
     * @param s3Client          S3Client
     * @param sourceBucket      コピー元バケット名
     * @param sourceKey         コピー元ファイル名
     * @param destinationBucket コピー先バケット名
     * @param destinationKey    コピー先ファイル名
     * @return ？？？
     */
    public void copyObject(S3Client s3Client, String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(sourceBucket)
                .sourceKey(sourceKey)
                .destinationBucket(destinationBucket)
                .destinationKey(destinationKey)
                .build();

        s3Client.copyObject(copyObjectRequest);
        log.info("Copy S3://{}/{} to S3://{}/{}", sourceBucket, sourceKey, destinationBucket, destinationKey);

    }

    /**
     * S3のファイルを削除する。
     *
     * @param s3Client
     * @param bucketName
     * @param objectKey
     */
    public void deleteObject(S3Client s3Client, String bucketName, String objectKey) {

        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        toDelete.add(ObjectIdentifier.builder().key(objectKey).build());

        DeleteObjectsRequest dor = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(toDelete).build())
                .build();

        s3Client.deleteObjects(dor);
        log.info("Delete S3://{}/{}", bucketName, objectKey);

    }

    /**
     * S3のファイルを移動する。
     *
     * @param s3Client
     * @param sourceBucket
     * @param sourceKey
     * @param destinationBucket
     * @param destinationKey
     */
    public void moveObject(S3Client s3Client, String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
        copyObject(s3Client, sourceBucket, sourceKey, destinationBucket, destinationKey);
        deleteObject(s3Client, sourceBucket, sourceKey);
    }

    /**
     * ダウンロード中の一時ファイル名を返す。/abc/efg/test.csv -> /abc/efg/downloading_test.csv_yyyyMMdd_HHmmss
     *
     * @param originalPath 元のファイル名(フルパス)
     * @return ダウンロード中の一時ファイル名
     */
    private String getDownloadingFilePath(String originalPath) {
        return getDir(originalPath) + "downloading_" + getFileName(originalPath)
                + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    /**
     * S3のファイルを指定のパスにダウンロードする。既にファイルが存在する場合上書きする。
     *
     * @param s3Client   S3Client
     * @param bucketName BuketName
     * @param objectKey  S3のファイル名の(フルパス)
     * @param localPath  ローカルファイル名(フルパス)
     * @throws IOException ローカルファイルに関する例外発生時
     */
    private void getObjectToFile(S3Client s3Client, String bucketName, String objectKey, String localPath) {
        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Download Start S3://{}/{} to {}", bucketName, objectKey, localPath);

        GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .key(objectKey)
                .bucket(bucketName)
                .build();

        File downloadingFile = new File(getDownloadingFilePath(localPath));
        GetObjectResponse getObjectResponse = s3Client.getObject(objectRequest, downloadingFile.toPath());
        downloadingFile.renameTo(new File(localPath));

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Download End, Elapsed Time: {}", formatDuration(duration));

    }

    /**
     * S3TransferManagerを用いてS3のファイルをダウンロードする。
     *
     * @param s3TransferManager S3TransferManager
     * @param bucketName        BuketName
     * @param objectKey         S3のファイル名の(フルパス)
     * @param localPath         ローカルファイル名(フルパス)
     */
    private void downloadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath) {
        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Download Start S3://{}/{} to {}", bucketName, objectKey, localPath);

        File downloadingFile = new File(getDownloadingFilePath(localPath));

        FileDownload download =
                s3TransferManager.downloadFile(d -> d.getObjectRequest(g -> g.bucket(bucketName).key(objectKey))
                        .destination(Paths.get(downloadingFile.toPath().toString())));
        download.completionFuture().join();
        downloadingFile.renameTo(new File(localPath));

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Download End, Time: {}, ETag: {}", formatDuration(duration));
    }

    private void uploadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath) {

        UploadRequest uploadRequest = UploadRequest.builder()
                .putObjectRequest(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .requestBody(AsyncRequestBody.fromFile(Paths.get(localPath)))
                .build();

        Upload upload = s3TransferManager.upload(uploadRequest);

        CompletedUpload completedUpload = upload.completionFuture().join();

        log.info(completedUpload.response().eTag());

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        final String bucketName = "data-integration-test-bucket";
        final String prefix = "Users/";
        final String prefix2 = "Users/A";
        final String prefix3 = "Users/_IF";
        final String delimiter = "/";
        final String downloadDir = "/home/taku/Downloads/";

        // バケット一覧
        log.info("--- Bucket list ---");
        s3Client.listBuckets().buckets().stream().forEach(
                s -> log.info(s.name())
        );

        // ファイル一覧
        log.info("--- Object list in {} ---", bucketName);
        s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
                .contents()
                .stream()
                .forEach(
                        s3Object -> log.info(s3Object.key())
                );

        // 前方一致検索でファイル一覧
        log.info("--- Object Ordered list in {} ---", bucketName);
        s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
                        }
                );


        // 指定したフォルダ直下のファイル一覧
        log.info("--- Object Ordered list in {}{}{} ---", bucketName, delimiter, prefix);
        s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .delimiter(delimiter)
                        .build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
                        }
                );

        // ダウンロード
        log.info("--- Download Objects from {}{}{} ---", bucketName, delimiter, prefix2);
        s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix2)
                        .delimiter(delimiter)
                        .build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
//                            getObjectToFile(s3Client, bucketName, s3Object.key(), downloadDir + getFileName(s3Object.key()));
//                            downloadObjectTM(s3TransferManager, bucketName, s3Object.key(), downloadDir + getFileName(s3Object.key()));
                        }
                );

        // ファイルのコピー
        log.info("--- Copy Object {}{}{} ---", bucketName, delimiter, prefix3);
        s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix3)
                        .delimiter(delimiter)
                        .build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
//                            copyObject(s3Client, bucketName, s3Object.key(), bucketName, getArchiveKey(s3Object.key()));
                        }
                );

        // ファイル削除
        log.info("--- Delete Object {}{}{} ---", bucketName, delimiter, prefix3);
        s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix3)
                        .delimiter(delimiter)
                        .build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
//                            deleteObject(s3Client, bucketName, s3Object.key());
                        }
                );

        // ファイル移動
        log.info("--- Delete Object {}{}{} ---", bucketName, delimiter, prefix3);
        s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix3)
                        .delimiter(delimiter)
                        .build())
                .contents()
                .stream()
                .forEachOrdered(
                        s3Object -> {
                            log.info("{} | {}", s3Object.key(), getFileName(s3Object.key()));
//                            moveObject(s3Client, bucketName, s3Object.key(), bucketName, getArchiveKey(s3Object.key()));
                        }
                );

        // アップロード
        log.info("--- Upload File ---");
        final String filePath = "/home/taku/Downloads/progit.pdf";
//        putS3Object(s3Client, bucketName, getFileName(filePath), filePath, null);
        uploadObjectTM(s3TransferManager, bucketName, getFileName(filePath), filePath);

        return RepeatStatus.FINISHED;
    }


    /**
     * アーカイブ先のS3オブジェクト名を返す。
     *
     * @param key
     * @return
     */
    private String getArchiveKey(String key) {
        return getDir(key) + "archive/" + getFileName(key);
    }

    /**
     * フルパスからファイル名の部分を取り出す。区切り文字は"/"。/abc/efg/test.csv -> test.csv
     *
     * @param path ファイル名(フルパス)
     * @return ファイル名
     */
    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * フルパスからディレクトリの部分を取り出す。区切り文字は"/"。/abc/efg/test.csv -> /abc/efg/
     *
     * @param path ファイル名(フルパス)
     * @return ディレクトリ名
     */
    private String getDir(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

}
