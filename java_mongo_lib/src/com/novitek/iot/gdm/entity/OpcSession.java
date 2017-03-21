/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author vijaytab
 */
@Entity(value = "opcsessions", noClassnameStored = true)
public class OpcSession {

    @Id
    private ObjectId id;
    private String name;
    private String endpoint;
    private String username;
    private String password;
    private int securityPolicy;
    private int securityMode;
    private int __v;

    public int getSecurityPolicy() {
        return securityPolicy;
    }

    public void setSecurityPolicy(int securityPolicy) {
        this.securityPolicy = securityPolicy;
    }

    public int getSecurityMode() {
        return securityMode;
    }

    public void setSecurityMode(int securityMode) {
        this.securityMode = securityMode;
    }
   
    public String getId() {
        return id.toHexString();
    }

    public void setId(String _id) {
        ObjectId objectId = new ObjectId(_id);
        this.id = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
