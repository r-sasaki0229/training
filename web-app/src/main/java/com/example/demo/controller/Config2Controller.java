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

import com.example.demo.entity.Config2;
import com.example.demo.service.Config2Service;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザーに関する画面の制御を行うコントローラークラスです。
 */

@Controller
public class Config2Controller {
	@Autowired
	Config2Service config2Service;

	/**
	 * ユーザーの一覧画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー一覧画面
	 */

	@GetMapping("/config2/list")
	public String displayConfig2List(Model model) {
		List<Config2> config2List = config2Service.searchAll();
		model.addAttribute("config2List", config2List);
		return "config2/list";
	}

	@GetMapping("/config2/index")
	public String displayconfig2index(Model model) {

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

	@GetMapping("/config2/{id}")
	public String displayConfig2Detail(@PathVariable Long id, Model model) {
		Config2 config2 = config2Service.search(id);
		model.addAttribute("config2", config2);
		return "config2/detail";
	}

	/**
	 * ユーザーの新規登録画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー登録画面
	 */

	@GetMapping("/config2/add")
	public String displayConfig2Add(Model model) {
		Config2 config2 = new Config2();
		model.addAttribute("config2", config2);
		return "config2/add";
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

	@PostMapping("/config2/create")
	public String createConfig2(@Validated Config2 config2, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "config2/add";
		}
		config2Service.createConfig2(config2);
		return "redirect:/config2/list";
	}

	/**
	 * 指定したユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@GetMapping("/config2/{id}/delete")
	public String deleteConfig2(@PathVariable Long id, Model model) {
		config2Service.deleteConfig2(id);

		return "redirect:/config2/list";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@GetMapping("/config2/{id}/edit")
	public String displayConfig2Edit(@PathVariable Long id, Model model) {

		Config2 config2 = config2Service.search(id);
		model.addAttribute("config2", config2);

		return "config2/edit";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@PostMapping("/config2/update")
	public String updateConfig2(@Validated Config2 config2, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "config2/edit";
		}

		try {
			config2Service.updateConfig2(config2);
			return "redirect:/config2/list";

		} catch (OptimisticLockException e) {
			model.addAttribute("message", e.getMessage());
			return "config2/edit";
		}
	}
}