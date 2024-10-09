package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.entity.User;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EntityManager entityManager;

	// usersテーブルを初期化する			
	private static final Operation TRUNCATE_USERS = Operations.sql("TRUNCATE TABLE users");

	// テストデータ（idは自動採番）			
	private static final Operation INSERT_USERS = Operations.insertInto("users")
			.columns("user_name", "address", "phone", "create_date", "update_date", "delete_date")
			// user_id = 1	
			.values("テスト太郎", "東京都", "0120-99-1234", "2023-01-01 00:10:01", "2023-01-02 09:20:02", null)
			// user_id = 2	
			.values("テスト次郎", "神奈川県", "090-1111-2222", "2023-02-01 12:30:03", "2023-02-02 21:40:04", null)
			.build();

	/**
	 * 
	 */
	@BeforeEach
	public void setup() {
		// DB接続情報を取得
		Destination destination = new DataSourceDestination(dataSource);
		// テストデータの初期化
		new DbSetup(destination, Operations.sequenceOf(TRUNCATE_USERS, INSERT_USERS)).launch();
	}

	/**
	 * 悲観的ロックモードを取得できるかテストする。 <br>
	 * ロックをするためにトランザクションの開始が必要なので、Transactional を付与する<br>
	 * テスト実行後にロックが開放されるように、Rollback を付与する<br>
	 */
	@Test
	@Transactional
	@Rollback
	@DisplayName("悲観ロックの取得")
	public void testFindOneForUpdate() {

		// テスト実行		
		User findUser = userRepository.findOneForUpdate(1L);

		// 検証		
		// id=1のデータを取得できているか		
		assertEquals(1, findUser.getId());
		assertEquals("テスト太郎", findUser.getName());
		assertEquals("東京都", findUser.getAddress());
		assertEquals("0120-99-1234", findUser.getPhone());
		LocalDateTime createDate = LocalDateTime.of(2023, 1, 1, 0, 10, 1);
		assertTrue(createDate.equals(findUser.getCreateDate()));
		LocalDateTime updateDate = LocalDateTime.of(2023, 1, 2, 9, 20, 2);
		assertTrue(updateDate.equals(findUser.getUpdateDate()));
		assertEquals(null, findUser.getDeleteDate());
		// ロックモードを確認		
		LockModeType lockMode = entityManager.getLockMode(findUser);
		assertEquals(LockModeType.PESSIMISTIC_WRITE, lockMode);
	}

}
