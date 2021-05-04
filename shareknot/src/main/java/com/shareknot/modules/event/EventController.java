package com.shareknot.modules.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.event.form.EventForm;
import com.shareknot.modules.event.validator.EventValidator;
import com.shareknot.modules.party.Party;
import com.shareknot.modules.party.PartyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/party/{path}")
@RequiredArgsConstructor
public class EventController {

	private final PartyService partyService;
	private final EventService eventService;
	private final ModelMapper modelMapper;
	private final EventValidator eventValidator;
	private final EventRepository eventRepository;

	@InitBinder("eventForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(eventValidator);
	}

	@GetMapping("/new-event")
	public String newEventForm(@CurrentAccount Account account, @PathVariable String path, Model model) {

		Party party = partyService.getPartyToUpdateStatus(account, path);

		model.addAttribute(party);
		model.addAttribute(account);
		model.addAttribute(new EventForm());

		return "event/form";
	}

	@PostMapping("/new-event")
	public String newEventSubmit(@CurrentAccount Account account, @PathVariable String path, @Valid EventForm eventForm,
			Errors errors, Model model) {

		Party party = partyService.getPartyToUpdateStatus(account, path);

		if (errors.hasErrors()) {
			model.addAttribute(party);
			model.addAttribute(account);
			return "event/form";
		}

		Event event = eventService.createEvent(modelMapper.map(eventForm, Event.class), party, account);

		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("/events/{id}")
	public String getEvent(@CurrentAccount Account account, @PathVariable String path, @PathVariable Long id,
			Model model) {

		model.addAttribute(account);
		model.addAttribute(eventRepository.findById(id).orElseThrow());
		model.addAttribute(partyService.getParty(path));

		return "event/view";
	}

	@GetMapping("/events/{id}/edit")
	public String updateEventForm(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("id") Event event, Model model) {
		Party party = partyService.getPartyToUpdate(account, path);
		model.addAttribute(party);
		model.addAttribute(account);
		model.addAttribute(event);
		model.addAttribute(modelMapper.map(event, EventForm.class));
		return "event/update-form";
	}

	@PostMapping("/events/{id}/edit")
	public String updateEventSubmit(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("id") Event event, @Valid EventForm eventForm, Errors errors, Model model) {
		Party party = partyService.getPartyToUpdate(account, path);
		eventForm.setEventType(event.getEventType());
		eventValidator.validateUpdateForm(eventForm, event, errors);

		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(party);
			model.addAttribute(event);
			return "event/update-form";
		}

		eventService.updateEvent(event, eventForm);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("/events")
	public String viewPartyEvents(@CurrentAccount Account account, @PathVariable String path, Model model) {
		Party study = partyService.getParty(path);
		model.addAttribute(account);
		model.addAttribute(study);

		List<Event> events = eventRepository.findByPartyOrderByStartDateTime(study);
		List<Event> newEvents = new ArrayList<>();
		List<Event> oldEvents = new ArrayList<>();
		events.forEach(e -> {
			if (e.getEndDateTime().isBefore(LocalDateTime.now())) {
				oldEvents.add(e);
			} else {
				newEvents.add(e);
			}
		});

		model.addAttribute("newEvents", newEvents);
		model.addAttribute("oldEvents", oldEvents);

		return "party/events";
	}

	@DeleteMapping("/events/{id}")
	public String cancelEvent(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("id") Event event) {
		Party party = partyService.getPartyToUpdateStatus(account, path);
		eventService.deleteEvent(event);
		return "redirect:/party/" + party.getEncodedPath() + "/events";
	}

	@PostMapping("/events/{id}/enrol")
	public String newEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("id") Event event) {
		Party party = partyService.getPartyToEnrol(path);
		eventService.newEnrolment(event, account);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@PostMapping("/events/{id}/disenrol")
	public String cancelEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("id") Event event) {
		Party party = partyService.getPartyToEnrol(path);
		eventService.cancelEnrolment(event, account);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("events/{eventId}/enrolments/{enrolmentId}/accept")
	public String acceptEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("eventId") Event event, @PathVariable("enrolmentId") Enrolment enrolment) {
		Party party = partyService.getPartyToUpdate(account, path);
		eventService.acceptEnrolment(event, enrolment);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("/events/{eventId}/enrolments/{enrolmentId}/reject")
	public String rejectEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("eventId") Event event, @PathVariable("enrolmentId") Enrolment enrolment) {
		Party party = partyService.getPartyToUpdate(account, path);
		eventService.rejectEnrolment(event, enrolment);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("/events/{eventId}/enrolments/{enrolmentId}/checkin")
	public String checkInEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("eventId") Event event, @PathVariable("enrolmentId") Enrolment enrolment) {
		Party party = partyService.getPartyToUpdate(account, path);
		eventService.checkInEnrolment(enrolment);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

	@GetMapping("/events/{eventId}/enrolments/{enrolmentId}/cancel-checkin")
	public String cancelCheckInEnrolment(@CurrentAccount Account account, @PathVariable String path,
			@PathVariable("eventId") Event event, @PathVariable("enrolmentId") Enrolment enrolment) {
		Party party = partyService.getPartyToUpdate(account, path);
		eventService.cancelCheckInEnrolment(enrolment);
		return "redirect:/party/" + party.getEncodedPath() + "/events/" + event.getId();
	}

}
