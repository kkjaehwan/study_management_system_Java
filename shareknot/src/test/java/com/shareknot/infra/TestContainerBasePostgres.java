package com.shareknot.infra;

import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Profile({ "test" })
@Testcontainers
public class TestContainerBasePostgres implements TestSQLContainer {

	static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

	static {
		POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();
		POSTGRE_SQL_CONTAINER.start();
	}

}
