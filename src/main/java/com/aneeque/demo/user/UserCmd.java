package com.aneeque.demo.user;


import com.aneeque.demo.role.Rolez;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class UserCmd {



    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username")
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Column(unique=true)
    private String email;

    private boolean active;

    @PastOrPresent(message = "Date of Registration Must be a Past or Present Date.")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDate dor;


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rolez> rolez;


    public UserCmd() {
    }

    public UserCmd(@Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username") String username,
                   String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rolez> getRolez() {
        return rolez;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRolez(List<Rolez> rolez) {
        this.rolez = rolez;
    }

    public LocalDate getDor() {
        return dor;
    }

    public void setDor(LocalDate dor) {
        this.dor = dor;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
