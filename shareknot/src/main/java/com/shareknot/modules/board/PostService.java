package com.shareknot.modules.board;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.board.form.PostForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;

	private final ModelMapper modelMapper;

	public Post createNewPost(Post post, Board board, Account account) {
		post.setBoard(board);
		post.setAuthor(account);
		return postRepository.save(post);
	}

	public void updatePost(Account account, Post post, @Valid PostForm postForm) {
		checkIfAuthor(account, post);

		modelMapper.map(postForm, post);
	}

	public void deletePost(Account account, Post post) {
		checkIfAuthor(account, post);
		postRepository.delete(post);

	}

	public void checkIfAuthor(Account account, Post post) {
		if (!post.isWriitenBy(account)) {
			throw new AccessDeniedException("The feature is not available.");
		}
	}

	public void addViewCount(Post post) {
		post.addViewCount();
	}

}
