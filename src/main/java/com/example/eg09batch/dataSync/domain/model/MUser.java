package com.example.eg09batch.dataSync.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Database Table Remarks:
 *   ユーザ
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table m_user
 */
@Data
public class MUser implements Serializable {
    /**
     * Database Column Remarks:
     *   ユーザID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.uid
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * Database Column Remarks:
     *   氏名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   パスワード
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.pass
     *
     * @mbg.generated
     */
    private String pass;

    /**
     * Database Column Remarks:
     *   メール
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.mail
     *
     * @mbg.generated
     */
    private String mail;

    /**
     * Database Column Remarks:
     *   ステータス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.status
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     * Database Column Remarks:
     *   作成日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.created_at
     *
     * @mbg.generated
     */
    private LocalDateTime createdAt;

    /**
     * Database Column Remarks:
     *   更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.changed_at
     *
     * @mbg.generated
     */
    private LocalDateTime changedAt;

    /**
     * Database Column Remarks:
     *   コメント
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_user.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table m_user
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}