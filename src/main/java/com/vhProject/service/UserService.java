package com.vhProject.service;

import com.vhProject.Util.EmailUtil;
import com.vhProject.config.CommonClasses;
import com.vhProject.config.MessageConfig;
import com.vhProject.repository.UserRepository;
import com.vhProject.model.UserModel;
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

    public ResponseEntity<Object> register(RegisterDto registerDto) throws MessagingException {
        UserModel user;
        Optional<UserModel> userModel = userRepository.findByEmail(registerDto.getEmail());
        user = userModel.orElseGet(UserModel::new);
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail().toLowerCase());
        user.setPassword(registerDto.getPassword());
        user.setActive(true);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return generateResponse(MessageConfig.VALID_ACCOUNT, HttpStatus.OK, null);
    }

    public ResponseEntity<Object> verifyAccount(String email, Integer otp) {
        UserModel user = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if(user != null) {
            if (user.getOtp() == null || !user.getOtp().equals(otp)) {
                return generateResponse(MessageConfig.INVALID_OTP, HttpStatus.NOT_FOUND, null);

            } else if (Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() >= 60) {
                return generateResponse(MessageConfig.OTP_EXPIRE, HttpStatus.NOT_FOUND, null);
            } else {
                user.setActive(true);
                userRepository.save(user);
                return generateResponse(MessageConfig.VALID_OTP, HttpStatus.OK, null);
            }
        } else {
            return generateResponse(MessageConfig.VALID_ACCOUNT, HttpStatus.OK, null);
        }
    }

    public ResponseEntity<Object> regenerateOtp(String email) throws MessagingException {
        Optional<UserModel> user = userRepository.findByEmail(email.toLowerCase());
        if(user.isEmpty()) {
            return generateResponse(MessageConfig.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        } else {
            UserModel userModel = user.get();
            Integer otp = random.nextInt(900000) + 100000;
            emailUtil.sendOtpEmail(email, otp, userModel.getName());
            userModel.setOtp(otp);
            userModel.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(userModel);
            return generateResponse(MessageConfig.VALID_ACCOUNT, HttpStatus.OK, null);
        }
    }

    public ResponseEntity<Object> login(LoginDto loginDto) {
        Optional<UserModel> user = userRepository.findByEmail(loginDto.getEmail().toLowerCase());
        if(user.isEmpty()) {
            return generateResponse(MessageConfig.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        if (!loginDto.getPassword().equals(user.get().getPassword())) {
            return generateResponse(MessageConfig.VALID_PASSWORD, HttpStatus.OK, null);
        } else if (!user.get().isActive()) {
            return generateResponse(MessageConfig.INVALID_ACCOUNT, HttpStatus.FORBIDDEN, null);
        }
        return generateResponse(MessageConfig.LOGIN_SUCCESSFULLY, HttpStatus.OK, null);
    }

    public ResponseEntity<Object> forgotPassword(String email) throws MessagingException {
        UserModel user = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if (user == null) {
            return generateResponse(MessageConfig.INVALID_ACCOUNT, HttpStatus.NOT_FOUND, null);
        } else {
            emailUtil.sendSetPasswordEmail(email);
            return generateResponse(MessageConfig.VALID_PASSWORD, HttpStatus.OK, null);
        }
    }


    public ResponseEntity<Object> setPassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if(user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return generateResponse(MessageConfig.VALID_PASSWORD, HttpStatus.OK, null);
        } else {
            return generateResponse(MessageConfig.INVALID_PASSWORD, HttpStatus.NOT_FOUND, null);
        }
    }
}
