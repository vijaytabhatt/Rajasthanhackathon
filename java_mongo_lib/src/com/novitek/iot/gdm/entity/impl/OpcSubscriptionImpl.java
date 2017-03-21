/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DeviceGroup;
import com.novitek.iot.gdm.entity.OpcSession;
import com.novitek.iot.gdm.entity.OpcSubscription;
import com.novitek.iot.gdm.util.Constants;
import com.novitek.iot.gdm.util.Utility;
import java.util.ArrayList;
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
public class OpcSubscriptionImpl {

    /**
     * This method is used to insert or update OPC S
     *
     * @param datastore
     * @param opcSubscription
     * @param ID
     * @return
     * @throws JSONException
     * @throws JAXBException
     */
    public Object insertOrUpdateOPCSubscription(Datastore datastore,
            OpcSubscription opcSubscription, String ID) throws Exception {
        
        OpcSubscription opcSubscriptionWithName = fetchOPCSubscriptionListByName(datastore, opcSubscription.getName());
        if (opcSubscriptionWithName != null) { //check if opc session with that name already exists
              if(!opcSubscriptionWithName.getId().equals(ID))
            throw new Exception(Constants.OPCSUBSCRITPION_WITH_NAME_EXISTS);
        }
        
        if (ID == null) {
            Key<OpcSubscription> savedKey = datastore.save(opcSubscription);
            return fetchOPCSubscriptionListByID(datastore, savedKey.getId().toString());            
        } else {
            opcSubscription.setId(ID);
            Key<OpcSubscription> savedKey = datastore.save(opcSubscription);
            return fetchOPCSubscriptionListByID(datastore, savedKey.getId().toString());

        }
    }

    /**
     * This method delete particular OPC subscription
     *
     * @param datastore
     * @param ID
     * @return
     */
    public Object deleteOPCSubscription(Datastore datastore, String ID) throws JSONException {

        Utility utility = new Utility();
        try {
            OpcSubscription opcSubscription = new OpcSubscription();
            opcSubscription.setId(ID);
            WriteResult result = datastore.delete(opcSubscription);

            return result.toString();          

        } catch (Exception exception) {
            exception.printStackTrace();
            return utility.errorResponse(exception);
        }
    }

    /**
     * This method returns all the OPC subscription details
     *
     * @param datastore
     * @param currentIndex
     * @return
     */
    public List<OpcSubscription> getOPCSubscriptionList(Datastore datastore, Integer currentIndex) {

        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = null;
        if (currentIndex == null) {
            dataStreamsList = query.asList();
        } else {
            dataStreamsList = query.asList().subList(currentIndex, currentIndex + 5);
        }
        return dataStreamsList;
    }

    /**
     * This method returns all the OPCSubscriptions by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public OpcSubscription fetchOPCSubscriptionListByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        return query.filter("_id", ObjectId).get();
    }
    
    

    /**
     * This method returns all the subscriptions as per Name
     *
     * @param datastore
     * @param name
     * @return
     */
    public OpcSubscription fetchOPCSubscriptionListByName(Datastore datastore, String name) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        OpcSubscription opcSubscriptionWithName = query.filter("name", name).get();
        return opcSubscriptionWithName;
    }

    /**
     * This method returns all the subscriptions as per OPC
     * RequestedPublishingInterval
     *
     * @param datastore
     * @param requestedPublishingInterval
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListByRequestedPublishingInterval(Datastore datastore, int requestedPublishingInterval) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("requestedPublishingInterval", requestedPublishingInterval).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC
     * requestedLifetimeCount
     *
     * @param datastore
     * @param requestedLifetimeCount
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListByrequestedLifetimeCount(Datastore datastore, int requestedLifetimeCount) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("requestedLifetimeCount", requestedLifetimeCount).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC
     * requestedMaxKeepAliveCount
     *
     * @param datastore
     * @param requestedMaxKeepAliveCount
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListByrequestedMaxKeepAliveCount(Datastore datastore, int requestedMaxKeepAliveCount) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("requestedMaxKeepAliveCount", requestedMaxKeepAliveCount).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC Session
     *
     * @param datastore
     * @param maxNotificationsPerPublish
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListBymaxNotificationsPerPublish(Datastore datastore, int maxNotificationsPerPublish) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("maxNotificationsPerPublish", maxNotificationsPerPublish).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC Session
     *
     * @param datastore
     * @param priority
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListBypriority(Datastore datastore, int priority) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("priority", priority).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC Session
     *
     * @param datastore
     * @param publishingEnabled
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListBypublishingEnabled(Datastore datastore, boolean publishingEnabled) {
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("publishingEnabled", publishingEnabled).asList();
        return dataStreamsList;
    }

    /**
     * This method returns all the subscriptions as per OPC Session
     *
     * @param datastore
     * @param OPCSessionID
     * @return
     */
    public List<OpcSubscription> fetchOPCSubscriptionListByOPCSessionID(Datastore datastore, String OPCSessionID) {
        ObjectId ObjectId = new ObjectId(OPCSessionID);
        Query<OpcSubscription> query = datastore.createQuery(OpcSubscription.class);
        List<OpcSubscription> dataStreamsList = query.filter("opcSession", ObjectId).asList();
        return dataStreamsList;
    }
}
