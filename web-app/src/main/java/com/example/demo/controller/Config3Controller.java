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

import com.example.demo.entity.Config3;
import com.example.demo.service.Config3Service;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザーに関する画面の制御を行うコントローラークラスです。
 */

@Controller
public class Config3Controller {
	@Autowired
	Config3Service config3Service;

	/**
	 * ユーザーの一覧画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー一覧画面
	 */

	@GetMapping("/config3/list")
	public String displayConfig3List(Model model) {
		List<Config3> config3List = config3Service.searchAll();
		model.addAttribute("config3List", config3List);
		return "config3/list";
	}

	@GetMapping("/config3/index")
	public String displayconfig3index(Model model) {

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

	@GetMapping("/config3/{id}")
	public String displayConfig3Detail(@PathVariable Long id, Model model) {
		Config3 config3 = config3Service.search(id);
		model.addAttribute("config3", config3);
		return "config3/detail";
	}

	/**
	 * ユーザーの新規登録画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー登録画面
	 */

	@GetMapping("/config3/add")
	public String displayConfig3Add(Model model) {
		Config3 config3 = new Config3();
		model.addAttribute("config3", config3);
		return "config3/add";
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

	@PostMapping("/config3/create")
	public String createConfig3(@Validated Config3 config3, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "config3/add";
		}
		config3Service.createConfig3(config3);
		return "redirect:/config3/list";
	}

	/**
	 * 指定したユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@GetMapping("/config3/{id}/delete")
	public String deleteConfig3(@PathVariable Long id, Model model) {
		config3Service.deleteConfig3(id);

		return "redirect:/config3/list";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@GetMapping("/config3/{id}/edit")
	public String displayConfig3Edit(@PathVariable Long id, Model model) {

		Config3 config3 = config3Service.search(id);
		model.addAttribute("config3", config3);

		return "config3/edit";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@PostMapping("/config3/update")
	public String updateConfig3(@Validated Config3 config3, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "config3/edit";
		}

		try {
			config3Service.updateConfig3(config3);
			return "redirect:/config3/list";

		} catch (OptimisticLockException e) {
			model.addAttribute("message", e.getMessage());
			return "config3/edit";
		}
	}
}