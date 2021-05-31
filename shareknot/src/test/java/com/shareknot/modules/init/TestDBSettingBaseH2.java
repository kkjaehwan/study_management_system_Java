package com.shareknot.modules.init;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.shareknot.infra.TestDBSetting;
import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.AccountRole;
import com.shareknot.modules.account.AccountRoleRepository;
import com.shareknot.modules.account.AccountService;
import com.shareknot.modules.account.form.SignUpForm;

import lombok.RequiredArgsConstructor;

@Profile("test_h2")
@Component
public class TestDBSettingBaseH2 implements TestDBSetting {

	@Autowired
	AccountRoleRepository accountRoleRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	public void init() {
		AccountRole roleAdmin = createRole("ROLE_ADMIN");
		AccountRole roleUser = createRole("ROLE_USER");

		createAccount("shareknot", roleAdmin);
		createAccount("kjaehwan89", roleUser);
	}

	private AccountRole createRole(String role) {
		AccountRole accountRole = accountRoleRepository.findByName(role);

		if (accountRole == null) {

			AccountRole newRole = new AccountRole();
			newRole.setName(role);

			accountRole = accountRoleRepository.save(newRole);

		}
		return accountRole;
	}

	private void createAccount(String nickname, AccountRole role) {
		if (accountRepository.findByNickname(nickname) == null) {
			Account account = makeAccount(nickname);
			Set<AccountRole> accountsRoles = account.getRoles();
			if (!accountsRoles.contains(role))
				account.getRoles()
						.add(role);

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
