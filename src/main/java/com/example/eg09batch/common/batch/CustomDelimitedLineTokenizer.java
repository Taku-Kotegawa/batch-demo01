package com.example.eg09batch.common.batch;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 囲み文字で括らない列はnull、囲み文字で括った列は空文字列(長さ0の文字列)で解釈するCSVトークンナイザー
 */
public class CustomDelimitedLineTokenizer extends DelimitedLineTokenizer {

    private String quoteString = "" + DEFAULT_QUOTE_CHARACTER;

    private final String BLANK_MARKER = "___EMPTY_STRING__EMPTY_STRING__EMPTY_STRING__EMPTY_STRING__";

    @Override
    public void setQuoteCharacter(char quoteCharacter) {
        super.setQuoteCharacter(quoteCharacter);
        quoteString = "" + quoteCharacter;
    }

    @Override
    protected List<String> doTokenize(String line) {
        // ""を"<ダミー文字列(BLANK_MARKER)>"に置換、後ほどで空白文字列に復元する。
        String line2 = line.replace(quoteString + quoteString, quoteString + BLANK_MARKER + quoteString);
        List<String> result = super.doTokenize(line2);
        return result.stream().map(x -> {
            if (BLANK_MARKER.equals(x)) {
                // ダミー文字列を空白文字列に復元
                return "";
            } else if (x.isEmpty()) {
                // 空の値はnullに置換
                return null;
            }
            return x;
        }).collect(Collectors.toList());
    }
}
