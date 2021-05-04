package com.shareknot.modules.account;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shareknot.modules.account.form.SignUpForm;
import com.shareknot.modules.account.validator.SignUpFormVaildator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final SignUpFormVaildator SignUpFormVaildator;
	private final AccountService accountService;
	private final AccountRepository accountRepository;

	@InitBinder("signUpForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(SignUpFormVaildator);
	}

	@GetMapping("/sign-up")
	public String signUpForm(Model model) {
		model.addAttribute("signUpForm", new SignUpForm());
		return "account/sign-up";
	}

	@PostMapping("/sign-up")
	public String signSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
		if (errors.hasErrors()) {
			return "account/sign-up";
		}

		Account account = accountService.processNewAccount(signUpForm);
		accountService.login(account);
		return "redirect:/";

	}

	@GetMapping("/check-email-token")
	public String checkEmailTocken(String token, String email, Model model) {

		Account account = accountRepository.findByEmail(email);
		String view = "account/checked-email";

		if (account == null) {
			model.addAttribute("error", "wrong.email");
			return view;
		}
		if (!account.isValidCheckToken(token)) {
			model.addAttribute("error", "wrong.email");
			return view;
		}

		accountService.completeSignUp(account);

		model.addAttribute("numberOfUser", accountRepository.count());
		model.addAttribute("nickname", account.getNickname());

		return view;

	}

	@GetMapping("/check-email")
	public String checkEmail(@CurrentAccount Account account, Model model) {

		String view = "account/check-email";
		model.addAttribute("email", account.getEmail());

		return view;

	}

	@GetMapping("/resend-confirm-email")
	public String resendConfirmEmail(@CurrentAccount Account account, Model model, RedirectAttributes attributes) {

		String view = "redirect:/";
		if (!account.canSendConfirmEmail()) {
			model.addAttribute("error", "Resenting a confirmation email is possible once an hour.");
			model.addAttribute("email", account.getEmail());
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			String formatDateTime = account.getEmailCheckTokenGenerateAt().format(format);
//			model.addAttribute("emailCheckTokenGenerateAt", formatDateTime);
			model.addAttribute("emailCheckTokenGenerateAt", account.getEmailCheckTokenGenerateAt());
			view = "account/check-email";
		} else {
			accountService.sendSignUpConfirmEmail(account);
			attributes.addFlashAttribute("message", "A confirmation email is resented. Please check your email.");
		}

		return view;

	}

	@GetMapping("/profile/{nickname}")
	public String viewProfile(@PathVariable String nickname, Model model, @CurrentAccount Account account)
			throws IllegalAccessException {
		Account findByNickname = accountRepository.findByNickname(nickname);

		if (findByNickname == null) {
			throw new IllegalAccessException(nickname + "is not found.");
		}
		model.addAttribute("account", findByNickname);
		model.addAttribute("isOwner", findByNickname.equals(account));

		return "account/profile";
	}

	@GetMapping("/email-login")
	public String emailLogin(Model model) {
		return "account/email-login";
	}

	@PostMapping("/email-login")
	public String sendEmailLogin(String email, Model model, RedirectAttributes attributes) {

		Account account = accountRepository.findByEmail(email);

		if (account == null) {
			model.addAttribute("error", "The email is not valid.");
			return "account/email-login";
		}
		if (!account.canSendEmailLogin()) {
			model.addAttribute("error", "You can request once an hour to log-in by email. Please check your email.");
			model.addAttribute("email", account.getEmail());
			model.addAttribute("emailLoginTokenGenerateAt", account.getEmailLoginTokenGenerateAt());
			return "account/email-login";
		}

		accountService.sendEmailLogin(account);
		attributes.addFlashAttribute("message", "An email with a login link has been sent. Please check your email.");

		return "redirect:/email-login";

	}

	@GetMapping("/login-by-email")
	public String loginByEmail(String token, String email, Model model, RedirectAttributes attributes) {

		Account account = accountRepository.findByEmail(email);
		String view = "account/email-login";

		if (account == null) {
			model.addAttribute("error", "wrong.email");
			return view;
		}
		if (!account.isValidLoginToken(token)) {
			model.addAttribute("error", "wrong.email");
			return view;
		}
		attributes.addFlashAttribute("message", "You have logged in by email. Please change your password.");
		accountService.login(account);
		return "redirect:/" + SettingsController.SETTINGS+SettingsController.PASSWORD;

	}
}
