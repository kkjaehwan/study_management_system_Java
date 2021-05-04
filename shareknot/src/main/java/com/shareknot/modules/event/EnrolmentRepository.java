package com.shareknot.modules.event;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;

@Transactional(readOnly = true)
public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {
	boolean existsByEventAndAccount(Event event, Account account);

	Enrolment findByEventAndAccount(Event event, Account account);

	@EntityGraph("Enrolment.withEventAndStudy")
	List<Enrolment> findByAccountAndAcceptedOrderByEnroledAtDesc(Account account, boolean accepted);

	Enrolment findEnrolmentById(Long id);

}
