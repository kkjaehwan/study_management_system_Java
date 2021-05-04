package com.shareknot.modules.party.event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shareknot.infra.config.AppProperties;
import com.shareknot.infra.email.EmailMessage;
import com.shareknot.infra.email.EmailService;
import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountPredicates;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.notification.Notification;
import com.shareknot.modules.notification.NotificationRepository;
import com.shareknot.modules.notification.NotificationType;
import com.shareknot.modules.party.Party;
import com.shareknot.modules.party.PartyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class PartyEventListener {

	private final PartyRepository partyRepository;
	private final AccountRepository accountRepository;
	private final EmailService emailService;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handlePartyCreatedEvent(PartyCreatedEvent partyCreatedEvent) {
		Party party = partyRepository.findPartyWithTagsAndZonesById(partyCreatedEvent.getParty().getId());

		Iterable<Account> accounts = accountRepository
				.findAll(AccountPredicates.findByTagsAndZones(party.getTags(), party.getZones()));

		accounts.forEach(account -> {
			if (account.isPartyCreatedByEmail()) {
				String contextMessage = "new party is created";
				String emailSubject = "ShareKnot, '" + party.getTitle() + "' , the new party, is created";
				sendPartyCreatedEmail(party, account, contextMessage, emailSubject);
			}

			if (account.isPartyCreatedByWeb()) {
				String message = party.getShortDescription();
				NotificationType notificationType = NotificationType.PARTY_CREATED;
				createNotification(party, account, message, notificationType);
			}
		});
	}

	@EventListener
	public void handleStudyUpdateEvent(PartyUpdateEvent partyUpdateEvent) {
		Party party = partyRepository.findStudyWithManagersAndMemebersById(partyUpdateEvent.getParty().getId());
		Set<Account> accounts = new HashSet<>();
		accounts.addAll(party.getManagers());
		accounts.addAll(party.getMembers());

		accounts.forEach(account -> {
			if (account.isPartyUpdatedByEmail()) {
				String contextMessage = partyUpdateEvent.getMessage();
				String emailSubject = "ShareKnot, '" + party.getTitle() + "' have new messages.";
				sendPartyCreatedEmail(party, account, contextMessage, emailSubject);
			}

			if (account.isPartyUpdatedByWeb()) {
				createNotification(party, account, partyUpdateEvent.getMessage(), NotificationType.PARTY_UPDATED);
			}
		});
	}

	private void sendPartyCreatedEmail(Party party, Account account, String contextMessage, String emailSubject) {
		Context context = new Context();
		context.setVariable("nickname", account.getNickname());
		context.setVariable("link", "/party/" + party.getEncodedPath());
		context.setVariable("linkName", party.getTitle());
		context.setVariable("message", contextMessage);
		context.setVariable("host", appProperties.getHost());

		String message = templateEngine.process("email/simple-link", context);

		EmailMessage emailMessage = EmailMessage.builder().subject(emailSubject).to(account.getEmail()).message(message)
				.build();

		emailService.send(emailMessage);
	}

	private void createNotification(Party party, Account account, String message, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle(party.getTitle());
		notification.setLink("/party/" + party.getEncodedPath());
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setMessage(message);
		notification.setAccount(account);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}
}
