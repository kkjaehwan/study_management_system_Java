package com.shareknot.modules.account;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shareknot.infra.config.AppProperties;
import com.shareknot.infra.email.EmailMessage;
import com.shareknot.infra.email.EmailService;
import com.shareknot.modules.account.form.Notifications;
import com.shareknot.modules.account.form.Profile;
import com.shareknot.modules.account.form.SignUpForm;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

//	private final String URL = "http://localhost:8080";
	private final AccountRepository accountRepository;

	private final EmailService emailService;
//	private final JavaMailSender javaMailSender;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
//	private final AuthenticationManager authenticationManager;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final AccountRoleRepository accountRoleRepository;

	public Account processNewAccount(SignUpForm signUpForm) {
		Account newAccount = saveNewAccount(signUpForm);

		sendSignUpConfirmEmail(newAccount);
		return newAccount;
	}

	private Account saveNewAccount(SignUpForm signUpForm) {
		signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));

		Account account = modelMapper.map(signUpForm, Account.class);

		AccountRole userRole = accountRoleRepository.findByName("ROLE_USER");
		account.setRoles(Stream.of(userRole).collect(Collectors.toSet()));

//		Account account = Account.builder()
//				.email(signUpForm.getEmail())
//				.nickname(signUpForm.getNickname())
//				.password(passwordEncoder.encode(signUpForm.getPassword()))
//				.partyCreatedByWeb(true)
//				.partyEnrolmentResultByWeb(true)
//				.partyUpdatedByWeb(true)
//				.build();

		Account newAccount = accountRepository.save(account);
		return newAccount;
	}

	public void sendSignUpConfirmEmail(Account account) {
		generateEmailCheckToken(account);

		Context context = new Context();

		context.setVariable("link", "/check-email-token?token=" + account.getEmailCheckToken()
				+ "&email=" + account.getEmail());
		context.setVariable("nickname", account.getNickname());
		log.info(account.getNickname());
		context.setVariable("linkName", "verify email");
		context.setVariable("message",
				"If you want to use the ShareKnot service, click on the link.");
		context.setVariable("host", appProperties.getHost());

		String message = templateEngine.process("email/simple-link", context);

		EmailMessage emailMessage = EmailMessage.builder()
				.to(account.getEmail())
				.subject("ShareKnot, Membership registration certification")
				.message(message)
				.build();
		emailService.send(emailMessage);

//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setTo(account.getEmail());
//		simpleMailMessage.setSubject("ShareKnot, Membership registration certification");
//		simpleMailMessage.setText(
//				URL+"/check-email-token?token=" + account.getEmailCheckToken() + "&email=" + account.getEmail());
//		javaMailSender.send(simpleMailMessage);
	}

	private void generateEmailCheckToken(Account account) {
		account.generateEmailCheckToken();
		accountRepository.save(account);
	}

	public void login(Account account) {
		UserAccount principal = new UserAccount(account);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, account.getPassword(), principal.getAuthorities());

//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//				principal, account.getPassword(),
//				List.of(new SimpleGrantedAuthority("ROLE_USER")));

//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//		Authentication authentication= authenticationManager.authenticate(token);
//		SecurityContext context = SecurityContextHolder.getContext();
//		context.setAuthentication(authentication);

		SecurityContextHolder.getContext()
				.setAuthentication(token);

	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(emailOrNickname);
		if (account == null) {
			account = accountRepository.findByNickname(emailOrNickname);
		}

		if (account == null) {
			throw new UsernameNotFoundException(emailOrNickname);
		}
		return new UserAccount(account);
	}

	public void completeSignUp(Account account) {
		account.completeSignUp();
		this.login(account);
	}

	public void updateProfile(Account account, @Valid Profile profile) {

		modelMapper.map(profile, account);
//		account.setUrl(profile.getUrl());
//		account.setLocation(profile.getLocation());
//		account.setOccupation(profile.getOccupation());
//		account.setBio(profile.getBio());
//		account.setProfileImage(profile.getProfileImage());

		accountRepository.save(account);
	}

	public void updatePassword(Account account, String newPassword) {
		account.setPassword(passwordEncoder.encode(newPassword));

		accountRepository.save(account);
	}

	public void updateNotifications(Account account, @Valid Notifications notifications) {

		modelMapper.map(notifications, account);
		accountRepository.save(account);

	}

	public void updateNickname(Account account, String nickname) {
		account.setNickname(nickname);

		accountRepository.save(account);
		login(account);
	}

	public void sendEmailLogin(Account account) {
		generateEmailLoginToken(account);

		Context context = new Context();

		context.setVariable("link", "/login-by-email?token=" + account.getEmailLoginToken()
				+ "&email=" + account.getEmail());
		context.setVariable("nickname", account.getNickname());
		context.setVariable("linkName", "Login by email");
		context.setVariable("message",
				"If you want to login the ShareKnot service, click on the link.");
		context.setVariable("host", appProperties.getHost());

		String message = templateEngine.process("email/simple-link", context);

		EmailMessage emailMessage = EmailMessage.builder()
				.to(account.getEmail())
				.subject("ShareKnot, You can login by email")
				.message(message)
				.build();
		emailService.send(emailMessage);

//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setTo(account.getEmail());
//		simpleMailMessage.setSubject("ShareKnot, You can login by email");
//		simpleMailMessage.setText(
//				URL + "/login-by-email?token=" + account.getEmailLoginToken() + "&email=" + account.getEmail());
//		javaMailSender.send(simpleMailMessage);
	}

	private void generateEmailLoginToken(Account account) {
		account.generateEmailLoginToken();
		accountRepository.save(account);
	}

	public void addTag(Account account, Tag tag) {
		Optional<Account> findById = accountRepository.findById(account.getId());
		findById.ifPresent(a -> a.getTags()
				.add(tag));
	}

	public Set<Tag> getTags(Account account) {
		Optional<Account> byId = accountRepository.findById(account.getId());

		return byId.orElseThrow()
				.getTags();
	}

	public void removeTag(Account account, Tag tag) {
		Optional<Account> findById = accountRepository.findById(account.getId());
		findById.ifPresent(a -> a.getTags()
				.remove(tag));
	}

	public void addZone(Account account, Zone zone) {
		Optional<Account> findById = accountRepository.findById(account.getId());
		findById.ifPresent(a -> a.getZones()
				.add(zone));
	}

	public Set<Zone> getZones(Account account) {
		Optional<Account> byId = accountRepository.findById(account.getId());

		return byId.orElseThrow()
				.getZones();
	}

	public void removeZone(Account account, Zone zone) {
		Optional<Account> findById = accountRepository.findById(account.getId());

		findById.ifPresent(a -> a.getZones()
				.remove(zone));
	}

}
