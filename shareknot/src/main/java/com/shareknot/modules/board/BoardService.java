package com.shareknot.modules.board;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRole;
import com.shareknot.modules.account.AccountRoleRepository;
import com.shareknot.modules.board.form.BoardForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;
	private final AccountRoleRepository accountRoleRepository;

	public Board createNewBoard(@Valid BoardForm boardForm, Account account) {
		isAdmin(account);
		return boardRepository.save(modelMapper.map(boardForm, Board.class));
	}

	public void deleteBoard(Account account, Board board) {
		isAdmin(account);
		boardRepository.delete(board);
	}

	public Board getBoardToUpdate(Account account, String title) {
		isAdmin(account);
		Board board = boardRepository.findByTitle(title);
		if (board == null) {
			throw new IllegalArgumentException("There are no board in the title : " + title);
		}
		return board;
	}

	private void isAdmin(Account account) {
		AccountRole adminRole = accountRoleRepository.findByName("ROLE_ADMIN");
		if (!account.getRoles()
				.contains(adminRole)) {
			throw new AccessDeniedException("The feature is not available.");
		}
	}

	public void updateTitle(Board board, @Valid BoardForm boardForm) {
		modelMapper.map(boardForm, board);
	}

	public Board getBoard(String title) {
		Board board = boardRepository.findByTitle(title);
		if (board == null) {
			throw new IllegalArgumentException("There are no board in the title : " + title);
		}
		return board;
	}

}
