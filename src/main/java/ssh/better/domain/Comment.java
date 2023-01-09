package ssh.better.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "comment")
@Setter @Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cmntNo;
	
	@Column(nullable = false)
	private Long boardNo;
	
	@Column(nullable = false)
	private Integer uidNo;
	private int cmntLike;
	private String cmntWriter;
	private String cmntContent;
	private LocalDateTime cmntDate = LocalDateTime.now();
	private LocalDateTime cmntUpdate = LocalDateTime.now();
	
	@Builder
	public Comment(Long boardNo, Integer uidNo, Integer cmntLike, String cmntWriter, String cmntContent) {
		this.boardNo = boardNo;
		this.uidNo = uidNo;
		this.cmntLike = cmntLike;
		this.cmntWriter = cmntWriter;
		this.cmntContent = cmntContent;
	}
}
