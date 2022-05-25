package com.example.eg09batch.config;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
@Component
public class PropertyReloader {

    @Autowired
    private StandardEnvironment environment;
    private long lastModTime = 0L;
    private PropertySource<?> appConfigPropertySource = null;
    private Path configPath;
    private static final String PROPERTY_NAME = "app.properties";

    @PostConstruct
    private void createContext() {
        MutablePropertySources propertySources = environment.getPropertySources();

        // first of all we check if application started with external file
        String property = "applicationConfig: [file:" + PROPERTY_NAME + "]";
        PropertySource<?> appConfigPsOp = propertySources.get(property);
        configPath = Paths.get(PROPERTY_NAME).toAbsolutePath();
        if (appConfigPsOp == null) {
            // if not we check properties file from resources folder
            property = "class path resource [" + PROPERTY_NAME + "]";
            configPath = Paths.get("src/main/resources/" + PROPERTY_NAME).toAbsolutePath();
        }
        appConfigPsOp = propertySources.get(property);
        appConfigPropertySource = appConfigPsOp;
    }

    // this method I call into REST controller for reloading all properties after change
//  app.properties file
    public void reload() {
        try {
            long currentModTs = Files.getLastModifiedTime(configPath).toMillis();
            if (currentModTs > lastModTime) {
                lastModTime = currentModTs;
                Properties properties = new Properties();
                @Cleanup InputStream inputStream = Files.newInputStream(configPath);
                properties.load(inputStream);
                String property = appConfigPropertySource.getName();
                PropertiesPropertySource updatedProperty = new PropertiesPropertySource(property, properties);
                environment.getPropertySources().replace(property, updatedProperty);
                log.info("Configs {} were reloaded", property);
            }
        } catch (Exception e) {
            log.error("Can't reload config file " + e);
        }
    }

}
