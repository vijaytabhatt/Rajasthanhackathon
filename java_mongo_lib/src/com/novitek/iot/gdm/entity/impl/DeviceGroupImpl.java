/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DeviceGroup;
import com.novitek.iot.gdm.entity.OpcSession;
import com.novitek.iot.gdm.util.Constants;
import com.novitek.iot.gdm.util.Utility;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 * This Class performs CRUD operations on DeviceGroup Document
 *
 * @author AjayS
 */
public class DeviceGroupImpl {

    /**
     * This method insert or update the Device Group
     *
     * @param datastore
     * @param deviceGroup
     * @param ID
     * @return
     * @throws JSONException
     */
    public DeviceGroup insertOrUpdateDeviceGroup(Datastore datastore, DeviceGroup deviceGroup, String ID) throws Exception {

         DeviceGroup devicegroupWithName = fetchDeviceGroupsListByName(datastore, deviceGroup.getName());
        if (devicegroupWithName != null) { //check if device group with that name already exists
            if(!devicegroupWithName.getId().equals(ID))
            throw new Exception(Constants.DEVICE_GROUP_WITH_NAME_EXISTS);
        }
        if (ID == null) {

                Key<DeviceGroup> savedKey = datastore.save(deviceGroup);
                DeviceGroup storedDeviceGroup = fetchDeviceGroupsDetailsByID(datastore, savedKey.getId().toString());
                return storedDeviceGroup;
        } else {

            deviceGroup.setId(ID);
            Key<DeviceGroup> savedKey = datastore.save(deviceGroup);
            DeviceGroup storedDeviceGroup = fetchDeviceGroupsDetailsByID(datastore, savedKey.getId().toString());
            return storedDeviceGroup;
        }

    }

    /**
     * This method delete the device group
     *
     * @param datastore
     * @param ID
     * @return
     * @throws JSONException
     */
    public Object deleteDeviceGroup(Datastore datastore, String ID) throws JSONException {
        
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setId(ID);
        WriteResult result = datastore.delete(deviceGroup);

        return result.toString();
    }

    /**
     * This method returns all the devices group details
     *
     * @param datastore
     * @param currentIndex
     * @return
     */
    public List<DeviceGroup> fetchAllDeviceGroupsList(Datastore datastore, Integer currentIndex) throws JAXBException {

        Utility utility = new Utility();
        Query<DeviceGroup> query = datastore.createQuery(DeviceGroup.class);
        List<DeviceGroup> deviceGroupList = null;
        if (currentIndex == null) {
            deviceGroupList = query.asList();
        } else {
            deviceGroupList = query.asList().subList(currentIndex, currentIndex + 5);
        }

        return deviceGroupList;
    }

    /**
     * This method returns all the device groups by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public DeviceGroup fetchDeviceGroupsDetailsByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<DeviceGroup> query = datastore.createQuery(DeviceGroup.class);
        return query.filter("_id", ObjectId).get();
    }

    /**
     * This method will return the list of device group based on Description
     *
     * @param datastore
     * @param description
     * @return
     */
    public List<DeviceGroup> fetchDeviceGroupsListByDescription(Datastore datastore, String description) {

        Query<DeviceGroup> deviceGroupQuery = datastore.createQuery(DeviceGroup.class);
        List<DeviceGroup> deviceGroupList = deviceGroupQuery.filter("desc", description).asList();
        return deviceGroupList;
    }

    /**
     * This method will return the list of device group based on Name
     *
     * @param datastore
     * @param name
     * @return
     */
    public DeviceGroup fetchDeviceGroupsListByName(Datastore datastore, String name) {

        Query<DeviceGroup> deviceGroupQuery = datastore.createQuery(DeviceGroup.class);
        DeviceGroup deviceGroupList = deviceGroupQuery.filter("name", name).get();
        return deviceGroupList;
    }

    /**
     * This method will return the list of device group if it is on
     *
     * @param datastore
     * @param isOn
     * @return
     */
    public List<DeviceGroup> fetchDeviceGroupsListIfOn(Datastore datastore, boolean isOn) {

        Query<DeviceGroup> deviceGroupQuery = datastore.createQuery(DeviceGroup.class);
        List<DeviceGroup> deviceGroupList = deviceGroupQuery.filter("isOn", isOn).asList();
        return deviceGroupList;
    }
}
