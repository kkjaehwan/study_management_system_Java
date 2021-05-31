package com.shareknot.modules.init;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.shareknot.infra.TestDBSetting;

import lombok.RequiredArgsConstructor;

@Profile("test_post")
@Component
public class TestDBSettingBasePostgres implements TestDBSetting {

//	static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;
//
//	static {
//		POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();
//		POSTGRE_SQL_CONTAINER.start();
//	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
