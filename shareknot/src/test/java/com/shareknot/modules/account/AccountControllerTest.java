package com.shareknot.modules.account;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.shareknot.infra.AbstractContainerBase;
import com.shareknot.infra.TestContainerBasePostgres;
import com.shareknot.infra.MockMvcTest;
import com.shareknot.infra.email.EmailMessage;
import com.shareknot.infra.email.EmailService;
import com.shareknot.modules.account.form.SignUpForm;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
class AccountControllerTest extends AbstractContainerBase {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@MockBean
	EmailService emailService;

	@DisplayName("check confirmation email - wrong")
	@Test
	void checkEmailToken_with_wrong_input() throws Exception {
		mockMvc.perform(get("/check-email-token").param("token", "test")
				.param("email", "kjaehwan89@gmail.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("account/checked-email"))
				.andExpect(unauthenticated());
	}

	@DisplayName("check confirmation email - correct")
	@Test
	void checkEmailToken() throws Exception {

		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setEmail("test@gmail.com");
		signUpForm.setNickname("test");
		signUpForm.setPassword("test");

		Account account = accountService.processNewAccount(signUpForm);

		mockMvc.perform(get("/check-email-token").param("token", account.getEmailCheckToken())
				.param("email", account.getEmail()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("error"))
				.andExpect(model().attributeExists("nickname"))
				.andExpect(model().attributeExists("numberOfUser"))
				.andExpect(view().name("account/checked-email"))
				.andExpect(authenticated().withUsername("test"));
		;
	}

	@DisplayName("Check Form of SignUp")
	@Test
	void signUpForm() throws Exception {
		mockMvc.perform(get("/sign-up"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("account/sign-up"))
				.andExpect(model().attributeExists("signUpForm"))
				.andExpect(unauthenticated());
	}

	@DisplayName("회원가입처리 - 입력 오류")
	@Test
	void signUpSubmit_with_wrong_input() throws Exception {
		mockMvc.perform(post("/sign-up").param("nickname", "test")
				.param("email", "email..")
				.param("password", "12345")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("account/sign-up"))
				.andExpect(unauthenticated());
	}

	@DisplayName("회원가입처리 - 입력 정상")
	@Test
	void signUpSubmit_with_correct_input() throws Exception {
		mockMvc.perform(post("/sign-up").param("nickname", "test")
				.param("email", "test@gmail.com")
				.param("password", "12345678")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"))
				.andExpect(authenticated().withUsername("test"));
		;

		Account account = accountRepository.findByEmail("test@gmail.com");

		assertTrue(accountRepository.existsByEmail("test@gmail.com"));

		assertNotNull(account);
		assertNotEquals(account.getPassword(), "12345678");
		assertNotNull(account.getEmailCheckToken());

		then(emailService).should()
				.send(any(EmailMessage.class));

	}
}
