package com.shareknot.modules.account;

import static com.shareknot.modules.account.SettingsController.PASSWORD;
import static com.shareknot.modules.account.SettingsController.PROFILE;
import static com.shareknot.modules.account.SettingsController.ROOT;
import static com.shareknot.modules.account.SettingsController.SETTINGS;
import static com.shareknot.modules.account.SettingsController.TAGS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareknot.infra.MockMvcTest;
import com.shareknot.infra.TestDBInit;
import com.shareknot.infra.TestDBSetting;
import com.shareknot.modules.account.form.TagForm;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.tag.TagRepository;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
class SettingsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	AccountService accountService;

	@Autowired
	TestDBSetting testDBSetting;

	@BeforeEach
	void initDB() {
		testDBSetting.init();
	}
	
	@AfterEach
	void afterEach() {
		accountRepository.deleteAll();
	}

	@WithAccount("kjaehwan89")
	@DisplayName("tags modify form")
	@Test
	void updateTagsForm() throws Exception {
		mockMvc.perform(get(ROOT + SETTINGS + TAGS))
				.andExpect(view().name(SETTINGS + TAGS))
				.andExpect(model().attributeExists("tags"))
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("whitelist"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("add tag")
	@Test
	void addTag() throws Exception {
		TagForm tagForm = new TagForm();
		tagForm.setTagTitle("newTag");

		mockMvc.perform(
				post(ROOT + SETTINGS + TAGS + "/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tagForm))
						.with(csrf()))
				.andExpect(status().isOk());

		Tag newTag = tagRepository.findByTitle("newTag");
		assertNotNull(newTag);
		Account kjaehwan89 = accountRepository.findByNickname("kjaehwan89");
		assertTrue(kjaehwan89.getTags()
				.contains(newTag));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("remove tag")
	@Test
	void removeTag() throws Exception {
		Account kjaehwan89 = accountRepository.findByNickname("kjaehwan89");
		Tag newTag = tagRepository.save(Tag.builder()
				.title("newTag")
				.build());
		accountService.addTag(kjaehwan89, newTag);

		assertTrue(kjaehwan89.getTags()
				.contains(newTag));

		TagForm tagForm = new TagForm();
		tagForm.setTagTitle("newTag");

		mockMvc.perform(
				post(ROOT + SETTINGS + TAGS + "/remove").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(tagForm))
						.with(csrf()))
				.andExpect(status().isOk());

		assertFalse(kjaehwan89.getTags()
				.contains(newTag));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("profile modify form ")
	@Test
	void updateProfileForm() throws Exception {
		mockMvc.perform(get(ROOT + SETTINGS + PROFILE))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("profile"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Change Profile - correct input data ")
	@Test
	void updateProfile() throws Exception {
		String bio = "chage brif introduction";
		mockMvc.perform(post(ROOT + SETTINGS + PROFILE).param("bio", bio)
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(ROOT + SETTINGS + PROFILE))
				.andExpect(flash().attributeExists("message"));

		Account findByNickname = accountRepository.findByNickname("kjaehwan89");
		assertEquals(bio, findByNickname.getBio());
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Change Profile - wrong input data ")
	@Test
	void updateProfile_error() throws Exception {
		String bio = "over than 35 charactors for brif introduction. it causes error";
		mockMvc.perform(post(ROOT + SETTINGS + PROFILE).param("bio", bio)
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name(SETTINGS + PROFILE))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("profile"));

		Account findByNickname = accountRepository.findByNickname("kjaehwan89");
		assertNull(findByNickname.getBio());
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Password Change Form ")
	@Test
	void updatePasswordForm() throws Exception {
		mockMvc.perform(get(ROOT + SETTINGS + PASSWORD))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("passwordForm"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Password Change - input correct data ")
	@Test
	void updatePassword_success() throws Exception {
		mockMvc.perform(post(ROOT + SETTINGS + PASSWORD).param("newPassword", "12345678")
				.param("newPasswordConfirm", "12345678")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(ROOT + SETTINGS + PASSWORD))
				.andExpect(flash().attributeExists("message"));

		Account findByNickname = accountRepository.findByNickname("kjaehwan89");
		assertTrue(passwordEncoder.matches("12345678", findByNickname.getPassword()));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Password Change - input incorrect data ")
	@Test
	void updatePassword_error() throws Exception {
		mockMvc.perform(post(ROOT + SETTINGS + PASSWORD).param("newPassword", "123456789")
				.param("newPasswordConfirm", "12345678")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name(SETTINGS + PASSWORD))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("passwordForm"));
	}
}
