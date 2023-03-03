package com.recrutement.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordHandler {

    private final static Integer OTP_LENGTH = 6;
    private final static String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

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

    public static boolean isValidPassword(String password){
        if(password.length() < 8){
            return false;
        }
        /*boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for(int i=0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if(Character.isUpperCase(ch))
                hasUpper = true;
            else if(Character.isLowerCase(ch))
                hasLower = true;
            else if(Character.isDigit(ch))
                hasDigit = true;
            else
                hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;*/
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
