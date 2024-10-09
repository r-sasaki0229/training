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
@Table(name = "config1")
public class Config {
	@Id
	@Column(name = "ConfigID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "Name")
	@NotBlank(message = "名前を入力してください")
	@Size(max = 50, message = "名前は100文字以内で入力してください")
	private String name;

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