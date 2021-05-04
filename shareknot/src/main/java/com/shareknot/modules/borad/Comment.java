package com.shareknot.modules.borad;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shareknot.modules.account.Account;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;

	private String comment;

	@ManyToOne
	private Post post;

	@ManyToOne
	private Account author;

	@OneToMany(mappedBy = "id")
	private Set<Comment> up_comment = new HashSet<>();
}
