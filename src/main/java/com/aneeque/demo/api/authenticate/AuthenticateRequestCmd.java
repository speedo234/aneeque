/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.api.authenticate;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Isidienu Chudi
 */

public class AuthenticateRequestCmd {

    @NotBlank(message = "Username field is empty")
    @NotEmpty(message = "Username field is empty")
    @Size(min = 4, max = 50, message = "Username/email must be between 4 and 50 characters")
    //@Pattern(regexp="^[a-zA-Z0-9]{3}",message="length must be 3")
    private String username;
    @NotBlank(message = "Password field is empty")
    @NotEmpty(message = "Password field is empty")
    @Size(min = 4, max = 50, message = "password must be between 4 and 8 characters")
    private String password;
    private String jwt;

    public AuthenticateRequestCmd() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
