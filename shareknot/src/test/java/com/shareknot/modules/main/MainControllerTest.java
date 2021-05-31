package com.shareknot.modules.main;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.shareknot.infra.MockMvcTest;
import com.shareknot.infra.TestDBInit;
import com.shareknot.infra.TestDBSetting;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.AccountService;
import com.shareknot.modules.account.form.SignUpForm;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
class MainControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TestDBSetting testDBSetting;
	
	@BeforeEach
	void initDB() {
		testDBSetting.init();
	}

	@BeforeEach
	void beforeEach() {
		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setEmail("test@gmail.com");
		signUpForm.setNickname("test");
		signUpForm.setPassword("12345678");
		accountService.processNewAccount(signUpForm);

	}

	@AfterEach
	void afterEach() {
		accountRepository.deleteAll();
	}

	@DisplayName("login success using email")
	@Test
	void login_with_email() throws Exception {
		mockMvc.perform(post("/login").param("username", "test@gmail.com")
				.param("password", "12345678")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andExpect(authenticated().withUsername("test"));
	}

	@DisplayName("login success using nickname")
	@Test
	void login_with_nickname() throws Exception {
		mockMvc.perform(post("/login").param("username", "test")
				.param("password", "12345678")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andExpect(authenticated().withUsername("test"));
	}

	@DisplayName("login fail")
	@Test
	void login_fail() throws Exception {
		mockMvc.perform(post("/login").param("username", "test")
				.param("password", "0000000")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login?error"))
				.andExpect(unauthenticated());
	}

	@DisplayName("logout")
	@Test
	void logout() throws Exception {
		mockMvc.perform(post("/logout").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andExpect(unauthenticated());
	}

}
