/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DeviceGroup;
import com.novitek.iot.gdm.entity.Devices;
import com.novitek.iot.gdm.entity.Users;
import com.novitek.iot.gdm.util.Utility;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.Query;
import com.novitek.iot.gdm.util.Constants;

/**
 *
 * @author AjayS
 */
public class UsersImpl {

    /**
     * This method insert or update the User Details
     *
     * @param datastore
     * @param users
     * @param ID
     * @return
     * @throws JSONException
     * @throws JAXBException
     */
    public Users insertOrUpdateUserDetails(Datastore datastore, Users users, String ID) throws Exception {

        String firstName = "";
        String lastName = "";
        if (users.getFirstName() != null) {
            firstName = users.getFirstName();
        }

        if (users.getFirstName() != null) {
            lastName = users.getLastName();
        }
        users.setDisplayName(firstName + " " + lastName);
        
        if (ID == null) {

            Users usernameUsers = fetchUserByUsername(datastore, users.getUsername());
            if (usernameUsers == null) {

                Users emailUsers = fetchUserByEmailId(datastore, users.getEmail());
                if (emailUsers == null) {

                    Key<Users> savedKey = datastore.save(users);
                    return fetchUsersByID(datastore, savedKey.getId().toString());
                } else {

                    throw new Exception(Constants.EMAIL_ID_ALREADY_USED);
                }
            } else {

                throw new Exception(Constants.USER_NAME_ALREADY_PRESENT);
            }
        } else {

            users.setId(ID);
            Key<Users> savedKey = datastore.save(users);
            return fetchUsersByID(datastore, savedKey.getId().toString());
        }

    }

    /**
     * This method deletes the user details
     *
     * @param datastore
     * @param ID
     * @return
     * @throws JSONException
     */
    public Object deleteUserDetails(Datastore datastore, String ID) {

        Users users = new Users();
        users.setId(ID);
        WriteResult result = datastore.delete(users);
        return result.toString();

    }

    /**
     * This method returns all users with pagination support
     *
     * @param datastore
     * @param index
     * @return
     */
    public List<Users> fetchAllUserList(Datastore datastore, Integer index) {

        Query<Users> query = datastore.createQuery(Users.class);
        List<Users> userseList = null;
        if (index == null || index < 0) {
            userseList = query.asList();
        } else {
            userseList = query.asList().subList(index, index + 10);
        }
        return userseList;
    }

    /**
     * This method returns user details by id
     *
     * @param datastore
     * @param ID
     * @return
     */
    public Users fetchUsersByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<Users> query = datastore.createQuery(Users.class);

        return query.filter("_id", ObjectId).get();

    }

    /**
     * This method returns the user details by token
     *
     * @param datastore
     * @param resetToken
     * @return
     */
    public Users fetchUserDetailsByResetToken(Datastore datastore, String resetToken) {

        Query<Users> query = datastore.createQuery(Users.class);
        return query.filter("resetToken", resetToken).get();
    }

    /**
     * This method returns user details by id
     *
     * @param datastore
     * @param emailId
     * @return
     */
    public Users fetchUserByEmailId(Datastore datastore, String emailId) {

        Query<Users> query = datastore.createQuery(Users.class);
        return query.filter("email", emailId).get();
    }

    /**
     * This method returns the user details by username
     *
     * @param datastore
     * @param userName
     * @return
     */
    public Users fetchUserByUsername(Datastore datastore, String userName) {

        Query<Users> query = datastore.createQuery(Users.class);
        return query.filter("username", userName).get();
    }

    /**
     * This method is used to validate the reset password authentication
     *
     * @param datastore
     * @param emailId
     * @return
     */
    public Users fetchUserByEmailIdAndResetToken(Datastore datastore, String emailId, String token) {

        Query<Users> query = datastore.createQuery(Users.class);
        query.field("email").equal(emailId);
        query.field("resetToken").equal(token);

        Users users = query.get();
        return users;
    }

    /**
     * This method authenticate the User
     *
     * @param datastore
     * @param users
     * @return
     */
    public Users isUserAuthenticated(Datastore datastore, Users users) {

        Query<Users> query = datastore.createQuery(Users.class);

        query.field("username").equal(users.getUsername());
        query.field("password").equal(users.getPassword());

        Users authenticatedUsers = query.get();
        return authenticatedUsers;
    }
}
