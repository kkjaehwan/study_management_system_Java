package com.shareknot.modules.account.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.account.form.PasswordForm;

public class PasswordFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PasswordForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PasswordForm passwordForm = (PasswordForm) target;
		
		if(!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordConfirm())) {
			errors.rejectValue("newPassword","wrong.value","The new password you entered does not match.");
		}
	}

}
