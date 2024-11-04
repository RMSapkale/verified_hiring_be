package com.vhProject.service;

import com.vhProject.Util.EmailUtil;
import com.vhProject.config.CommonClasses;
import com.vhProject.repository.UserRepository;
import com.vhProject.model.UserModel; // Import the User class
import com.vhProject.dto.RegisterDto;
import com.vhProject.dto.LoginDto;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final EmailUtil emailUtil;

    @Autowired
    public UserService(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterDto registerDto) throws MessagingException {
        Integer otp = Integer.parseInt(CommonClasses.generateSixDigitOTP());
        emailUtil.sendOtpEmail(registerDto.getEmail(), otp, registerDto.getName());
        UserModel user = new UserModel(); // Use UserModel class here
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) throws MessagingException {
//        UserModel user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
//        String otp = OtpUtil.generateOtp();
//        emailUtil.sendOtpEmail(email, otp);
//        user.setOtp(otp);
//        user.setOtpGeneratedTime(LocalDateTime.now());
//        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    public String login(LoginDto loginDto) {
//        UserModel user = userRepository.findByEmail(loginDto.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
//        if (!loginDto.getPassword().equals(user.getPassword())) {
//            return "Password is incorrect";
//        } else if (!user.isActive()) {
//            return "Your account is not verified";
//        }
        return "Login successful";
    }


    public String forgotPassword(String email) throws MessagingException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));
        emailUtil.sendSetPasswordEmail(email);
        return "Please Check your email to set new password to your account";
    }

    public String setPassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));

        user.setPassword(newPassword);
        userRepository.save(user);
        return "new password set successfully, login with new password";
    }
}
