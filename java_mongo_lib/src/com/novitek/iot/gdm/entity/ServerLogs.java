/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import java.util.ArrayList;
import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author AjayS
 */
@Entity(value = "serverlogs", noClassnameStored = true)
public class ServerLogs {

    @Id
    private ObjectId id;

    private Logging logs;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        ObjectId objectId = new ObjectId(id);
        this.id = objectId;
    }

    public Logging getLogs() {
        return logs;
    }

    public void setLogs(Logging logs) {
        this.logs = logs;
    }

    

}
