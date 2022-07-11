package com.example.eg09batch.base.application.service;


import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface S3Service {

    /**
     * S3オブジェクト(ファイル)の一覧を取得する。
     *
     * @param bucketName バケット名
     * @param prefix 検索条件
     * @return S3Objectのリスト(一致するものがなければ空のリスト)
     */
    List<S3Object> getListS3Objects(String bucketName, String prefix);

    /**
     * ファイルをS3にアップロードする。
     *
     * @param bucketName バケット名
     * @param objectKey  ファイル名
     * @param localPath  ローカルファイル名(フルパス)
     * @param metadata   S3オブジェクトにセットするメタデータ
     * @return PutObjectResponse
     */
    PutObjectResponse putS3Object(String bucketName, String objectKey, String localPath, Map<String, String> metadata);

    /**
     * S3のファイルを指定のパスにダウンロードする。既にファイルが存在する場合上書きする。
     *
     * @param bucketName バケット名
     * @param objectKey  S3のファイル名の(フルパス)
     * @param localPath  ローカルファイル名(フルパス)
     * @throws IOException ローカルファイルに関する例外発生時
     * @return GetObjectResponse
     */
    GetObjectResponse getObjectToFile(String bucketName, String objectKey, String localPath);

    /**
     * S3のファイルをコピーする。
     *
     * @param sourceBucket      コピー元バケット名
     * @param sourceKey         コピー元ファイル名
     * @param destinationBucket コピー先バケット名
     * @param destinationKey    コピー先ファイル名
     * @return CopyObjectResponse
     */
    CopyObjectResponse copyObject(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey);

    /**
     * S3のファイルを削除する。
     *
     * @param bucketName バケット名
     * @param objectKey  ファイル名
     * @return DeleteObjectsResponse
     */
    DeleteObjectsResponse deleteObject(String bucketName, String objectKey);

    /**
     * S3のファイルを移動(コピー＋削除)する。
     *
     * @param sourceBucket      移動元バケット名
     * @param sourceKey         移動元ファイル名
     * @param destinationBucket 移動先バケット名
     * @param destinationKey    移動先ファイル名
     * @return CopyObjectResponse
     */
    CopyObjectResponse moveObject(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey);

    /**
     * S3TransferManagerを用いてS3のファイルをダウンロードする。
     *
     * @param s3TransferManager S3TransferManager
     * @param bucketName        バケット名
     * @param objectKey         S3のファイル名の(フルパス)
     * @param localPath         ローカルファイル名(フルパス)
     */
    void downloadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath);

    /**
     * S3TransferManagerを用いてS3のファイルをアップロードする。
     *
     * @param s3TransferManager S3TransferManager
     * @param bucketName        バケット名
     * @param objectKey         S3のファイル名の(フルパス)
     * @param localPath         ローカルファイル名(フルパス)
     */
    void uploadObjectTM(S3TransferManager s3TransferManager, String bucketName, String objectKey, String localPath);

    }
