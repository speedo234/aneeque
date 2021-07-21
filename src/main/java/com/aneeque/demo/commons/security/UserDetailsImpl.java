package com.aneeque.demo.commons.security;



import com.aneeque.demo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Isidienu Chudi
 */
public class UserDetailsImpl implements UserDetails {

    static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

    private String username;
    private String password;
    private boolean active;
    List<GrantedAuthority> authorityList;

    public UserDetailsImpl(String username) {
        this.username = username;
    }

    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorityList = new ArrayList<>();
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