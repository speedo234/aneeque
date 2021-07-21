package com.aneeque.demo.user;

import com.aneeque.demo.exception.ApplicationException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);

    User addUser(SignUpCmd signUpCmd);

    User addUser2(SignUpCmd signUpCmd) throws ApplicationException;

    List<User> getAllUsers();

}
