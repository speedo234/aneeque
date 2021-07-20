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
//@Embeddable
public class Rolez {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String roleName;
    private String privilege;
    private boolean active;


    @ManyToOne
    private User user;


    public Rolez(String roleName, String privilege, boolean active) {
        this.roleName = roleName;
        this.privilege = privilege;
        this.active = active;
    }

    public Rolez() {

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

    @Override
    public String toString() {
        return "Rolez{" +
                "roleName='" + roleName + '\'' +
                ", privilege='" + privilege + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rolez)) return false;
        Rolez rolez = (Rolez) o;
        return Objects.equals(id, rolez.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
