package com.aneeque.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private String username = null;
    private String password = null;
    private String email = null;

    private User user = null;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        username = "glory";
        password = "tar565";
        email = "glory@yahoo.com";
        user = new User(username, password, email);
        userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void getUserByUsername() {
        //when
        when(userRepository.findByUsername(username)).thenReturn(user);
        User actual = userService.getUserByUsername(username);
        User expected = user;
        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getUserByEmail() {
        //when
        when(userRepository.findByEmail(email)).thenReturn(user);
        User actual = userService.getUserByEmail(email);
        User expected = user;
        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getUserByUsernameAndPassword() {
        //when
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, password)).thenReturn(true);
        User actual = userService.getUserByUsernameAndPassword(username, password);
        User expected = user;
        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void addUser() {
        //given
        SignUpCmd signUpCmd = new SignUpCmd();
        signUpCmd.setUsername(username);
        signUpCmd.setPassword(password);
        signUpCmd.setEmail(email);
        User signUpUser = new User(signUpCmd);
        //when
        when(userRepository.save(user)).thenReturn(user);
        User actual = userService.addUser(signUpUser);
        User expected = user;
        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void addUser2() {
        //given
        SignUpCmd signUpCmd = new SignUpCmd();
        signUpCmd.setUsername(username);
        signUpCmd.setPassword(password);
        signUpCmd.setEmail(email);
        User signUpUser = new User(signUpCmd);
        //when
        when(userService.getUserByUsername(username)).thenReturn(null);
        when(userService.addUser(signUpUser)).thenReturn(user);
        User actual = userService.addUser(signUpUser);
        User expected = user;
        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getAllUsers() {
        //given
        List<User> userList = new ArrayList<>();
        userList.add(user);
        //when
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actual = userService.getAllUsers();
        List<User> expected = userList;
        //then
        assertThat(expected).isEqualTo(actual);
    }
}