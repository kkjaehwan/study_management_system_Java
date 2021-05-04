package com.shareknot.modules.event.validator;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shareknot.modules.event.Event;
import com.shareknot.modules.event.form.EventForm;

@Component
public class EventValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EventForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		EventForm eventForm = (EventForm) target;

		if (isNotValidEndEnrolmentDateTime(eventForm)) {
			errors.rejectValue("endEnrolmentDateTime", "wrong.datetime",
					"Please enter the exact date and time of end of meeting reception.");
		}

		if (isNotValidEndDateTime(eventForm)) {
			errors.rejectValue("endDateTime", "wrong.datetime",
					"Please enter an exact date and time for the meeting to end.");
		}

		if (isNotValidStartDateTime(eventForm)) {
			errors.rejectValue("startDateTime", "wrong.datetime",
					"Please enter an exact date and time for the meeting to start.");
		}

	}

	private boolean isNotValidEndEnrolmentDateTime(EventForm eventForm) {
		return eventForm.getEndEnrolmentDateTime().isBefore(LocalDateTime.now());
	}

	private boolean isNotValidEndDateTime(EventForm eventForm) {
		LocalDateTime endDateTime = eventForm.getEndDateTime();
		return endDateTime.isBefore(eventForm.getStartDateTime())
				|| endDateTime.isBefore(eventForm.getEndEnrolmentDateTime());
	}

	private boolean isNotValidStartDateTime(EventForm eventForm) {
		return eventForm.getStartDateTime().isBefore(eventForm.getEndEnrolmentDateTime());
	}

	public void validateUpdateForm(@Valid EventForm eventForm, Event event, Errors errors) {
		if (eventForm.getLimitOfEnrolments() < event.getNumberOfAcceptedEnrolments()) {
            errors.rejectValue("limitOfEnrollments", "wrong.value", "The number of applicants must be larger than the confirmed application for patience.");
        }
	}

}
