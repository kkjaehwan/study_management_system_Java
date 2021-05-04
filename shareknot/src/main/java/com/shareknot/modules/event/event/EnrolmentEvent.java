package com.shareknot.modules.event.event;

import com.shareknot.modules.event.Enrolment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EnrolmentEvent {
	protected final Enrolment enrolment;
	protected final String message;
}
