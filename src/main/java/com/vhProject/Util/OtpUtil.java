package com.vhProject.Util;

import java.security.SecureRandom;
import java.util.Base64;

public class OtpUtil {
    private static final int OTP_LENGTH = 6; // Length of OTP
    private static final SecureRandom secureRandom = new SecureRandom();

    // Generates a random OTP
    public static String generateOtp() {
        int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%0" + OTP_LENGTH + "d", otp);
    }

    // Validates the OTP (in this example, we just check if it's numeric and the right length)
    public static boolean validateOtp(String otp) {
        return otp != null && otp.matches("\\d{" + OTP_LENGTH + "}");
    }

    //public static void main(String[] args) {
        // Example usage
       // String otp = generateOtp();
        //System.out.println("Generated OTP: " + otp);
        //System.out.println("Is valid: " + validateOtp(otp));//
    }
//}