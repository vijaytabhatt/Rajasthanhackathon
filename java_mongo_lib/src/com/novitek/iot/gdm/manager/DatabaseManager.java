/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.manager;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.novitek.iot.gdm.entity.DailyStats;
import com.novitek.iot.gdm.entity.DataStreams;
import com.novitek.iot.gdm.entity.Devices;
import com.novitek.iot.gdm.entity.impl.DataStreamsImpl;
import com.novitek.iot.gdm.entity.impl.DevicesImpl;
import com.novitek.iot.gdm.util.Utility;
import java.time.Instant;
import static java.time.Instant.now;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author AjayS
 */
public class DatabaseManager {

    private MongoClient mongo;
    private Morphia morphia;
    private Datastore datastore;

    private String host;
    private String database;
    private int portNumber;
    private String userName;
    private String password;

    private static DatabaseManager databaseManager;

    public MongoClient getMongo() {
        return mongo;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    private DatabaseManager() {
        try {
            host = "localhost";
            portNumber = 27017;
            database = "novitek-dev";

            mongo = new MongoClient(host, portNumber);
            morphia = new Morphia();
            datastore = morphia.createDatastore(mongo, database);
            System.out.println("Host: " + host);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    private DatabaseManager(Properties properties) {
        try {

            host = properties.getProperty("hostName");;
            portNumber = Integer.parseInt(properties.getProperty("portNumber"));
            database = properties.getProperty("databaseName");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");

            ServerAddress addr = new ServerAddress(host, portNumber);
            List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
            if (userName != null && password != null) {

                if (!userName.trim().isEmpty() && !password.trim().isEmpty()) {
                    MongoCredential credentia = MongoCredential.createCredential(
                            userName, database, password.toCharArray());
                    credentialsList.add(credentia);
                    mongo = new MongoClient(addr, credentialsList);
                } else {

                    mongo = new MongoClient(host, portNumber);
                }
            } else {

                mongo = new MongoClient(host, portNumber);
            }
            morphia = new Morphia();
            datastore = morphia.createDatastore(mongo, database);
            System.out.println("Host: " + host);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    /**
     * Method to return object of Database manager
     *
     * @param properties
     * @return
     */
    public static synchronized DatabaseManager getInstance(Properties properties) {

        if (databaseManager == null) {

            databaseManager = new DatabaseManager(properties);
        }

        return databaseManager;
    }

    public void flushResources() {

        if (databaseManager != null & mongo != null) {

            mongo.close();
            databaseManager = null;
            mongo = null;
            morphia = null;
            datastore = null;
        }
    }

    /**
     * This method returns the device list based
     *
     * @return
     * @throws JAXBException
     */
    public String getDevicesList(String deviceID, Integer currentIndex) throws Exception {

        DevicesImpl devicesImpl = new DevicesImpl();
        List<Devices> devicesList = null;
        Utility utility = new Utility();

        if (deviceID == null) {
            // get all records without pagination
            devicesList = devicesImpl.getAllDevicesList(datastore, currentIndex);
        } else {

            // get all device specific records 
            Object resObject = devicesImpl.fetchDeviceDetailsById(datastore, deviceID);
            if (resObject instanceof Devices) {

                Devices devices = (Devices) resObject;
                return utility.createResponse(Devices.class, devices);
            } else {
                devicesList = (List<Devices>) devicesImpl.fetchDeviceDetailsById(datastore, deviceID);
            }
        }

        Devices[] deviceses = new Devices[devicesList.size()];
        devicesList.toArray(deviceses);

        String responseString = utility.createResponse(Devices[].class, deviceses);

        return responseString;
    }

    public void insertStreams() throws Exception {

        DateTime dateTime = new DateTime(DateTimeZone.UTC);

        DataStreams dataStreams = new DataStreams();

        dataStreams.setDeviceId("583d699f4ff94922a407682d");
        DataStreamsImpl impl = new DataStreamsImpl();
        impl.insertOrUpdateDataStreams(datastore, dataStreams, null);
    }

    public static void main(String[] args) throws Exception {

    }
}
