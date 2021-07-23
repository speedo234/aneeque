/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import com.aneeque.demo.user.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Isidienu Chudi
 */

@Entity
@JsonPropertyOrder({"name", "id"})
public class DefaultRole extends Rolez implements Serializable {


    public DefaultRole() {
        setRoleName("ROLE_USER");
        setPrivilege("user");
        setActive(true);
    }


    public String getRoleName() {
        return roleName;
    }

    protected void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPrivilege() {
        return privilege;
    }

    protected void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Rolez{" +
                "roleName='" + roleName + '\'' +
                ", privilege='" + privilege + '\'' +
                '}';
    }


}
