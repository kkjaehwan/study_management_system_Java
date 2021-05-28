package com.shareknot.modules.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareknot.infra.AbstractContainerBase;
import com.shareknot.infra.MockMvcTest;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.AccountService;
import com.shareknot.modules.account.WithAccount;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
class PostControllerTest extends AbstractContainerBase {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	BoardService boardService;

	@Autowired
	PostService postService;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WithBoardPostFactoryImpl withBoardPostFactoryImpl;

	@BeforeEach
	void makeFreeboard() {
		withBoardPostFactoryImpl.makeBoard("freeboard");
	}

	@DisplayName("@GetMapping(\"/posts\") : view posts without authority")
	@Test
	void view_posts() throws Exception {
		mockMvc.perform(get("/board/lists/freeboard/posts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("post/lists"));
	}

	@DisplayName("@GetMapping(\"/new-post\") : view a new post form without auth")
	@Test
	void view_new_post_form_without_auth() throws Exception {
		mockMvc.perform(get("/board/lists/freeboard/new-post"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@PostMapping(\"/new-post\") : create a new post with authority")
	@Test
	void create_new_post_with_auth() throws Exception {
		MvcResult rv = mockMvc
				.perform(post("/board/lists/freeboard/new-post").param("title", "test1")
						.param("content", "test data")
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("/board/lists/freeboard/posts/*"))
				.andReturn();
		String[] split = rv.getModelAndView()
				.getViewName()
				.split("/");
		Long id = Long.valueOf(split[split.length - 1]);
		Optional<Post> post = postRepository.findById(id);

		assertNotNull(post);
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@PostMapping(\"/new-post\") : create a new post without board")
	@Test
	void create_new_post_without_board() throws Exception {
		/*
		 * NestedServletException exception = assertThrows(NestedServletException.class,
		 * () -> mockMvc
		 * .perform(post("/board/lists/freeboard2/new-post").param("title", "test1")
		 * .param("content", "test data") .with(csrf())));
		 * 
		 * String message = exception.getMessage(); log.info(message);
		 */

		mockMvc.perform(post("/board/lists/freeboard2/new-post").param("title", "test1")
				.param("content", "test data")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
		/*
		 * // .andExpect((rslt) -> assertTrue(rslt.getResolvedException() // .getClass()
		 * // .isAssignableFrom(IllegalAccessException.class))) // .andExpect( // //
		 * assert로 예외를 검사하는 람다 사용 // (rslt) -> assertEquals(rslt.getResolvedException()
		 * // .getClass() // .getCanonicalName(), //
		 * IllegalAccessException.class.getCanonicalName())) // .andExpect((rslt) ->
		 * assertTrue( // rslt.getResolvedException() instanceof
		 * IllegalAccessException));
		 */
	}

	@DisplayName("@PostMapping(\"/new-post\") : create a new post without login")
	@Test
	void create_new_post_without_login() throws Exception {
		mockMvc.perform(post("/board/lists/freeboard/new-post").param("title", "test1")
				.param("content", "test data")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@DisplayName("@GetMapping(\"/posts/{id}\") : view a post in detail")
	@Test
	void view_post_in_detail() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id;
		mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(view().name("post/view"))
				.andExpect(status().isOk());
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@GetMapping(\"/posts/{id}/edit\") : connect edit a post page with autor")
	@Test
	void connect_edit_post_page_with_autor() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/edit";
		mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("post/update-form"));
	}

	@WithAccount("testuser")
	@DisplayName("@GetMapping(\"/posts/{id}/edit\") : connect edit a post page without auth")
	@Test
	void connect_edit_post_page_without_auth() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/edit";
		mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@PostMapping(\"/posts/{id}/edit\") : edit a post information with auth")
	@Test
	void eidt_post_information_with_auth() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/edit";
		mockMvc.perform(post(url).param("title", "test2")
				.param("content", "test3")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/board/lists/freeboard/posts/" + id));

		Optional<Post> modifiedPost = postRepository.findById(id);
		modifiedPost.ifPresent(pt -> {
			assertEquals(pt.getTitle(), "test2");
			assertEquals(pt.getContent(), "test3");
		});
	}

	@WithAccount("testuesr")
	@DisplayName("@PostMapping(\"/posts/{id}/edit\") : edit a post information without auth")
	@Test
	void eidt_post_information_without_auth() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/edit";
		mockMvc.perform(post(url).param("title", "test2")
				.param("content", "test3")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}

	@WithAccount("kjaehwan89")
	@DisplayName("@PostMapping(\"/posts/{id}/delete\") : delete a post with auth")
	@Test
	void delete_post_with_auth() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/delete";
		mockMvc.perform(post(url).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/board/lists/freeboard/posts"));

		Optional<Post> modifiedPost = postRepository.findById(id);
		assertFalse(modifiedPost.isPresent());
	}

	@WithAccount("testuser")
	@DisplayName("@PostMapping(\"/posts/{id}/delete\") : delete a post without auth")
	@Test
	void delete_post_without_auth() throws Exception {
		Post post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
		Long id = post.getId();

		String url = "/board/lists/freeboard/posts/" + id + "/delete";
		mockMvc.perform(post(url).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));

		Optional<Post> modifiedPost = postRepository.findById(id);
		assertTrue(modifiedPost.isPresent());
	}

}
