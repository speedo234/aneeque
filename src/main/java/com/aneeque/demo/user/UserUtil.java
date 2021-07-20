package com.aneeque.demo.user;


import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.exception.ValidationException;
import com.aneeque.demo.role.Rolez;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserUtil {

    @Value("${default.user.role.name}")
    private String defaultUserRole;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUtil customUtil;


    public void checkPasswordsMatch(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            throw new ValidationException("password and confirm passwords do not match");
        }
    }




    public void setDefaultUserRole(SignUpCmd signUpCmd){
        List<Rolez> rolezList;
        rolezList = new ArrayList<>();
        Rolez rolez = new Rolez();
        rolez.setActive(true);
        rolez.setPrivilege("user");
        rolez.setRoleName("ROLE_USER");
        rolezList.add(rolez);
        signUpCmd.setRolez(rolezList);
    }


    public void setUserRole(UserCmd userCmd ){
        List<Rolez> rolezList = new ArrayList<>();
        Rolez rolez = new Rolez();
        rolez.setActive(true);
        rolez.setPrivilege("user");
        rolez.setRoleName("ROLE_USER");
        rolezList.add(rolez);
        userCmd.setRolez(rolezList);
    }




    public void checkIfDoBUpdate(UserCmd userCmd, User user){
        if(userCmd.getDob() != null && user.getDob() != null && (!user.getDob().equals(userCmd.getDob()))){
            userCmd.setDob(null);
            throw new ValidationException("You cannot change your Date of Birth after initial update");
        }
    }



}
