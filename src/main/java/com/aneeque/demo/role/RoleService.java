/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import java.util.List;

/**
 * @author Isidienu Chudi
 */
public interface RoleService {

    public Rolez getRoleByRole(String role);

    public List<Rolez> getAllRoles();

    public Rolez addRoles(RoleCmd roleCmd);

    public Rolez getRolesByRoleName(String roleName);

    public Rolez updateRole(RoleCmd roleCmd);

    public Rolez save(Rolez role);

    public void deleteAll();
}
