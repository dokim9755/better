package ssh.better.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "board")
@Setter @Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;
	
	@Column(nullable = false)
	private Integer uidNo;
	
	@Column(nullable = false)
	private String boardWriter;
	
	@Column(nullable = false)
	private String boardTitle;
	
	@Column(nullable = false )
	private String boardContent;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private int boardCnt;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private int boardCmnts;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private int boardLike;
	
	@CreationTimestamp
	private LocalDateTime boardDate;
	
	@UpdateTimestamp
	private LocalDateTime boardUpdate;
	
	@Builder
	public Board(Integer uidNo, String boardWriter, String boardTitle, String boardContent) {
		this.uidNo = uidNo;
		this.boardWriter = boardWriter;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}
}
