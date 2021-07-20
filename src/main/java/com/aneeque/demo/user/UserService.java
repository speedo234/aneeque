package com.aneeque.demo.user;

import com.aneeque.demo.exception.ApplicationException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public User getUserByUsername(String username);

    public User getUserByEmail(String email);

    public User getUserByPhoneNumber(String phoneNumber);

    public User getUserByUsernameAndPassword(String username, String password);

    public User updateUser(UserCmd userRequestCmd);

    public void addUser(SignUpCmd signUpCmd);

    public void addUser2(SignUpCmd signUpCmd) throws ApplicationException;

}
