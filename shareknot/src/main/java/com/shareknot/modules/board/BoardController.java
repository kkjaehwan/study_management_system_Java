package com.shareknot.modules.board;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.board.form.BoardForm;
import com.shareknot.modules.board.validator.BoardFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final ModelMapper modelMapper;
	private final BoardRepository boardRepository;
	private final BoardService boardService;
	private final BoardFormValidator boardFormValidator;

	@InitBinder("boardForm")
	public void initNicknameFormBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(boardFormValidator);
	}

	@GetMapping("/lists")
	public String viewBoards(@CurrentAccount Account account, Model model) {
		model.addAttribute("boardList", boardRepository.findAll());

		return "board/lists";
	}

	@GetMapping("/new-board")
	public String newPartyForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new BoardForm());
		return "board/form";
	}

	@PostMapping("/new-board")
	public String addBoard(@CurrentAccount Account account, @Valid BoardForm boardForm,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(boardForm);
			return "board/form";
		}
		Board board = boardService.createNewBoard(boardForm, account);

		return "redirect:" + "/board/lists/";
	}

	@GetMapping("/lists/{title}")
	public String viewLists(@CurrentAccount Account account, @PathVariable String title,
			Model model) {
		Board board = boardService.getBoardToUpdate(account, title);
		model.addAttribute(board);

		return "board/view";
	}

	@GetMapping("/lists/{current_title}/edit")
	public String editBoard(@CurrentAccount Account account,
			@PathVariable("current_title") String current_title, Model model) {
		Board board = boardService.getBoardToUpdate(account, current_title);
		model.addAttribute(board);
		model.addAttribute(modelMapper.map(board, BoardForm.class));
		return "board/update-form";
	}

	@PostMapping("/lists/{current_title}/edit")
	public String editBoard(@CurrentAccount Account account,
			@PathVariable("current_title") String current_title, @Valid BoardForm boardForm,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			Board board = boardService.getBoardToUpdate(account, current_title);
			model.addAttribute(board);
			return "board/update-form";
		}
		Board board = boardService.getBoardToUpdate(account, current_title);
		boardService.updateTitle(board, boardForm);
		return "redirect:" + "/board/lists/";
	}

	@PostMapping("/lists/{title}/delete")
	public String deletePost(@CurrentAccount Account account, @PathVariable("title") String title) {
		Board board = boardService.getBoardToUpdate(account, title);
		boardService.deleteBoard(account, board);
		return "redirect:" + "/board/lists";
	}

}
