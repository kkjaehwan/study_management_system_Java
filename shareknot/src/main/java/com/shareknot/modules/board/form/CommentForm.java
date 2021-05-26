package com.shareknot.modules.board.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.shareknot.modules.board.Board;

import lombok.Data;

@Data
public class CommentForm {

	private String boardTitle;

	private Long postId;

	private Long parentCommentId = null;

	@NotBlank
	@Length(max = 100, min = 10)
	private String content;

}
