package com.example.eg09batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.vpc.endpoint.url}")
    private String S3_VPC_ENDPOINT_URL;

    private static final long MB = 1024;

    ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

    /**
     * AWS S3接続用のクライアント
     *
     * AWS
     */
    @Bean
    S3Client s3Client() {

        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
//                .endpointOverride(URI.create(S3_VPC_ENDPOINT_URL))
                .build();
    }

    @Bean
    S3TransferManager s3TransferManager() {
        return S3TransferManager.builder()
                .s3ClientConfiguration(cfg -> cfg.credentialsProvider(credentialsProvider)
                        .targetThroughputInGbps(20.0)
                        .minimumPartSizeInBytes(10 * MB))
                .build();
    }



}
