/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import com.aneeque.demo.user.User;


import javax.persistence.*;
import java.util.Objects;

/**
 * @author Isidienu Chudi
 */

@Entity
public abstract class Rolez {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String roleName;
    protected String privilege;
    protected boolean active;

    @ManyToOne
    protected User user;


    public abstract String getRoleName();

    protected abstract void setRoleName(String roleName);

    public abstract String getPrivilege();

    protected abstract void setPrivilege(String privilege);

    public abstract boolean isActive();

    protected abstract void setActive(boolean active);

}
