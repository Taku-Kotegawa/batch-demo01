package com.example.eg09batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.ApplicationContext;

@EnableBatchProcessing
@SpringBootApplication(exclude = {ElastiCacheAutoConfiguration.class, ContextInstanceDataAutoConfiguration.class}) // 非接続時のエラー回避のためAWS自動構成を無効化
public class Eg09BatchApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Eg09BatchApplication.class, args);
        System.exit(SpringApplication.exit(applicationContext));
    }
}
