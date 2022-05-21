package com.example.eg09batch.dataSync.application.common;

import com.example.eg09batch.dataSync.domain.dto.GitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@StepScope
public class WebClientTasklet implements Tasklet {

    private final String BASE_URL = "https://api.github.com/users/defunkt";

    private final String ADDRESS_BOOK_ID = "abc";

    WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("defaultKey1", "defaultValue1") // 認証情報等を設定
            .defaultHeader("defaultKey2", "defaultValue2")
            .build();

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        String address_content = "CSVの中身";

//        String body = client.post()
//                .uri("/abook/{address_book_id}/import", ADDRESS_BOOK_ID)
//                .header("key", "value") //追加パラメータを設定
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(address_content)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        log.info(body);

        log.info(address_content);

        return RepeatStatus.FINISHED;
    }

}
