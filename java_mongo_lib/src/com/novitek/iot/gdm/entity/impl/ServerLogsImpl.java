/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DataStreams;
import com.novitek.iot.gdm.entity.Logging;
import com.novitek.iot.gdm.entity.OpcSubscription;
import com.novitek.iot.gdm.entity.ServerLogs;
import com.novitek.iot.gdm.manager.DatabaseManager;
import com.novitek.iot.gdm.util.Utility;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author AjayS
 */
public class ServerLogsImpl {

    /**
     * This method insert and update the Server log details
     *
     * @param datastore
     * @param serverLogs
     * @param logID
     * @return
     * @throws JAXBException
     */
    public static ServerLogs insertOrUpdateDataStreams(Datastore datastore, ServerLogs serverLogs, String logID) throws JAXBException {

        Key<ServerLogs> savedKey = null;
        if (logID == null) {

            savedKey = datastore.save(serverLogs);
        } else {
            serverLogs.setId(logID);
        }
        System.out.println("Key: " + savedKey.getId());
        ServerLogs newServerLogs = fetchServerLogByID(datastore, savedKey.getId().toString());
        return newServerLogs;
    }

    /**
     * This method delete the server logs
     *
     * @param datastore
     * @param logId
     * @return
     */
    public Object deleteServerLogs(Datastore datastore, String logId) {

        ServerLogs serverLogs = new ServerLogs();
        serverLogs.setId(logId);
        WriteResult result = datastore.delete(serverLogs);
        return result.toString();
    }

    /**
     * This method deletes all records
     * @param datastore
     * @return 
     */
    public Object deleteAllLogs(Datastore datastore) {

        DBCollection collection = datastore.getDB().getCollection("serverlogs");
        BasicDBObject document = new BasicDBObject();
        // Delete All documents from collection Using blank BasicDBObject
        WriteResult result = collection.remove(document);
        return result.toString();
    }

    /**
     * This method returns the server log details by ID
     *
     * @param datastore
     * @param logID
     * @return
     */
    private static ServerLogs fetchServerLogByID(Datastore datastore, String logID) {

        ObjectId ObjectId = new ObjectId(logID);
        Query<ServerLogs> query = datastore.createQuery(ServerLogs.class);
        return query.filter("_id", ObjectId).get();
    }

    /**
     * This method returns all the server logs
     *
     * @param datastore
     * @param index
     * @return
     */
    public List<ServerLogs> getServerLogsList(Datastore datastore, Integer index) {

        Query<ServerLogs> query = datastore.createQuery(ServerLogs.class);
        List<ServerLogs> serverLogsList = null;
        if (index == null) {

            serverLogsList = query.order("-_id").asList();
        } else {

            serverLogsList = query.order("-_id").asList().subList(index, index + 5);
        }

        return serverLogsList;
    }

    public static void storeLogs(Logging logging, Properties properties) {
    
        try {
            ServerLogs serverLogs = new ServerLogs();
            serverLogs.setLogs(logging);
            
            DatabaseManager databaseManager = DatabaseManager.getInstance(properties);
            insertOrUpdateDataStreams(databaseManager.getDatastore(), serverLogs, null);
        } catch (JAXBException ex) {
            Logger.getLogger(ServerLogsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
    
}
