package com.example.eg09batch.dataSync.application.batch.chunck1;

import com.example.eg09batch.base.application.service.BatchJobService;
import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;
import com.example.eg09batch.common.batch.FlatFileWriterFactory;
import com.example.eg09batch.dataSync.application.common.JobStartTasklet;
import com.example.eg09batch.dataSync.domain.dto.IF001UserCsv;
import com.example.eg09batch.dataSync.domain.model.MUser;
import com.example.eg09batch.dataSync.domain.model.MUserExample;
import com.github.dozermapper.core.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.example.eg09batch.config.BatchConfig.CHUNK_SIZE;
import static com.example.eg09batch.config.FileTypeEnum.CSV;
import static com.example.eg09batch.dataSync.application.batch.chunck1.C001Constants.*;

/**
 * チュンクモードの送信(DB->File)
 */
@Configuration
public class C001Config {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    BatchJobService batchJobService;

    @Autowired
    Mapper beanMapper;

    @Autowired
    JobStartTasklet jobStartTasklet;

    @Bean(JOB_ID)
    public Job job() {
        return jobBuilderFactory.get(JOB_ID)
                .incrementer(new RunIdIncrementer())
                .start(step0())
                .next(step1())
                .build();
    }

    private Step step0() {
        return stepBuilderFactory.get("step0")
                .tasklet(jobStartTasklet)
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .<MUser, IF001UserCsv>chunk(CHUNK_SIZE)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    public MyBatisCursorItemReader<MUser> reader() {

        MUserExample example = new MUserExample();

        //前回正常終了ジョブの実行時間を取得し、テーブルの最終更新日と比較して差分データを抽出
        BatchJobExecution batchJobExecution = batchJobService.findLastComplete(JOB_ID);
        if (batchJobExecution != null) {
            example.or().andChangedAtGreaterThan(batchJobExecution.getStartTime());
        }

        Map parameterValues = new HashMap<String, Object>();
        parameterValues.put("oredCriteria", example.getOredCriteria());
        parameterValues.put("orderByClause", example.getOrderByClause());

        return new MyBatisCursorItemReaderBuilder<MUser>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId(QUERY_ID)
                .parameterValues(parameterValues)
                .build();
    }

    public ItemProcessor<MUser, IF001UserCsv> processor() {
        return user -> {
            return beanMapper.map(user, IF001UserCsv.class);
        };
    }

    public ItemStreamWriter<IF001UserCsv> writer() {
        String outputFile = IF_ID + ".csv";
        ItemStreamWriter<IF001UserCsv> writer = new FlatFileWriterFactory<IF001UserCsv>(IF001UserCsv.COLUMNS, IF001UserCsv.HEADER)
                .getItemStreamWriter(CSV, outputFile);
        return writer;
    }
}
