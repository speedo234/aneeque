//package com.aneeque.demo.user;
//
//import com.sun.javafx.collections.MappingChange;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class UserRepositoryImpl implements UserRepository {
//
//    private RedisTemplate<String, User> redisTemplate;
//    private HashOperations hashOperations;
//
//
//    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        hashOperations = redisTemplate.opsForHash();
//    }
//
//    @Override
//    public void save(User user) {
//        hashOperations.put("USER", user.getId(), user);
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return (User)hashOperations.get("USER", username);
//    }
//
//    @Override
//    public User findByUsernameOrEmail(String username, String email) {
//        return (User)hashOperations.get("USER", username);
//    }
//
//    @Override
//    public User findByEmail(String email) {
//        return (User)hashOperations.get("USER", email);
//    }
//
//    @Override
//    public List<User> findAll() {
//        return (List<User>)hashOperations.entries("USER");
//    }
//}
