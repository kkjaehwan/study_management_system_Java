package com.shareknot.modules.event.event;

import com.shareknot.modules.event.Enrolment;

public class EnrolmentRejectedEvent extends EnrolmentEvent {

	public EnrolmentRejectedEvent(Enrolment enrolment) {
		super(enrolment, "Your application has been rejected for the meeting.");
	}

}
