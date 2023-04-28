package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.UsersService;

/**
 * ログインコントローラー
 */
@Controller /** APIの入り口 */
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String resetPassword(Model model) {
		return "passwordReset"; // jspファイル名
	}
	/**
	 * ログイン処理
	 *
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,@RequestParam("passwordForCheck") String passwordForCheck, Model model) {
	
		if(password.length()>=8 && password.matches("[0-9a-zA-Z]+")) {
			if(password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				/*UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.registUser(userInfo);
				*/
				usersService.resetUserInfo(email, password);
				return "login";
				
			}else {
				model.addAttribute("errorMessage","パスワードが一致しません");
				return "resetPass";
			}	
		}else {
			model.addAttribute("errorMessage","パスワードは8文字以上かつ半角英数字に設定してください");
			return "resetPass";
		}
	}
}