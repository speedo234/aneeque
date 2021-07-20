package com.aneeque.demo.commons.security;


import com.aneeque.demo.user.User;
import com.aneeque.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Isidienu Chudi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new UserDetailsImpl(username);
        User user = userRepository.findByUsername(username);
//            return user.map(UserDetailsImpl::new).get();

        if (user == null)
            throw new UsernameNotFoundException(username + " not found on the system...");

//            UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
//            System.out.println("rolez are => "+userDetailsImpl.getAuthorities().toString());

        return new UserDetailsImpl(user);
    }

}