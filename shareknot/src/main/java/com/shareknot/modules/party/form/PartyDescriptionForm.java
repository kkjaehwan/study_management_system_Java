package com.shareknot.modules.party.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PartyDescriptionForm {

	@NotBlank
	@Length(max = 100)
	private String shortDescription;

	@NotBlank
	private String fullDescription;
}
