package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報を表すEntityクラスです。
 * <p>
 * このクラスは、データベースのusersテーブルと対応しています。
 * </p>
 */

@Entity
@Data
@Table(name = "qanda")
public class Qanda {
	@Id
	@Column(name = "QuestionID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Question")
	@NotBlank(message = "質問を入力してください")
	@Size(max = 255, message = "質問は255文字以内で入力してください")
	private String question;

	@Column(name = "Answer")
	@Size(max = 255, message = "回答は255文字以内で入力してください")
	private String answer;

	@Column(name = "update_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updateDate;

	@Column(name = "create_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime createDate;

	@Column(name = "delete_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime deleteDate;
}