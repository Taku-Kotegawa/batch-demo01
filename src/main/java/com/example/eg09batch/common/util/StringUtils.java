package com.example.eg09batch.common.util;

import com.google.common.base.CaseFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 文字列操作のユーティリティ
 */
public class StringUtils {

    /**
     * キャメルケースをスネークケース(小文字)に変換
     *
     * @param camelCase キャメルケース文字列
     * @return スネークケース(小文字)文字列, nullの場合はnull
     */
    public static String toLowerSnakeCase(String camelCase) {
        if (camelCase == null) {
            return null;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
    }

    /**
     * キャメルケースをスネークケース(大文字)に変換
     *
     * @param camelCase キャメルケース文字列
     * @return スネークケース(大文字)文字列, nullの場合はnull
     */
    public static String toUpperSnakeCase(String camelCase) {
        if (camelCase == null) {
            return null;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelCase);
    }

    /**
     * スネークケースをキャメルケース(小文字)に変換
     *
     * @param snakeCase 　スネークケース文字列
     * @return キャメルケース(小文字)文字列, nullの場合はnull
     */
    public static String toLowerCamelCase(String snakeCase) {
        if (snakeCase == null) {
            return null;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, snakeCase);
    }

    /**
     * スネークケースをキャメルケース(大文字)に変換
     *
     * @param snakeCase 　スネークケース文字列
     * @return キャメルケース(大文字)文字列, nullの場合はnull
     */
    public static String toUpperCamelCase(String snakeCase) {
        if (snakeCase == null) {
            return null;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, snakeCase);
    }

    /**
     * nullだった空白文字列を返す。
     *
     * @param s 文字列
     * @return 文字列に変換したもの
     */
    public static String nvl(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(formatter);
    }

}
