package com.shareknot.modules.party;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.CurrentAccount;
import com.shareknot.modules.account.form.Profile;
import com.shareknot.modules.account.form.TagForm;
import com.shareknot.modules.account.form.ZoneForm;
import com.shareknot.modules.party.form.PartyDescriptionForm;
import com.shareknot.modules.party.form.PartyPathForm;
import com.shareknot.modules.party.form.PartyTitleForm;
import com.shareknot.modules.party.validator.PartyPathFormValidator;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.tag.TagRepository;
import com.shareknot.modules.tag.TagService;
import com.shareknot.modules.zone.Zone;
import com.shareknot.modules.zone.ZoneRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/party/{path}/settings")
@RequiredArgsConstructor
public class PartySettingsController {

	private final PartyService partyService;
	private final ModelMapper modelMapper;
	private final PartyRepository partyRepository;
	private final TagRepository tagRepository;
	private final ObjectMapper objectMapper;
	private final TagService tagService;
	private final ZoneRepository zoneRepository;
	private final PartyPathFormValidator partyPathFormValidator;

	@InitBinder("partyPathForm")
	public void partyFormInitBinder(WebDataBinder webDatabinder) {
		webDatabinder.addValidators(partyPathFormValidator);
	}

	@GetMapping("/description")
	public String viewParty(@CurrentAccount Account account, @PathVariable String path, Model model) {
		Party party = partyService.getPartyToUpdate(account, path);
		model.addAttribute(account);
		model.addAttribute(partyRepository.findByPath(path));
		model.addAttribute(modelMapper.map(party, PartyDescriptionForm.class));
		return "party/settings/description";
	}

	@PostMapping("/description")
	public String viewParty(@CurrentAccount Account account, @PathVariable String path,
			@Valid PartyDescriptionForm partyDescriptionForm, Errors errors, Model model,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdate(account, path);

		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(party);
			return "party/settings/description";
		}

		partyService.updatePartyDescription(party, partyDescriptionForm);
		attributes.addFlashAttribute("message", "You have modified the party introduction.");
		return "redirect:/party/" + party.getEncodedPath() + "/settings/description";
	}

	@GetMapping("/banner")
	public String viewBanner(@CurrentAccount Account account, @PathVariable String path, Model model) {
		Party party = partyService.getPartyToUpdate(account, path);
		model.addAttribute(account);
		model.addAttribute(partyRepository.findByPath(path));
		return "party/settings/banner";
	}

	@PostMapping("/banner")
	public String setBanner(@CurrentAccount Account account, @PathVariable String path, String image,
			RedirectAttributes attributes) {
		Party party = partyService.getPartyToUpdate(account, path);

		partyService.updatePartyImage(party, image);
		attributes.addFlashAttribute("message", "You have modified the party Banner Image.");
		return "redirect:/party/" + party.getEncodedPath() + "/settings/banner";
	}

	@PostMapping("/banner/enable")
	public String enableBanner(@CurrentAccount Account account, @PathVariable String path,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdate(account, path);
		partyService.enableBanner(party);
		attributes.addFlashAttribute("message", "You have modified the banner setting.");
		return "redirect:/party/" + party.getEncodedPath() + "/settings/banner";
	}

	@PostMapping("/banner/disable")
	public String disableBanner(@CurrentAccount Account account, @PathVariable String path,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdate(account, path);
		partyService.disableBanner(party);
		attributes.addFlashAttribute("message", "You have modified the banner setting.");
		return "redirect:/party/" + party.getEncodedPath() + "/settings/banner";
	}

	@GetMapping("/tags")
	public String updateTags(@CurrentAccount Account account, @PathVariable String path, Model model)
			throws JsonProcessingException {

		Party party = partyService.getPartyToUpdate(account, path);

		model.addAttribute(account);
		model.addAttribute(party);

		model.addAttribute("tags", party.getTags().stream().map(Tag::getTitle).collect(Collectors.toList()));

		List<String> allTags = tagRepository.findAll().stream().map(Tag::getTitle).collect(Collectors.toList());
		model.addAttribute("whitelist", objectMapper.writeValueAsString(allTags));

		return "party/settings/tags";
	}

	@PostMapping("/tags/add")
	@ResponseBody
	public ResponseEntity addTag(@CurrentAccount Account account, @PathVariable String path,
			@RequestBody TagForm tagForm) {
		Party party = partyService.getPartyToUpdateTag(account, path);

		String title = tagForm.getTagTitle();

		Tag tag = tagService.findOrCreateNew(title);

		partyService.addTag(party, tag);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/tags/remove")
	@ResponseBody
	public ResponseEntity removeTag(@CurrentAccount Account account, @PathVariable String path,
			@RequestBody TagForm tagForm) {
		Party party = partyService.getPartyToUpdateTag(account, path);

		String title = tagForm.getTagTitle();

		Tag tag = tagService.findOrCreateNew(title);

		partyService.removeTag(party, tag);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/zones")
	public String updateZones(@CurrentAccount Account account, @PathVariable String path, Model model)
			throws JsonProcessingException {

		Party party = partyService.getPartyToUpdate(account, path);

		model.addAttribute(account);
		model.addAttribute(party);

		Set<Zone> zones = party.getZones();

		model.addAttribute("zones", zones.stream().map(Zone::toString).collect(Collectors.toList()));

		List<String> allZones = zoneRepository.findAll().stream().map(Zone::toString).collect(Collectors.toList());
		model.addAttribute("whitelist", objectMapper.writeValueAsString(allZones));

		return "party/settings/zones";
	}

	@PostMapping("/zones/add")
	@ResponseBody
	public ResponseEntity addZones(@CurrentAccount Account account, @PathVariable String path,
			@RequestBody ZoneForm zoneForm) {
		Party party = partyService.getPartyToUpdateZone(account, path);

		Zone zone = zoneRepository.findByCityAndProvinceAndCountry(zoneForm.getCityName(), zoneForm.getProvinceName(),
				zoneForm.getCountryName());

		if (zone == null) {
			return ResponseEntity.badRequest().build();
		}

		partyService.addZone(party, zone);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/zones/remove")
	@ResponseBody
	public ResponseEntity removeZones(@CurrentAccount Account account, @PathVariable String path,
			@RequestBody ZoneForm zoneForm) {

		Party party = partyService.getPartyToUpdateZone(account, path);

		Zone zone = zoneRepository.findByCityAndProvinceAndCountry(zoneForm.getCityName(), zoneForm.getProvinceName(),
				zoneForm.getCountryName());
		if (zone == null) {
			return ResponseEntity.badRequest().build();
		}

		partyService.removeZone(party, zone);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/party")
	public String updateParty(@CurrentAccount Account account, @PathVariable String path, Model model) {

		Party party = partyService.getPartyToUpdate(account, path);

		model.addAttribute(account);
		model.addAttribute(party);

		model.addAttribute(modelMapper.map(party, PartyPathForm.class));
		model.addAttribute(modelMapper.map(party, PartyTitleForm.class));
		
		return "party/settings/party";
	}

	@PostMapping("/search/allow")
	public String allowSearch(@CurrentAccount Account account, @PathVariable String path, Model model,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);
		partyService.allowSearch(party);

		attributes.addFlashAttribute("message", "You have modified the serch option");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/search/deny")
	public String denySearch(@CurrentAccount Account account, @PathVariable String path, Model model,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);
		partyService.denySearch(party);

		attributes.addFlashAttribute("message", "You have modified the search option");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/recruit/allow")
	public String allowRecruit(@CurrentAccount Account account, @PathVariable String path, Model model,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);
		partyService.allowRecruit(party);

		attributes.addFlashAttribute("message", "You have modified the recruting option");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/recruit/deny")
	public String denyRecruit(@CurrentAccount Account account, @PathVariable String path, Model model,
			RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);
		partyService.denyRecruit(party);

		attributes.addFlashAttribute("message", "You have modified the recruting option");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/path/update")
	public String updatePath(@CurrentAccount Account account, @PathVariable String path,
			@Valid PartyPathForm partyPathForm, Errors errors, Model model, RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);

		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(party);
			model.addAttribute(modelMapper.map(party, PartyTitleForm.class));
			return "party/settings/party";
		}
		partyService.updatePath(party, partyPathForm.getPath());
		attributes.addFlashAttribute("message", "You have modified the party path");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/title/update")
	public String updateTitle(@CurrentAccount Account account, @PathVariable String path,
			@Valid PartyTitleForm partyTitleForm, Errors errors, Model model, RedirectAttributes attributes) {

		Party party = partyService.getPartyToUpdateStatus(account, path);
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(party);
			model.addAttribute(modelMapper.map(party, PartyPathForm.class));
			return "party/settings/party";
		}
		partyService.updateTitle(party, partyTitleForm.getTitle());
		attributes.addFlashAttribute("message", "You have modified the party title");

		return "redirect:/party/" + party.getEncodedPath() + "/settings/party";
	}

	@PostMapping("/party/remove")
	public String removeParty(@CurrentAccount Account account, @PathVariable String path, Model model) {
		Party party = partyService.getPartyToUpdateStatus(account, path);
		partyService.remove(party);
		return "redirect:/";
	}

}
