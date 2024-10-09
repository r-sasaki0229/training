package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Config2;
import com.example.demo.repository.Config2Repository;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザー情報を扱サービスクラスです。
 */

@Service
public class Config2Service {

	@Autowired
	Config2Repository config2Repository;

	/**
	 * 全てのユーザー情報を取得します。
	 *
	 * @return 全てのユーザー情報
	 */

	public List<Config2> searchAll() {
		return config2Repository.findAll();
	}

	/**
	 * 指定されたユーザーIDに対応するユーザー情報を取得します。
	 *
	 * @param id ユーザーID
	 * @return 指定されたユーザーIDに対応するユーザー情報
	 */

	public Config2 search(Long id) {
		return config2Repository.findById(id).get();
	}

	/**
	 * 指定されたユーザー情報を登録します。
	 *
	 * @param user 登録するユーザー情報
	 * @return 登録されたユーザー情報
	 */

	public Config2 createConfig2(Config2 config2) {
		LocalDateTime now = LocalDateTime.now();
		config2.setCreateDate(now);
		config2.setUpdateDate(now);
		return config2Repository.save(config2);
	}

	/**
	 * 指定されたユーザーIDに対応するユーザー情報を削除します。
	 *
	 * @param id 削除するユーザーID
	 */

	public void deleteConfig2(Long id) {
		config2Repository.deleteById(id);
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
	public Config2 updateConfig2(Config2 config2) {
		Config2 currentconfig2 = config2Repository.findOneForUpdate(config2.getId());

		if (currentconfig2.getUpdateDate().equals(config2.getUpdateDate())) {
			LocalDateTime now = LocalDateTime.now();
			config2.setUpdateDate(now);
			return config2Repository.save(config2);

		} else {
			String message = "データが他の方によって更新されたようです。一覧画面に戻ってから再実施してください。";
			throw new OptimisticLockException(message);
		}
	}
}