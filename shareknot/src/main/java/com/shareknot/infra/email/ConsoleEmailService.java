package com.shareknot.infra.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({"local", "test", "test_h2", "test_post"})
@Component
public class ConsoleEmailService implements EmailService {

	@Override
	public void send(EmailMessage emailMessage) {
		log.info("sent email : {}", emailMessage.getMessage());
	}

}
