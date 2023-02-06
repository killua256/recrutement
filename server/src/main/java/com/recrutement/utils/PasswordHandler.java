package com.recrutement.utils;

import java.util.Random;

public class PasswordHandler {

    private final static Integer OTP_LENGTH = 6;

    public static String generateRandomToken(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String generateOTP(){
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomNumber = random.nextInt(10);
            otp.append(randomNumber);
        }
        return otp.toString();
    }

}
