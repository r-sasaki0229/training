package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Config;
import com.example.demo.entity.Config2;
import com.example.demo.entity.Config3;
import com.example.demo.entity.User;
import com.example.demo.service.Config2Service;
import com.example.demo.service.Config3Service;
import com.example.demo.service.ConfigService;
import com.example.demo.service.UserService;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザーに関する画面の制御を行うコントローラークラスです。
 */

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	ConfigService configService;

	@Autowired
	Config2Service config2Service;

	@Autowired
	Config3Service config3Service;

	/**
	 * ユーザーの一覧画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー一覧画面
	 */

	@GetMapping("/user/list")
	public String displayUserList(Model model) {
		List<User> userList = userService.searchAll();
		model.addAttribute("userList", userList);

		List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config2", config2);

		List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config3", config3);

		return "user/list";
	}

	@GetMapping("/user/index")
	public String displayuserindex(Model model) {

		return "index";
	}

	/**
	 * ユーザーの詳細画面を表示する。<br>
	 * hoge
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー詳細画面
	 */

	@GetMapping("/user/{id}")
	public String displayUserDetail(@PathVariable Long id, Model model) {
		User user = userService.search(id);
		model.addAttribute("user", user);

		List<Config> config = configService.searchAll(); // ユーザ情報を取得
		model.addAttribute("config", config);

		List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config2", config2);

		List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config3", config3);

		return "user/detail";
	}

	/**
	 * ユーザーの新規登録画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー登録画面
	 */

	@GetMapping("/user/add")
	public String displayUserAdd(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		List<Config> config = configService.searchAll(); // ユーザ情報を取得
		model.addAttribute("config", config);

		List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config2", config2);

		List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config3", config3);

		return "user/add";
	}

	/**
	 * ユーザーを新規登録する。
	 * <p>
	 * 入力エラーがある場合は、もとの入力画面にエラー内容を表示する。
	 * </p>
	 *
	 * @param user ユーザー情報
	 * @param result 入力チェック結果
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@PostMapping("/user/create")
	public String createUser(@Validated User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("message", result.getFieldError().toString());

			List<Config> config = configService.searchAll(); // ユーザ情報を取得
			model.addAttribute("config", config);

			List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config2", config2);

			List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config3", config3);

			return "user/add";
		}
		userService.createUser(user);
		return "redirect:/user/list";
	}

	/**
	 * 指定したユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@GetMapping("/user/{id}/delete")
	public String deleteUser(@PathVariable Long id, Model model) {
		userService.deleteUser(id);

		return "redirect:/user/list";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@GetMapping("/user/{id}/edit")
	public String displayUserEdit(@PathVariable Long id, Model model) {

		User user = userService.search(id);
		model.addAttribute("user", user);

		List<Config> config = configService.searchAll(); // ユーザ情報を取得
		model.addAttribute("config", config);

		List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config2", config2);

		List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
		model.addAttribute("config3", config3);

		return "user/edit";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@PostMapping("/user/update")
	public String updateUser(@Validated User user, BindingResult result, Model model) {

		if (result.hasErrors()) {

			List<Config> config = configService.searchAll(); // ユーザ情報を取得
			model.addAttribute("config", config);

			List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config2", config2);

			List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config3", config3);

			return "user/edit";
		}

		try {
			userService.updateUser(user);
			return "redirect:/user/list";

		} catch (OptimisticLockException e) {
			model.addAttribute("message", e.getMessage());

			List<Config> config = configService.searchAll(); // ユーザ情報を取得
			model.addAttribute("config", config);

			List<Config2> config2 = config2Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config2", config2);

			List<Config3> config3 = config3Service.searchAll(); // ユーザ情報を取得
			model.addAttribute("config3", config3);

			return "user/edit";
		}
	}
}