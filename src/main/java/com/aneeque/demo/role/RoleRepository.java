/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Isidienu Chudi
 */
@Repository
public interface RoleRepository extends JpaRepository<Rolez, Long> {

    Rolez findByRoleName(String role);

}
