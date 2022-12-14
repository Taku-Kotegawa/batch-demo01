package com.example.eg09batch.common.batch;


import com.example.eg09batch.common.mapper.NullBindBeanWrapperFieldSetMapper;
import com.example.eg09batch.config.FileTypeEnum;
import com.example.eg09batch.dataSync.domain.dto.IF001UserCsv;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import static com.example.eg09batch.config.BatchConfig.*;
import static com.example.eg09batch.config.FileTypeEnum.CSV;

@SuppressWarnings("unchecked")
@AllArgsConstructor
public class FlatFileReaderFactory<T> {

    private final String[] columns;
    private final Class<T> clazz;

    /**
     * 選択したフォーマット用のItemStreamReaderを取得する
     *
     * @param fileType  fileType
     * @param inputFile inputFile
     * @return ItemStreamReader
     */
    public ItemStreamReader<T> getItemStreamReader(FileTypeEnum fileType, String inputFile) {
        FlatFileItemReader<T> fileReader;
        if (CSV.equals(fileType)) {
            fileReader = csvReader(inputFile);
        } else {
            fileReader = tsvReader(inputFile);
        }
        return fileReader;
    }

    /**
     * CSV用Reader
     *
     * @param inputFile inputFile
     * @return FlatFileItemReader
     */
    public FlatFileItemReader<T> csvReader(String inputFile) {

        DelimitedLineTokenizer delimitedLineTokenizer = new CustomDelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(CSV_DELIMITER);
        delimitedLineTokenizer.setQuoteCharacter(CSV_ENCLOSURE);
        delimitedLineTokenizer.setNames(columns);


        DefaultLineMapper defaultLineMapper = new DefaultLineMapper<T>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

//        NullBindBeanWrapperFieldSetMapper nullBindBeanWrapperFieldSetMapper = new NullBindBeanWrapperFieldSetMapper();
//        nullBindBeanWrapperFieldSetMapper.setTargetType(clazz);
//        defaultLineMapper.setFieldSetMapper(nullBindBeanWrapperFieldSetMapper);
        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
        beanWrapperFieldSetMapper.setTargetType(clazz);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);



        return new FlatFileItemReaderBuilder<T>()
                .name("flatFileItemReader")
                .strict(true)
                .linesToSkip(1)
                .recordSeparatorPolicy(new BlankLineRecordSeparatorPolicy())
                .resource(new FileSystemResource(inputFile))
                .encoding(CSV_ENCODING)
                .lineMapper(defaultLineMapper)
                .build();
    }

    /**
     * TSV用Reader
     *
     * @param inputFile inputFile
     * @return FlatFileItemReader
     */
    public FlatFileItemReader<T> tsvReader(String inputFile) {

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(TSV_DELIMITER);
        delimitedLineTokenizer.setNames(columns);

        NullBindBeanWrapperFieldSetMapper nullBindBeanWrapperFieldSetMapper = new NullBindBeanWrapperFieldSetMapper();
        nullBindBeanWrapperFieldSetMapper.setTargetType(clazz);

        DefaultLineMapper defaultLineMapper = new DefaultLineMapper<T>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(nullBindBeanWrapperFieldSetMapper);

        return new FlatFileItemReaderBuilder<T>()
                .name("flatFileItemReader")
                .strict(true)
                .linesToSkip(1)
                .recordSeparatorPolicy(new BlankLineRecordSeparatorPolicy())
                .resource(new FileSystemResource(inputFile))
                .encoding(CSV_ENCODING)
                .lineMapper(defaultLineMapper)
                .build();
    }
}
