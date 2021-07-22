package com.aneeque.demo.user;


import com.aneeque.demo.role.Rolez;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 137214104940822792L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username")
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @Column(unique=true)
    private String email;

    @JsonIgnore
    private boolean active;

    @PastOrPresent(message = "Date of Registration Must be a Past or Present Date.")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDate dor;


    @JsonIgnore
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rolez> rolez;

    public User() {}

    public User(SignUpCmd signUpCmd) {
        if(signUpCmd.getUsername() != null)
            this.username =  signUpCmd.getUsername();
        if(signUpCmd.getPassword() != null)
            this.password =  signUpCmd.getPassword();
        if(signUpCmd.getConfirmPassword() != null)
            this.confirmPassword =  signUpCmd.getConfirmPassword();
        if(signUpCmd.getEmail() != null)
            this.email =  signUpCmd.getEmail();
        if(signUpCmd.isActive())
            this.active =  signUpCmd.isActive();
        if(signUpCmd.getDor() == null)
            this.dor =  LocalDate.now();
        if(signUpCmd.getRolez() != null)
            this.rolez = signUpCmd.getRolez();
    }


    public User(UserCmd userCmd) {
        if(userCmd.getUsername() != null)
            this.username = userCmd.getUsername();
        if(userCmd.getPassword() != null)
            this.password = userCmd.getPassword();
        if(userCmd.getEmail() != null)
            this.email = userCmd.getEmail();
        if(userCmd.isActive())
            this.active = userCmd.isActive();
        if(userCmd.getDor() != null)
            this.dor = userCmd.getDor();
        if(userCmd.getRolez() != null)
            this.rolez = userCmd.getRolez();
    }

    public User(@Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid character in username") String username,
                String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
