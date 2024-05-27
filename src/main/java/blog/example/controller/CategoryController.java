package blog.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.model.entity.CategoryEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.CategoryService;
import blog.example.service.UserService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	// categoryテーブルを操作するための Serviceクラス
	@Autowired
	private CategoryService categoryService;

	// accountテーブルを操作するための Serviceクラス
	@Autowired
	private UserService userService;

	/* category一覧画面を出すための処理
	 * ログインしている人のメールアドレスを使用して、ログインしている人のuserIdとuserNameを取得
	 * カテゴリー一覧を取得
	 * 取得した情報をセットして画面から参照可能にする */
	// カテゴリー一覧を表示
	@GetMapping("/all")
	public String getCategoryAll(Model model) {
		
		// 現在のリクエストに紐づく Authentication を取得するには
		// SecurityContextHolder.getContext().getAuthentication() とする
		// SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している
		// Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthorityのコレクション）を取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// ログインした人のメールアドレスを取得
		String userEmail = auth.getName();
		
		// accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーのID情報を取得
		UserEntity user = userService.selectById(userEmail);

		// accountテーブルの中からログインしているユーザーの名前の取得
		String userName = user.getUserName();
		
		// categoryテーブルに入っている内容を全て取得し、categorylistに格納
		List<CategoryEntity> categorylist = categoryService.findByAll();

		// categorylist（category一覧情報）とuserName（管理者の名前）をmodelにセットし
		// admin_category_all.htmlから参照可能にする
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("userName", userName);

		return "admin_category_all.html";
	}

	/* categoryの内容を保存する画面を表示する処理
	 * ログインしている人のメールアドレスを使用して、ログインしている人のuserIdとuserNameを取得
	 * 取得した情報をセットして画面から参照可能にする */
	// カテゴリー登録画面を表示
	@GetMapping("/register")
	public String geCategoryListRegister(Model model) {
		
		// 現在のリクエストに紐づく Authentication を取得するには
		// SecurityContextHolder.getContext().getAuthentication() とする
		// SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している
		// Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthorityのコレクション）を取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// ログインした人のメールアドレスを取得
		String userEmail = auth.getName();

		// accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーのID情報を取得
		UserEntity user = userService.selectById(userEmail);

		// accountテーブルの中からログインしているユーザーの名前の取得
		String userName = user.getUserName();

		// userName（管理者の名前）をmodelにセットし
		// admin_category_register.htmlから参照可能にする
		model.addAttribute("userName", userName);
		
		return "admin_category_register.html";
	}

	/* categoryの内容を保存する処理
	 * 入力された情報によってcategoryテーブルに保存処理を行う
	 * 取得したuserIdを使用してログインしている人のブログ記事を取得
	 * 保存処理後は、カテゴリー一覧画面にリダイレクト */
	// カテゴリー登録内容を保存
	@PostMapping("/register")
	public String register(@RequestParam String categoryName) {
		
		// サービスクラスのメソッドに値を渡して保存
		categoryService.insert(categoryName);
		
		return "redirect:/admin/category/all";
	}

	/* categoryの編集画面を表示させるための処理
	 * ログインしている人のメールアドレスを使用して、ログインしている人のuserIdとuserNameを取得
	 * リンクからcategoryIdを取得
	 * categoryIdに紐づくレコードを探す
	 * 取得した情報をセットして画面から参照可能にする */
	// カテゴリー編集画面の表示
	@GetMapping("/edit/{categoryId}")
	public String getCategroyEditPage(@PathVariable Long categoryId, Model model) {
		
		// 現在のリクエストに紐づく Authentication を取得するには
		// SecurityContextHolder.getContext().getAuthentication() とする
		// SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している
		// Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthorityのコレクション）を取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// ログインした人のメールアドレスを取得
		String userEmail = auth.getName();

		// accountテーブルの中から、ユーザーのEmailで検索をかけて該当するユーザーのID情報を取得
		UserEntity user = userService.selectById(userEmail);

		// accountテーブルの中からログインしているユーザーの名前の取得
		String userName = user.getUserName();

		// categoryテーブルの中から、category_idで検索をかけて該当するcategory情報を取得
		CategoryEntity categoryEntity = categoryService.selectCategoryId(categoryId);

		// categoryEntity(category情報）とuserName（管理者の名前）をmodelにセットし
		// admin_category_edit.htmlから参照可能にする
		model.addAttribute("userName", userName);
		model.addAttribute("category", categoryEntity);
		
		return "admin_category_edit.html";
	}

	/* categoryの編集内容を保存する処理
	 * 画面から情報を受け取りcategoryテーブルに更新処理を行う
	 * リンクからcategoryIdを取得
	 * 更新処理後は、category一覧画面にリダイレクト */
	@PostMapping("/update")
	public String update(@RequestParam String categoryName, @RequestParam Long categoryId) {
		
		// CategoryServiceのメソッドに値を渡して更新を行う
		categoryService.update(categoryId, categoryName);
		
		return "redirect:/admin/category/all";
	}

	/* categoryを削除させるための処理
	 * categoryIdに紐づくレコードを探す
	 * 紐づくレコードを削除する */
	// カテゴリー登録内容を削除
	@PostMapping("/delete")
	public String deleteCategory(@RequestParam Long categoryId) {
		
		// Serviceクラスに値をわたし、削除処理を行う
		categoryService.delete(categoryId);
		
		return "redirect:/admin/category/all";
	}
}
