package com.shareknot.modules.borad;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shareknot.modules.account.Account;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	private String contents;

	@ManyToOne
	private Account author;

	@OneToMany(mappedBy = "post",cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Comment> comments = new HashSet<>();
}
