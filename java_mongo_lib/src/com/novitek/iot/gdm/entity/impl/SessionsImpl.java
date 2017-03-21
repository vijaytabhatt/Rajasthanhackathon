/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.google.gson.Gson;
import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.Devices;
import com.novitek.iot.gdm.entity.Sessions;
import java.util.Date;
import javax.swing.SwingWorker;
import javax.ws.rs.core.NewCookie;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author AjayS
 */
public class SessionsImpl {

    /**
     * This method insert or update session details
     *
     * @param datastore
     * @param sessions
     * @param sessionID
     * @return
     */
    public Object insertOrUpdate(Datastore datastore, Sessions sessions, String sessionID) {

        Sessions newSessions = null;
        Sessions existingSessions = fetchSessionDetailsBySessionToken(datastore, sessions.getToken());

        if (existingSessions != null) {

            sessions.setId(existingSessions.getId());
            Key<Sessions> sessionsKey = datastore.save(sessions);
            newSessions = fetchSessionDetailsByID(datastore, sessionsKey.getId().toString());
            return newSessions;
        } else {

            Key<Sessions> sessionsKey = datastore.save(sessions);
            newSessions = fetchSessionDetailsByID(datastore, sessionsKey.getId().toString());
            return newSessions;
        }

    }

    /**
     * This method delete the session stored
     *
     * @param datastore
     * @param token
     * @return
     */
    public Object deleteSession(Datastore datastore, String token) {

        Sessions existingSessions = fetchSessionDetailsBySessionToken(datastore, token);
        if (existingSessions != null) {
            WriteResult result = datastore.delete(existingSessions);
            return result.toString();
        }
        else{
            return "Session Object not found.";
        }
    }

    /**
     * This method returns session details by ID
     *
     * @param datastore
     * @param sessionID
     * @return
     */
    public Sessions fetchSessionDetailsByID(Datastore datastore, String sessionID) {

        ObjectId ObjectId = new ObjectId(sessionID);
        Query<Sessions> query = datastore.createQuery(Sessions.class);
        return query.filter("_id", ObjectId).get();

    }

    /**
     * This method returns the session object by token
     * @param datastore
     * @param sessionToken
     * @return 
     */
    public Sessions fetchSessionDetailsBySessionToken(Datastore datastore, String sessionToken) {

        Query<Sessions> query = datastore.createQuery(Sessions.class);
        Sessions sessions = query.filter("token", sessionToken).get();
        return sessions;

    }
    
    /**
     * This method checks the session token expiry
     * @param datastore
     * @param sessionToken
     * @return 
     */
    public boolean isSessionExpired(Datastore datastore, String sessionToken){
        
        Query<Sessions> query = datastore.createQuery(Sessions.class);
        Sessions sessions = query.filter("token", sessionToken).get();
        Date expiryDate = sessions.getExpires();
        Date systemDate = new Date();
        
        System.out.println("#############################################");
        System.out.println("System Date: "+systemDate);
        System.out.println("Expiry Date: "+expiryDate);
        System.out.println("#############################################");
        
        if(expiryDate.before(systemDate)){
            
            return true;
        }
        else{
            return false;
        }
    }
}
