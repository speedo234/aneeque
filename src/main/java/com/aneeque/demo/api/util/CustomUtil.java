/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.api.util;

import com.aneeque.demo.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author talk2
 */
@Service
public class CustomUtil {

    @Value("${email.regex}")
    String emailRegex;

    @Value("${phone.regex}")
    String phoneRegex;


    public void isValidEmail(String email){
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new ValidationException("invalid email address.");
        }
    }


    public void isValidPhone(String phoneNumber){
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches())
            throw new ValidationException("Invalid Phone Number. Format should be 11 digits without spacing.");
    }


    public void sendEmail(){ }


}
