package com.shareknot.modules.event.form;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.shareknot.modules.event.EventType;

import lombok.Data;

@Data
public class EventForm {

	@NotBlank
	@Length(max = 50)
	private String title;

	private String description;

	private EventType eventType = EventType.FCFS;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endEnrolmentDateTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDateTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDateTime;

	@Min(2)
	private Integer limitOfEnrolments = 2;	                
}
