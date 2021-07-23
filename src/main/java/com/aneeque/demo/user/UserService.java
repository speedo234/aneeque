package com.aneeque.demo.user;

import com.aneeque.demo.exception.ApplicationException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface UserService {

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);

    User addUser(User signUpUser);

    List<User> getAllUsers();

    void deleteAllUsers();

    void deleteUser(String username);

    User getUser(String username);

}
