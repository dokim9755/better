package ssh.better.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssh.better.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	

}
