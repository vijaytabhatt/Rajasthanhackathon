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
@Entity(value = "devices", noClassnameStored = true)
public class Devices {

    @Id
    ObjectId id;

    ObjectId user;//ObjectId
    ObjectId group;//ObjectId
    ObjectId opcSession;
    ObjectId[] opcMonitors;

    BasicDBObject opcNodeIds;

    String desc;

    String name;
    String protocol;
    Integer opcReadFrequency;
    Object trapList;
    String type;
    String description;
    String sensor;
    boolean syncStatus;
    String syncFrequency;
    boolean deleteAfterSync;
    AlertAction alertAction;
    String syncTime;

    String syncOption;//ObjectId

    boolean alertIsOn;

    boolean isOn;
    String[] topics;

    int __v;

    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BasicDBObject getOpcNodeIds() {

        if (opcNodeIds != null) {

            String json = this.opcNodeIds.toJson();
            return this.opcNodeIds;

        }
        return null;
    }

    public void setOpcNodeIds(BasicDBObject opcNodeIds) {
        this.opcNodeIds = opcNodeIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOpcReadFrequency() {
        return opcReadFrequency;
    }

    public void setOpcReadFrequency(Integer opcReadFrequency) {
        this.opcReadFrequency = opcReadFrequency;
    }

    public String getOpcSession() {
        if (opcSession != null) {
            return opcSession.toHexString();
        }
        return null;
    }

    public void setOpcSession(String opcSession) {
        if (opcSession != null) {
            ObjectId objectId = new ObjectId(opcSession);
            this.opcSession = objectId;
        }
    }

    public String[] getOpcMonitors() {

        if (this.opcMonitors != null) {
            if (this.opcMonitors.length > 0) {

                String[] monitors = new String[opcMonitors.length];
                for (int i = 0; i < opcMonitors.length; i++) {

                    monitors[i] = opcMonitors[i].toHexString();
                }
                return monitors;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public void setOpcMonitors(String[] opcMonitors) {
        if (opcMonitors != null) {
            if (opcMonitors.length > 0) {
                this.opcMonitors = new ObjectId[opcMonitors.length];
                for (int i = 0; i < opcMonitors.length; i++) {
                    ObjectId objectId = new ObjectId(opcMonitors[i]);
                    this.opcMonitors[i] = objectId;
                }
            } else {
                this.opcMonitors = null;
            }
        } else {
            this.opcMonitors = null;
        }

    }

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroup() {
        if (group == null) {
            return null;
        }
        return group.toHexString();
    }

    public void setGroup(String group) {
        if (group != null) {
            ObjectId objectId = new ObjectId(group);
            this.group = objectId;
        }

    }

    public String getUser() {
        if (user == null) {
            return null;
        }
        return user.toHexString();
    }

    public void setUser(String user) {
        if (user != null) {
            ObjectId objectId = new ObjectId(user);
            this.user = objectId;
        }
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getSyncOption() {
        return syncOption;
    }

    public void setSyncOption(String syncOption) {
        this.syncOption = syncOption;
    }

    public String getSyncFrequency() {
        return syncFrequency;
    }

    public void setSyncFrequency(String syncFrequency) {
        this.syncFrequency = syncFrequency;
    }

    public AlertAction getAlertAction() {
        return alertAction;
    }

    public void setAlertAction(AlertAction alertAction) {
        this.alertAction = alertAction;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public Object getTrapList() {
        //return trapList;
        return null;
    }

    public void setTrapList(Object trapList) {
        //this.trapList = trapList;
        this.trapList = null;
    }

    public boolean isAlertIsOn() {
        return alertIsOn;
    }

    public void setAlertIsOn(boolean alertIsOn) {
        this.alertIsOn = alertIsOn;
    }

    public boolean isDeleteAfterSync() {
        return deleteAfterSync;
    }

    public void setDeleteAfterSync(boolean deleteAfterSync) {
        this.deleteAfterSync = deleteAfterSync;
    }

    public boolean isIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getV() {
        return __v;
    }

    public void setV(int __v) {
        this.__v = __v;
    }

}
