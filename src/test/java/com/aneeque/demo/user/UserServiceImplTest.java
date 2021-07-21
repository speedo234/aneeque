package com.aneeque.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl();

    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getUserByUsernameAndPassword() {
    }

    @Test
    void addUser() {
    }

    @Test
    void addUser2() {
    }

    @Test
    void getAllUsers() {
    }
}