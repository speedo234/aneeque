/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aneeque.demo.role;

import com.aneeque.demo.user.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.cache.annotation.CacheConfig;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Isidienu Chudi
 */


//@JsonDeserialize(as = DefaultRole.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultRole.class, name = "defaultRole")
})
@Entity
public abstract class Rolez implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String roleName;
    protected String privilege;
    protected boolean active;

    @ManyToOne
    protected User user;


    public Rolez() {
    }

    public abstract String getRoleName();

    protected abstract void setRoleName(String roleName);

    public abstract String getPrivilege();

    protected abstract void setPrivilege(String privilege);

    public abstract boolean isActive();

    protected abstract void setActive(boolean active);

}
