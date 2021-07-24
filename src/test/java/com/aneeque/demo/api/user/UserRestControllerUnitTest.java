package com.aneeque.demo.api.user;

import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsServiceImpl;
import com.aneeque.demo.role.DefaultRole;
import com.aneeque.demo.role.Rolez;
import com.aneeque.demo.user.SignUpCmd;
import com.aneeque.demo.user.User;
import com.aneeque.demo.user.UserServiceImpl;
import com.aneeque.demo.user.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




public class UserRestControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    CustomUtil customUtil;

    @MockBean
    UserUtil userUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserRestController userRestController;

    private String baseEndpoint = "http://localhost:8089/api";

    SignUpCmd signUpCmd = null;
    List<Rolez> rolezList = null;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService= mock(UserServiceImpl.class);
        customUtil= mock(CustomUtil.class);
        userUtil= mock(UserUtil.class);
        jwtUtil= mock(JwtUtil.class);
        userRestController = new UserRestController(userService, customUtil, userUtil, jwtUtil);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        //
        signUpCmd = new SignUpCmd();
        signUpCmd.setUsername("speedo");
        signUpCmd.setPassword("abcd");
        signUpCmd.setConfirmPassword("abcd");
        signUpCmd.setDor(LocalDate.now());
        signUpCmd.setEmail("talk2speedy@yahoo.com");
        rolezList = new ArrayList<Rolez>();
        rolezList.add(new DefaultRole());
        signUpCmd.setRolez(rolezList);
    }

    @Test
    void signup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        User user = new User(signUpCmd);
        //when
        when(userService.addUser(user)).thenReturn(new User(signUpCmd));
        mockMvc.perform(
                post(baseEndpoint+"/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpCmd)) )
                .andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        String username = "speedo";

        when(userService.getUser(username)).thenReturn(new User());

        mockMvc.perform(
                get("http://localhost:8089/api/user/xxxxx")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(
                get(baseEndpoint+"/user/all")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(
                delete(baseEndpoint+"/user/delete/speedoxsdfhj")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllUsers() throws Exception {
        mockMvc.perform(
                delete(baseEndpoint+"/user/delete/all")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isNoContent());
    }
}