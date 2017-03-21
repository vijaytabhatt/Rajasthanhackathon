/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import com.mongodb.BasicDBObject;
import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author AjayS
 */
@Entity(noClassnameStored = true, value = "settings")
public class Settings {

    @Id
    private ObjectId id;

    private String recordType;

    private Date timeStamp;

    private BasicDBObject settingDetails;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {

        if (id != null) {
            ObjectId objectId = new ObjectId(id);
            this.id = objectId;
        }
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BasicDBObject getSettingDetails() {
        return settingDetails;
    }

    public void setSettingDetails(BasicDBObject settingDetails) {
        this.settingDetails = settingDetails;
    }

}
