package com.vhProject.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class EmailUtil {

    @Value("${spring.mail.email}")
    private String senderEmail;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtpEmail(String email, Integer otp, String name) {
        javaMailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(senderEmail);
                message.setTo(email);
                message.setSubject("Interview Portal OTP Verification");
                String emailBody = "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" + ".header { background-color: #4c81ba; color: #ffffff; text-align: center; padding: 1px 0; }" + ".content { background-color: #ffffff; padding: 30px; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }" + ".footer { background-color: #f4f4f4; color: #666666; text-align: center; padding: 1px 0; font-size: 12px; }" + ".otp-text { font-size: 24px; text-align: center; }" + // Style for OTP text
                        "</style>" + "</head>" + "<body>" + "<div class='container'>" + "<div class='header'>" + "<h2>Interview Portal OTP Verification</h2>" + "</div>" + "<div class='content'>" + "<p>Hi " + Arrays.stream(name.split("\\s+")).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).collect(Collectors.joining(" ")) + ",</p>" + "<p>Use the following OTP to complete your registration procedure.<br>" + "This OTP is valid for 5 minutes.</p>" + "<p class='otp-text'><b>" + otp + "</b></p><br>" + "<div class='footer'>" + "<p>&copy; iWingsHR. All rights reserved.</p>" + "</div>" + "</body>" + "</html>";
                message.setText(emailBody, true);
            }
        });
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
