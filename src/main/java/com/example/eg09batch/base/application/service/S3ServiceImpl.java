package com.example.eg09batch.base.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.transfer.s3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Lazy
@Slf4j
@Service
@Transactional(readOnly = true)
public class S3ServiceImpl implements S3Service {

    // アーカイブする場合の保存フォルダ名
    private static final String ARCHIVE_DIR = "archive/";

    private static final String DELIMITER = "/";

    @Autowired
    S3Client s3Client;

    @Autowired
    S3TransferManager s3TransferManager;

    @Override
    public List<S3Object> getListS3Objects(String bucketName, String prefix) {
        return s3Client.listObjects(ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .delimiter(DELIMITER)
                        .build())
                .contents();
    }


    @Override
    public PutObjectResponse putS3Object(String bucketName, String objectKey, String localPath, Map<String, String> metadata) {

        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Upload Start {} to S3://{}/{}", localPath, bucketName, objectKey);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, Paths.get(localPath));

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Upload End, Elapsed Time: {}, ETag: {}", formatDuration(duration), putObjectResponse.eTag());

        return putObjectResponse;
    }

    @Override
    public GetObjectResponse getObjectToFile(String bucketName, String objectKey, String localPath) {

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
        log.info("Download End, Elapsed Time: {}, ETag: {}", formatDuration(duration), getObjectResponse.eTag());

        return getObjectResponse;
    }

    @Override
    public CopyObjectResponse copyObject(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(sourceBucket)
                .sourceKey(sourceKey)
                .destinationBucket(destinationBucket)
                .destinationKey(destinationKey)
                .build();

        CopyObjectResponse copyObjectResponse = s3Client.copyObject(copyObjectRequest);

        log.info("Copy S3://{}/{} to S3://{}/{}, ETag: {}",
                sourceBucket, sourceKey, destinationBucket, destinationKey, copyObjectResponse.copyObjectResult().eTag());

        return copyObjectResponse;
    }

    @Override
    public DeleteObjectsResponse deleteObject(String bucketName, String objectKey) {

        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        toDelete.add(ObjectIdentifier.builder()
                .key(objectKey)
                .build());

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder()
                        .objects(toDelete)
                        .build())
                .build();

        DeleteObjectsResponse deleteObjectsResponse = s3Client.deleteObjects(deleteObjectsRequest);

        log.info("Delete S3://{}/{}", bucketName, objectKey);

        return deleteObjectsResponse;
    }

    @Override
    public CopyObjectResponse moveObject(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
        CopyObjectResponse copyObjectResponse = copyObject(sourceBucket, sourceKey, destinationBucket, destinationKey);
        deleteObject(sourceBucket, sourceKey);
        return copyObjectResponse;
    }

    @Override
    public void downloadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath) {
        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Download Start S3://{}/{} to {}", bucketName, objectKey, localPath);

        File downloadingFile = new File(getDownloadingFilePath(localPath));

        DownloadFileRequest downloadFileRequest = DownloadFileRequest.builder()
                .getObjectRequest(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .destination(Paths.get(downloadingFile.toPath().toString()))
                .build();

        FileDownload download = s3TransferManager.downloadFile(downloadFileRequest);

        CompletedFileDownload completedFileDownload = download.completionFuture().join();
        downloadingFile.renameTo(new File(localPath));

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Download End, Time: {}, ETag: {}", formatDuration(duration), completedFileDownload.response().eTag());
    }

    @Override
    public void uploadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath) {
        LocalDateTime startDateTime = LocalDateTime.now();
        log.info("Upload Start S3://{}/{} to {}", bucketName, objectKey, localPath);

        UploadRequest uploadRequest = UploadRequest.builder()
                .putObjectRequest(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .requestBody(AsyncRequestBody.fromFile(Paths.get(localPath)))
                .build();

        Upload upload = s3TransferManager.upload(uploadRequest);

        CompletedUpload completedUpload = upload.completionFuture().join();

        Duration duration = Duration.between(startDateTime, LocalDateTime.now());
        log.info("Upload End, Time: {}, ETag: {}", formatDuration(duration), completedUpload.response().eTag());
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
     * アーカイブ先のS3オブジェクト名を返す。
     *
     * @param objectKey
     * @return アーカイブ先のファイル名(フルパス)
     */
    private String getArchiveKey(String objectKey) {
        return getDir(objectKey) + ARCHIVE_DIR + getFileName(objectKey);
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

    /**
     * Durationのフォーマット変換(hh:mm:ss.sss)
     *
     * @param duration Duration
     * @return 変換後の文字列
     */
    private String formatDuration(Duration duration) {
        long millis = duration.toMillis();
        return String.format("%02d:%02d:%02d.%03d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis));
    }

}
