package com.shareknot.modules.board;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_generator")
	@SequenceGenerator(name = "comment_id_generator", sequenceName = "comment_id_seq", allocationSize = 1)
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
