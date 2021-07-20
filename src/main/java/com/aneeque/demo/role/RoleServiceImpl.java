/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Isidienu Chudi
 */
@Service
public class RoleServiceImpl implements RoleService {

    static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Rolez getRoleByRole(String role) {
        return roleRepository.findByRoleName(role);
    }


    @Override
    public List<Rolez> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Rolez addRoles(RoleCmd roleCmd) {
        Rolez newRolez = new Rolez();
        newRolez.setPrivilege(roleCmd.getPrivilege());
        newRolez.setRoleName(roleCmd.getRoleName());
        newRolez.setActive(roleCmd.isActive());
        newRolez = roleRepository.save(newRolez);
        return newRolez;
    }


    @Override
    public Rolez getRolesByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }


    @Override
    public Rolez save(Rolez role) {
        return roleRepository.save(role);
    }

    @Override
    public Rolez updateRole(RoleCmd roleCmd) {

        Rolez updateRole = roleRepository.findByRoleName(roleCmd.getRoleName());

        if (roleCmd.getPrivilege() != null) {
            updateRole.setPrivilege(roleCmd.getPrivilege());
        }

        if (roleCmd.getRoleName() != null) {
            updateRole.setRoleName(roleCmd.getRoleName());
        }


        try {
            updateRole.setActive(roleCmd.isActive());
        } catch (NullPointerException npe) {
            logger.info("NullPointerException: {}", npe);
        }


        return roleRepository.save(updateRole);
    }


    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }


}
