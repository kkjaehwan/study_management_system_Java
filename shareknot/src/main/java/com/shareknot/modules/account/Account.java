package com.shareknot.modules.account;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

import com.shareknot.modules.board.Comment;
import com.shareknot.modules.board.Post;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
	@SequenceGenerator(name = "account_id_generator", sequenceName = "account_id_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String nickname;

	private String password;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<AccountRole> roles = new HashSet<>();

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
	@Type(type = "org.hibernate.type.TextType")
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
		this.emailCheckToken = UUID.randomUUID()
				.toString();
		this.emailCheckTokenGenerateAt = LocalDateTime.now();

	}

	public void generateEmailLoginToken() {
		this.emailLoginToken = UUID.randomUUID()
				.toString();
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
		return this.emailCheckTokenGenerateAt.isBefore(LocalDateTime.now()
				.minusHours(1));
	}

	public boolean canSendEmailLogin() {
		if (this.emailLoginTokenGenerateAt == null)
			return true;
		return this.emailLoginTokenGenerateAt.isBefore(LocalDateTime.now()
				.minusHours(1));
	}

	public boolean isValidLoginToken(String token) {
		if (this.emailLoginToken.isEmpty() || this.emailLoginToken.isBlank())
			return false;
		return this.emailLoginToken.equals(token);
	}
	
	public void addRole(AccountRole role) {
		if(!this.roles.contains(role))
			this.roles.add(role);
	}

}
