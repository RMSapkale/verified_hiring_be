package com.vhProject.service;

import com.vhProject.Util.EmailUtil;
import com.vhProject.config.CommonClasses;
import com.vhProject.config.MessageConfig;
import com.vhProject.repository.UserRepository;
import com.vhProject.model.UserModel; // Import the User class
import com.vhProject.dto.RegisterDto;
import com.vhProject.dto.LoginDto;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.vhProject.service.ResponseHandler.generateResponse;

@Service
public class UserService {

    private final EmailUtil emailUtil;
    private static final Random random = new Random();

    @Autowired
    public UserService(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterDto registerDto) throws MessagingException {
       // Integer otp = Integer.parseInt(CommonClasses.generateSixDigitOTP());
       // emailUtil.sendOtpEmail(registerDto.getEmail(), otp, registerDto.getName());
       UserModel user = new UserModel(); // Use UserModel class here
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
       // user.setOtp(otp);
       // user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, Integer otp) {
        UserModel user = userRepository.findByEmail(email).orElse(null);
        if(user != null ){
            if (!user.getOtp().equals(otp)) {
                return "Invalid OTP; please try again.";
            } else if (Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() >= 60) {
                return "OTP expired; please regenerate a new one and try again.";
            } else {
                user.setActive(true);
                userRepository.save(user);
                return "OTP verified";
            }
        }
        else {
            return "Please provide valid email";
        }
    }

    public String regenerateOtp(String email) throws MessagingException {
       UserModel user = userRepository.findByEmail(email).orElse(null);
       if(user==null){
           return "email not found";
       }

      else{
           Integer otp = random.nextInt(900000) + 100000;
           emailUtil.sendOtpEmail(email, otp,"SAVIM");
           user.setOtp(otp);
           user.setOtpGeneratedTime(LocalDateTime.now());
           userRepository.save(user);
           return "Email sent... please verify account within 1 minute";
       }
    }

    public ResponseEntity<Object> login(LoginDto loginDto) {
        Optional<UserModel> user = userRepository.findByEmail(loginDto.getEmail());
        if(user.isEmpty()){
            return generateResponse(MessageConfig.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND,null);
        }
        if (!loginDto.getPassword().equals(user.get().getPassword())) {
            return generateResponse(MessageConfig.INVALID_PASSWORD, HttpStatus.NOT_FOUND,null);
        } else if (!user.get().isActive()) {
            return generateResponse(MessageConfig.INVALID_ACCOUNT, HttpStatus.NOT_FOUND,null);
        }
        return generateResponse(MessageConfig.LOGIN_SUCCESSFULLY, HttpStatus.OK,null);
    }


    public String forgotPassword(String email) throws MessagingException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));
        emailUtil.sendSetPasswordEmail(email);
        return "Please Check your email to set new password to your account";
    }

    public String setPassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email).orElse(null);
        if(user != null){
            user.setPassword(newPassword);
            userRepository.save(user);
            return "Password reset successfully";
        }
        else {
            return "Invalid details";
        }
    }
}
