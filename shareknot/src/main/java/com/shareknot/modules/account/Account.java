package com.shareknot.modules.account;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.shareknot.modules.borad.Comment;
import com.shareknot.modules.borad.Post;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String nickname;

	private String password;

	private boolean emailVerified;

	private String emailCheckToken;

	private LocalDateTime emailCheckTokenGenerateAt;

	private String emailLoginToken;

	private LocalDateTime emailLoginTokenGenerateAt;

	private LocalDateTime joinedAt;

	private String bio;

	private String url;

	private String occupation;

	private String location;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String profileImage;

	private boolean partyCreatedByEmail;

	private boolean partyCreatedByWeb = true;

	private boolean partyEnrolmentResultByEmail;

	private boolean partyEnrolmentResultByWeb = true;

	private boolean partyUpdatedByEmail;

	private boolean partyUpdatedByWeb = true;

	@ManyToMany
	private Set<Tag> tags = new HashSet<>();

	@ManyToMany
	private Set<Zone> zones = new HashSet<>();

	@OneToMany(mappedBy = "author")
	private Set<Post> posts = new HashSet<>();
	@OneToMany(mappedBy = "author")
	private Set<Comment> comments = new HashSet<>();

	public void generateEmailCheckToken() {
		this.emailCheckToken = UUID.randomUUID().toString();
		this.emailCheckTokenGenerateAt = LocalDateTime.now();

	}

	public void generateEmailLoginToken() {
		this.emailLoginToken = UUID.randomUUID().toString();
		this.emailLoginTokenGenerateAt = LocalDateTime.now();

	}

	public void completeSignUp() {
		this.emailVerified = true;
		this.joinedAt = LocalDateTime.now();
	}

	public boolean isValidCheckToken(String token) {
		if (this.emailCheckToken.isEmpty() || this.emailCheckToken.isBlank())
			return false;
		return this.emailCheckToken.equals(token);
	}

	public boolean canSendConfirmEmail() {
		return this.emailCheckTokenGenerateAt.isBefore(LocalDateTime.now().minusHours(1));
	}

	public boolean canSendEmailLogin() {
		if (this.emailLoginTokenGenerateAt == null)
			return true;
		return this.emailLoginTokenGenerateAt.isBefore(LocalDateTime.now().minusHours(1));
	}

	public boolean isValidLoginToken(String token) {
		if (this.emailLoginToken.isEmpty() || this.emailLoginToken.isBlank())
			return false;
		return this.emailLoginToken.equals(token);
	}

}
