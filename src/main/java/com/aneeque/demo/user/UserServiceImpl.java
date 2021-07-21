package com.aneeque.demo.user;


import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.exception.ApplicationException;
import com.aneeque.demo.exception.AuthenticationException;
import com.aneeque.demo.exception.EntityNotFoundException;
import com.aneeque.demo.exception.ValidationException;
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

//    @Autowired
    UserRepository userRepository;


    @Autowired
    CustomUtil customUtil;


    @Value("${default.user.role.name}")
    private String defaultUserRole;



    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String email = username;//email may be passed as the username.
        User user = userRepository.findByUsernameOrEmail(username, email);

        if(user == null)
            throw new AuthenticationException("invalid username");

            boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
            if (!isCorrectPassword)
                throw new AuthenticationException("invalid password");

        return user;
    }

    @Override
    public User addUser(SignUpCmd signUpCmd) {
        User newRegisteredUser = new User();
        newRegisteredUser.setUsername(signUpCmd.getUsername());
        newRegisteredUser.setPassword(bCryptPasswordEncoder.encode(signUpCmd.getPassword()));
        newRegisteredUser.setEmail(signUpCmd.getEmail());
        newRegisteredUser.setDor(LocalDate.now());
        newRegisteredUser.setActive(signUpCmd.isActive());
        newRegisteredUser.setRolez(signUpCmd.getRolez());
        newRegisteredUser.setActive(signUpCmd.isActive());
        return userRepository.save(newRegisteredUser);
    }





    @Override
    public User addUser2(SignUpCmd signUpCmd) throws ApplicationException {
        User user = getUserByUsername(signUpCmd.getUsername());
        if (user != null)
            throw new ApplicationException("user "+signUpCmd.getUsername()+" already exists on the system");
        user = getUserByEmail(signUpCmd.getEmail());
        if (user != null)
            throw new ApplicationException("email "+signUpCmd.getEmail()+" already exists on the system");
        signUpCmd.setActive(true);
        return addUser(signUpCmd);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}