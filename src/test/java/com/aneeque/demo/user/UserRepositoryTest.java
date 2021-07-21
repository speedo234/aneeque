package com.aneeque.demo.user;

import com.aneeque.demo.role.DefaultRole;
import com.aneeque.demo.role.Rolez;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {


    static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    UserRepository userRepository;


    String username = null;
    String password = null;
    String email = null;
    List<Rolez> rolezList = null;
    Rolez role = null;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        username = "glory";
        password = "glory";
        email = "glory@yahoo.com";
        rolezList = new ArrayList<>();
        role = new DefaultRole();
        //
        User newRegisteredUser = new User();
        newRegisteredUser.setUsername(username);
        newRegisteredUser.setPassword(password);
        newRegisteredUser.setEmail(email);
        newRegisteredUser.setDor(LocalDate.now());
        newRegisteredUser.setActive(true);
        newRegisteredUser.setRolez(rolezList);
        userRepository.save(newRegisteredUser);
    }

    @Test
    void findByUsername() {
        //when
        User actual = userRepository.findByUsername(username);
        String expected = username;
        //then
        assertThat(expected).isEqualTo(actual.getUsername());
    }


    @Test
    void findByEmail() {
        //when
        User actual = userRepository.findByEmail(email);
        String expected = email;
        //then
        assertThat(expected).isEqualTo(actual.getEmail());
    }


    @Test
    void findByUsernameOrEmail() {
        //when
        User actual = userRepository.findByUsernameOrEmail(username, email);
        String expectedUsername = username;
        String expectedEmail = email;
        //then
        assertThat(expectedUsername).isEqualTo(actual.getUsername());
        assertThat(expectedEmail).isEqualTo(actual.getEmail());
    }

}