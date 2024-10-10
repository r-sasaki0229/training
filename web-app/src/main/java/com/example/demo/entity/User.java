package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "users")
public class User {
	@Id
	@Column(name = "customerid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "companyname")
	@NotBlank(message = "会社名を入力してください")
	@Size(max = 100, message = "名前は100文字以内で入力してください")
	private String companyname;

	@Column(name = "ceoname")
	@Size(max = 100, message = "名前は100文字以内で入力してください")
	private String ceoname;

	@Column(name = "phone")
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "|\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号の形式（xxxx-xxxx-xxxx）で入力してください")
	private String phone;

	@Column(name = "addressnumber")
	@Pattern(regexp = "|\\d{1,4}-\\d{4}", message = "郵便番号の形式（xxxx-xxxx）で入力してください")
	private String addressnumber;

	@Column(name = "address")
	@Size(max = 255, message = "住所は255文字以内で入力してください")
	private String address;

	@Column(name = "depmentname")
	@NotBlank(message = "お客様担当者の部署名を入力してください")
	@Size(max = 100, message = "お客様担当者の部署名は100文字以内で入力してください")
	private String depmentname;

	@Column(name = "manegername")
	@NotBlank(message = "お客様担当者の氏名を入力してください")
	@Size(max = 100, message = "お客様担当者の氏名は100文字以内で入力してください")
	private String manegername;

	@Column(name = "scale")
	private int scale;

	@Column(name = "product")
	private int product;

	@Column(name = "status")
	private int status;

	@Column(name = "contrastartymd")
	private LocalDate contrastartymd;

	@Column(name = "introstartymd")
	private LocalDate introstartymd;

	@Column(name = "maintestartymd")
	private LocalDate maintestartymd;

	@Column(name = "contraendymd")
	private LocalDate contraendymd;

	@Column(name = "docpass")
	@Size(max = 255, message = "255文字以内で入力してください")
	private String docpass;

	@Column(name = "introname")
	@Size(max = 100, message = "100文字以内で入力してください")
	private String introname;

	@Column(name = "introcontactmail")
	@Size(max = 100, message = "100文字以内で入力してください")
	private String introcontactmail;

	@Column(name = "introcontactphone")
	@Size(max = 100, message = "100文字以内で入力してください")
	private String introcontactphone;

	@Column(name = "maintename")
	@Size(max = 100, message = "100文字以内で入力してください")
	private String maintename;

	@Column(name = "remark")
	@Size(max = 255, message = "255文字以内で入力してください")
	private String remark;

	/*-------------------------------------*/
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