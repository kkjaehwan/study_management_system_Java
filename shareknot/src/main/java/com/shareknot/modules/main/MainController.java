package com.shareknot.modules.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.event.EnrolmentRepository;
import com.shareknot.modules.party.Party;
import com.shareknot.modules.party.PartyRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final PartyRepository partyRepository;
	private final AccountRepository accountRepository;
	private final EnrolmentRepository enrolmentRepository;

	@GetMapping("/")
	public String home(@CurrentAccount Account account, Model model) {
		if (account != null) {
			Account accountLoaded = accountRepository.findAccountWithTagsAndZonesById(account.getId());
			model.addAttribute(accountLoaded);
			model.addAttribute("enrolmentList",
					enrolmentRepository.findByAccountAndAcceptedOrderByEnroledAtDesc(accountLoaded, true));
			model.addAttribute("partyList",
					partyRepository.findByAccount(accountLoaded.getTags(), accountLoaded.getZones()));
			model.addAttribute("partyManagerOf", partyRepository
					.findFirst5ByManagersContainingOrderByPublishedDateTimeDesc(account));
			model.addAttribute("partyMemberOf",
					partyRepository.findFirst5ByMembersContainingAndClosedOrderByPublishedDateTimeDesc(account, false));
			return "index-after-login";
		}
		model.addAttribute("partyList",
				partyRepository.findFirst9ByPublishedAndClosedOrderByPublishedDateTimeDesc(true, false));
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/ownner-profile")
	public String ownnerProfile() {
		return "ownner-profile";
	}

	@GetMapping("/search/party")
	public String searchParty(String keyword, Model model,
			@PageableDefault(size = 9, sort = "publishedDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Party> partyPage = partyRepository.findByKeyword(keyword, pageable);
		model.addAttribute("partyPage", partyPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortProperty",
				pageable.getSort().toString().contains("publishedDateTime") ? "publishedDateTime" : "memberCount");
		return "search";
	}
}
