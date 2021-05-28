package com.shareknot.infra;

import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;

@Profile({"test_h2"})
public class TestContainerBaseH2 implements TestSQLContainer {

}
