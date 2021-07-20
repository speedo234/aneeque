package com.aneeque.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    User findByUsernameAndPassword(String username, String password);


}
