package com.shareknot.modules.account.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.form.NicknameForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NicknameFormValidator implements Validator {

	private final AccountRepository accountRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return NicknameForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NicknameForm nickNameForm = (NicknameForm) target;
		Account findByNickname = accountRepository.findByNickname(nickNameForm.getNickname());
		if (findByNickname != null) {
			errors.rejectValue("nickname", "wrong.value", "This nickname is already exsited.");
		}
	}

}
