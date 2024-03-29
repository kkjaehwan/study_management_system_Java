package com.shareknot.modules.notification;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.shareknot.modules.account.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Notification {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	private String link;

	private String message;

	private boolean checked;

	@ManyToOne
	private Account account;

	private LocalDateTime createdDateTime;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

}
