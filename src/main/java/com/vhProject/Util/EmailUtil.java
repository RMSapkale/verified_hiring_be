package com.vhProject.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject("Your OTP Code");
        helper.setText("Your OTP is: " + otp, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
        <div>
            <a href="http://localhost:8080/set-password?email=%s" target="_blank">Click link to set password</a>
        </div>
        """.formatted(email), true);
        javaMailSender.send(mimeMessage);
    }

}
