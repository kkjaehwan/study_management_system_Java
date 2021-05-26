package com.shareknot.modules.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {

	long countByPostAndCommentGrp(Post post, long comment_group);

	Comment findFirstByPostAndCommentGrpOrderByCommentOdrDesc(Post post, long comment_group);

	Comment findFirstByPostOrderByCommentGrpDesc(Post post);

	List<Comment> findAllByPostOrderByCommentGrpDescCommentOdrDesc(Post post);

	Comment findFirstByPostAndCommentGrpOrderByCommentOdrAsc(Post post, long comment_group);

}
