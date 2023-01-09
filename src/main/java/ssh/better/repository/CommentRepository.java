package ssh.better.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.Comment;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentRepository {

	private final EntityManager em;
	
	// 댓글 작성
	public Comment save(Comment comment) {
		em.persist(comment);
		return comment;
	}
	
	// 댓글 기본키 조회
	public Optional<Comment> findByCmntNo(Integer cmntNo) {
		em.find(Comment.class, cmntNo);
		return Optional.ofNullable(null);
	}
	
	// 내용 수정
	public Comment updateCmnt(Integer cmntNo, Comment cmntUp) {
		Comment cmnt = em.find(Comment.class, cmntNo);
		cmnt.setCmntContent(cmntUp.getCmntContent());
		cmnt.setCmntUpdate(cmntUp.getCmntDate());
		
		Comment updateCmnt = em.merge(cmnt);
		log.info("리포지토리 수정 완료?? >> {}", updateCmnt);
		
		return updateCmnt;
	}
	
}
