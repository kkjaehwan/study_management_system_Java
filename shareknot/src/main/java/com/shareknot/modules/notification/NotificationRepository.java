package com.shareknot.modules.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	long countByAccountAndChecked(Account account, boolean b);

	List<Notification> findByAccountAndCheckedOrderByCreatedDateTimeDesc(Account account, boolean b);

	@Transactional
	void deleteByAccountAndChecked(Account account, boolean b);

}
