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
@Entity(value = "opcsubscriptions", noClassnameStored = true)
public class OpcSubscription {

    @Id
    private ObjectId id;
    private String name;
    private ObjectId opcSession;

    private int requestedPublishingInterval;
    private int requestedLifetimeCount;
    private int requestedMaxKeepAliveCount;
    private int maxNotificationsPerPublish;
    private int priority;
    private boolean publishingEnabled;
    private int __v;


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

    public String getOpcSession() {
        return opcSession.toHexString();
    }

    public void setOpcSession(String opcSession) {
        ObjectId objectId = new ObjectId(opcSession);
        this.opcSession = objectId;
    }

    public int getRequestedPublishingInterval() {
        return requestedPublishingInterval;
    }

    public void setRequestedPublishingInterval(int requestedPublishingInterval) {
        this.requestedPublishingInterval = requestedPublishingInterval;
    }

    public int getRequestedLifetimeCount() {
        return requestedLifetimeCount;
    }

    public void setRequestedLifetimeCount(int requestedLifetimeCount) {
        this.requestedLifetimeCount = requestedLifetimeCount;
    }

    public int getRequestedMaxKeepAliveCount() {
        return requestedMaxKeepAliveCount;
    }

    public void setRequestedMaxKeepAliveCount(int requestedMaxKeepAliveCount) {
        this.requestedMaxKeepAliveCount = requestedMaxKeepAliveCount;
    }

    public int getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    public void setMaxNotificationsPerPublish(int maxNotificationsPerPublish) {
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    public void setPublishingEnabled(boolean publishingEnabled) {
        this.publishingEnabled = publishingEnabled;
    }
}
