package com.shareknot.modules.board;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.UserAccount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@Setter

public class Comment {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Post post;

	private Long commentGrp;

	private Long commentOdr;

	@ManyToOne
	private Comment parentComment;

	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
	private Set<Comment> childComments = new HashSet<Comment>();

	@Column(nullable = false)
	private String content;

	private LocalDateTime createdDateTime = LocalDateTime.now();

	@ManyToOne
	private Account author;

	public boolean isAuthor(UserAccount userAccount) {
		return this.author.equals(userAccount.getAccount());
	}

}
