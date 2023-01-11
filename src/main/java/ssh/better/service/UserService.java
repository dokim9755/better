package ssh.better.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.User;
import ssh.better.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User save(User user) {
		log.info("유저 정보 >> {}", user);
		
		String encoderPwd = passwordEncoder.encode(user.getUserPwd());
		user.setUserPwd(encoderPwd);
		
		userRepository.save(user);
		log.info("암호화된 유저 정보 저장 확인 >> {}", user);
		
		return user;
	}
	
	public User findByUserId(String userId) {
		Optional<User> user =userRepository.findByUserId(userId);
		
		if(user.isEmpty())
			return null;
		return user.get();
	}
}
