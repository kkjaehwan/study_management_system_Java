package com.shareknot.modules.board.form;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import com.shareknot.modules.board.Board;
import com.shareknot.modules.board.BoardRepository;

import lombok.Data;

@Data
public class PostForm {

	@NotBlank
	@Length(max = 100, min = 2)
	private String title;

	@Lob
	@NotBlank
	private String content;

}