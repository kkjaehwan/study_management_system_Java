package com.shareknot.modules.party.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.party.PartyRepository;
import com.shareknot.modules.party.form.PartyForm;
import com.shareknot.modules.party.form.PartyPathForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PartyPathFormValidator implements Validator {

	private final PartyRepository partyRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return PartyPathForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PartyPathForm partyPathForm = (PartyPathForm) target;

		if (partyRepository.existsByPath(partyPathForm.getPath())) {
			errors.rejectValue("path","wrong.path","The path is already exsist.");
		}
	}

}
