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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.infra.email.EmailMessage;
import com.shareknot.infra.email.EmailService;
import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountRepository;

	@MockBean
	EmailService emailService;

	@DisplayName("check confirmation email - wrong")
	@Test
	void checkEmailToken_with_wrong_input() throws Exception {
		mockMvc.perform(get("/check-email-token").param("token", "test").param("email", "kjaehwan89@gmail.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("account/checked-email"))
				.andExpect(unauthenticated());
	}

	@DisplayName("check confirmation email - correct")
	@Test
	void checkEmailToken() throws Exception {
		Account account = Account.builder().email("test@gmail.com").password("test").nickname("test").build();

		Account newAccount = accountRepository.save(account);
		newAccount.generateEmailCheckToken();

		mockMvc.perform(get("/check-email-token").param("token", newAccount.getEmailCheckToken())
				.param("email", newAccount.getEmail()))
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
		mockMvc.perform(post("/sign-up").param("nickname", "kjaehwan89")
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
		mockMvc.perform(post("/sign-up").param("nickname", "kjaehwan89")
				.param("email", "kjaehwan89@gmail.com")
				.param("password", "12345678")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"))
				.andExpect(authenticated().withUsername("kjaehwan89"));
		;

		Account account = accountRepository.findByEmail("kjaehwan89@gmail.com");

		assertTrue(accountRepository.existsByEmail("kjaehwan89@gmail.com"));

		assertNotNull(account);
		assertNotEquals(account.getPassword(), "12345678");
		assertNotNull(account.getEmailCheckToken());

		then(emailService).should().send(any(EmailMessage.class));

	}
}
