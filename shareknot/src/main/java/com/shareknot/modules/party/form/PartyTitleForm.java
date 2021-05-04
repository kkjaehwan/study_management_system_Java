package com.shareknot.modules.party.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PartyTitleForm {

	@NotBlank
	@Length(max = 50)
	private String title;

}
