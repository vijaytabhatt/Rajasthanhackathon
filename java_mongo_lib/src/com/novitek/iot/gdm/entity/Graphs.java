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
 * This is Entity class
 * @author AjayS
 */
@Entity(value = "graphs", noClassnameStored = true)
public class Graphs {
    
    @Id
    private ObjectId id;
    
    private ObjectId deviceID;
    
    private String deviceName;
    
    private String[] yDataTag;
    
    private String xDataTag;
    
    private String yLabel;
    
    private String xLabel;
    
    private String graphName;
    
    private boolean isPinned;
    
    private String graphType;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String _id) {
        ObjectId objectId = new ObjectId(_id);
        this.id = objectId;
    }

    public String getDeviceID() {
        if(deviceID==null){
            return null;
        }
        return deviceID.toHexString();
    }

    public void setDeviceID(String deviceID) {
        ObjectId objectId = new ObjectId(deviceID);
        this.deviceID = objectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String[] getyDataTag() {
        return yDataTag;
    }

    public void setyDataTag(String[] yDataTag) {
        this.yDataTag = yDataTag;
    }

    public String getxDataTag() {
        return xDataTag;
    }

    public void setxDataTag(String xDataTag) {
        this.xDataTag = xDataTag;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public boolean isIsPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }
    
    
    
    
}
