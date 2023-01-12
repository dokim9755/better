package ssh.better.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssh.better.domain.Board;
import ssh.better.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	
	// 게시글 작성
	public Board save(Integer uidNo, Board board) {
		board.setUidNo(uidNo);
		boardRepository.save(board);
		return board;
	}
	
	// 기본키 조회
	public Board findBoardNo(Long boardNo) {
		Optional<Board> findPk = boardRepository.findByBoardNo(boardNo);
		
		if(findPk.isEmpty())
			return null;
		
		return findPk.get();
	}
	
	// << 조회수 기준 상위 10개 >>
	public List<Board> findTop() {
		return boardRepository.findTop();
	}
	
	// 검색_제목 조회
	public List<Board> findByBoardTitle(String boardTitle) {
		return boardRepository.findByBoardTitle(boardTitle);
	}
	
	// 검색_작성자
	public List<Board> findByBoardWriter(String boardWriter) {
		return boardRepository.findByBoardWriter(boardWriter);
	}
	
	// 전체 조회 : << 기본키 내림차순 >>
	public List<Board> findAll() {
		return boardRepository.findAll();
	}
	
	// 정렬 : << 조회기준 내림차순 >>
	public List<Board> findByBoardCntOrder() {
		return boardRepository.findByBoardCntOrder();
	}
	
	// 정렬 : << 추천기준 내림차순 >>
	public List<Board> findByBoardLikeOrder() {
		return boardRepository.findByBoardLikeOrder();
	}

	// 게시글 제목 및 내용 수정
	public void updateBoard(Long boardNo, Board boardUp) {
		boardRepository.updateBoard(boardNo, boardUp);
	}
	
	// 게시글 삭제
	public void delete(Long boardPk) {
		boardRepository.delete(boardPk);
	}
}
