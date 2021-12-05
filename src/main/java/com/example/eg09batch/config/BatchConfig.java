package com.example.eg09batch.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * バッチ処理の共通的な設定
 */
@Configuration
/* バッチで処理するデータ操作用のMyBatisのRepository */
@MapperScan(basePackages = "com.example.eg09batch.dataSync.application.repository", sqlSessionTemplateRef = "batchModeSqlSessionTemplate")
/* ジョブ実行結果を参照するためのMyBatisのRepository */
@MapperScan(basePackages = "com.example.eg09batch.base.application.repository")
public class BatchConfig {

    /**
     * MyBatisのRepositoryをbatch modeで動かすため
     *
     * @param sessionFactory
     * @return SqlSessionTemplate
     */
    @Bean("batchModeSqlSessionTemplate")
    public SqlSessionTemplate getBatchSqlSessionTemplate(@Autowired SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory, ExecutorType.BATCH);
    }

    /**
     * CSVのエンコーディング
     */
    public final static String CSV_ENCODING = "UTF-8";

    /**
     * CSVの区切り文字
     */
    public final static Character CSV_DELIMITER_CHAR = ',';
    public final static String CSV_DELIMITER = ",";

    /**
     * CSVの囲み文字
     */
    public final static Character CSV_ENCLOSURE = '"';

    /**
     * 全てカラムを囲み文字で囲むかどうか(true=囲む, false=必要なカラムのみ)
     */
    public final static Boolean CSV_ALL_ENCLOSURE = true;

    /**
     * TSVの区切り文字
     */
    public final static String TSV_DELIMITER = "\t";

    /**
     * CSVの改行文字
     */
    public final static String CSV_LINE_SEPARATOR = "\r\n";

    /**
     * FileOutputDefaultChunkSize
     */
    public final static Integer CHUNK_SIZE = 20;

    /**
     * Package of MyBatis Repository
     */
    public final static String PACKAGE_REPOSITORY = "com.example.eg09batch.dataSync.application.repository";
}
