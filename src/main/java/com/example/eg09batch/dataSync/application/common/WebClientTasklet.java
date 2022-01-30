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

    WebClient client = WebClient.create("https://api.github.com/users/defunkt");

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        GitResponse body = client.get().retrieve().bodyToMono(GitResponse.class).block();
        log.info(body.toString());


        return RepeatStatus.FINISHED;
    }

}
