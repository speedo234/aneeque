package com.aneeque.demo.user;


import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.exception.ApplicationException;
import com.aneeque.demo.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.*;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Isidienu Chudi
 */
@Service
public class UserServiceImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //  private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    @Autowired
    CustomUtil customUtil;


    @Value("${default.user.role.name}")
    private String defaultUserRole;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode returnObjectNode = mapper.createObjectNode();

        String email = username;//email may be passed as the username.
        User user = userRepository.findByUsernameOrEmail(username, email);

        try{
            logger.info("db password===---<> {} ", user.getPassword());
            boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
            logger.info("isCorrectPassword---> {} ", isCorrectPassword);
            if (isCorrectPassword) {
                return user;
            }
        }catch(NullPointerException npe){
            logger.info("login user is probably null...");
            npe.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUser(SignUpCmd signUpCmd) {
        User newRegisteredUser = new User();
        newRegisteredUser.setUsername(signUpCmd.getUsername());
        newRegisteredUser.setPassword(bCryptPasswordEncoder.encode(signUpCmd.getPassword()));
        newRegisteredUser.setEmail(signUpCmd.getEmail());
        newRegisteredUser.setDor(LocalDate.now());
        newRegisteredUser.setActive(signUpCmd.isActive());
        newRegisteredUser.setRolez(signUpCmd.getRolez());
        /*return*/ userRepository.save(newRegisteredUser);
    }



    @Override
    public User updateUser(UserCmd userRequestCmd) throws EntityNotFoundException {
        User updateUser = userRepository.findByUsername(userRequestCmd.getUsername());
        if(updateUser == null){
            throw new EntityNotFoundException(userRequestCmd.getUsername()+" was not found on the system");
        }
        if (userRequestCmd.getPassword() != null) {
            updateUser.setPassword(bCryptPasswordEncoder.encode(userRequestCmd.getPassword()));
        }
        if (userRequestCmd.getFirstName() != null) {
            updateUser.setFirstName(userRequestCmd.getFirstName().trim());
        }
        if (userRequestCmd.getLastName() != null) {
            updateUser.setLastName(userRequestCmd.getLastName().trim());
        }
        if (userRequestCmd.getEmail() != null) {
            updateUser.setEmail(userRequestCmd.getEmail());
        }
        if (userRequestCmd.getPhoneNumber() != null) {
            updateUser.setPhoneNumber(userRequestCmd.getPhoneNumber().trim());
        }

/*        if (userRequestCmd.getRolez() != null) {
            updateUser.setRolez(userRequestCmd.getRolez());
        }*/

        if (userRequestCmd.getDob() != null) {
            updateUser.setDob(userRequestCmd.getDob());
        }
        if (userRequestCmd.getDor() != null) {
            updateUser.setDor(userRequestCmd.getDor());
        }

        /*if (userRequestCmd.getViews() != null) {
            updateUser.setViews(userRequestCmd.getViews());
        }*/

        if (userRequestCmd.getRolez() != null) {//cross check this condition to confirm it does not break anything...
            logger.info("getRolez is not null... so save rolez data...");
            updateUser.getRolez().clear();
            updateUser.getRolez().addAll(userRequestCmd.getRolez());
//            updateUser.setRolez(userRequestCmd.getRolez());
        }
        try {
            updateUser.setActive(userRequestCmd.isActive());

        } catch (NullPointerException npe) {
            logger.info("NullPointerException npe : {}", npe);
        }
        return userRepository.save(updateUser);
    }


    @Override
    public void addUser2(SignUpCmd signUpCmd) throws ApplicationException {
        User user = getUserByUsername(signUpCmd.getUsername());
        if (user != null){
            throw new ApplicationException("user "+signUpCmd.getUsername()+" already exists on the system");
        }
        user = getUserByEmail(signUpCmd.getEmail());
        if (user != null){
            throw new ApplicationException("email "+signUpCmd.getEmail()+" already exists on the system");
        }
        addUser(signUpCmd);
    }


}