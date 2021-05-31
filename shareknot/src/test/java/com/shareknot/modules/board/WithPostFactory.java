package com.shareknot.modules.board;

public interface WithPostFactory {

	Post makePost(String account_nickname, String board_title, String post_title);
}
