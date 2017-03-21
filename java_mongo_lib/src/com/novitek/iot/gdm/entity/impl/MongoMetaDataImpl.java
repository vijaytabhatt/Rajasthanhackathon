/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.novitek.iot.gdm.entity.MongoMetaData;
import java.util.Set;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author AjayS
 */
public class MongoMetaDataImpl {

    /**
     * This method returns the metadata of database like
     * totalCollection,dataSize,storageSize
     *
     * @param datastore
     * @return
     */
    public MongoMetaData getMetaData(Datastore datastore) {

        DB db = datastore.getDB();
        Set<String> collectionSet = db.getCollectionNames();
        int totalCollection = 0;
        int dataSize = 0;
        int storageSize = 0;

        for (String collection : collectionSet) {

            totalCollection++;
            try {
                CommandResult commandResult = db.getCollection(collection).getStats();
                if(commandResult.containsKey("avgObjSize"))
                dataSize = dataSize + (int) commandResult.get("avgObjSize");
                if(commandResult.containsKey("storageSize"))
                storageSize = storageSize + (int) commandResult.get("storageSize");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        MongoMetaData mongoMetaData = new MongoMetaData();
        mongoMetaData.setCount(totalCollection);
        mongoMetaData.setDataSize(dataSize);
        mongoMetaData.setStorageSize(storageSize);

        return mongoMetaData;
    }
}
