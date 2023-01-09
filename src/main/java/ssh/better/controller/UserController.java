package ssh.better.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.User;
import ssh.better.repository.UserRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final UserRepository userRepository;
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	@GetMapping("/signup")
	public String signupForm() {
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute User user) {
		log.info("유저 정보 {}", user);
		userRepository.save(user);
		log.info("저장 확인 {}", userRepository.findByUidNo(user.getUidNo()));
		return "../static/index";
	}

}
