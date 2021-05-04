package com.shareknot.modules.party.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PartyPathForm {
	
	@NotBlank
	@Length(min = 2, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,20}$")
	private String path;

}
