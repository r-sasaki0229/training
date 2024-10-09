package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Config3;

import jakarta.persistence.LockModeType;

/**
 * ユーザーデータを扱うリポジトリクラスです。
 */

public interface Config3Repository extends JpaRepository<Config3, Long> {

	/**
	 * 指定されたIDに該当するユーザー情報を Pessimistic Lock で取得します。
	 *
	 * @param id 取得するユーザー情報のID
	 * @return 指定されたIDに該当するユーザー情報
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT u FROM Config3 u WHERE u.id = :id")
	Config3 findOneForUpdate(@Param("id") Long id);
}