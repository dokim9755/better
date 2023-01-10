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
public class LoginService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(userId).get();
		
		if(user == null)
			return null;
		
		String pwd = user.getUserPwd();
		
		return org.springframework.security.core.userdetails.User.builder()
				.username(userId)
				.password(pwd)
				.roles("USER")
				.build();
	}

}
