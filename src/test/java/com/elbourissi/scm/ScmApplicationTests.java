package com.elbourissi.scm;

import com.elbourissi.scm.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest() {
		service.sendEmail(
				"hamza.el-bourissi@ump.ac.ma",
				"Just managing the emails",
				"this is scm project working on email service");
	}

}
