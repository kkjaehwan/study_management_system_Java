package com.shareknot.modules.account;

import static com.shareknot.modules.account.SettingsController.ROOT;
import static com.shareknot.modules.account.SettingsController.SETTINGS;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareknot.modules.account.form.NicknameForm;
import com.shareknot.modules.account.form.Notifications;
import com.shareknot.modules.account.form.PasswordForm;
import com.shareknot.modules.account.form.Profile;
import com.shareknot.modules.account.form.TagForm;
import com.shareknot.modules.account.form.ZoneForm;
import com.shareknot.modules.account.validator.NicknameFormValidator;
import com.shareknot.modules.account.validator.PasswordFormValidator;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.tag.TagRepository;
import com.shareknot.modules.zone.Zone;
import com.shareknot.modules.zone.ZoneRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(ROOT + SETTINGS)
@RequiredArgsConstructor
public class SettingsController {

	public static final String ROOT = "/";
	public static final String SETTINGS = "settings";
	public static final String PASSWORD = "/password";
	public static final String NOTIFICATIONS = "/notifications";
	public static final String PROFILE = "/profile";
	public static final String ACCOUNT = "/account";
	public static final String TAGS = "/tags";
	public static final String ZONES = "/zones";

	private final AccountService accountService;
	private final ModelMapper modelMapper;
	private final NicknameFormValidator nicknameFormValidator;
	private final TagRepository tagRepository;
	private final ZoneRepository zoneRepository;
	private final ObjectMapper objectMapper;

	@InitBinder("passwordForm")
	public void initPasswordFormBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PasswordFormValidator());
	}

	@InitBinder("nicknameForm")
	public void initNicknameFormBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(nicknameFormValidator);
	}

	@GetMapping(PROFILE)
	public String updateProfileForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
//		model.addAttribute(new Profile(account));
		model.addAttribute(modelMapper.map(account, Profile.class));
		return SETTINGS + PROFILE;
	}

	@PostMapping(PROFILE)
	public String updateProfileForm(@CurrentAccount Account account, @Valid @ModelAttribute Profile profile,
			Errors errors, Model model, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + PROFILE;
		}

		accountService.updateProfile(account, profile);
		attributes.addFlashAttribute("message", "Profile is updated.");

		return "redirect:" + ROOT + SETTINGS + PROFILE;
	}

	@GetMapping(PASSWORD)
	public String updatePasswordForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new PasswordForm());
		return SETTINGS + PASSWORD;
	}

	@PostMapping(PASSWORD)
	public String updatePasswordForm(@CurrentAccount Account account, @Valid @ModelAttribute PasswordForm passwordForm,
			Errors errors, Model model, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + PASSWORD;
		}

		accountService.updatePassword(account, passwordForm.getNewPassword());
		attributes.addFlashAttribute("message", "Password is changed.");

		return "redirect:" + ROOT + SETTINGS + PASSWORD;
	}

	@GetMapping(NOTIFICATIONS)
	public String updateNotificationsForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
//		model.addAttribute(new Notifications(account));
		model.addAttribute(modelMapper.map(account, Notifications.class));
		return SETTINGS + NOTIFICATIONS;
	}

	@PostMapping(NOTIFICATIONS)
	public String updateNotifications(@CurrentAccount Account account,
			@Valid @ModelAttribute Notifications notifications, Errors errors, Model model,
			RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + NOTIFICATIONS;
		}

		accountService.updateNotifications(account, notifications);
		attributes.addFlashAttribute("message", "Notifications Setting is changed.");

		return "redirect:" + ROOT + SETTINGS + NOTIFICATIONS;
	}

	@GetMapping(ACCOUNT)
	public String updateAccountForm(@CurrentAccount Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(modelMapper.map(account, NicknameForm.class));

		return SETTINGS + ACCOUNT;
	}

	@PostMapping(ACCOUNT)
	public String updateAccount(@CurrentAccount Account account, @Valid @ModelAttribute NicknameForm nickNameForm,
			Errors errors, Model model, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + ACCOUNT;
		}

		accountService.updateNickname(account, nickNameForm.getNickname());
		attributes.addFlashAttribute("message", "Nickname is changed.");

		return "redirect:" + ROOT + SETTINGS + ACCOUNT;
	}

	@GetMapping(TAGS)
	public String updateTags(@CurrentAccount Account account, Model model) throws JsonProcessingException {
		model.addAttribute(account);

		Set<Tag> tags = accountService.getTags(account);

		model.addAttribute("tags", tags.stream().map(Tag::getTitle).collect(Collectors.toList()));

		List<String> allTags = tagRepository.findAll().stream().map(Tag::getTitle).collect(Collectors.toList());
		model.addAttribute("whitelist", objectMapper.writeValueAsString(allTags));

		return SETTINGS + TAGS;
	}

	@PostMapping(TAGS + "/add")
	@ResponseBody
	public ResponseEntity addTag(@CurrentAccount Account account, @RequestBody TagForm tagForm) {
		String title = tagForm.getTagTitle();

		Tag tag = tagRepository.findByTitle(title);
		if (tag == null) {
			tag = tagRepository.save(Tag.builder().title(title).build());
		}

		accountService.addTag(account, tag);

		return ResponseEntity.ok().build();
	}

	@PostMapping(TAGS + "/remove")
	@ResponseBody
	public ResponseEntity removeTag(@CurrentAccount Account account, @RequestBody TagForm tagForm) {
		String title = tagForm.getTagTitle();

		Tag tag = tagRepository.findByTitle(title);
		if (tag == null) {
			return ResponseEntity.badRequest().build();
		}

		accountService.removeTag(account, tag);

		return ResponseEntity.ok().build();
	}

	@GetMapping(ZONES)
	public String updateZones(@CurrentAccount Account account, Model model) throws JsonProcessingException {
		model.addAttribute(account);

		Set<Zone> zones = accountService.getZones(account);

		model.addAttribute("zones", zones.stream().map(Zone::toString).collect(Collectors.toList()));

		List<String> allZones = zoneRepository.findAll().stream().map(Zone::toString).collect(Collectors.toList());
		model.addAttribute("whitelist", objectMapper.writeValueAsString(allZones));

		return SETTINGS + ZONES;
	}

	@PostMapping(ZONES + "/add")
	@ResponseBody
	public ResponseEntity addZones(@CurrentAccount Account account, @RequestBody ZoneForm zoneForm) {

		Zone zone = zoneRepository.findByCityAndProvinceAndCountry(zoneForm.getCityName(), zoneForm.getProvinceName(),
				zoneForm.getCountryName());
		if (zone == null) {
			return ResponseEntity.badRequest().build();
		}

		accountService.addZone(account, zone);
		return ResponseEntity.ok().build();
	}

	@PostMapping(ZONES + "/remove")
	@ResponseBody
	public ResponseEntity removeZones(@CurrentAccount Account account, @RequestBody ZoneForm zoneForm) {
		
		Zone zone = zoneRepository.findByCityAndProvinceAndCountry(zoneForm.getCityName(), zoneForm.getProvinceName(),
				zoneForm.getCountryName());
		if (zone == null) {
			return ResponseEntity.badRequest().build();
		}

		accountService.removeZone(account, zone);

		return ResponseEntity.ok().build();
	}
}
