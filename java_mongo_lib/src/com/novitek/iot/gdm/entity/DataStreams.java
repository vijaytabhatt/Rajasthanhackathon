/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import com.mongodb.BasicDBObject;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

/**
 * This is DataStreams Entity Class
 *
 * @author AjayS
 */
@Entity(value = "datastreams", noClassnameStored = true)
public class DataStreams {

    @Id
    private ObjectId id;

    private ObjectId deviceId;

    //@Property(value = "data")
    private BasicDBObject data;

    private boolean isSynced;

    private Date timestamp;

    private String deviceName;

  
    @Transient
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000z");

  
    public DataStreams() {

    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public BasicDBObject getData() {
        return data;
    }

    public void setData(BasicDBObject data) {
        this.data = data;
    }

//    public Object getData() {
//        System.err.println("Inside getData"); 
//        BasicDBObject basicDBObject = (BasicDBObject) data;
//        System.out.println(basicDBObject.toString());
//        return data.toString();
//    }
//
//    public void setData(Object data) {
//        System.err.println("Inside setData");
//        System.out.println(data);
//        System.out.println("*********************************************");
//        BasicDBObject basicDBObject = (BasicDBObject) data;
//        this.data = data;
//    }
    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        ObjectId objectId = new ObjectId(id);
        this.id = objectId;
    }

    public String getTimestamp() {
        if (timestamp != null) {

            Instant now = timestamp.toInstant();
            ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);
            //ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, currentZone);

//String timeString = dateFormat.format(this.timestamp);
            String timeString = localDateTime.format(formatter);
            return timeString;

        }
        return null;

    }

    public void setTimestamp(Date timestamp) {

//        Instant now = timestamp.toInstant();
//        ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, currentZone);
//        Date date = Date.from(localDateTime.toInstant());
//        this.timestamp = date;

        if (timestamp != null) {
            this.timestamp = timestamp;
        } else {
            this.timestamp = new Date();
        }
    }

    public String getDeviceId() {
        return deviceId.toHexString();
    }

    public void setDeviceId(String deviceId) {
        ObjectId objectId = new ObjectId(deviceId);
        this.deviceId = objectId;
    }

    public boolean isIsSynced() {
        return isSynced;
    }

    public void setIsSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }

}
