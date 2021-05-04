package com.shareknot.modules.party;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.UserAccount;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NamedEntityGraph(name = "Party.withAll", attributeNodes = { @NamedAttributeNode("tags"), @NamedAttributeNode("zones"),
		@NamedAttributeNode("managers"), @NamedAttributeNode("members") })
@NamedEntityGraph(name = "Party.withTagsAndManagers", attributeNodes = { @NamedAttributeNode("tags"),
		@NamedAttributeNode("managers") })
@NamedEntityGraph(name = "Party.withZonesAndManagers", attributeNodes = { @NamedAttributeNode("zones"),
		@NamedAttributeNode("managers") })
@NamedEntityGraph(name = "Party.withManagers", attributeNodes = { @NamedAttributeNode("managers") })
@NamedEntityGraph(name = "Party.withMembers", attributeNodes = { @NamedAttributeNode("members") })
@NamedEntityGraph(name = "Party.withTagsAndZones", attributeNodes = { @NamedAttributeNode("zones"),
		@NamedAttributeNode("tags") })
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Party {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany
	private Set<Account> managers = new HashSet<>();

	@ManyToMany
	private Set<Account> members = new HashSet<>();

	@Column(unique = true)
	private String path;

	private String title;

	private String shortDescription;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String fullDescription;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String image;

	@ManyToMany
	private Set<Tag> tags = new HashSet<>();

	@ManyToMany
	private Set<Zone> zones = new HashSet<>();

	private LocalDateTime publishedDateTime;

	private LocalDateTime closedDateTime;

	private LocalDateTime recruitingUpdatedDateTime;

	private boolean recruiting = false;

	private boolean published = false;
	private boolean closed = true;
	private boolean useBanner = false;
	private int memberCount=0;

	public void addManager(Account account) {
		this.managers.add(account);
	}

	public boolean isJoinable(UserAccount userAccount) {
		Account account = userAccount.getAccount();
		boolean rv = this.isRecruiting() && !this.members.contains(account)
				&& !this.managers.contains(account);
		return rv;
	}

	public boolean isMember(UserAccount userAccount) {
		return this.members.contains(userAccount.getAccount());
	}

	public boolean isManager(UserAccount userAccount) {
		return this.managers.contains(userAccount.getAccount());
	}

	public boolean isManagerBy(Account account) {
		return this.getManagers().contains(account);
	}

	public String getEncodedPath() {
		return URLEncoder.encode(this.path, StandardCharsets.UTF_8);
	}

	public void allowSearch() {
		if (this.closed == true && this.published == false) {
			this.published = true;
			this.closed = false;
			this.publishedDateTime = LocalDateTime.now();
		} else {
			throw new RuntimeException("Unable to disclose party.");
		}
	}

	public void denySearch() {
		if (this.closed == false && this.published == true) {
			this.published = false;
			this.closed = true;
			this.closedDateTime = LocalDateTime.now();
		} else {
			throw new RuntimeException("Unable to close party.");
		}

	}

	public void allowRecruit() {
		if (canUpdateRecruiting()) {
			this.recruiting = true;
			this.recruitingUpdatedDateTime = LocalDateTime.now();
		} else {
			throw new RuntimeException("Unable to disclose recruiting");

		}

	}

	public void denyRecruit() {
		if (canUpdateRecruiting()) {
			this.recruiting = false;
			this.recruitingUpdatedDateTime = LocalDateTime.now();
		} else {
			throw new RuntimeException("Unable to close recruiting");

		}
	}

	public boolean canUpdateRecruiting() {
		return this.recruitingUpdatedDateTime == null
				|| this.recruitingUpdatedDateTime.isBefore(LocalDateTime.now().minusHours(1));
	}

	public void addMember(Account account) {
		this.getMembers().add(account);
		this.memberCount++;

	}

	public void removeMember(Account account) {
		this.getMembers().remove(account);
		this.memberCount++;
	}

}
