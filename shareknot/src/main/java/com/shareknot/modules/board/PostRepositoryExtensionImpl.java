package com.shareknot.modules.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

public class PostRepositoryExtensionImpl extends QuerydslRepositorySupport
		implements PostRepositoryExtension {

	public PostRepositoryExtensionImpl() {
		super(Post.class);
	}

	@Override
	public Page<Post> findAllByBoardAndKeyword(Board board, String keyword, Pageable pageable) {
		QPost post = QPost.post;
		keyword = keyword == null ? "" : keyword;
		JPQLQuery<Post> query = from(post).where(post.board.eq(board)
				.and(post.title.containsIgnoreCase(keyword)));

		JPQLQuery<Post> pageableQuery = getQuerydsl().applyPagination(pageable, query);

		QueryResults<Post> fetchResults = pageableQuery.fetchResults();

		return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}

}
