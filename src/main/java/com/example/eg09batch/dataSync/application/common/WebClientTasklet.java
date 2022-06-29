package com.example.eg09batch.dataSync.application.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Lazy
@Slf4j
@Component
@StepScope
public class WebClientTasklet implements Tasklet {

    @Value("${address.book.file}")
    private FileSystemResource ADDRESS_BOOK_FILE;

    private final String BASE_URL = "https://fc7631.cuenote.jp/fcapi/v3.0";

    private final String ADDRESS_BOOK_ID = "61ef66a6";

    WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("Authorization", "Basic xxx") // 認証情報等を設定
            .build();

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        String address_content = IOUtils.toString(ADDRESS_BOOK_FILE.getInputStream(), "UTF-8");
        log.info(address_content);

        String body = client.post()
                .uri("/adbook/61ef66a6/import")
                .header("Host", "fc7631.cuenote.jp") //追加パラメータを設定
                .header("X-Mode", "replace") //追加パラメータを設定
                .header("X-Duplicate", "ignore") //追加パラメータを設定
                .header("X-HeaderStyle", "columnname") //追加パラメータを設定
                .header("X-UpdateDevice", "none") //追加パラメータを設定
                .contentType(new MediaType("text", "csv"))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(address_content)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info(body);

        return RepeatStatus.FINISHED;
    }

}
