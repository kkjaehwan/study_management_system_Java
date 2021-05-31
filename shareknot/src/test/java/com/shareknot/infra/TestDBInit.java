package com.shareknot.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDBInit {

	@Autowired
	protected static TestDBSetting testDBSetting;
}
