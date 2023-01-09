package ssh.better.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.User;


@Slf4j
@SpringBootTest
@Transactional
class UserRepositoryTest {
	
	@Autowired UserRepository userReppsi;

	@Test
	void save() {
		// given
		User user = new User("유저7", "비번1", "닉네임7", "네이버");
		log.info("세이브 전 [user] >> {}", user);
		
		// when
		User savedUser = userReppsi.save(user);
		
		// then
		assertThat(savedUser.getUidNo()).isNotNull();
		assertThat(savedUser).isEqualTo(user);
		log.info("유저 정보 확인[user       >> {}", user);
		log.info("유저 정보 확인[savedUser] >> {}", savedUser);
		System.out.println("디비 확인 >> " + userReppsi.findByUidNo(1));
	}

	@Test
	void findByUidNo() {
		// given
		User user = new User("유저7", "비번1", "닉네임7", "네이버");
		User savedUser = userReppsi.save(user); 
		
		// when
		userReppsi.findByUidNo(savedUser.getUidNo());
		
		// then
		assertThat(savedUser.getUidNo()).isNotNull();
		assertThat(savedUser).isEqualTo(user);
		log.info("유저 정보 확인[user       >> {}", user);
		log.info("유저 정보 확인[savedUser] >> {}", savedUser);
	}
	
	@Test
	void updateUser() {
		// given
		User user = new User("유저7", "비번1", "닉네임7", "네이버");
		userReppsi.save(user);
		
		// when
		User updateUser = new User("유저8", "비번2", "닉네임9", "네이버");
		updateUser = userReppsi.updateUser(user.getUidNo(), updateUser);
		
		// then
		assertThat(updateUser.getUidNo()).isEqualTo(user.getUidNo());
		assertThat(user.getUserPwd()).isEqualTo(updateUser.getUserPwd());
		assertThat(user.getUserNick()).isEqualTo("닉네임9");
		log.info("user의 정보가 수정되었는지 확인       >> {}", user);
		log.info("updateUser의 정보가 수정되었는지 확인 >> {}", updateUser);
	}
}
