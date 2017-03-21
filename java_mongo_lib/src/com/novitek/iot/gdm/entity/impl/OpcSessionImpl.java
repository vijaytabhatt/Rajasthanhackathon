/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.OpcSession;
import com.novitek.iot.gdm.util.Constants;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author vijaytab
 */
public class OpcSessionImpl {

    /**
     * This method is used to insert or update OPC Session
     *
     * @param datastore
     * @param opcSession
     * @param ID
     * @return
     * @throws java.lang.Exception
     */
    public Object insertOrUpdateOPCSession(Datastore datastore, OpcSession opcSession, String ID) throws Exception {
      
        OpcSession opcSessionWithName = fetchOPCSessionsListByName(datastore, opcSession.getName());
        if (opcSessionWithName != null) { //check if opc session with that name already exists
            if(!opcSessionWithName.getId().equals(ID))
            throw new Exception(Constants.OPCSESSION_WITH_NAME_EXISTS);
        }
        if (ID == null) {

            Key<OpcSession> savedKey = datastore.save(opcSession);
            return fetchOpcSessionListByID(datastore, savedKey.getId().toString());

        } else {
            opcSession.setId(ID);
            Key<OpcSession> savedKey = datastore.save(opcSession);
            return fetchOpcSessionListByID(datastore, savedKey.getId().toString());
        }

    }

    /**
     * This method is used to delete OPC Session
     *
     * @param datastore
     * @param ID
     * @return
     */
    public Object deleteOPCSession(Datastore datastore, String ID) {       
        OpcSession opcSession = new OpcSession();   
        opcSession.setId(ID);
        WriteResult result = datastore.delete(opcSession);

        return result.toString();       
    }

    /**
     * This method returns all the OPC Session details
     *
     * @param datastore
     * @param currentIndex
     * @return
     */
    public List<OpcSession> fetchOPCSessionList(Datastore datastore, Integer currentIndex) {

        Query<OpcSession> query = datastore.createQuery(OpcSession.class);
        List<OpcSession> opcSessionList = null;
        if (currentIndex == null) {
            opcSessionList = query.asList();
        } else {
            opcSessionList = query.asList().subList(currentIndex, currentIndex + 5);
        }

        return opcSessionList;
    }

    /**
     * This method returns all OPC Sessions by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public OpcSession fetchOpcSessionListByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<OpcSession> query = datastore.createQuery(OpcSession.class);
        return query.filter("_id", ObjectId).get();
    }

    /**
     * This method will return the list of OPC Sessions based on Name
     *
     * @param datastore
     * @param name
     * @return
     */
    public OpcSession fetchOPCSessionsListByName(Datastore datastore, String name) {
        Query<OpcSession> opcSessionQuery = datastore.createQuery(OpcSession.class);
        OpcSession opcSessionWithName = opcSessionQuery.filter("name", name).get();
        return opcSessionWithName;
    }

    /**
     * This method will return the list of OPC Sessions based on EndPoint
     *
     * @param datastore
     * @param endpoint
     * @return
     */
    public List<OpcSession> fetchOPCSessionsListByEndPoint(Datastore datastore, String endpoint) {
        Query<OpcSession> opcSessionQuery = datastore.createQuery(OpcSession.class);
        List<OpcSession> opcSessionList = opcSessionQuery.filter("endpoint", endpoint).asList();
        return opcSessionList;
    }

    /**
     * This method will return the list of OPC Sessions based on UserName
     *
     * @param datastore
     * @param username
     * @return
     */
    public List<OpcSession> fetchOPCSessionsListByUserName(Datastore datastore, String username) {
        Query<OpcSession> opcSessionQuery = datastore.createQuery(OpcSession.class);
        List<OpcSession> opcSessionList = opcSessionQuery.filter("username", username).asList();
        return opcSessionList;
    }

}
