package com.cms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender javaMailSender;

	public Boolean sendEmail(String to, String subject, String body) {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		boolean isSent = false;

		try {
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body);

			javaMailSender.send(mimeMessage);
			isSent = true;

		} catch (MessagingException e) {

			e.printStackTrace();
		}
		return isSent;

	}

}