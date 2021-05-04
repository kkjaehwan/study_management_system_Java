package com.shareknot.modules.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationRepository notificationRepository;

	private final NotificationService notificationService;

	@GetMapping("/notifications")
	public String getNotifications(@CurrentAccount Account account, Model model) {
		List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);
		
		long numberOfChecked = notificationRepository.countByAccountAndChecked(account, true);
		
		putCategorizedNotifications(model, notifications, numberOfChecked, notifications.size());
		
		model.addAttribute("isNew", true);
		notificationService.markAsRead(notifications);
		return "notification/list";
	}

	@GetMapping("/notifications/old")
	public String getOldNotifications(@CurrentAccount Account account, Model model) {
		List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
		long numberOfNotChecked = notificationRepository.countByAccountAndChecked(account, false);
		putCategorizedNotifications(model, notifications, notifications.size(), numberOfNotChecked);
		model.addAttribute("isNew", false);
		return "notification/list";
	}

	@DeleteMapping("/notifications")
	public String deleteNotifications(@CurrentAccount Account account) {
		notificationRepository.deleteByAccountAndChecked(account, true);
		return "redirect:/notifications";
	}

	private void putCategorizedNotifications(Model model, List<Notification> notifications, long numberOfChecked,
			long numberOfNotChecked) {
		List<Notification> newPartyNotifications = new ArrayList<>();
		List<Notification> eventEnrollmentNotifications = new ArrayList<>();
		List<Notification> watchingPartyNotifications = new ArrayList<>();
		
		for (var notification : notifications) {
			switch (notification.getNotificationType()) {
			case PARTY_CREATED:
				newPartyNotifications.add(notification);
				break;
			case EVENT_ENROLLMENT:
				eventEnrollmentNotifications.add(notification);
				break;
			case PARTY_UPDATED:
				watchingPartyNotifications.add(notification);
				break;
			}
		}

		model.addAttribute("numberOfNotChecked", numberOfNotChecked);
		model.addAttribute("numberOfChecked", numberOfChecked);
		model.addAttribute("notifications", notifications);
		model.addAttribute("newPartyNotifications", newPartyNotifications);
		model.addAttribute("eventEnrollmentNotifications", eventEnrollmentNotifications);
		model.addAttribute("watchingPartyNotifications", watchingPartyNotifications);
	}

}
