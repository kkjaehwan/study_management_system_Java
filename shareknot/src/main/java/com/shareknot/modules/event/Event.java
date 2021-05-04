package com.shareknot.modules.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.UserAccount;
import com.shareknot.modules.party.Party;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@NamedEntityGraph(name = "Event.withEnrolments", attributeNodes = @NamedAttributeNode("enrolments"))
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Party party;

	@ManyToOne
	private Account createdBy;

	@Column(nullable = false)
	private String title;

	@Lob
	private String description;

	@Column(nullable = false)
	private LocalDateTime createdDateTime;

	@Column(nullable = false)
	private LocalDateTime endEnrolmentDateTime;

	@Column(nullable = false)
	private LocalDateTime startDateTime;

	@Column(nullable = false)
	private LocalDateTime endDateTime;

	@Column(nullable = true)
	private Integer limitOfEnrolments;

	@OneToMany(mappedBy = "event")
	@OrderBy("enroledAt")
	private List<Enrolment> enrolments = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	public boolean isEnrolableFor(UserAccount userAccount) {
		return isNotClosed() && !isAttended(userAccount) && !isAlreadyEnroled(userAccount);
	}

	public boolean isDisenrolableFor(UserAccount userAccount) {
		return isNotClosed() && !isAttended(userAccount) && isAlreadyEnroled(userAccount);
	}

	private boolean isNotClosed() {
		return this.endEnrolmentDateTime.isAfter(LocalDateTime.now());
	}

	public boolean isAttended(UserAccount userAccount) {
		Account account = userAccount.getAccount();
		for (Enrolment e : this.enrolments) {
			if (e.getAccount().equals(account) && e.isAttended()) {
				return true;
			}
		}

		return false;
	}

	private boolean isAlreadyEnroled(UserAccount userAccount) {
		Account account = userAccount.getAccount();
		for (Enrolment e : this.enrolments) {
			if (e.getAccount().equals(account)) {
				return true;
			}
		}
		return false;
	}

	public int numberOfRemainSpots() {
		return this.limitOfEnrolments - (int) this.enrolments.stream().filter(Enrolment::isAccepted).count();
	}

	public long getNumberOfAcceptedEnrolments() {
		return this.enrolments.stream().filter(Enrolment::isAccepted).count();
	}

	public void addEnrolment(Enrolment enrolment) {
		this.enrolments.add(enrolment);
		enrolment.setEvent(this);
	}

	public boolean isAbleToAcceptWaitingEnrolment() {
		return this.eventType == EventType.FCFS && this.limitOfEnrolments > this.getNumberOfAcceptedEnrolments();
	}

	public void removeEnrolment(Enrolment enrolment) {
		this.enrolments.remove(enrolment);
		enrolment.setEvent(null);
	}

	private List<Enrolment> getWaitingList() {
		return this.enrolments.stream().filter(enrolment -> !enrolment.isAccepted()).collect(Collectors.toList());
	}

	public void acceptWaitingList() {
		if (this.isAbleToAcceptWaitingEnrolment()) {
			var waitingList = getWaitingList();
			int numberToAccept = (int) Math.min(this.limitOfEnrolments - this.getNumberOfAcceptedEnrolments(),
					waitingList.size());
			waitingList.subList(0, numberToAccept).forEach(e -> e.setAccepted(true));
		}
	}

	public void acceptNextWaitingEnrolment() {
		if (this.isAbleToAcceptWaitingEnrolment()) {
			Enrolment enrolmentToAccept = this.getTheFirstWaitingEnrolment();
			if (enrolmentToAccept != null) {
				enrolmentToAccept.setAccepted(true);
			}
		}
	}

	private Enrolment getTheFirstWaitingEnrolment() {
		for (Enrolment e : this.enrolments) {
			if (!e.isAccepted()) {
				return e;
			}
		}

		return null;
	}

	public boolean canAccept(Enrolment enrolment) {
		return this.eventType == EventType.CONFIRMATIVE && this.enrolments.contains(enrolment)
				&& this.limitOfEnrolments > this.getNumberOfAcceptedEnrolments() && !enrolment.isAttended()
				&& !enrolment.isAccepted();
	}

	public boolean canReject(Enrolment enrolment) {
		return this.eventType == EventType.CONFIRMATIVE && this.enrolments.contains(enrolment)
				&& !enrolment.isAttended() && enrolment.isAccepted();
	}

	public void accept(Enrolment enrolment) {
		if (this.eventType == EventType.CONFIRMATIVE && this.limitOfEnrolments > this.getNumberOfAcceptedEnrolments()) {
			enrolment.setAccepted(true);
		}
	}

	public void reject(Enrolment enrolment) {
		if (this.eventType == EventType.CONFIRMATIVE) {
			enrolment.setAccepted(false);
		}
	}
}
