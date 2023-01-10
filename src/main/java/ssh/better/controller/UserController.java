package ssh.better.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
		log.info("유저 정보 {}", user);
		
		String encoderPwd = encoder.encode(user.getUserPwd());
		user.setUserPwd(encoderPwd);
		
		userRepository.save(user);
		log.info("저장 확인 {}", userRepository.findByUidNo(user.getUidNo()));
		
		return "redirect:/login";
	}

}
