package ssh.better.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ssh.better.domain.User;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepository {
	
	private final EntityManager em;
	
	// 회원가입(유저 정보 등록)
	public User save(User user) {
		em.persist(user);
		return user;
	}
	
	// 유저 기본키 조회
	public Optional<User> findByUidNo(Integer uidNo) {
		User user = em.find(User.class, uidNo);
		return Optional.ofNullable(user);
	}
	
	// 닉네임 수정
	public User updateUser(Integer uidNo, User userUp) {
		User user = em.find(User.class, uidNo);
		user.setUserPwd(userUp.getUserPwd());
		user.setUserNick(userUp.getUserNick());
		
		User updateUser = em.merge(user);
		
		return updateUser;
	}
	
	
	// 회원탈퇴
	
}
