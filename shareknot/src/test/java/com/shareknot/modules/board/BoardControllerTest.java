package com.shareknot.modules.board;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.shareknot.infra.MockMvcTest;
import com.shareknot.infra.TestDBInit;
import com.shareknot.infra.TestDBSetting;
import com.shareknot.modules.account.WithAccount;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
public class BoardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	TestDBSetting testDBSetting;

	@BeforeEach
	void initDB() {
		testDBSetting.init();
	}
	
	@DisplayName("@GetMapping(\"/new-board\") : view a new board form without authority")
	@Test
	void view_new_board_form() throws Exception {
		mockMvc.perform(get("/board/new-board"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@WithAccount("shareknot")
	@DisplayName("@PostMapping(\"/new-board\") : create a new board : correct authority")
	@Test
	void view_board_atuh() throws Exception {
		mockMvc.perform(post("/board/new-board").param("title", "freeboard")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/board/lists/"));

		Board board = boardRepository.findByTitle("freeboard");
		assertNotNull(board);
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@PostMapping(\"/new-board\") : create a new board : dinied authority")
	@Test
	void newBoard_Denied() throws Exception {
		mockMvc.perform(post("/board/new-board").param("title", "freeboard")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}

	@DisplayName("@GetMapping(\"/lists\") : view a list of boards")
	@Test
	void viewBoard_without_login() throws Exception {
		mockMvc.perform(get("/board/lists"))
				.andExpect(status().isOk());
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@GetMapping(\"/lists/{title}\") : view a board without auth")
	@Test
	void board_detail() throws Exception {

		mockMvc.perform(get("/board/lists/freeboard"))
				.andExpect(view().name("error"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@GetMapping(\"/lists/{current_title}/edit\") : view a board edit page")
	@Test
	void view_board_eidt_page() throws Exception {
		Board board = boardRepository.save(Board.builder()
				.title("freeboard")
				.build());
		mockMvc.perform(get("/board/lists/freeboard/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}

	@WithAccount("shareknot")
	@DisplayName("@GetMapping(\"/lists/{current_title}/edit\") : view a board edit page")
	@Test
	void view_board_eidt_page_with_auth() throws Exception {
		Board board = boardRepository.save(Board.builder()
				.title("freeboard")
				.build());
		mockMvc.perform(get("/board/lists/freeboard/edit"))
				.andExpect(view().name("board/update-form"));
	}

	@WithAccount("shareknot")
	@DisplayName("@PostMapping(\"/lists/{current_title}/edit\") : edit a board information")
	@Test
	void eidt_board_information() throws Exception {
		Board board = boardRepository.save(Board.builder()
				.title("freeboard")
				.build());

		mockMvc.perform(post("/board/lists/freeboard/edit").param("title", "freeboard2")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/board/lists/"));

		Board modifiedboard = boardRepository.findByTitle("freeboard2");
		assertNotNull(modifiedboard);
	}

	@WithAccount("shareknot")
	@DisplayName("@PostMapping(\"/lists/{title}/delete\") : delete a board")
	@Test
	void delete_a_board() throws Exception {
		boardRepository.save(Board.builder()
				.title("freeboard")
				.build());

		mockMvc.perform(post("/board/lists/freeboard/delete").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/board/lists"));

		Board board = boardRepository.findByTitle("freeboard");
		assertNull(board);
	}

}
