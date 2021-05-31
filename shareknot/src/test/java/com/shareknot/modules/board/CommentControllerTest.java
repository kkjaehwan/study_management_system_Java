package com.shareknot.modules.board;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareknot.infra.MockMvcTest;
import com.shareknot.infra.TestDBInit;
import com.shareknot.infra.TestDBSetting;
import com.shareknot.modules.account.Account;
import com.shareknot.modules.account.AccountRepository;
import com.shareknot.modules.account.AccountService;
import com.shareknot.modules.account.WithAccount;
import com.shareknot.modules.board.form.CommentForm;

import lombok.extern.slf4j.Slf4j;

@MockMvcTest
@Slf4j
class CommentControllerTes {

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
	CommentService commentService;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WithBoardPostFactoryImpl withBoardPostFactoryImpl;

	private Board board;
	private Post post;

	@Autowired
	TestDBSetting testDBSetting;

	@BeforeEach
	void initDB() {
		testDBSetting.init();
	}

	@BeforeEach
	void makeFreeboard() {
		this.board = withBoardPostFactoryImpl.makeBoard("freeboard");
		this.post = withBoardPostFactoryImpl.makePost("kjaehwan89", "freeboard", "test_post");
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Add a comment")
	@Test
	void addComment() throws Exception {
		CommentForm commentForm = new CommentForm();
		commentForm.setContent("1234567890");
		commentForm.setBoardTitle(board.getTitle());
		commentForm.setPostId(post.getId());

		int before_size = post.getComments()
				.size();
		String url = "/comments/add";
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentForm))
				.with(csrf()))
				.andExpect(status().isOk());
		int after_size = post.getComments()
				.size();
		assertTrue(after_size > before_size);
	}

	@WithAccount("kjaehwan89")
	@DisplayName("Add a comment of comment")
	@Test
	void addCommentofComment() throws Exception {
		CommentForm fstCommentForm = new CommentForm();
		fstCommentForm.setContent("==========test1==========");
		fstCommentForm.setBoardTitle(board.getTitle());
		fstCommentForm.setPostId(post.getId());

		Account account = withBoardPostFactoryImpl.getPrincipalAccount()
				.getAccount();

		Comment saveComment = commentService.saveComment(board, post, account, fstCommentForm);
		commentService.saveComment(board, post, account, fstCommentForm);

		CommentForm sndCommentForm = new CommentForm();

		sndCommentForm.setContent("==========test2==========");
		sndCommentForm.setBoardTitle(board.getTitle());
		sndCommentForm.setPostId(post.getId());
		sndCommentForm.setParentCommentId(saveComment.getId());

		int before_size = post.getComments()
				.size();
		log.info("==========================");
		String url = "/comments/add/";
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sndCommentForm))
				.with(csrf()))
				.andExpect(status().isOk());
		int after_size = post.getComments()
				.size();
		assertTrue(after_size > before_size);

		printComments(post);
	}

	private void printComments(Post post) {
		List<Comment> findAll = commentRepository
				.findAllByPostOrderByCommentGrpAscCommentOdrAsc(post);
		for (Comment comment : findAll) {
			Comment parentComment = comment.getParentComment();
			log.info("\nid:{} post:{} grp:{} order:{} parent:{} content:{}", comment.getId(),
					comment.getPost()
							.getId(),
					comment.getCommentGrp(), comment.getCommentOdr(),
					parentComment != null ? parentComment.getId() : "-", comment.getContent());
		}
	}

	@WithAccount("kjaehwan89")
	@DisplayName("delete a comment")
	@Test
	void deleteComment() throws Exception {
		CommentForm fstCommentForm = new CommentForm();
		fstCommentForm.setContent("1234567890");
		Account account = withBoardPostFactoryImpl.getPrincipalAccount()
				.getAccount();

		Comment saveComment = commentService.saveComment(board, post, account, fstCommentForm);

		printComments(post);

		int before_size = post.getComments()
				.size();
		String url = "/comments/remove/" + saveComment.getId();

		removeComment(url);

		printComments(post);
		assertTrue(commentRepository.findById(saveComment.getId())
				.isEmpty());
		int after_size = post.getComments()
				.size();
		assertTrue(after_size < before_size);
	}

	@WithAccount("kjaehwan89")
	@DisplayName("delete a sub comment of comments")
	@Test
	void deleteSubComment() throws Exception {
		CommentForm fstCommentForm = new CommentForm();
		CommentForm sndCommentForm = new CommentForm();
		Account account = withBoardPostFactoryImpl.getPrincipalAccount()
				.getAccount();

		fstCommentForm = createCommentForm("=============1=============", board.getTitle(),
				post.getId(), null);
		commentService.saveComment(board, post, account, fstCommentForm);

		fstCommentForm = createCommentForm("=============2=============", board.getTitle(),
				post.getId(), null);
		Comment saveComment = commentService.saveComment(board, post, account, fstCommentForm);

		fstCommentForm = createCommentForm("=============3=============", board.getTitle(),
				post.getId(), null);
		commentService.saveComment(board, post, account, fstCommentForm);

		fstCommentForm = createCommentForm("=============4=============", board.getTitle(),
				post.getId(), null);
		commentService.saveComment(board, post, account, fstCommentForm);

		Long parentId = saveComment.getId();
		sndCommentForm = createCommentForm("=============2-1=============", board.getTitle(),
				post.getId(), parentId);
		commentService.saveComment(board, post, account, sndCommentForm);

		sndCommentForm = createCommentForm("=============2-2=============", board.getTitle(),
				post.getId(), parentId);
		Comment subComment = commentService.saveComment(board, post, account, sndCommentForm);

		sndCommentForm = createCommentForm("=============2-3=============", board.getTitle(),
				post.getId(), parentId);
		commentService.saveComment(board, post, account, sndCommentForm);

		fstCommentForm = createCommentForm("=============5=============", board.getTitle(),
				post.getId(), null);
		commentService.saveComment(board, post, account, fstCommentForm);

		printComments(post);

		int before_size = post.getComments()
				.size();

		// delete 2-2
		String url = "/comments/remove/" + subComment.getId();
		removeComment(url);
		assertTrue(commentRepository.findById(subComment.getId())
				.isEmpty());

		// delete 2
		printComments(post);
		url = "/comments/remove/" + parentId;
		removeComment(url);
//		url = "/board/lists/freeboard/posts/" + post.getId() + "/delete";
//		mockMvc.perform(post(url).with(csrf()))
//				.andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrl("/board/lists/freeboard/posts"));
		printComments(post);
		int after_size = post.getComments()
				.size();
		assertTrue(after_size < before_size);
	}

	private CommentForm createCommentForm(String string, String title, Long id, Long parentId) {
		CommentForm commentForm = new CommentForm();
		commentForm.setContent(string);
		commentForm.setBoardTitle(title);
		commentForm.setPostId(id);
		commentForm.setParentCommentId(parentId);

		return commentForm;
	}

	private void removeComment(String url) throws Exception {
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
