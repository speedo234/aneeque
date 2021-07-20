/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.api.util;

import com.aneeque.demo.exception.ApplicationException;
import com.aneeque.demo.exception.ValidationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author talk2
 */
@Service
public class CustomUtil {

//    static final Logger logger = LoggerFactory.getLogger(ImageResourceHttpRequestHandler.class);


    @Value("${email.regex}")
    String emailRegex;

    @Value("${phone.regex}")
    String phoneRegex;




    public UUID generatueUUID() {
       // new UUID(3,3);
        return UUID.randomUUID();
    }

  public String generateRandomAlphaNumericString(int length) {
        return RandomStringUtils.random(length, true, true);
  }


    public String getFileExtensionFromFilePath(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1)).get().toString();//convert Optional to normal String object before returning...
    }


    public String getMediaType(String file_path){
        String fileExtension = getFileExtensionFromFilePath(file_path);
        if(fileExtension.equalsIgnoreCase("jpg"))
            return MediaType.IMAGE_JPEG_VALUE;
        if(fileExtension.equalsIgnoreCase("jpeg"))
            return MediaType.IMAGE_JPEG_VALUE;
        else if(fileExtension.equalsIgnoreCase("png"))
            return MediaType.IMAGE_PNG_VALUE;
        else if(fileExtension.equalsIgnoreCase("pdf"))
            return MediaType.APPLICATION_PDF_VALUE;
        else if(fileExtension.equalsIgnoreCase("txt"))
            return MediaType.TEXT_PLAIN_VALUE;
        else if(fileExtension.equalsIgnoreCase("doc"))
            return "application/msword";
        else if(fileExtension.equalsIgnoreCase("docx"))
            return "application/vnd.openxmlformats-officedocument-wordprocessingml.document";
        else{
//            logger.info("no MediaType handler found for {} fileExtension. So returning null as the extension", fileExtension);
            return null;
        }
    }


    public boolean isCorrectFileType(String fileExtension){
        if(!fileExtension.equalsIgnoreCase("pdf") && !fileExtension.equalsIgnoreCase("doc") &&
                !fileExtension.equalsIgnoreCase("docx")){
            throw new ApplicationException("Wrong file format. Only pdf, doc and docx formats are allowed");
        }
        return true;
    }

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
//        logger.info("PhoneNumber<> " + phoneNumber + " is " + (matcher.matches() ? "valid" : "invalid"));
        if(!matcher.matches())
            throw new ValidationException("Invalid Phone Number. Format should be 11 digits without spacing.");
    }


    public void sendEmail(){ }


}
