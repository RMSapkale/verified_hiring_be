package com.vhProject.controller;

import com.vhProject.dto.LoginDto;
import com.vhProject.dto.RegisterDto;
import com.vhProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) throws MessagingException {
        return userService.register(registerDto);
    }

    @PostMapping("/verify-account")
    public ResponseEntity<Object> verifyAccount(@RequestParam String email, @RequestParam Integer otp) {
        return userService.verifyAccount(email, otp);
    }

    @PostMapping("/regenerate-otp") //end point url
    public ResponseEntity<Object> regenerateOtp(@RequestParam String email) throws MessagingException {
        return userService.regenerateOtp(email);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PutMapping("/forget-password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email) throws MessagingException {
        return userService.forgotPassword(email);
    }

    @PutMapping("/set-password")
    public ResponseEntity<Object> setPassword(@RequestParam String email, @RequestParam String newPassword) throws MessagingException {
        return userService.setPassword(email, newPassword);
    }

    @PutMapping("/resend-otp")
    public ResponseEntity<Object> resendOtp(@RequestParam String email) throws MessagingException {
        return userService.resendOtp(email);
    }
}




