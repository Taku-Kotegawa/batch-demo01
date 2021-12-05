package com.example.eg09batch.config;


import com.example.eg09batch.common.auditing.CustomDateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.terasoluna.gfw.common.date.jodatime.DefaultJodaTimeDateFactory;

@Configuration
public class TimeConfig {

    @Bean
    public DefaultJodaTimeDateFactory defaultJodaTimeDateFactory() {
        return new DefaultJodaTimeDateFactory();
    }

    @Bean
    public CustomDateFactory customDateFactory() {
        return new CustomDateFactory();
    }

    public static final String TIME_STAMP_PATTERN = "yyyyMMddHHmmss";

}
