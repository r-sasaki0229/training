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

import com.example.demo.entity.Qanda;
import com.example.demo.service.QandaService;

import jakarta.persistence.OptimisticLockException;

/**
 * ユーザーに関する画面の制御を行うコントローラークラスです。
 */

@Controller
public class QandaController {
	@Autowired
	QandaService qandaService;

	/**
	 * ユーザーの一覧画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー一覧画面
	 */

	@GetMapping("/qanda/list")
	public String displayqandaList(Model model) {
		List<Qanda> qandaList = qandaService.searchAll();
		model.addAttribute("qandaList", qandaList);
		return "qanda/list";
	}

	@GetMapping("/qanda/index")
	public String displayqandaindex(Model model) {

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

	@GetMapping("/qanda/{id}")
	public String displayQandaDetail(@PathVariable Long id, Model model) {
		Qanda qanda = qandaService.search(id);
		model.addAttribute("qanda", qanda);
		return "qanda/detail";
	}

	/**
	 * ユーザーの新規登録画面を表示する。
	 *
	 * @param model モデル
	 * @return ユーザー登録画面
	 */

	@GetMapping("/qanda/add")
	public String displayQandaAdd(Model model) {
		Qanda qanda = new Qanda();
		model.addAttribute("qanda", qanda);
		return "qanda/add";
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

	@PostMapping("/qanda/create")
	public String createQanda(@Validated Qanda qanda, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "qanda/add";
		}
		qandaService.createQanda(qanda);
		return "redirect:/qanda/list";
	}

	/**
	 * 指定したユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー一覧画面にリダイレクトする
	 */

	@GetMapping("/qanda/{id}/delete")
	public String deleteQanda(@PathVariable Long id, Model model) {
		qandaService.deleteQanda(id);

		return "redirect:/qanda/list";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@GetMapping("/qanda/{id}/edit")
	public String displayQandaEdit(@PathVariable Long id, Model model) {

		Qanda qanda = qandaService.search(id);
		model.addAttribute("qanda", qanda);

		return "qanda/edit";
	}

	/**
	 * ユーザーの編集画面を表示する。
	 *
	 * @param id ユーザーID
	 * @param model モデル
	 * @return ユーザー編集画面
	 */

	@PostMapping("/qanda/update")
	public String updateQanda(@Validated Qanda qanda, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "qanda/edit";
		}

		try {
			qandaService.updateQanda(qanda);
			return "redirect:/qanda/list";

		} catch (OptimisticLockException e) {
			model.addAttribute("message", e.getMessage());
			return "qanda/edit";
		}
	}
}