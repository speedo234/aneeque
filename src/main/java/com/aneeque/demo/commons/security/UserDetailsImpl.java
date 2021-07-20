package com.aneeque.demo.commons.security;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import lombok.Data;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import com.aneeque.demo.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Isidienu Chudi
 */
//@Data
//@Slf4j
public class UserDetailsImpl implements UserDetails {

    static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

    private String username;
    private String password;
    //    private String roles;
    private boolean active;
    List<GrantedAuthority> authorityList;

    public UserDetailsImpl(String username) {
        this.username = username;
    }

    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        /*below line is deprecated because the user roles are no longer stored on the user table. instead the user roles are stored in a user_rolez table
        with ManyToMany relationship between User and Rolez*/
//        this.authorityList = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        this.authorityList = new ArrayList<>();

        logger.info("::::::just about to retrieve and process user roles for SimpleGrantedAuthourity");
        user.getRolez().forEach((role) -> {
            this.authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        this.active = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


}