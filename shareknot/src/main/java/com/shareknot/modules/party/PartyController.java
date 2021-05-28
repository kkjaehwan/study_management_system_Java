package com.shareknot.modules.party;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.party.form.PartyForm;
import com.shareknot.modules.party.validator.PartyFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PartyController {

	private final PartyService partyService;
	private final ModelMapper modelMapper;
	private final PartyFormValidator partyFormValidator;
	private final PartyRepository partyRepository;

	@InitBinder("partyForm")
	public void partyFormInitBinder(WebDataBinder webDatabinder) {
		webDatabinder.addValidators(partyFormValidator);
	}

	@GetMapping("/new-party")
	public String newPartyForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new PartyForm());
		return "party/form";
	}

	@PostMapping("/new-party")
	public String newPartySubmit(@CurrentAccount Account account, @Valid PartyForm partyForm,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute(account);
			return "party/form";
		}
		Party party = partyService.createNewParty(modelMapper.map(partyForm, Party.class), account);
		return "redirect:/party/" + URLEncoder.encode(party.getPath(), StandardCharsets.UTF_8);
	}

	@GetMapping("/party")
	public String viewParties(@CurrentAccount Account account, Model model) {
		model.addAttribute("partyManagerOf",
				partyRepository.findAllByManagersContainingOrderByPublishedDateTimeDesc(account));
		model.addAttribute("partyMemberOf", partyRepository
				.findAllByMembersContainingAndClosedOrderByPublishedDateTimeDesc(account, false));
		return "account/party-lists";
	}

	@GetMapping("/party/{path}")
	public String viewParty(@CurrentAccount Account account, @PathVariable String path,
			Model model) {
		Party party = partyService.getParty(path);
		model.addAttribute(account);
		model.addAttribute(party);
		return "party/view";
	}

	@GetMapping("/party/{path}/members")
	public String viewPartyMembers(@CurrentAccount Account account, @PathVariable String path,
			Model model) {
		model.addAttribute(account);
		model.addAttribute(partyRepository.findByPath(path));
		return "party/members";
	}

	@PostMapping("/party/{path}/join")
	public String joinParty(@CurrentAccount Account account, @PathVariable String path,
			Model model) {
		Party party = partyRepository.getPartyWithMemberByPath(path);
		partyService.addMember(party, account);

		return "redirect:/party/" + URLEncoder.encode(party.getPath(), StandardCharsets.UTF_8);
	}

	@PostMapping("/party/{path}/leave")
	public String leaveParty(@CurrentAccount Account account, @PathVariable String path,
			Model model) {
		Party party = partyRepository.getPartyWithMemberByPath(path);

		partyService.removeMember(party, account);
		return "redirect:/party/" + URLEncoder.encode(party.getPath(), StandardCharsets.UTF_8);
	}
}
