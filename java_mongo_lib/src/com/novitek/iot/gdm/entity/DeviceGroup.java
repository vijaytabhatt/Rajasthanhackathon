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
 * @author AjayS
 */
@Entity(value = "groups", noClassnameStored = true)
public class DeviceGroup {
    
    @Id
    ObjectId id;    
    boolean isOn;
    String desc;
    String name;
    Long __v;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        ObjectId objectId = new ObjectId(id);
        this.id = objectId;
    }   
    
    public boolean isIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getV() {
        return __v;
    }

    public void setV(Long __v) {
        this.__v = __v;
    }
    
    
}
