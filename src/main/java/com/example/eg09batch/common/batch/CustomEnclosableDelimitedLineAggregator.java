package com.example.eg09batch.common.batch;

import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.PassThroughFieldExtractor;
import org.springframework.util.Assert;
import org.terasoluna.batch.item.file.transform.EnclosableDelimitedLineAggregator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnclosableDelimitedLineAggregator<T> extends EnclosableDelimitedLineAggregator<T> {

    private final String NULL_MARKER = "___NULL___NULL___NULL___NULL___NULL___";

    private FieldExtractor<T> fieldExtractor = new PassThroughFieldExtractor();

    @Override
    public void setFieldExtractor(FieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public String aggregate(T item) {
        Assert.notNull(item, "Item is required");
        Object[] fields = this.fieldExtractor.extract(item);
        Object[] args = new Object[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            if (fields[i] == null) {
                args[i] = NULL_MARKER;
            } else {
                args[i] = fields[i];
            }
        }

        return this.doAggregate(args);
    }

    @Override
    protected String doAggregate(Object[] fields) {
        return (String) Arrays.stream(fields).map(Object::toString).map((field) -> {

            if (NULL_MARKER.equals(field)) {
                return "";
            }
            return this.hasTargetChar(field) ? this.encloseAndEscape(field) : field;

        }).collect(Collectors.joining(this.delimiter));
    }

    private boolean hasTargetChar(String field) {
        return this.allEnclosing || field.contains(this.delimiter) || field.contains(this.enclosure) || this.containsCrlf(field);
    }

    private boolean containsCrlf(String field) {
        return field.contains("\r") || field.contains("\n");
    }

    private String encloseAndEscape(String field) {
        return this.enclosure + field.replace(this.enclosure, this.escapedEnclosure) + this.enclosure;
    }



}
