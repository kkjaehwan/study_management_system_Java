package com.shareknot.modules.event.event;

import com.shareknot.modules.event.Enrolment;

public class EnrolmentAcceptedEvent extends EnrolmentEvent {

	public EnrolmentAcceptedEvent(Enrolment enrolment) {
		super(enrolment, "Your application has been accepted for the meeting. Please attend the meeting.");
	}

}
