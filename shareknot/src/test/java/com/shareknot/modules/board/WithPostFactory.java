package com.shareknot.modules.board;

import org.junit.jupiter.api.extension.Extension;
import java.lang.annotation.Annotation;

public interface WithPostFactory {

	Post makePost(String account_nickname, String board_title, String post_title);
}
