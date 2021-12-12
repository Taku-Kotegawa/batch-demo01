package com.example.eg09batch.base.application.service;

import com.example.eg09batch.base.domain.model.mbg.BatchJobExecution;

import java.time.LocalDateTime;


public interface BatchJobService {

    /**
     * 前回正常終了ジョブの検索
     *
     * @param jobName ジョブ名
     * @return ジョブ実行結果
     */
    BatchJobExecution findLastComplete(String jobName);

    /**
     * 前回正常終了ジョブの開始日時
     *
     * @param jobName ジョブ名
     * @return 開始日時
     */
    LocalDateTime getLastCompleteStartTime(String jobName);

    /**
     * ジョブが実行中かどうか
     *
     * @param jobName ジョブ名
     * @return true:実行中、false:実行していない
     */
    boolean isRunning(String jobName);

}
