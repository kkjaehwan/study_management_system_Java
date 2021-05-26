package com.shareknot.modules.board;

import org.junit.jupiter.api.extension.Extension;
import java.lang.annotation.Annotation;

public interface WithBoardFactory {

	Board makeBoard(String board_title);
}
