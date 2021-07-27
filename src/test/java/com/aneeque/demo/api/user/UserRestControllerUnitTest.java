package com.aneeque.demo.api.user;

import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsImpl;
import com.aneeque.demo.commons.security.UserDetailsServiceImpl;
import com.aneeque.demo.role.DefaultRole;
import com.aneeque.demo.role.Rolez;
import com.aneeque.demo.user.SignUpCmd;
import com.aneeque.demo.user.User;
import com.aneeque.demo.user.UserServiceImpl;
import com.aneeque.demo.user.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    UserDetails userDetails;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserRestController userRestController;

    private String baseEndpoint = "http://localhost:8089/api";

    private static SignUpCmd signUpCmd = null;
    private static List<Rolez> rolezList = null;

    String jwt = "qwerty";
    private static String username = "test_user";
    private static String password = "test_password";
    private static String email = "test@email.com";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService= mock(UserServiceImpl.class);
        customUtil= mock(CustomUtil.class);
        userUtil= mock(UserUtil.class);
        jwtUtil= mock(JwtUtil.class);
        userDetails= mock(UserDetailsImpl.class);
        userRestController = new UserRestController(userService, customUtil, userUtil, jwtUtil);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        //
        signUpCmd = new SignUpCmd();
        signUpCmd.setUsername(username);
        signUpCmd.setPassword(password);
        signUpCmd.setConfirmPassword(password);
        signUpCmd.setDor(LocalDate.now());
        signUpCmd.setEmail(email);
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
        when(userService.addUser(user)).thenReturn(user);
        when(jwtUtil.generateToken(userDetails)).thenReturn(jwt);
        MvcResult mvcResult = mockMvc.perform(
                post(baseEndpoint+"/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpCmd)) )
                .andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        ObjectNode actual = mapper.readValue(actualResponse, ObjectNode.class);
        actual.put("jwt", jwt);

        ObjectNode expected = mapper.createObjectNode();
        expected.put("jwt", jwt);
        expected.put("username", signUpCmd.getUsername());

        System.out.println("actual> "+actual);
        System.out.println("expected> "+expected);

        assertThat(expected).isEqualTo(actual);
        assertThat(expected).isEqualTo(actual);
        assertThat(expected.get("username")).isEqualTo(actual.get("username"));

    }

    @Test
    void getUser() throws Exception {
        //given
//        String username = "test_user";
        User expected = new User(signUpCmd);
        //when
        when(userService.getUser(signUpCmd.getUsername())).thenReturn(expected);
        MvcResult mvcResult = mockMvc.perform(
                get(baseEndpoint+"/user/"+signUpCmd.getUsername())
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        //then
        User actual = mapper.readValue(actualResponse, User.class);
        assertThat(expected).isEqualTo(actual);
        assertThat(expected.getUsername()).isEqualTo(actual.getUsername());
    }

    @Test
    void getAllUsers() throws Exception {
        //given
        User expectedUser = new User(signUpCmd);
        List<User> expectedList = new ArrayList<>();
        expectedList.add(expectedUser);
        //when
        when(userService.getAllUsers()).thenReturn(expectedList);

        MvcResult mvcResult = mockMvc.perform(
                get(baseEndpoint+"/user/all")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        //then
        User[] actual = mapper.readValue(actualResponse, User[].class);
        List<User> actualList = Arrays.asList(actual);
        //then
        assertThat(expectedList).isEqualTo(actualList);
        assertThat(expectedList.size()).isEqualTo(actualList.size());
        assertThat(expectedList.get(0).getUsername()).isEqualTo(actualList.get(0).getUsername());
    }

    @Test
    void deleteUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                delete(baseEndpoint+"/user/delete/"+signUpCmd.getUsername())
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    void deleteAllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                delete(baseEndpoint+"/user/delete/all")
                        .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isNoContent()).andReturn();
    }
}