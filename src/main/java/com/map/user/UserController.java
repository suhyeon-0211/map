package com.map.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/user/sign_in_view")
	public String signInView() {
		return "user/sign_in";
	}
	
	@RequestMapping("/user/sign_up_view")
	public String signUpView() {
		return "user/sign_up";
	}
	
	@RequestMapping("/user/sign_out")
	public String signOut(HttpServletRequest request) {
		// 로그아웃
		HttpSession session = request.getSession();
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		session.removeAttribute("userId");
		return "redirect:/main";
	}
}
