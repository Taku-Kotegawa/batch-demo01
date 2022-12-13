package com.example.eg09batch.common.batch;

import com.example.eg09batch.config.FileTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.terasoluna.batch.item.file.transform.EnclosableDelimitedLineAggregator;

import static com.example.eg09batch.config.BatchConfig.*;
import static com.example.eg09batch.config.FileTypeEnum.CSV;

@SuppressWarnings({"unchecked", "rawtypes"})
public class FlatFileWriterFactory<T> {

    private static final String WRITER_NAME = "flatFileItemWriter";

    private String encoding = CSV_ENCODING;
    private final String[] columns;
    private final String headerLine;

    public FlatFileWriterFactory(String[] columns, String headerLine) {
        this.columns = columns;
        this.headerLine = headerLine;
    }

    public ItemStreamWriter<T> getItemStreamWriter(FileTypeEnum fileType, String outputFile) {
        FlatFileItemWriter<T> writer;
        if (CSV.equals(fileType)) {
            writer = csvWriter(outputFile);
        } else {
            writer = tsvWriter(outputFile);
        }
        return writer;
    }

    /**
     * CSV用Writer
     *
     * @param outputFile 出力ファイル名
     * @return CSV用Writer
     */
    public FlatFileItemWriter<T> csvWriter(String outputFile) {

        BeanWrapperFieldExtractor beanWrapperFieldExtractor = new BeanWrapperFieldExtractor();
        beanWrapperFieldExtractor.setNames(columns);
        beanWrapperFieldExtractor.afterPropertiesSet();

        EnclosableDelimitedLineAggregator delimitedLineAggregator = new CustomEnclosableDelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter(CSV_DELIMITER_CHAR);
        delimitedLineAggregator.setEnclosure(CSV_ENCLOSURE);
        delimitedLineAggregator.setAllEnclosing(CSV_ALL_ENCLOSURE);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        delimitedLineAggregator.afterPropertiesSet();

        return new FlatFileItemWriterBuilder<T>()
                .name(WRITER_NAME)
                .resource(new FileSystemResource(outputFile))
                .encoding(encoding)
                .lineSeparator(CSV_LINE_SEPARATOR)
                .append(false)
                .shouldDeleteIfEmpty(false)
                .shouldDeleteIfExists(true)
                .transactional(true)
                .lineAggregator(delimitedLineAggregator)
                .headerCallback(new WriteHeaderFlatFileHeaderCallback(headerLine))
                .build();
    }

    public FlatFileItemWriter<T> tsvWriter(String outputFile) {

        BeanWrapperFieldExtractor beanWrapperFieldExtractor = new BeanWrapperFieldExtractor();
        beanWrapperFieldExtractor.setNames(columns);
        beanWrapperFieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter(TSV_DELIMITER);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        return new FlatFileItemWriterBuilder<T>()
                .name(WRITER_NAME)
                .resource(new FileSystemResource(outputFile))
                .encoding(encoding)
                .lineSeparator(CSV_LINE_SEPARATOR)
                .append(false)
                .shouldDeleteIfEmpty(false)
                .shouldDeleteIfExists(true)
                .transactional(true)
                .lineAggregator(delimitedLineAggregator)
                .headerCallback(new WriteHeaderFlatFileHeaderCallback(headerLine.replaceAll(CSV_DELIMITER, TSV_DELIMITER)))
                .build();
    }
}
