package ssh.better.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import ssh.better.domain.Board;
import ssh.better.repository.BoardRepository;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	private final BoardRepository repository;

	@GetMapping("/")
	public String index(Model model) {
		List<Board> boardList = repository.findTop();
		
		model.addAttribute("boardList", boardList);
		
		return "../static/index";
	}
}
