package com.shareknot.modules.board;

import java.io.Serializable;

import lombok.Data;

@Data
//@Embeddable
public class PostPk implements Serializable {

	private Board board;

	private Long id;
}
