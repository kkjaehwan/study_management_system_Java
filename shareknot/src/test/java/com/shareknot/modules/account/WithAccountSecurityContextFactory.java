package com.shareknot.modules.account;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.shareknot.modules.account.form.SignUpForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

	private final AccountService accountService;
	private final AccountRepository accountRepository;

	private final AccountRoleRepository accountRoleRepository;

	@Override
	public SecurityContext createSecurityContext(WithAccount withAccount) {
		AccountRole roleAdmin = createRole("ROLE_ADMIN");
		AccountRole roleUser = createRole("ROLE_USER");
		createAccount("shareknot", roleAdmin);
		createAccount("kjaehwan89", roleUser);

		String nickname = withAccount.value();
		SecurityContext context;
		if (!nickname.equals("")) {
			Account findByNickname = accountRepository.findByNickname(nickname);
			if (findByNickname == null) {
				createAccount(nickname, roleUser);
			}

			UserDetails principal = accountService.loadUserByUsername(nickname);

			Authentication authentication = new UsernamePasswordAuthenticationToken(principal,
					principal.getPassword(), principal.getAuthorities());

			context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
		} else {
			context = SecurityContextHolder.createEmptyContext();
		}
		return context;

	}

	private AccountRole createRole(String role) {
		AccountRole accountRole = accountRoleRepository.findByName(role);

		if (accountRole == null) {

			AccountRole newRole =new AccountRole();
			newRole.setName(role);
					
			accountRole = accountRoleRepository.save(newRole);

		}
		return accountRole;
	}

	private void createAccount(String nickname, AccountRole role) {
		if (accountRepository.findByNickname(nickname) == null) {
			Account account = makeAccount(nickname);
			Set<AccountRole> accountsRoles = account.getRoles();
			if(!accountsRoles.contains(role))
				account.getRoles().add(role);

			accountRepository.save(account);
		}
	}

	private Account makeAccount(String nickname) {

		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setNickname(nickname);
		signUpForm.setEmail(nickname + "@email.com");
		signUpForm.setPassword("12345678");

		Account newAccount = accountService.saveNewAccount(signUpForm);
		return newAccount;
	}

}
