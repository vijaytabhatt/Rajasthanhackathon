/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AjayS
 */
public class ServerStatistics {

    private String userDir;

    @XmlElement(name = "dailyStats")
    private List<DailyStats> dailyStatseList;

    @XmlElement(name = "memInfo")
    private MemoryInfo memoryInfo;

    @XmlElement(name = "database")
    private MongoMetaData mongoMetaData;

    @XmlElement(name = "deviceCount")
    private int deviceCount;

    @XmlElement(name = "localStreamCount")
    private long localStreamCount;

    public long getLocalStreamCount() {
        return localStreamCount;
    }

    public void setLocalStreamCount(long localStreamCount) {
        this.localStreamCount = localStreamCount;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public MemoryInfo getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(MemoryInfo memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public MongoMetaData getMongoMetaData() {
        return mongoMetaData;
    }

    public void setMongoMetaData(MongoMetaData mongoMetaData) {
        this.mongoMetaData = mongoMetaData;
    }

    public List<DailyStats> getDailyStatseList() {
        return dailyStatseList;
    }

    public void setDailyStatseList(List<DailyStats> dailyStatseList) {
        this.dailyStatseList = dailyStatseList;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

}
