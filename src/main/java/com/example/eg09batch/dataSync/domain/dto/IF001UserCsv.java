package com.example.eg09batch.dataSync.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * CSVファイルレイアウトDTO(Fieldの型はString)
 */
@Data
public class IF001UserCsv {

    public static String[] COLUMNS = {"uid","name", "pass", "mail", "status", "createdAt", "changedAt", "comment"};
    public static String HEADER = "ID,氏名,パスワード,メールアドレス,ステータス,作成日時,更新日時,コメント";

    private String uid;
    private String name;
    private String pass;
    private String mail;
    private String status;
    private LocalDate createdAt;
    private LocalDateTime changedAt;
    private String comment;

}
