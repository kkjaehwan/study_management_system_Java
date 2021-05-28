package com.shareknot.modules.board;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.AccountService;
import com.shareknot.modules.account.UserAccount;
import com.shareknot.modules.account.form.SignUpForm;
import com.shareknot.modules.board.form.BoardForm;
import com.shareknot.modules.board.form.PostForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WithBoardPostFactoryImpl implements WithBoardFactory, WithPostFactory {

	@Autowired
	BoardService boardService;

	@Autowired
	AccountService accountService;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	PostService postService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Board makeBoard(String board_title) {
		Board board = boardRepository.findByTitle(board_title);
		if (board == null) {
			String nickname = "shareknot";

			Account account = accountRepository.findByNickname(nickname);
			if (account == null) {
				account = createNewAccount(nickname);
			}

			BoardForm boardForm = new BoardForm();
			boardForm.setTitle(board_title);
			board = boardService.createNewBoard(boardForm, account);
		}
		return board;
	}

	private Account createNewAccount(String nickname) {
		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setNickname(nickname);
		signUpForm.setEmail(nickname + "@email.com");
		signUpForm.setPassword("12345678");
		return accountService.processNewAccount(signUpForm);
	}

	@Override
	public Post makePost(String account_nickname, String board_title, String post_title) {
		Board board = getBoard(board_title);
		Account account = accountRepository.findByNickname(account_nickname);
		if (account == null) {
			account = createNewAccount(account_nickname);
		}
		Post post = makePost(account, board, post_title, "test_contents");
		return post;
	}

	private Post makePost(Account account, Board board, String title, String content) {

		PostForm postForm = new PostForm();
		postForm.setTitle(title);
		postForm.setContent(content);

		return postService.createNewPost(modelMapper.map(postForm, Post.class), board, account);
	}

	private Board getBoard(String boardName) {
		return boardRepository.findByTitle(boardName);
	}

	public UserAccount getPrincipalAccount() {
		SecurityContext context = SecurityContextHolder.getContext();
		UserAccount account = (UserAccount) context.getAuthentication()
				.getPrincipal();
		return account;
	}
}
