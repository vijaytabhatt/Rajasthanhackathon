/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.OpcMonitor;
import com.novitek.iot.gdm.entity.OpcSubscription;
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
 *
 * @author vijaytab
 */
public class OpcMonitorImpl {

    /**
     * This method is used to insert or update OPC Monitor
     *
     * @param datastore
     * @param opcMonitor
     * @param ID
     * @return
     * @throws JSONException
     * @throws JAXBException
     */
    public Object insertOrUpdateOPCMonitor(Datastore datastore, OpcMonitor opcMonitor, String ID) throws Exception {
      
        OpcMonitor opcMonitorWithName = fetchOpcMonitorListByName(datastore, opcMonitor.getName());
        if (opcMonitorWithName != null) { //check if opc session with that name already exists
             if(!opcMonitorWithName.getId().equals(ID))
            throw new Exception(Constants.OPCMONITOR_WITH_NAME_EXISTS);
        }
        
        
        if (ID == null) {
            Key<OpcMonitor> savedKey = datastore.save(opcMonitor);
            return fetchOPCMonitorListByID(datastore, savedKey.getId().toString());          
        } else {
            opcMonitor.setId(ID);
            Key<OpcMonitor> savedKey = datastore.save(opcMonitor);
            return fetchOPCMonitorListByID(datastore, savedKey.getId().toString());
        }
    }

    /**
     * This method delete particular OPC Monitor
     *
     * @param datastore
     * @param ID
     * @return
     */
    public Object deleteOPCMonitor(Datastore datastore, String ID) throws JSONException {

        Utility utility = new Utility();
        try {
            OpcMonitor opcMonitor = new OpcMonitor();
            opcMonitor.setId(ID);
            WriteResult result = datastore.delete(opcMonitor);

            return result.toString();
            //return datastore.delete(devices);

        } catch (Exception exception) {
            exception.printStackTrace();
            return utility.errorResponse(exception);
        }
    }

    /**
     * This method returns all the OPC Monitor details
     *
     * @param datastore
     * @param currentIndex
     * @return
     */
    public List<OpcMonitor> getOPCMonitorList(Datastore datastore, Integer currentIndex) {

        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        List<OpcMonitor> dataStreamsList = null;
        if (currentIndex == null) {
            dataStreamsList = query.asList();
        } else {
            dataStreamsList = query.asList().subList(currentIndex, currentIndex + 5);
        }
        return dataStreamsList;
    }

    /**
     * This method returns all the OPC Monitor by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public OpcMonitor fetchOPCMonitorListByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        return query.filter("_id", ObjectId).get();

    }

    /**
     * This method returns all the monitors as per Name
     *
     * @param datastore
     * @param name
     * @return
     */
    public OpcMonitor fetchOpcMonitorListByName(Datastore datastore, String name) {
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        OpcMonitor opcMonitorWithName = query.filter("name", name).get();
        return opcMonitorWithName;
    }

    /**
     * This method returns all the monitors as per Name
     *
     * @param datastore
     * @param monitorNodeIds
     * @return
     */
    public List<OpcMonitor> fetchOpcMonitorListByMonitorNodeIds(Datastore datastore, String monitorNodeIds) {
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        List<OpcMonitor> dataStreamsList = query.filter("monitorNodeIds", monitorNodeIds).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the monitors as per Name
     *
     * @param datastore
     * @param samplingInterval
     * @return
     */
    public List<OpcMonitor> fetchOpcMonitorListBySamplingInterval(Datastore datastore, int samplingInterval) {
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        List<OpcMonitor> dataStreamsList = query.filter("samplingInterval", samplingInterval).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the monitors as per Name
     *
     * @param datastore
     * @param queueSize
     * @return
     */
    public List<OpcMonitor> fetchOpcMonitorListByQueueSize(Datastore datastore, int queueSize) {
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        List<OpcMonitor> dataStreamsList = query.filter("queueSize", queueSize).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the monitors as per Name
     *
     * @param datastore
     * @param OPCSubscriptionID
     * @return
     */
    public List<OpcMonitor> fetchOpcMonitorListByOpcSubscriptionID(Datastore datastore, String OPCSubscriptionID) {
        ObjectId ObjectId = new ObjectId(OPCSubscriptionID);
        Query<OpcMonitor> query = datastore.createQuery(OpcMonitor.class);
        List<OpcMonitor> dataStreamsList = query.filter("opcSubscription", ObjectId).asList();
        return dataStreamsList;
    }

}
