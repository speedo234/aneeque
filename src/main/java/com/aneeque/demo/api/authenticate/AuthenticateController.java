/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.api.authenticate;

//import com.fasterxml.jackson.databind.node.ObjectNode;

import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsImpl;
import com.aneeque.demo.exception.AuthenticationException;
import com.aneeque.demo.exception.ValidationException;
import com.aneeque.demo.role.Rolez;
import com.aneeque.demo.user.User;
import com.aneeque.demo.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//import org.json.JSONObject;

/**
 * @author Isidienu Chudi
 */
@RestController
//@RequestMapping("/api")
public class AuthenticateController {

    static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUtil customUtil;

    @Autowired
    private JwtUtil jwtUtil;




    @PostMapping("/api/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@Validated @RequestBody AuthenticateRequestCmd authenticateRequestCmd,
                                                       BindingResult result) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        if(result.hasErrors()){
            for (FieldError fieldError: result.getFieldErrors()) {
                throw new ValidationException(fieldError.getDefaultMessage());
            }
        }
        User user = userService.getUserByUsernameAndPassword(authenticateRequestCmd.getUsername(), authenticateRequestCmd.getPassword());
        String jwt = null;
        jwt = jwtUtil.generateToken(new UserDetailsImpl(user));
        objectNode.put("jwt", jwt);
        objectNode.put("username", user.getUsername());
        String rolezString = "";
        for(Rolez rolez: user.getRolez()){
            rolezString = rolez.getRoleName();//consider implementing for multiple rolez belonging to one user.
        }
        objectNode.put("role", rolezString);
        return ResponseEntity.ok(objectNode);
    }



    @PostMapping("/validate/token")
    public ResponseEntity<?> validAuthenticationToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            bearerToken = bearerToken.substring(7, bearerToken.length());
        }
//        String username = new JwtUtil().extractUsername(bearerToken);
        boolean isTokenExpired = jwtUtil.isTokenExpired(bearerToken);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        if (!isTokenExpired) {
            objectNode.put("success", "token is valid");
            return ResponseEntity.ok(objectNode.put("success", "token is valid"));
        } else {
            throw new AuthenticationException("token is invalid");
        }
    }

}
