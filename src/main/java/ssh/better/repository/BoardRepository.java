package ssh.better.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.Board;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardRepository {

	private final EntityManager em;
	
	// 게시글 작성
	public Board save(Board board) {
		em.persist(board);
		return board;
	}
	
	// 기본키 조회
	public Optional<Board> findByBoardNo(Long boardNo) {
		Board board = em.find(Board.class, boardNo);
		return Optional.ofNullable(board);
	}
	
	// 전체 조회 : << 최신순 >>
	public List<Board> findAll() {
		return em.createQuery("select b from board b order by boardNo desc", Board.class).getResultList();
	}
	
	// 정렬 : << 조회순 >>
	public List<Board> findByBoardCntOrder() {
		return em.createQuery("select b from board b order by b.boardCnt desc", Board.class).getResultList();
	}
	
	// 정렬 : << 추천순 >>
	public List<Board> findByBoardLikeOrder() {
		return em.createQuery("select b from board b order by b.boardLike desc", Board.class).getResultList();
	}
	
	// 조회수 기준 상위 10개 조회
	public List<Board> findTop() {
		String sql = "select b from board b order by b.boardCnt desc";
		
		return em.createQuery(sql, Board.class).setMaxResults(10).getResultList();
	}
	
	// 검색_제목 조회
	public List<Board> findByBoardTitle(String boardTitle) {
		List<Board> titleList = em.createQuery("select b from board b where b.boardTitle like :title", Board.class)
								.setParameter("title", "%" + boardTitle + "%")
								.getResultList();
		
		return titleList;
	}
	
	// 검색_작성자
	public List<Board> findByBoardWriter(String boardWriter) {
		List<Board> writerList = em.createQuery("select b from board b where boardWriter like :writer", Board.class)
									.setParameter("writer", "%" + boardWriter + "%")
									.getResultList();
		return writerList;
	}
	
	// 게시글 제목 및 내용 수정
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	public void updateBoard(Long boardNo, Board boardUp) {
		String sql = "update board b set b.boardTitle = :title, "
				+ "b.boardContent = :content "
				+ "where b.boardNo = :boardNo";
		
		int rows = em.createQuery(sql).setParameter("title", boardUp.getBoardTitle())
							.setParameter("content", boardUp.getBoardContent())
							.setParameter("boardNo", boardNo)
							.executeUpdate();
		
		Board board = findByBoardNo(boardNo).get();
		em.refresh(board);
		
		log.info("변경이 이루어졌는지 확인 1 == ? >> {}", rows);
	}
	
	// 게시글 삭제
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	public void delete(Long boardPk) {
		String sql = "delete from board b where b.boardNo = :pk";
		
		em.createQuery(sql).setParameter("pk", boardPk).executeUpdate();
		em.clear();
	}
	
}
