package com.shareknot.modules.event;

import java.time.LocalDateTime;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.event.event.EnrolmentAcceptedEvent;
import com.shareknot.modules.event.event.EnrolmentRejectedEvent;
import com.shareknot.modules.event.form.EventForm;
import com.shareknot.modules.party.Party;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;
	private final EnrolmentRepository enrolmentRepository;
	private final ApplicationEventPublisher enrolmentPublisher;

	public Event createEvent(Event event, Party party, Account account) {
		event.setCreatedBy(account);
		event.setCreatedDateTime(LocalDateTime.now());
		event.setParty(party);
		return eventRepository.save(event);
	}

	public void updateEvent(Event event, @Valid EventForm eventForm) {
		modelMapper.map(eventForm, event);
		event.acceptWaitingList();
	}

	public void deleteEvent(Event event) {
		eventRepository.delete(event);
	}

	public void newEnrolment(Event event, Account account) {
		if (!enrolmentRepository.existsByEventAndAccount(event, account)) {
			Enrolment enrolment = new Enrolment();
			enrolment.setEnroledAt(LocalDateTime.now());
			enrolment.setAccepted(event.isAbleToAcceptWaitingEnrolment());
			enrolment.setAccount(account);
			event.addEnrolment(enrolment);
			enrolmentRepository.save(enrolment);
		}

	}

	public void cancelEnrolment(Event event, Account account) {
		Enrolment enrolment = enrolmentRepository.findByEventAndAccount(event, account);
		if (!enrolment.isAttended()) {
			event.removeEnrolment(enrolment);
			enrolmentRepository.delete(enrolment);
			event.acceptNextWaitingEnrolment();
		}
	}

	public void acceptEnrolment(Event event, Enrolment enrolment) {
		event.accept(enrolment);
		enrolmentPublisher.publishEvent(new EnrolmentAcceptedEvent(enrolment));

	}

    public void rejectEnrolment(Event event, Enrolment enrolment) {
        event.reject(enrolment);
		enrolmentPublisher.publishEvent(new EnrolmentRejectedEvent(enrolment));
    }

    public void checkInEnrolment(Enrolment enrolment) {
    	enrolment.setAttended(true);
    }

    public void cancelCheckInEnrolment(Enrolment enrolment) {
    	enrolment.setAttended(false);
    }

}
