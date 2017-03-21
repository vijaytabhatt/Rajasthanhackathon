/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.mongodb.operation.UpdateOperation;
import com.novitek.iot.gdm.entity.AlertAction;
import com.novitek.iot.gdm.entity.DataStreams;
import com.novitek.iot.gdm.entity.Devices;
import com.novitek.iot.gdm.util.Constants;
import com.novitek.iot.gdm.util.Utility;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 * This Class performs CRUD operations on Devices Document
 *
 * @author AjayS
 */
public class DevicesImpl {

    /**
     * This method insert or update device data and returns the same
     *
     * @param datastore
     * @param devices
     * @param deviceID
     * @return
     * @throws java.lang.Exception
     */
    public Object insertOrUpdateDevice(Datastore datastore, Devices devices, String deviceID) throws Exception {

        if (deviceID == null) {
            //make deviceon by default to start related data collection 
            devices.setIsOn(true);
            Devices deviceWithName = fetchDeviceDetailsByName(datastore, devices.getName());
            if (deviceWithName == null) {
                Key<Devices> savedKey = datastore.save(devices);

                Devices storeDevices = fetchDeviceDetailsById(datastore, savedKey.getId().toString());
                return storeDevices;
            } else {
                throw new Exception(Constants.DEVICE_WITH_NAME_EXISTS);
            }          
        } else {
            devices.setId(deviceID);
            Key<Devices> savedKey = datastore.save(devices);
            Devices storeDevices = fetchDeviceDetailsById(datastore, savedKey.getId().toString());
            return storeDevices;          
        }

    }

    /**
     * This method delete particular device
     *
     * @param datastore
     * @param deviceID
     * @return
     */
    public Object deleteDevice(Datastore datastore, String deviceID) throws JSONException {

        Utility utility = new Utility();
        try {
            Devices devices = new Devices();
            devices.setId(deviceID);
            WriteResult result = datastore.delete(devices);

            return result.toString();          

        } catch (Exception exception) {
            exception.printStackTrace();
            return utility.errorResponse(exception);
        }
    }

    /**
     * This method returns all the devices details
     *
     * @param datastore
     * @param index
     * @return
     */
    public List<Devices> getAllDevicesList(Datastore datastore, Integer index) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public Devices fetchDeviceDetailsById(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<Devices> query = datastore.createQuery(Devices.class);
        return query.filter("_id", ObjectId).get();
    }

    /**
     * This method returns all the devices as per user
     *
     * @param datastore
     * @param userId
     * @return
     */
    public List<Devices> fetchListByUser(Datastore datastore, String userId) {

        ObjectId ObjectId = new ObjectId(userId);
        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("user", ObjectId).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per protocol
     *
     * @param datastore
     * @param protocol
     * @return
     */
    public List<Devices> fetchListByProtocol(Datastore datastore, String protocol) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("protocol", protocol).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per type
     *
     * @param datastore
     * @param type
     * @return
     */
    public List<Devices> fetchListByType(Datastore datastore, String type) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("type", type).asList();
        return dataStreamsList;
    }

    public List<Devices> fetchDevicesListByMonitorId(Datastore datastore, String monitorId){
        
        Query<Devices> query = datastore.createQuery(Devices.class);
        ObjectId id = new ObjectId(monitorId);
        query.field("opcMonitors").hasThisOne(id);
        List<Devices> devicesList = query.asList();        
        return devicesList;
        
    }
    
    
    /**
     * This method returns all the devices as per sensor
     *
     * @param datastore
     * @param sensor
     * @return
     */
    public List<Devices> fetchListBySensor(Datastore datastore, String sensor) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("sensor", sensor).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per groupID
     *
     * @param datastore
     * @param groupID
     * @return
     */
    public List<Devices> fetchListByGroupId(Datastore datastore, String groupID) {

        ObjectId ObjectId = new ObjectId(groupID);
        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("group", ObjectId).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per syncOptionID
     *
     * @param datastore
     * @param syncOptionID
     * @return
     */
    public List<Devices> fetchListBySyncOptionID(Datastore datastore, String syncOptionID) {

        ObjectId ObjectId = new ObjectId(syncOptionID);
        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("syncOption", ObjectId).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per sync Frequency
     *
     * @param datastore
     * @param syncFrequency
     * @return
     */
    public List<Devices> fetchListBySyncFrequency(Datastore datastore, String syncFrequency) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("syncFrequency", syncFrequency).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the devices as per Alert Action
     *
     * @param datastore
     * @param alertAction
     * @return
     */
    public List<Devices> fetchListByAlertAction(Datastore datastore, AlertAction alertAction) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> dataStreamsList = query.filter("alertAction", alertAction).asList();
        return dataStreamsList;
    }

    /**
     * This method returns the devices List by sync Time
     *
     * @param datastore
     * @param syncTime
     * @return
     */
    public List<Devices> fetchListBySyncTime(Datastore datastore, String syncTime) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        List<Devices> devicesList = query.filter("syncTime", syncTime).asList();
        return devicesList;
    }
    
    public List<Devices> fetchListByOpcSession(Datastore datastore, String opcSessionID){
        
        ObjectId objectId = new ObjectId(opcSessionID);
        Query<Devices> query =datastore.createQuery(Devices.class);
        List<Devices> devicesList = query.filter("opcSession", objectId).asList();
        
        return devicesList;
    }

    /**
     * This method returns the device details by device name
     *
     * @param datastore
     * @param name
     * @return
     */
    private Devices fetchDeviceDetailsByName(Datastore datastore, String name) {

        Query<Devices> query = datastore.createQuery(Devices.class);
        Devices devicesWithName = query.filter("name", name).get();
        return devicesWithName;
    }

}
