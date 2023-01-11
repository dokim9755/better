package ssh.better.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.User;

@SpringBootTest
@Transactional
@Slf4j
class UserServiceTest {
	
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("유저 인코딩 확인")
	void save() {
		   // given
		   String rawPassword = "12345678";

		   User user = User.builder()
				   			.userId("아이디")
				   			.userPwd(rawPassword)
				   			.userNick("test")
				   			.userEmail("email@test")
				   			.build();
		   log.info("비영속성 유저의 비밀번호 >> {}", user.getUserPwd());
				   
		   // when
		   userService.save(user);
		   log.info("영속성 유저의 비밀번호 >> {}", user.getUserPwd());
		   String encodedPassword = userService.findByUserId(user.getUserId()).getUserPwd();

		   // then
		   assertAll(
		         () -> assertNotEquals(rawPassword, encodedPassword),
		         () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
		   );
	}
}
