package com.shareknot.modules.party.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.party.PartyRepository;
import com.shareknot.modules.party.form.PartyForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PartyFormValidator implements Validator {

	private final PartyRepository partyRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return PartyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PartyForm partyForm = (PartyForm) target;

		if (partyRepository.existsByPath(partyForm.getPath())) {
			errors.rejectValue("path","wrong.path","The path is already exsist.");
		}
	}

}
