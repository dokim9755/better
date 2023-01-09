package ssh.better.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ssh.better.domain.Board;
import ssh.better.service.BoardService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
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
	
	@GetMapping("/{boardNo}")
	public String detail(@PathVariable Long boardNo, Model model) {
		String boardTitle = boardService.findBoardNo(boardNo).getBoardTitle();
		Board board = boardService.findBoardNo(boardNo);
		
		model.addAttribute("boardTitle", boardTitle);
		model.addAttribute(board);
		return "board/detail";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(required = false, defaultValue = "title") String select, @RequestParam String text, Model model) {
		List<Board> boardList = null;
		System.out.println("select 확인 >> " + select);
		System.out.println("text 확인 >> " + text);
		
		if(select.equals("title"))
			boardList = boardService.findByBoardTitle(text);
		else if(select.equals("nick"))
			boardList = boardService.findByBoardWriter(text);
		
		/*
		 * 1. 검색 결과가 없을 때, 화면에 무엇을 보여줄 것인가
		 * 2. list.size가 0일 때, IndexOutOfBoundsException => 예외처리? 프젝 트러블 슈팅
		 */
		model.addAttribute("boardList", boardList);
		return "board/community";
	}
}
