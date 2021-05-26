package com.shareknot.modules.board;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.board.form.CommentForm;
import com.shareknot.modules.board.form.PostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board/lists/{board}")
@RequiredArgsConstructor
public class PostController {

	private final ModelMapper modelMapper;
	private final PostRepository postRepository;
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final PostService postService;
	private final BoardService boardService;

//	@GetMapping("/posts")
//	public String viewPosts(@CurrentAccount Account account,
//			@PathVariable("board") String board_title, Model model) {
//		Board board = boardService.getBoard(board_title);
//		model.addAttribute(board);
//		model.addAttribute("postList", postRepository.findAllByBoard(board));
//
//		return "post/lists";
//	}

	@GetMapping("/posts")
	public String searchParty(@CurrentAccount Account account,
			@PathVariable("board") String board_title, String psearch, Model model,
			@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Board board = boardService.getBoard(board_title);
		model.addAttribute(board);
		Page<Post> postPage;
		if (psearch == null)
			psearch = "";
		postPage = postRepository.findAllByBoardAndKeyword(board, psearch, pageable);
		model.addAttribute("postPage", postPage);
		model.addAttribute("psearch", psearch);
		return "post/lists";
	}

	@GetMapping("/new-post")
	public String newPartyForm(@CurrentAccount Account account,
			@PathVariable("board") String board_title, Model model) {
		Board board = boardService.getBoard(board_title);
		model.addAttribute(board);
		model.addAttribute(account);
		model.addAttribute(new PostForm());
		return "post/form";
	}

	@PostMapping("/new-post")
	public String addPost(@CurrentAccount Account account,
			@PathVariable("board") String board_title, @Valid PostForm postForm, Errors errors,
			Model model, RedirectAttributes attributes) throws Exception {

		Board board = boardService.getBoard(board_title);

		if (errors.hasErrors()) {
			model.addAttribute(board);
			model.addAttribute(account);
			return "post/form";
		}
		Post post = postService.createNewPost(modelMapper.map(postForm, Post.class), board,
				account);

		return "redirect:" + "/board/lists/" + board_title + "/posts/" + post.getId();
	}

	@GetMapping("/posts/{id}")
	public String viewPosts(@CurrentAccount Account account,
			@PathVariable("board") String board_title, @PathVariable("id") Post post, Model model) {
		Board board = boardService.getBoard(board_title);
		model.addAttribute(board);
		model.addAttribute(post);
		postService.addViewCount(post);

		model.addAttribute(new CommentForm());
		return "post/view";
	}

	@GetMapping("/posts/{id}/edit")
	public String editPost(@CurrentAccount Account account,
			@PathVariable("board") String board_title, @PathVariable("id") Post post, Model model) {

		postService.checkIfAuthor(account, post);

		Board board = boardService.getBoard(board_title);
		model.addAttribute(board);
		model.addAttribute(account);
		model.addAttribute(post);
		return "post/update-form";
	}

	@PostMapping("/posts/{id}/edit")
	public String editPost(@CurrentAccount Account account,
			@PathVariable("board") String board_title, @PathVariable("id") Post post,
			@Valid PostForm postForm, Errors errors, Model model) {
		Board board = boardRepository.findByTitle(board_title);
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(postForm);
			return "post/update-form";
		}
		postService.updatePost(account, post, postForm);
		return "redirect:" + "/board/lists/" + board_title + "/posts/" + post.getId();
	}

	@PostMapping("/posts/{id}/delete")
	public String deletePost(@CurrentAccount Account account,
			@PathVariable("board") String board_title, @PathVariable("id") Post post) {
		Board board = boardRepository.findByTitle(board_title);
		postService.deletePost(account, post);
		return "redirect:" + "/board/lists/" + board_title + "/posts";
	}

}
