/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;

import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DataStreams;
import com.novitek.iot.gdm.entity.Graphs;
import com.novitek.iot.gdm.util.Utility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author AjayS
 */
public class GraphsImpl {

    /**
     * this method insert or update the graph details
     *
     * @param datastore
     * @param graphs
     * @param graphID
     * @return
     * @throws JAXBException
     */
    public Object insertOrUpdateGraphDetails(Datastore datastore, Graphs graphs, String graphID) throws Exception {

        if (graphs.getyDataTag() == null) {
            throw new Exception("Y Axis Element(s) required");
        }

        Key<Graphs> savedKey = null;
        if (graphs.getDeviceID() == null) {

            System.out.println("Device Id is null");
            throw new Exception("Device Id can not be null.");
        } else {
            if (graphID == null) {

                savedKey = datastore.save(graphs);
            } else {
                graphs.setId(graphID);
                savedKey = datastore.save(graphs);
            }

            Graphs responseGraphs = fetchGraphDetailsByID(datastore, savedKey.getId().toString());
            return responseGraphs;
        }
    }

    /**
     * This method delete the graph details
     *
     * @param datastore
     * @param id
     * @return
     */
    public Object deleteGraph(Datastore datastore, String id) {

        try {
            Graphs graphs = new Graphs();
            graphs.setId(id);
            WriteResult result = datastore.delete(graphs);
            return result.toString();

        } catch (Exception exception) {
            exception.printStackTrace();
            return exception.getMessage();
        }
    }

    /**
     * This Method returns all Graphs
     *
     * @param datastore
     * @param index
     * @return
     */
    public List<Graphs> fetchAllGraphList(Datastore datastore, Integer index) {

        Query<Graphs> query = datastore.createQuery(Graphs.class);
        List<Graphs> graphsList = null;
        if (index == null || index < 0) {

            graphsList = query.asList();
        } else {
            int nextIndex = index + 10;
            graphsList = query.asList().subList(index, nextIndex);
        }

        return graphsList;
    }

    /**
     * This method return the Graph Details by id
     *
     * @param datastore
     * @param id
     * @return
     */
    public Graphs fetchGraphDetailsByID(Datastore datastore, String id) {

        ObjectId ObjectId = new ObjectId(id);
        Query<Graphs> query = datastore.createQuery(Graphs.class);
        Graphs graphs = query.filter("_id", ObjectId).get();
        return graphs;
    }

    /**
     * This method returns the list of graphs by graph type
     *
     * @param datastore
     * @param graphType
     * @return
     */
    public List<Graphs> fetchGraphListByGraphType(Datastore datastore, String graphType) {

        Query<Graphs> query = datastore.createQuery(Graphs.class);
        List<Graphs> graphsList = query.filter("graphType", graphType).asList();
        return graphsList;
    }

    /**
     * This method returns the list of graphs by graph name
     *
     * @param datastore
     * @param graphName
     * @return
     */
    public List<Graphs> fetchGraphListByGraphName(Datastore datastore, String graphName) {

        Query<Graphs> query = datastore.createQuery(Graphs.class);
        List<Graphs> graphsList = query.filter("graphName", graphName).asList();
        return graphsList;
    }

    /**
     * This method returns the list of graphs by device ID
     *
     * @param datastore
     * @param deviceID
     * @return
     */
    public List<Graphs> fetchGraphListByDeviceID(Datastore datastore, String deviceID) {

        ObjectId objectId = new ObjectId(deviceID);
        Query<Graphs> graphsQuery = datastore.createQuery(Graphs.class);
        List<Graphs> graphsList = graphsQuery.filter("deviceID", objectId).asList();
        return graphsList;
    }

    /**
     * This method returns the list of pinned graphs
     *
     * @param datastore
     * @return
     */
    public List<Graphs> fetchPinnedGraphList(Datastore datastore) {

        Query<Graphs> graphsQuery = datastore.createQuery(Graphs.class);
        List<Graphs> graphsList = graphsQuery.filter("isPinned", true).asList();
        return graphsList;
    }

    /**
     * This method returns the Graph Data Streams by Graph ID for each device
     * mapped to the graph
     *
     * @param datastore
     * @param graphID
     * @param afterDate
     * @param limit
     * @return
     */
    public List<DataStreams> fetchGraphData(Datastore datastore, String graphID,
            Date afterDate, Integer limit) {

        
            
        Graphs graphs = fetchGraphDetailsByID(datastore, graphID);
        DataStreamsImpl dataStreamsImpl = new DataStreamsImpl();
        List<DataStreams> dataStreamsList = dataStreamsImpl.fetchListByDeviceIdWithFilters(datastore,
                graphs.getDeviceID(), afterDate, limit);

        return dataStreamsList;

    }
}
