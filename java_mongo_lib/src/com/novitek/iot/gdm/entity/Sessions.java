/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author AjayS
 */
@Entity(value = "session", noClassnameStored = true)
public class Sessions {

    @Id
    private ObjectId id;

    private String session;

    private String token;

    private ObjectId user;

    private Date expires;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        ObjectId objectId = new ObjectId(id);
        this.id = objectId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user.toHexString();
    }

    public void setUser(String user) {
        ObjectId objectId = new ObjectId(user);
        this.user = objectId;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

}
