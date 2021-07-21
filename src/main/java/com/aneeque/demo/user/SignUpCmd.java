package com.aneeque.demo.user;


import com.aneeque.demo.role.Rolez;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;


public class SignUpCmd {



    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username")
    @Column(unique = true)
    private String username;

//    @JsonIgnore
    private String password;

//    @JsonIgnore
    private String confirmPassword;

    @Column(unique=true)
    private String email;

    private boolean active;

    @PastOrPresent(message = "Date of Registration Must be a Past or Present Date.")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDate dor;


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rolez> rolez;


    public SignUpCmd() {
    }

    public SignUpCmd(@Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username") String username,
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

    public void setRolez(List<Rolez> rolez) {
        this.rolez = rolez;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDor() {
        return dor;
    }

    public void setDor(LocalDate dor) {
        this.dor = dor;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    @Override
    public String toString() {
        return "SignUpCmd{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", dor=" + dor +
                ", rolez=" + rolez +
                '}';
    }
}
