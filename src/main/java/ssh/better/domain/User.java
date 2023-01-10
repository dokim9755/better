package ssh.better.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")
@Setter @Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uidNo; 
	
	@Column(nullable = false, unique = true)
	private String userId;
	
	@JsonIgnore
	@Column(nullable = false)
	private String userPwd;
	
	@Column(nullable = false, unique = true)
	private String userNick;
	
	@Column(nullable = false)
	private String userEmail;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Builder
	public User(String userId, String userPwd, String userNick, String userEmail) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNick = userNick;
		this.userEmail = userEmail;
	}
}
