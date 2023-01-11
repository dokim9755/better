package ssh.better.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssh.better.domain.User;
import ssh.better.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(userId).get();
		System.out.println("유저 정보 >> " + user);
		
		if(user == null)
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		
		System.out.println("디테일로 넘어가나? >> " + user);
		return new PrincipalDetails(user);
	}
}
