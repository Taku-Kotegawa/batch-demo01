package com.example.eg09batch.dataSync.application.batch.b001;

import com.example.eg09batch.base.application.service.BatchJobService;
import com.example.eg09batch.common.batch.FlatFileWriterFactory;
import com.example.eg09batch.common.util.DateTimeUitls;
import com.example.eg09batch.dataSync.domain.dto.IF001UserCsv;
import com.example.eg09batch.dataSync.domain.model.MUser;
import com.example.eg09batch.dataSync.domain.model.MUserExample;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.slf4j.MDC;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.eg09batch.config.FileTypeEnum.CSV;
import static com.example.eg09batch.dataSync.application.batch.b001.B001Constants.*;
import static com.example.eg09batch.dataSync.application.common.DataSyncConstants.MSG_OUTPUT_RECORD_NUM;
import static java.lang.String.format;

// @See https://macchinetta.github.io/batch-guideline/current/ja/single_index.html#Ch05_DBAccess_HowToUse_Input_MyBatisItemReader
// @See https://macchinetta.github.io/batch-guideline/current/ja/single_index.html#Ch09_Impl_FileAccessJob_Tasklet_Logic

@Slf4j
@Component(TASKLET_NAME)
public class B001Tasklet implements Tasklet {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    Mapper beanMapper;

    @Autowired
    BatchJobService batchJobService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        int count = 0;

        Long jobInstanceId = chunkContext.getStepContext().getJobInstanceId();
        Long jobExecutionId = chunkContext.getStepContext().getStepExecution().getJobExecutionId();
        String jobName = chunkContext.getStepContext().getJobName();
        String parameterFromTime = stepContribution.getStepExecution().getJobParameters().getString("from");

        MDC.put("jobInstanceId", jobInstanceId.toString());
        MDC.put("jobName", jobName);
        MDC.put("jobExecutionId", jobExecutionId.toString());
        MDC.put("jobName_jobExecutionId", jobName + "_" + jobExecutionId);

        LocalDateTime fromTime = null;
        if (StringUtils.isNotBlank(parameterFromTime)) {
            try {
                fromTime = DateTimeUitls.toLocalDateTime(parameterFromTime, "yyyyMMddHHmmss");
                log.info("パラメータ「抽出範囲(from)」: {}", parameterFromTime);
            } catch (Exception e) {
                throw new IllegalArgumentException("日時に変換できませんでした(yyyyMMddHHmmss)", e);
            }
        }
        if (fromTime == null) {
            fromTime = batchJobService.getLastCompleteStartTime(JOB_ID);
            log.info("前回ジョブ開始時間: {}", fromTime);
        }

        String outputFile = IF_ID + ".csv";
        MUser item = null;
        List<IF001UserCsv> items = new ArrayList<>(CHUNK_SIZE);
        ItemStreamReader<MUser> reader = makeReader(fromTime);
        FlatFileItemWriter<IF001UserCsv> writer = makeWriter(outputFile);
        try {
            reader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            writer.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
            while ((item = reader.read()) != null) {
                items.add(beanMapper.map(item, IF001UserCsv.class));
                count++;

                if (items.size() == CHUNK_SIZE) {
                    writer.write(items);
                    writer.doWrite(items);
                    items.clear();
                    log.info(format(MSG_OUTPUT_RECORD_NUM, count));
                }
            }
            writer.write(items);
            log.info(format(MSG_OUTPUT_RECORD_NUM, count));
        } finally {
            reader.close();
            writer.close();
        }

        MDC.clear();

        // 次のステップに値を渡す
        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("outputFile", outputFile);

        return RepeatStatus.FINISHED;
    }


    /**
     * @return
     */
    private MyBatisCursorItemReader<MUser> makeReader(LocalDateTime lastStartTime) {

        MUserExample example = new MUserExample();
        if (lastStartTime != null) {
            // 抽出条件の指定
            example.or().andChangedAtGreaterThan(lastStartTime);
        }
        // 並び順の指定
        example.setOrderByClause("uid desc");

        Map parameterValues = new HashMap<String, Object>();
        parameterValues.put("oredCriteria", example.getOredCriteria());
        parameterValues.put("orderByClause", example.getOrderByClause());

        return new MyBatisCursorItemReaderBuilder<MUser>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId(QUERY_ID)
                .parameterValues(parameterValues)
                .build();
    }

    /**
     * @return
     */
    private FlatFileItemWriter<IF001UserCsv> makeWriter(String outputFile) {
        FlatFileItemWriter<IF001UserCsv> writer = new FlatFileWriterFactory<IF001UserCsv>(IF001UserCsv.COLUMNS, IF001UserCsv.HEADER)
                .csvWriter(outputFile);
        return writer;
    }


}
