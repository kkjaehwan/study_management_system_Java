package com.shareknot.modules.board.form;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.shareknot.modules.event.EventType;
import com.shareknot.modules.event.form.EventForm;

import lombok.Data;

@Data
public class BoardForm {

	@NotBlank
	@Length(max = 100, min = 2)
	private String title;

}
