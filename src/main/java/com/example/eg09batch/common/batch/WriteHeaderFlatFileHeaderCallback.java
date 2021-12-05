package com.example.eg09batch.common.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

@AllArgsConstructor
public class WriteHeaderFlatFileHeaderCallback implements FlatFileHeaderCallback {

    private String headerLine;

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(headerLine);
    }
}
