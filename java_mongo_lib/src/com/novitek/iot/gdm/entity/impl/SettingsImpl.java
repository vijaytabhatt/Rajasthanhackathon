/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.novitek.iot.gdm.entity.Sessions;
import com.novitek.iot.gdm.entity.Settings;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author AjayS
 */
public class SettingsImpl {

    private Settings insertUpdateRecord(Datastore datastore, Settings settings) {

        Key<Settings> settingKey = datastore.save(settings);
        Settings newSettings = fetchSettingsById(datastore, settingKey.getId().toString());
        return newSettings;
    }

    public Settings insertOrUpdate(Datastore datastore, Settings settings, String id,
            String recordType) {

        switch (recordType) {

            case "SMTP":
                Settings smtpSettings = fetchSettingsByRecordType(datastore, recordType);
                if (smtpSettings != null) {

                    settings.setId(smtpSettings.getId());
                    return insertUpdateRecord(datastore, settings);
                } else {
                    return insertUpdateRecord(datastore, settings);
                }
            default:
                if (id == null) {

                    return insertUpdateRecord(datastore, settings);
                } else {

                    settings.setId(id);
                    return insertUpdateRecord(datastore, settings);
                }
        }

    }

    public Settings fetchSettingsById(Datastore datastore, String id) {

        ObjectId ObjectId = new ObjectId(id);
        Query<Settings> query = datastore.createQuery(Settings.class);
        return query.filter("_id", ObjectId).get();
    }

    public Settings fetchSettingsByRecordType(Datastore datastore, String recordType) {

        Query<Settings> query = datastore.createQuery(Settings.class);
        return query.filter("recordType", recordType).get();
    }

}
