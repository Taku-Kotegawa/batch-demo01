package com.example.eg09batch.common.batch;


import com.example.eg09batch.common.mapper.NullBindBeanWrapperFieldSetMapper;
import com.example.eg09batch.config.FileTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static com.example.eg09batch.config.BatchConfig.*;
import static com.example.eg09batch.config.FileTypeEnum.CSV;

@SuppressWarnings("unchecked")
@AllArgsConstructor
public class FlatFileReaderFactory<T> {

    static final private String DATE_FORMAT = "yyyy/MM/dd";
    static final private String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    static final private String DATETIME2_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
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

        // LocalDateTimeの変換
        HashMap<Class, PropertyEditor> customEditors = new HashMap<>();
        customEditors.put(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
                } catch (DateTimeParseException ex) {
                    setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATETIME2_FORMAT)));
                }
            }
        });

        // LocalDateの変換
        customEditors.put(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern(DATE_FORMAT)));
            }
        });

        // 制御文字の変換
        customEditors.put(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text
                        .replace("\\n", "\n")
                        .replace("\\t", "\t")); //TODO 見直し
            }
        });


        DefaultLineMapper defaultLineMapper = new DefaultLineMapper<T>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        // CSVのクランをnullで解釈
        NullBindBeanWrapperFieldSetMapper nullBindBeanWrapperFieldSetMapper = new NullBindBeanWrapperFieldSetMapper();
        nullBindBeanWrapperFieldSetMapper.setTargetType(clazz);
        nullBindBeanWrapperFieldSetMapper.setCustomEditors(customEditors);
        defaultLineMapper.setFieldSetMapper(nullBindBeanWrapperFieldSetMapper);

//        // CSVの空欄を空文字列で解釈
//        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
//        beanWrapperFieldSetMapper.setTargetType(clazz);
//        beanWrapperFieldSetMapper.setCustomEditors(customEditors);
//        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

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
