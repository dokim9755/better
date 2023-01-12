package ssh.better.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.Board;
import ssh.better.domain.User;

@SpringBootTest
@Transactional
@Slf4j
class BoardRepositoryTest {

	@Autowired UserRepository userRepository;
	@Autowired BoardRepository repository;

	@Test
	void save() {
		// given
		User user = userRepository.findByUidNo(3).get();
		log.info("유저 정보 확인 >> {}", user);
		
		Board board = Board.builder().uidNo(user.getUidNo())
					.boardWriter(user.getUserNick())
					.boardTitle("제목5")
					.boardContent("내용5")
					.build();
		
		// when
		Board saved = repository.save(board);
		
		// then
		log.info("정상적으로 등록 되어 있는지 확인 >> {}", saved);
		assertThat(saved.getBoardWriter()).isEqualTo(user.getUserNick());
		assertThat(saved.getBoardTitle()).isEqualTo("제목5");
		assertThat(saved.getBoardContent()).isEqualTo("내용5");
	}
	
	@Test
	void findByBoardNo() {
		// given
		User user = userRepository.findByUidNo(1).get();
		Board board = Board.builder().uidNo(user.getUidNo())
				.boardWriter(user.getUserNick())
				.boardTitle("제목1")
				.boardContent("내용1")
				.build();
		
		repository.save(board);
		
		// when
		Board findBoard = repository.findByBoardNo(board.getBoardNo()).get(); 
		
		// then
		log.info("row 확인 >> {}", findBoard);
		assertThat(findBoard.getBoardNo()).isEqualTo(board.getBoardNo());
		assertThat(findBoard.getUidNo()).isEqualTo(board.getUidNo());
		assertThat(findBoard.getBoardWriter()).isEqualTo(board.getBoardWriter());
		assertThat(findBoard.getBoardTitle()).isEqualTo(board.getBoardTitle());
		assertThat(findBoard.getBoardContent()).isEqualTo(board.getBoardContent());
	}
	
	@Test
	void findAll() {
		// given
		List<Board> before = repository.findAll();
		
		Board board = Board.builder().uidNo(2)
				.boardWriter("작성자1")
				.boardTitle("제목1")
				.boardContent("내용1")
				.build();
		
		repository.save(board);
		
		// when
		List<Board> after = repository.findAll();
		
		// then
		log.info("before List size >> {}", before.size());
		log.info("after  List size >> {}", after.size());
		assertThat(after.size()).isEqualTo(before.size() + 1);
		assertThat(after).extracting("boardNo").contains(board.getBoardNo());
		assertThat(after).extracting("boardTitle").contains(board.getBoardTitle());
		assertThat(after).extracting("boardContent").contains("내용1");
	}
	
	@Test
	void findByBoardTitle() {
		// given
		User user = userRepository.findByUidNo(7).get();
		Board board = Board.builder().uidNo(user.getUidNo())
				.boardWriter(user.getUserNick())
				.boardTitle("제목7")
				.boardContent("내용7")
				.build();
		
		repository.save(board);
		
		// when
		List<Board> findWriter = repository.findByBoardTitle("제목");
		
		// then
		System.out.println(findWriter.size());
		assertThat(findWriter).extracting("boardTitle").contains("제목2");
		assertThat(findWriter).extracting("boardTitle").contains("제목3");
		assertThat(findWriter).extracting("boardTitle").contains("제목4");
		assertThat(findWriter).extracting("boardTitle").contains("제목5");
	}
	
	@Test
	void findByBoardWriter() {
		// given
		User user = userRepository.findByUidNo(1).get();
		Board board = Board.builder().uidNo(user.getUidNo())
				.boardWriter("작성자1")
				.boardTitle("제목1")
				.boardContent("내용1")
				.build();
		
		repository.save(board);
		
		// when
		List<Board> findWriter = repository.findByBoardWriter("작");
		
		// then
		System.out.println(findWriter.size());
		assertThat(findWriter).extracting("boardWriter").contains("작성자1");
		assertThat(findWriter).extracting("boardWriter").contains("작성자2");
		assertThat(findWriter).extracting("boardWriter").contains("작성자3");
		assertThat(findWriter).extracting("boardWriter").contains("작성자4");
	}
	
	@Test
	void updateBoard() {
		// given
		Board board = Board.builder().uidNo(2)
				.boardWriter("작성자1")
				.boardTitle("제목1")
				.boardContent("내용1")
				.build();
		
		repository.save(board);
		
		// when
		Board update = Board.builder().uidNo(1)
				.boardWriter("작성자1")
				.boardTitle("제목2")
				.boardContent("내용2")
				.build();
		
		repository.updateBoard(board.getBoardNo(), update);
		
		// then
		System.out.println(board);
		assertThat(board.getBoardTitle()).isEqualTo(update.getBoardTitle());
		assertThat(board.getBoardContent()).isEqualTo(update.getBoardContent());
	}
	
	@Test
	void delete() {
		// given
		Board board = Board.builder().uidNo(5)
				.boardWriter("작성자1")
				.boardTitle("제목1")
				.boardContent("내용1")
				.build();
		
		repository.save(board);
		
		// when
		repository.delete(board.getBoardNo());
		
		// then
		Optional<Board> result = repository.findByBoardNo(board.getBoardNo());
		System.out.println(result);
		assertThat(result.isEmpty()).isEqualTo(true);
		assertThat(result).isEmpty();
	}
}
