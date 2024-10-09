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
import com.example.demo.service.ConfigService;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザーに関する画面の制御を行うコントローラークラスです。
 */

@Controller
public class ConfigController {
	@Autowired
	ConfigService configService;

	/**
	 * ユーザーの一覧画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー一覧画面
	 */

	@GetMapping("/config/list")
	public String displayConfigList(Model model) {
		List<Config> configList = configService.searchAll();
		model.addAttribute("configList", configList);
		return "config/list";
	}

	@GetMapping("/config/index")
	public String displayconfigindex(Model model) {

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

	@GetMapping("/config/{id}")
	public String displayConfigDetail(@PathVariable Long id, Model model) {
		Config config = configService.search(id);
		model.addAttribute("config", config);
		return "config/detail";
	}

	/**
	 * ユーザーの新規登録画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー登録画面
	 */

	@GetMapping("/config/add")
	public String displayConfigAdd(Model model) {
		Config config = new Config();
		model.addAttribute("config", config);
		return "config/add";
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

	@PostMapping("/config/create")
	public String createConfig(@Validated Config config, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "config/add";
		}
		configService.createConfig(config);
		return "redirect:/config/list";
	}

	/**
	 * 指定したユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@GetMapping("/config/{id}/delete")
	public String deleteConfig(@PathVariable Long id, Model model) {
		configService.deleteConfig(id);

		return "redirect:/config/list";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@GetMapping("/config/{id}/edit")
	public String displayConfigEdit(@PathVariable Long id, Model model) {

		Config config = configService.search(id);
		model.addAttribute("config", config);

		return "config/edit";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@PostMapping("/config/update")
	public String updateConfig(@Validated Config config, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "config/edit";
		}

		try {
			configService.updateConfig(config);
			return "redirect:/config/list";

		} catch (OptimisticLockException e) {
			model.addAttribute("message", e.getMessage());
			return "config/edit";
		}
	}
}