package com.shareknot.modules.event.event;

import java.time.LocalDateTime;

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
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.event.Enrolment;
import com.shareknot.modules.event.EnrolmentRepository;
import com.shareknot.modules.event.Event;
import com.shareknot.modules.notification.Notification;
import com.shareknot.modules.notification.NotificationRepository;
import com.shareknot.modules.notification.NotificationType;
import com.shareknot.modules.party.Party;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class EnrolmentEventListener {

	private final EmailService emailService;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleEnrolmentAcceptedEvent(EnrolmentEvent enrolmentEvent) {
		Enrolment enrolment = enrolmentEvent.getEnrolment();
		String message = enrolmentEvent.getMessage();
		Account account = enrolment.getAccount();
		Event event = enrolment.getEvent();
		Party party = event.getParty();

		if (account.isPartyCreatedByEmail()) {
			String emailSubject = "ShareKnot, '" + event.getTitle() + "' , the result of application of meeting";
			sendEmail(enrolmentEvent, party, event, account, emailSubject);
		}

		if (account.isPartyCreatedByWeb()) {
			createNotification(enrolmentEvent, party, event, account, message);
		}
	}

	private void sendEmail(EnrolmentEvent enrolmentEvent, Party party, Event event, Account account,
			String emailSubject) {
		Context context = new Context();
		context.setVariable("nickname", account.getNickname());
		context.setVariable("link", "/party/" + party.getEncodedPath() + "/events/" + event.getId());
		context.setVariable("linkName", party.getTitle());
		context.setVariable("message", enrolmentEvent.getMessage());
		context.setVariable("host", appProperties.getHost());

		String message = templateEngine.process("email/simple-link", context);

		EmailMessage emailMessage = EmailMessage.builder().subject(emailSubject).to(account.getEmail()).message(message)
				.build();

		emailService.send(emailMessage);
	}

	private void createNotification(EnrolmentEvent enrolmentEvent, Party party, Event event, Account account,
			String message) {
		Notification notification = new Notification();
		notification.setTitle(party.getTitle());
		notification.setLink("/party/" + party.getEncodedPath() + "/events/" + event.getId());
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setMessage(message);
		notification.setAccount(account);
		notification.setNotificationType(NotificationType.EVENT_ENROLLMENT);
		notificationRepository.save(notification);
	}
}
