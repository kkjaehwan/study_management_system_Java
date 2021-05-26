package com.shareknot.modules.board;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.board.form.CommentForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	private final CommentRepository commentRepository;

	private final ModelMapper modelMapper;

	public Comment saveComment(Board board, Post post, Account account, CommentForm commentForm) {

		Optional<Comment> parent_comment = null;
		if (commentForm.getParentCommentId() != null) {
			parent_comment = commentRepository.findById(commentForm.getParentCommentId());
		}

		Comment comment = null;

		if (parent_comment != null && parent_comment.isPresent()) {
			Comment parentComment = parent_comment.orElseThrow();
			long comment_group = parent_comment.orElseThrow()
					.getCommentGrp();

			Comment lastComment = commentRepository
					.findFirstByPostAndCommentGrpOrderByCommentOdrDesc(post, comment_group);
			long countByPostAndCommentGrp = lastComment.getCommentOdr() + 1;

			comment = createNewPost(modelMapper.map(commentForm, Comment.class), post,
					comment_group, countByPostAndCommentGrp, parentComment, account);
			parentComment.getChildComments()
					.add(comment);
		} else {
			Comment lastGrpComment = commentRepository.findFirstByPostOrderByCommentGrpDesc(post);
			long lastGrpNo = lastGrpComment == null ? 0 : lastGrpComment.getCommentGrp() + 1;

			comment = createNewPost(modelMapper.map(commentForm, Comment.class), post, lastGrpNo, 0,
					null, account);
		}
		Comment save = commentRepository.save(comment);
		post.addComment(save);
		return save;
	}

	private Comment createNewPost(Comment comment, Post post, long comment_group,
			long countByPostAndCommentGrp, Comment parentComment, Account account) {
		comment.setPost(post);
		comment.setCommentGrp(comment_group);
		comment.setCommentOdr(countByPostAndCommentGrp);
		comment.setParentComment(parentComment);
		comment.setAuthor(account);
		return commentRepository.save(comment);
	}

	public void removeComment(Account account,  Comment comment) {
		if (isAuthor(account, comment)) {
			Post post = comment.getPost();

			Set<Comment> childComments = comment.getChildComments();
			for (Comment childComment : childComments) {
				post.removeComment(childComment);
			}
			post.removeComment(comment);
			commentRepository.delete(comment);
		}
		
	}

	private boolean isAuthor(Account account, Comment comment) {
		if (!comment.getAuthor()
				.equals(account))
			throw new AccessDeniedException("The feature is not available.");
		return true;
	}

}
