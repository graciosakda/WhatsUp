package com.graciosakda.whatsup.Utilities;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by graciosa.kda on 27/08/2017.
 */

public class Validation {
    private static String passwordRegex = "^(?=.*\\w)[a-zA-Z0-9.#$%^&+=]{6,20}$";
    private static Pattern passwordPattern = Pattern.compile(passwordRegex);
    private static String usernameRegex = "^(?=.*\\w)[a-zA-Z0-9]{6,12}$";
    private static Pattern usernamePattern = Pattern.compile(usernameRegex);

    public static boolean isValidUsername(String username){
       return usernamePattern.matcher(username).matches();
    }

    public static boolean isValidPassword(String password){
        return passwordPattern.matcher(password).matches();
    }

    public static boolean isValidPassword2(String password1, String password2){
        return password1.matches(password2);
    }

    public static boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
