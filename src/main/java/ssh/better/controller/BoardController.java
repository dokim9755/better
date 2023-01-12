package ssh.better.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ssh.better.domain.Board;
import ssh.better.service.BoardService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping
	public String boardAll(@RequestParam(required = false) String sort, Model model) {
		Map<String, String> sortMap = new HashMap<String, String>();
		sortMap.put("boardNo", "최신순");
		sortMap.put("boardCnt", "조회순");
		sortMap.put("boardLike", "추천순");
		
		String sortValue = sortMap.get(sort);
		Optional<String> sortOptional = Optional.ofNullable(sort);
		
		List<Board> boardList = boardService.findAll();
		
		if(sortOptional.isPresent()) {
			if(sort.equals("boardCnt"))
				boardList = boardService.findByBoardCntOrder();
			else if(sort.equals("boardLike"))
				boardList = boardService.findByBoardLikeOrder();
		}
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("sortValue", sortValue);
		
		return "board/community";
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/newCommunity";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(required = false, defaultValue = "title") String select, @RequestParam String boardTitle, Model model) {
		List<Board> boardList = null;
		
		if(select.equals("title"))
			boardList = boardService.findByBoardTitle(boardTitle);
		else if(select.equals("nick")) {
			boardList = boardService.findByBoardWriter(boardTitle);
			model.addAttribute("select", select);
		}
		
		/*
		 * 1. 검색 결과가 없을 때, 화면에 무엇을 보여줄 것인가
		 * 2. list.size가 0일 때, IndexOutOfBoundsException => 예외처리? 프젝 트러블 슈팅
		 */
		model.addAttribute("boardList", boardList);
		model.addAttribute("boardTitle", boardTitle);
		return "board/community";
	}
	
	@GetMapping("/{boardNo}")
	public String detail(@PathVariable Long boardNo, Model model) {
		String boardTitle = boardService.findBoardNo(boardNo).getBoardTitle();
		Board board = boardService.findBoardNo(boardNo);
		
		model.addAttribute("boardTitle", boardTitle);
		model.addAttribute(board);
		return "board/detail";
	}
	
	@PostMapping("/{boardNo}")
	public String delete(@PathVariable Long boardNo) {
		//boardService.delete(boardNo);
		
		return "redirect:/posts";
	}
	
	@GetMapping("/{boardNo}/modify")
	public String modify(@PathVariable Long boardNo, Model model) {
		Board board = boardService.findBoardNo(boardNo);
		
		model.addAttribute(board);
		return "board/modify";
	}
	
	
	@PostMapping("/{boardNo}/modify")
	public String modifySave(@PathVariable Long boardNo, @ModelAttribute Board boardUpdate) {
		log.info("수정 적용된 제목 >> {}", boardUpdate.getBoardTitle());
		log.info("수정 적용된 제목 >> {}", boardUpdate.getBoardContent());
		
		boardService.updateBoard(boardNo, boardUpdate);
		
		Board board = boardService.findBoardNo(boardNo);
		log.info("수정 적용된 제목 >> {}", board.getBoardTitle());
		log.info("수정 적용된 제목 >> {}", board.getBoardContent());
		return "redirect:/posts/{boardNo}";
	}
	
}
