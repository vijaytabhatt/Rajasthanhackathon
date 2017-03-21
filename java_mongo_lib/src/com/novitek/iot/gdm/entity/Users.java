/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author AjayS
 */
@Entity(value = "users", noClassnameStored = true)
public class Users {

    @Id
    private ObjectId id;

    private String salt;
    private String username;
    private String password;
    private String provider;
    private String displayName;
    private Date created;
    private String[] roles;
    private String email;
    private String lastName;
    private String firstName;
    int __v;
    Layout layout;
    private Date updated;
    private String viewAnimation;
    private Date notificationsAfterDate;
    private String resetToken;
    private Date resetExpiry;

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getResetExpiry() {
        return resetExpiry;
    }

    public void setResetExpiry(Date resetExpiry) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        resetExpiry = calendar.getTime();
        this.resetExpiry = resetExpiry;
    }

    public String getId() {
        try {
            return id.toHexString();
        } catch (Exception ex) {
            return "";
        }
    }

    public void setId(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            this.id = objectId;
        } catch (Exception ex) {

        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getV() {
        return __v;
    }

    public void setV(int __v) {
        this.__v = __v;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getViewAnimation() {
        return viewAnimation;
    }

    public void setViewAnimation(String viewAnimation) {
        this.viewAnimation = viewAnimation;
    }

    public Date getNotificationsAfterDate() {
        return notificationsAfterDate;
    }

    public void setNotificationsAfterDate(Date notificationsAfterDate) {
        this.notificationsAfterDate = notificationsAfterDate;
    }

}
