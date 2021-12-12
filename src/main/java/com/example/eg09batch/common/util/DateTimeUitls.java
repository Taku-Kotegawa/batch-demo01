package com.example.eg09batch.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUitls {

    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(String dateTime, String pattern) {
        DateTimeFormatter dtFt = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime, dtFt);
    }


}
