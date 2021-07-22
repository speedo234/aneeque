package com.aneeque.demo.user;


import com.aneeque.demo.api.util.CustomUtil;
import com.aneeque.demo.exception.ApplicationException;
import com.aneeque.demo.exception.AuthenticationException;
import com.aneeque.demo.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Isidienu Chudi
 */
@Service
public class UserServiceImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String redisCacheValue = "users";
    public static final String KEY = "cacheKey";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUtil customUtil;

    @Value("${default.user.role.name}")
    private String defaultUserRole;


    public UserServiceImpl() {
    }

//    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Cacheable(value = redisCacheValue, key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    @Cacheable(value = redisCacheValue, key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    @Cacheable(value = redisCacheValue, key = "#username")
    public User getUserByUsernameAndPassword(String username, String password) {
        String email = username;//email may be passed as the username.
        User user = userRepository.findByUsernameOrEmail(username, email);
        if(user == null)
            throw new EntityNotFoundException("username not found");
        boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!isCorrectPassword)
            throw new AuthenticationException("incorrect password");
        return user;
    }

    @Override
    @CachePut(value = redisCacheValue, key = "#signUpUser.username")
    public User addUser(User signUpUser) {
        User user = getUserByUsername(signUpUser.getUsername());
        if (user != null)
            throw new ApplicationException("user "+signUpUser.getUsername()+" already exists on the system");
        user = getUserByEmail(signUpUser.getEmail());
        if (user != null)
            throw new ApplicationException("email "+signUpUser.getEmail()+" already exists on the system");
        signUpUser.setActive(true);
        signUpUser.setPassword(bCryptPasswordEncoder.encode(signUpUser.getPassword()));
        return userRepository.save(signUpUser);
    }

    @Override
    @Cacheable(value = redisCacheValue, key = "#username")
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @CacheEvict(value = redisCacheValue, allEntries = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    @CacheEvict(value = redisCacheValue, key = "#username")
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User does not exist.");
        userRepository.delete(user);
    }



    @Override
    @CacheEvict(value = redisCacheValue, allEntries = true)
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}