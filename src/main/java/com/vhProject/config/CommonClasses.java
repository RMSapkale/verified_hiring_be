package com.vhProject.config;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@Service
public class CommonClasses {

    private static final Random random = new Random();

    private CommonClasses() {
    }

    public static String generateSixDigitOTP() {
        int otp = random.nextInt(900000) + 100000;
        return String.format("%06d", otp);
    }
}