package com.shareknot.modules.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PostRepositoryExtension {

	@EntityGraph(attributePaths = { "comment" })
	Page<Post> findAllByBoardAndKeyword(Board board, String keyword, Pageable pageable);

}
