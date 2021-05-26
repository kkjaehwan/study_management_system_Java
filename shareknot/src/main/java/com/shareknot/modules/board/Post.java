package com.shareknot.modules.board;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.UserAccount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Post {

	@ManyToOne(optional = true)
	private Board board;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_generator")
	@SequenceGenerator(name = "post_id_generator", sequenceName = "post_id_seq", allocationSize = 1)
	private Long id;

	private String title;

	@Lob
	private String content;

	@ManyToOne
	private Account author;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();

	private LocalDateTime createdDateTime = LocalDateTime.now();

	private long viewCount = 0;

	private long commentCount=0;

	public boolean isWriitenBy(Account account) {
		return this.author.equals(account);
	}
	
	public boolean isAuthor(UserAccount userAccount) {
		return this.author.equals(userAccount.getAccount());
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPost(this);
		commentCount++;
	}

	public void removeComment(Comment comment) {
		if (comments.contains(comment)) {
			comments.remove(comment);
			commentCount--;
		}
	}

	public void addViewCount() {
		this.viewCount++;
	}


}
