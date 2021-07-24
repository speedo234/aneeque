package com.aneeque.demo.api.user;

import com.aneeque.demo.role.DefaultRole;
import com.aneeque.demo.role.Rolez;
import com.aneeque.demo.user.SignUpCmd;
import com.aneeque.demo.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String jwt = null;
    private static String username = "test_user";
    private static String password = "test_password";
    private static String email = "test@email.com";

    @Test
    @Order(1)
    void signup() {
        SignUpCmd signUpCmd = new SignUpCmd();
        signUpCmd.setUsername(username);
        signUpCmd.setPassword(password);
        signUpCmd.setConfirmPassword(password);
        signUpCmd.setDor(LocalDate.now());
        signUpCmd.setEmail(email);
        List<Rolez> rolezList = new ArrayList<Rolez>();
        rolezList.add(new DefaultRole());
        signUpCmd.setRolez(rolezList);
        //
        String signupEndPoint = "http://localhost:"+port+"/api/signup";
        ObjectNode responseObjectNode = testRestTemplate.postForObject(signupEndPoint, signUpCmd, ObjectNode.class);
        assertThat(responseObjectNode).isNotNull();
        assertThat(responseObjectNode.get("username")).isNotNull();
        assertThat(responseObjectNode.get("jwt")).isNotNull();
        //
        String expectedUsername = signUpCmd.getUsername();
        String actualUsername = responseObjectNode.get("username").toString().replaceAll("\"", "");
        assertThat(expectedUsername).isEqualTo(actualUsername);
        jwt = responseObjectNode.get("jwt").toString().replaceAll("\"", "");
    }

    @Test
    @Order(2)
    void getUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String getUserEndPoint = "http://localhost:"+port+"/api/user/"+username;
        ResponseEntity<User> actual = testRestTemplate.exchange(getUserEndPoint, HttpMethod.GET, request, User.class);
        assertThat(actual).isNotNull();

        System.out.println("actual-->> "+actual);

        User actualUser = actual.getBody();
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo(username);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @Order(3)
    void getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String getAllUsersEndPoint = "http://localhost:"+port+"/api/user/all";
        ResponseEntity<User[]> actual = testRestTemplate.exchange(getAllUsersEndPoint, HttpMethod.GET, request, User[].class);
        assertThat(actual).isNotNull();
        User[] actualUsers = actual.getBody();

        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers.length).isGreaterThan(0);

        assertThat(actualUsers[0].getUsername()).isEqualTo(username);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String deleteUserEndPoint = "http://localhost:"+port+"/api/user/delete/"+username;
        ResponseEntity<String> actual = testRestTemplate.exchange(deleteUserEndPoint, HttpMethod.DELETE, request, String.class);

        System.out.println("<<1>>"+actual);

        assertThat(actual).isNotNull();
//        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(5)
    void deleteAllUsers() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String deleteAllUsersEndPoint = "http://localhost:"+port+"/api/user/delete/all";
        ResponseEntity<String> actual = testRestTemplate.exchange(deleteAllUsersEndPoint, HttpMethod.DELETE, request, String.class);
        assertThat(actual).isNotNull();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}