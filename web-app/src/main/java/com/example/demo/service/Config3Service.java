package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Config3;
import com.example.demo.repository.Config3Repository;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザー情報を扱サービスクラスです。
 */

@Service
public class Config3Service {

	@Autowired
	Config3Repository config3Repository;

	/**
	 * 全てのユーザー情報を取得します。
	 *
	 * @return 全てのユーザー情報
	 */

	public List<Config3> searchAll() {
		return config3Repository.findAll();
	}

	/**
	 * 指定されたユーザーIDに対応するユーザー情報を取得します。
	 *
	 * @param id ユーザーID
	 * @return 指定されたユーザーIDに対応するユーザー情報
	 */

	public Config3 search(Long id) {
		return config3Repository.findById(id).get();
	}

	/**
	 * 指定されたユーザー情報を登録します。
	 *
	 * @param user 登録するユーザー情報
	 * @return 登録されたユーザー情報
	 */

	public Config3 createConfig3(Config3 config3) {
		LocalDateTime now = LocalDateTime.now();
		config3.setCreateDate(now);
		config3.setUpdateDate(now);
		return config3Repository.save(config3);
	}

	/**
	 * 指定されたユーザーIDに対応するユーザー情報を削除します。
	 *
	 * @param id 削除するユーザーID
	 */

	public void deleteConfig3(Long id) {
		config3Repository.deleteById(id);
	}

	/**
	 * 指定されたユーザー情報を更新します。
	 * <p>
	 * 更新時に排他制御（オプティミスティックロック）を行います。
	 * </p>
	 *
	 * @param user 更新するユーザー情報
	 * @return 更新されたユーザー情報
	 * @throws OptimisticLockException 他のユーザーによってデータが更新されている場合にスローされます。
	 */

	@Transactional
	public Config3 updateConfig3(Config3 config3) {
		Config3 currentconfig3 = config3Repository.findOneForUpdate(config3.getId());

		if (currentconfig3.getUpdateDate().equals(config3.getUpdateDate())) {
			LocalDateTime now = LocalDateTime.now();
			config3.setUpdateDate(now);
			return config3Repository.save(config3);

		} else {
			String message = "データが他の方によって更新されたようです。一覧画面に戻ってから再実施してください。";
			throw new OptimisticLockException(message);
		}
	}
}