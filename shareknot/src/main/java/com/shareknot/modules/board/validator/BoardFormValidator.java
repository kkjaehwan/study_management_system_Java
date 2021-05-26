package com.shareknot.modules.board.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.board.Board;
import com.shareknot.modules.board.BoardRepository;
import com.shareknot.modules.board.form.BoardForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardFormValidator implements Validator {

	private final BoardRepository boardRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return BoardForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BoardForm boardForm = (BoardForm) target;
		Board findByTitle = boardRepository.findByTitle(boardForm.getTitle());

		if (findByTitle != null) {
			errors.rejectValue("title", "wrong.value", "This title is already exsited.");
		}
	}

}
