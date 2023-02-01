package com.example.eg09batch.dataSync.application.common;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileReader;

import static java.lang.String.format;

/**
 * PostgresqlのCOPY命令を利用した高速なInsert
 */
@Slf4j
@Component
@StepScope
public class PostgresCopyInTasklet implements Tasklet {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String table = "m_user";   // 挿入先のテーブル
    String file = "IF001.csv"; // 読み込みファイル
//    String COPY_CMD = "COPY " + table + " from STDIN (FORMAT CSV, HEADER, DELIMITER '\t')"; // TAB区切り
    String COPY_CMD = "COPY " + table + " from STDIN (FORMAT CSV, HEADER)"; // カンマ区切り

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        BaseConnection con = jdbcTemplate.getDataSource().getConnection().unwrap(BaseConnection.class);
        CopyManager copyManager = new CopyManager(con);

        FileReader fileReader = new FileReader(file);
        // https://jdbc.postgresql.org/documentation/publicapi/org/postgresql/copy/CopyManager.html
        long count = copyManager.copyIn(COPY_CMD, fileReader);
        log.info(format("number of insert: %d", count));

        return RepeatStatus.FINISHED;
    }
}
