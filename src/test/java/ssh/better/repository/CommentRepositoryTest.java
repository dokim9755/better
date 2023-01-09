package ssh.better.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.Board;
import ssh.better.domain.Comment;

@SpringBootTest
@Transactional
@Slf4j
class CommentRepositoryTest {

	@Autowired BoardRepository boardR; 
	@Autowired CommentRepository cmntR;
	
	@Test
	void save() {
		// given
		Board board = boardR.findByBoardNo(3L).get();
		Comment cmnt = new Comment(board.getBoardNo(), 1, 0, "댓글1", "내용1");
		log.info("다른 사람 게시글의 작성한 댓글 >> {}", cmnt);
		
		// when
		Comment afterC = cmntR.save(cmnt);
		
		// then
		assertThat(afterC.getBoardNo()).isNotNull();
		assertThat(afterC).isEqualTo(cmnt);
		log.info("댓글 정보 >> {}", afterC);
	}
	
	@Test
	void updateCmnt() {
		// given
		Board board = boardR.findByBoardNo(4L).get();
		Comment cmnt = new Comment(board.getBoardNo(), board.getUidNo(), 0, "본인글1", "내용1");
		cmntR.save(cmnt);
		log.info("본인 게시글의 작성한 댓글 >> {}", cmnt);
		
		// when
		Comment updateCmnt = new Comment(board.getBoardNo(), board.getUidNo(), 0, "본인글1", "추천해주세요~");
		updateCmnt = cmntR.updateCmnt(cmnt.getCmntNo(), updateCmnt);
		
		// then
		log.info("댓글의 내용이 변경 되었는지 확인 >> {}", cmnt.getCmntContent());
		log.info("최초 댓글 작성 날짜 확인 >> {}", cmnt.getCmntDate());
		log.info("댓글 수정 날짜가 변경되었는지 확인 >> {}", cmnt.getCmntUpdate());
		assertThat(updateCmnt.getCmntNo()).isEqualTo(cmnt.getCmntNo());
		assertThat(cmnt.getCmntContent()).isEqualTo(updateCmnt.getCmntContent());
		assertThat(cmnt.getCmntUpdate()).isEqualTo(updateCmnt.getCmntUpdate());
	}
	
	
	

}
