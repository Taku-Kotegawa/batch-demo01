package com.example.eg09batch.dataSync.application.batch.b001;

import static com.example.eg09batch.config.BatchConfig.PACKAGE_REPOSITORY;

public class B001Constants {
    static final String JOB_ID = "b001";
    static final String TASKLET_NAME = JOB_ID + "Tasklet";

    static final String SELECT_REPOSITORY = "MUserRepository";
    static final String SELECT_METHOD = "selectByExample";
    static final String QUERY_ID = PACKAGE_REPOSITORY + "." + SELECT_REPOSITORY + "." + SELECT_METHOD;

    static final String IF_ID = "IF001";
    static final int CHUNK_SIZE = 1000;
}
