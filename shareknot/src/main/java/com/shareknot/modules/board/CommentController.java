package com.shareknot.modules.board;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.board.form.CommentForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

	private final BoardRepository boardRepository;
	private final PostRepository postRepository;

	private final CommentService commentService;

	private final CommentRepository commentRepository;

	@GetMapping("/get-comments/{id}")
	public String getComments(@CurrentAccount Account account,
			@PathVariable("id") Post post, Model model) {
		model.addAttribute("commentList",
				commentRepository.findAllByPostOrderByCommentGrpDescCommentOdrDesc(post));

		return "fragment::comments-list";
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity addComment(@CurrentAccount Account account,
			@Valid @RequestBody CommentForm commentForm, Errors errors) {

		Board board = boardRepository.findByTitle(commentForm.getBoardTitle());
		Post post = postRepository.findById(commentForm.getPostId())
				.orElseThrow();

		if (errors.hasErrors() || board == null || post == null) {
			return ResponseEntity.badRequest()
					.body(errors.getAllErrors()
							.stream()
							.map(x -> x.getDefaultMessage())
							.collect(Collectors.joining(",")));
		}

		commentService.saveComment(board, post, account, commentForm);

		return ResponseEntity.ok()
				.build();
	}

	@PostMapping("/remove/{id}")
	@ResponseBody
	public ResponseEntity deleteComment(@CurrentAccount Account account,
			@PathVariable("id") Comment comment) {


		if (comment == null || !comment.getAuthor()
				.equals(account)) {
			return ResponseEntity.badRequest()
					.build();
		}

		commentService.removeComment(account,  comment);

		return ResponseEntity.ok()
				.build();
	}

	@Override
	public String toString() {
		return null;
	}
}
