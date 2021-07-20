/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import com.aneeque.demo.user.User;


import javax.persistence.ManyToOne;

/**
 * @author Isidienu Chudi
 */

public class RoleCmd {


    private String roleName;
    private String privilege;
    private boolean active;

    @ManyToOne
    private User user;

    public RoleCmd(String roleName, String privilege, boolean active) {
        this.roleName = roleName;
        this.privilege = privilege;
        this.active = active;
    }


    public RoleCmd() {
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
