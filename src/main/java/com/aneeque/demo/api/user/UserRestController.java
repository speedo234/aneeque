package com.aneeque.demo.api.user;

import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsImpl;
import com.aneeque.demo.exception.ValidationException;
import com.aneeque.demo.user.SignUpCmd;
import com.aneeque.demo.user.UserService;
import com.aneeque.demo.user.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserRestController {


    static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUtil customUtil;

    @Autowired
    private UserUtil userUtil;



    @Value("${default.user.role.name}")
    private String defaultUserRole;

    @Value("${default.admin.role.name}")
    private String defaultAdminRole;

    @Value("${cors.allowed.origins}")
    String corsAllowedOrigins;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpCmd signUpCmd, BindingResult result) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode returnObjectNode = mapper.createObjectNode();
        if(result.hasErrors()){
            for (FieldError fieldError: result.getFieldErrors()) {
                throw new ValidationException(fieldError.getDefaultMessage());
            }
        }

        customUtil.isValidEmail(signUpCmd.getEmail());
        userUtil.checkPasswordsMatch(signUpCmd.getPassword(), signUpCmd.getConfirmPassword());
        userUtil.setDefaultUserRole(signUpCmd);
        userService.addUser2(signUpCmd);
        String jwt = jwtUtil.generateToken(new UserDetailsImpl(signUpCmd.getUsername()));
        returnObjectNode.put("jwt", jwt);
        returnObjectNode.put("username", signUpCmd.getUsername());
        return ResponseEntity.ok(returnObjectNode);
    }


}
