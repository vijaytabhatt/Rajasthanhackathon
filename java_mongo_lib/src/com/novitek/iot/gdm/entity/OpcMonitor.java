/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author vijaytab
 */
@Entity(value = "opcmonitors", noClassnameStored = true)
public class OpcMonitor {

    @Id
    private ObjectId id;
    private String name;
    private BasicDBObject monitorNodeIds;
    private ObjectId opcSubscription;
    private int samplingInterval;
    private int queueSize;
    private int __v;

    public String getOpcSubscription() {
        return opcSubscription.toHexString();
    }

    public void setOpcSubscription(String opcSubscription) {
        ObjectId objectId = new ObjectId(opcSubscription);
        this.opcSubscription = objectId;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        ObjectId objectId = new ObjectId(id);
        this.id = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BasicDBObject getMonitorNodeIds() {
        return monitorNodeIds;
    }

    public void setMonitorNodeIds(BasicDBObject monitorNodeIds) {
        this.monitorNodeIds = monitorNodeIds;
    }

    public int getSamplingInterval() {
        return samplingInterval;
    }

    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    

}
