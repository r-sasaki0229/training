package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Config2;

import jakarta.persistence.LockModeType;

/**
 * ユーザーデータを扱うリポジトリクラスです。
 */

public interface Config2Repository extends JpaRepository<Config2, Long> {

	/**
	 * 指定されたIDに該当するユーザー情報を Pessimistic Lock で取得します。
	 *
	 * @param id 取得するユーザー情報のID
	 * @return 指定されたIDに該当するユーザー情報
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT u FROM Config2 u WHERE u.id = :id")
	Config2 findOneForUpdate(@Param("id") Long id);
}