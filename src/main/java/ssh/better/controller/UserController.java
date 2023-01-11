package ssh.better.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.User;
import ssh.better.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
//	@PostMapping("/login")
//	public String loginCheck(@ModelAttribute User user) {
//		log.info("유저 아이디 확인 >> {}", user.getUserId());
//		log.info("유저 비밀번호 확인 >> {}", user.getUserPwd());
//		return "redirect:/";
//	}
	
	@GetMapping("/signup")
	public String signupForm() {
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute User user) {
		userService.save(user);
		
		log.info("회원가입 최종 확인 >> {}", user);
		return "redirect:/login";
	}
}
