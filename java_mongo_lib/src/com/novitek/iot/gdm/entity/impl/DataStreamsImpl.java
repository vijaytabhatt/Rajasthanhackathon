/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.impl;


import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.novitek.iot.gdm.entity.DailyStats;
import com.novitek.iot.gdm.entity.DataStreams;
import com.novitek.iot.gdm.entity.Devices;
import com.novitek.iot.gdm.util.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

/**
 * This class is responsible for all CRUD operations on DataStreams Entity
 *
 * @author AjayS
 */
public class DataStreamsImpl {

    /**
     * This method insert or update data streams and returns the same
     *
     * @param datastore
     * @param dataStreams
     * @param dataStreamsID
     * @return DataStreams object
     * @throws java.lang.Exception
     */
    public Object insertOrUpdateDataStreams(Datastore datastore, DataStreams dataStreams,
            String dataStreamsID) throws Exception {

        Key<DataStreams> savedKey = null;
        if (dataStreamsID == null) {

            DevicesImpl devicesImpl = new DevicesImpl();
            Devices devices = devicesImpl.fetchDeviceDetailsById(datastore, dataStreams.getDeviceId());
            if (devices != null) {
                savedKey = datastore.save(dataStreams);
            } else {

                throw new Exception(Constants.DEVICE_DOES_NOT_EXISTS);
            }
        } else {
            dataStreams.setId(dataStreamsID);
        }
        System.out.println("Key: " + savedKey.getId());
        DataStreams responseDataStreams = fetchDataStreamsByID(datastore, savedKey.getId().toString());        
        return responseDataStreams;
    }

    /**
     * This method delete the particular data stream
     *
     * @param datastore
     * @param id
     * @return delete operation result
     */
    public Object deleteDataStream(Datastore datastore, String id) {

        try {
            DataStreams dataStreams = new DataStreams();
            dataStreams.setId(id);
            WriteResult result = datastore.delete(dataStreams);
            return result.toString();

        } catch (Exception exception) {          
            return exception.getMessage();
        }
    }

    /**
     * This method returns all data streams
     *
     * @param datastore
     * @param index
     * @return data streams list
     */
    public List<DataStreams> fetchAllDataStreams(Datastore datastore, Integer index) {

        List<DataStreams> dataStreamsList = new ArrayList<>();

        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        dataStreamsList = query.asList();
        return dataStreamsList;
    }

    /**
     * This method returns the total count of streams in DB
     * @param datastore
     * @return 
     */
    public long fetchDataStreamCount(Datastore datastore) {

        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        long totalRecord = query.countAll();
        return totalRecord;
    }

    /**
     * Returns recent 200 records of data streams
     *
     * @param datastore
     * @param index
     * @return latest 200 DataStreams records
     */
    public List<DataStreams> fetchRecentDataStreamsRecords(Datastore datastore, Integer index) {

        List<DataStreams> dataStreamsList = new ArrayList<>();
        DevicesImpl devicesImpl = new DevicesImpl();
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        dataStreamsList = query.limit(200).order("-timestamp").asList();

        return dataStreamsList;
    }

    /**
     * This Method returns data stream between particular date range
     *
     * @param datastore
     * @param afterDate
     * @param beforeDate
     * @return
     */
    public List<DataStreams> fetchDataStreamsBetweenTwoDays(Datastore datastore, Date afterDate, Date beforeDate) {

        List<DataStreams> dataStreamsList = null;
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);      
        query.field("timestamp").greaterThan(afterDate);

        dataStreamsList = query.asList();
        return dataStreamsList;

    }

    /**
     * This Method returns data stream of particular device between particular
     * date range
     *
     * @param datastore
     * @param deviceID
     * @param afterDate
     * @param beforeDate
     * @param limit is record limit
     * @return
     */
    public List<DataStreams> fetchListByDeviceIDBetweenTwoDays(Datastore datastore,
            String deviceID, Date afterDate, Date beforeDate, Integer limit) {

        List<DataStreams> dataStreamsList = new ArrayList<>();

        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        if (deviceID != null) {
            if (!deviceID.trim().isEmpty()) {
                ObjectId deviceObjectId = new ObjectId(deviceID);
                query.field("deviceId").equal(deviceObjectId);
            }
        }
        if (afterDate != null) {
            query.field("timestamp").greaterThanOrEq(afterDate);
        }
        if (beforeDate != null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beforeDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();
            System.out.println("nextDate Date: " + endDate);
            query.field("timestamp").lessThan(endDate);
        }
        if (limit == null) {
           dataStreamsList = query.asList();        
        } else if (limit == -1) {
            dataStreamsList = query.asList();
        } else {

            dataStreamsList = query.limit(limit).order("-timestamp").asList();
        }
        return dataStreamsList;

    }
    
    /**
     * This Method returns data stream of particular device between particular
     * date range
     *
     * @param datastore
     * @param deviceID
     * @param afterDate
     * @param beforeDate
     * @param limit is record limit
     * @return
     */
    public List<byte[]> fetchFileByDeviceIDBetweenTwoDays(Datastore datastore,
            String deviceID, Date afterDate, Date beforeDate, Integer limit) {
        byte[] data=null;       
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        List<byte[]> filepath=new ArrayList<byte[]>();
        
        //check if there is any filter on device
        if (deviceID != null) {
            if (!deviceID.trim().isEmpty()) {
                ObjectId deviceObjectId = new ObjectId(deviceID);
                query.field("deviceId").equal(deviceObjectId);
            }
        }
        
        //check if there is any filter on afterDate
        if (afterDate != null) {
            query.field("timestamp").greaterThanOrEq(afterDate);
        }
        
        //check if there is any filter on beforeDate
        if (beforeDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beforeDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();
            System.out.println("nextDate Date: " + endDate);
            query.field("timestamp").lessThan(endDate);            
        }
                
        //check if there is any filter on limit
        if (limit == null||limit==-1) {                      
            long totalRecord = query.countAll();
            Iterable<DataStreams> iterable= query.batchSize(0).fetch();
            Iterator<DataStreams> iterator=iterable.iterator();
            System.out.println("total number of records="+totalRecord);
            int batchCount=30000;
            //int batchCount=10;
            if(totalRecord<batchCount)
            {
                try {
                    File temp=File.createTempFile("temp", ".json");
                    FileWriter file=new FileWriter(temp);                  
                    file.write("[");  
                    while (iterator.hasNext()) {
                        Gson gson = new Gson();
                        DataStreams obj = iterator.next();
                        String strJson = gson.toJson(obj, DataStreams.class);
                        file.write(strJson);
                        if (iterator.hasNext()) {
                            file.write(",");
                        }
                    }
                    file.write("]");
                    file.close();
                    filepath.add(Files.readAllBytes(temp.toPath()));
                    
                    //delete temp files
                    temp.delete();
                } catch (Exception ex) {
                    Logger.getLogger(DataStreamsImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return filepath;
            }
         else
            {
         
            int i=0;           
            try
            {                
                //query.limit(10).asList();
                File temp=null;
                FileWriter file=null;
                 while (iterator.hasNext()) {                 
                     if(i==0)
                     {
                         temp = File.createTempFile("temp", ".json");
                         file = new FileWriter(temp);
                         System.out.println("path of temp file="+temp.getPath());
                         file.write("[");                       
                     }
                     if(i<=batchCount-1)
                     {
                         Gson gson = new Gson();
                         DataStreams obj = iterator.next();
                         String strJson = gson.toJson(obj, DataStreams.class);
                         file.write(strJson);
                         if (iterator.hasNext()) {
                             file.write(",");
                         }
                         i++;
                     }
                     if((i%batchCount==0)||!(iterator.hasNext()))
                     {
                         file.write("]");
                         file.close();
                         filepath.add(Files.readAllBytes(temp.toPath()));
                         //delete temp files
                         temp.delete();
                         i=0;
                     }                                              
                 }
           
                return filepath;
            }
            catch(IOException ex)
            {
                  System.out.println("error is"+ex.getMessage());
            }
            System.out.println("iteration done");
            }
        }  else {          
            Iterable<DataStreams> iterable= query.limit(limit).order("-timestamp").fetch();
            Iterator<DataStreams> iterator=iterable.iterator();
            int i=0;
            try
            {
            File temp = File.createTempFile("temp", ".json");
            FileWriter file = new FileWriter(temp);
            
            while (iterator.hasNext()) {          
               Gson gson = new Gson();
               file.write(gson.toJson(iterator.next().toString()));
               System.out.println("printing record="+i);
               i++;
            }                  
            file.close();          
            filepath.add(Files.readAllBytes(temp.toPath()));               
            }
            catch(IOException ex)
            {
                  System.out.println("error is"+ex.getMessage());
            }
           System.out.println("iteration done");
        }
        return filepath;
    }
    
    
    /**
     * This Method returns recent data stream of particular device 
     *
     * @param datastore
     * @param deviceID
     * @param afterDate
     * @param beforeDate
     * @param limit is record limit
     * @return
     */
    public List<DataStreams> fetchRecentListByDeviceIDBetweenTwoDays(Datastore datastore,
            String deviceID, Date afterDate, Date beforeDate, Integer limit) {

        List<DataStreams> dataStreamsList = new ArrayList<>();
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        if (deviceID != null) {
            if (!deviceID.trim().isEmpty()) {
                ObjectId deviceObjectId = new ObjectId(deviceID);
                query.field("deviceId").equal(deviceObjectId);
            }
        }                
        dataStreamsList = query.limit(limit).order("-timestamp").asList();       
        return dataStreamsList;
    }
    
    
    

    /**
     * This method returns the list of Data Streams based on device ID
     *
     * @param datastore
     * @param deviceID
     * @return
     */
    public List<DataStreams> fetchListByDeviceID(Datastore datastore, String deviceID) {

        ObjectId deviceObjectId = new ObjectId(deviceID);
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        List<DataStreams> dataStreamsList = query.filter("deviceId", deviceObjectId).asList();
        return dataStreamsList;
    }

    /**
     * This method returns the Data Stream list by date and limit filter on a
     * device
     *
     * @param datastore
     * @param deviceID
     * @param afterDate
     * @param limit
     * @return
     */
    public List<DataStreams> fetchListByDeviceIdWithFilters(Datastore datastore,
            String deviceID, Date afterDate, Integer limit) {
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        Date prevDate = null;

        if (afterDate != null) {
            query.field("timestamp").greaterThanOrEq(afterDate);
        }
        if (limit != null) {
            query.limit(limit);
        }
        if (deviceID != null) {
            ObjectId deviceObjectId = new ObjectId(deviceID);
            query.filter("deviceId", deviceObjectId);
        }

        List<DataStreams> dataStreamsList = query.asList();
        return dataStreamsList;
    }

    /**
     * This method returns the list of Data Streams based on passed ID
     *
     * @param datastore
     * @param ID
     * @return
     */
    public DataStreams fetchDataStreamsByID(Datastore datastore, String ID) {

        ObjectId ObjectId = new ObjectId(ID);
        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        DataStreams dataStreamsList = query.filter("_id", ObjectId).get();
        return dataStreamsList;
    }

    /**
     * This method returns list of data streams based on sync condition
     *
     * @param datastore
     * @param syncCondition
     * @return
     */
    public List<DataStreams> fetchListBySyncCondition(Datastore datastore, boolean syncCondition) {

        Query<DataStreams> query = datastore.createQuery(DataStreams.class);
        List<DataStreams> dataStreamsList = query.filter("isSynced", syncCondition).asList();
        return dataStreamsList;
    }

    public List<DataStreams> getDateStreams(Date incDate, Datastore datastore) {

        System.out.println("Current Date: " + incDate);
        Date nextDate, prevdate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(incDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        prevdate = calendar.getTime();
        System.out.println("prevdate Date: " + prevdate);

        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(incDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        nextDate = calendar.getTime();
        System.out.println("nextDate Date: " + nextDate);

        Query<DataStreams> query = datastore.createQuery(DataStreams.class);

        query.field("timestamp").lessThan(nextDate);
        query.field("timestamp").greaterThan(prevdate);

        List<DataStreams> dataStreamses = query.asList();

        return dataStreamses;
    }

    public List<DailyStats> getDateWiseCount(Datastore datastore) {

        List<DailyStats> dailyStatses = new ArrayList<>();
        List<DataStreams> dataStreamses = fetchAllDataStreams(datastore, null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Getting System Date in UTC
        Instant now = new Date().toInstant();
        ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);

        //Getting 6 days before date in UTC
        ZonedDateTime monthZonedDateTime = localDateTime.minusDays(6);
        ZonedDateTime recordDate = monthZonedDateTime;

        for (int i = 0; i <= 6; i++) {

            if (i != 0) {

                recordDate = monthZonedDateTime.plusDays(1);
                monthZonedDateTime = recordDate;
            }
            String recordDateString = recordDate.format(formatter);
            List<DataStreams> dataStreamses1 = new ArrayList<>();
            ArrayList<Integer> indexes = new ArrayList<>();

            for (DataStreams dataStreams : dataStreamses) {
                try {
                    Date date = dateFormat.parse(dataStreams.getTimestamp());
                    String dbDate = dateFormat.format(date);
                    if (recordDateString.equals(dbDate)) {

                        dataStreamses1.add(dataStreams);

                    }
                } catch (ParseException ex) {
                    Logger.getLogger(DataStreamsImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (dataStreamses1.size() > 0) {
                DailyStats dailyStats = new DailyStats();
                dailyStats.setCount(dataStreamses1.size());
                dailyStats.setSysDate(recordDateString);
                dailyStatses.add(dailyStats);
            }

        }
        return dailyStatses;
    }

    public List<DailyStats> getDateWiseStatusCount(Datastore datastore) throws ParseException {

        ArrayList<DailyStats> dailyStatsList = new ArrayList<>();
        int record = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        //Getting System Date in UTC
        Instant now = new Date().toInstant();
        ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);

        //Getting 6 days before date in UTC
        ZonedDateTime monthZonedDateTime = localDateTime.minusDays(6);
        ZonedDateTime recordDate = monthZonedDateTime;

        for (int i = 0; i <= 6; i++) {

            if (i != 0) {

                recordDate = monthZonedDateTime.plusDays(1);
                monthZonedDateTime = recordDate;
            }

            ZonedDateTime prevZonedDateTime = recordDate.minusDays(1);
            ZonedDateTime nextZonedDateTime = recordDate.plusDays(1);

            DateTime recordDateTime = new DateTime(recordDate.toInstant().toEpochMilli(),
                    DateTimeZone.UTC);

            DateTime prevdateTime = new DateTime(prevZonedDateTime.toInstant().toEpochMilli(),
                    DateTimeZone.UTC);

            DateTime nextdateTime = new DateTime(nextZonedDateTime.toInstant().toEpochMilli(),
                    DateTimeZone.UTC);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(prevZonedDateTime.toEpochSecond() * 1000);

            Date prevString = calendar.getTime();

            calendar.setTimeInMillis(nextZonedDateTime.toEpochSecond() * 1000);

            Date nextString = calendar.getTime();

            Query<DataStreams> query = datastore.createQuery(DataStreams.class);
            query.field("timestamp").equal(recordDateTime.toDate());


            List<DataStreams> dataStreamses = query.asList();

            if (dataStreamses != null) {

                if (dataStreamses.size() > 0) {

                    //if record found then adding the count
                    record++;
                    DailyStats dailyStats = new DailyStats();
                    dailyStats.setCount(dataStreamses.size());
                    dailyStats.setSysDate(recordDate.format(formatter));
                    dailyStatsList.add(dailyStats);
                }
            }
        }


        System.out.println("***********************************************");
        System.out.println("record: " + record);
        return dailyStatsList;
    }

    public List<DailyStats> getCount(Datastore datastore) {

        List<DailyStats> dailyStatses = new ArrayList<>();
        ArrayList<DailyStats> dailyStatsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Getting System Date in UTC
        Instant now = new Date().toInstant();
        ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);

        //Getting 6 days before date in UTC
        ZonedDateTime monthZonedDateTime = localDateTime.minusDays(6);
        ZonedDateTime recordDate = monthZonedDateTime;

        DB db = datastore.getDB();

        for (int i = 0; i <= 6; i++) {

            try {
                if (i != 0) {

                    recordDate = monthZonedDateTime.plusDays(1);
                    monthZonedDateTime = recordDate;
                }

                ZonedDateTime prevZonedDateTime = recordDate.minusDays(1);
                ZonedDateTime nextZonedDateTime = recordDate.plusDays(1);

                DateTime prevdateTime = new DateTime(prevZonedDateTime.toInstant().toEpochMilli(),
                        DateTimeZone.UTC);

                DateTime nextdateTime = new DateTime(nextZonedDateTime.toInstant().toEpochMilli(),
                        DateTimeZone.UTC);

                Date prevString = dateFormat.parse(prevZonedDateTime.format(formatter));
                Date nextString = dateFormat.parse(nextZonedDateTime.format(formatter));
                Date recordDateString = dateFormat.parse(recordDate.format(formatter));

                Date dt = prevdateTime.toDate();

                BasicDBObject query = new BasicDBObject("timestamp",
                        new BasicDBObject("$gte", prevdateTime.toDate())
                                .append("$lt", nextdateTime.toDate()));
                DBObject bObject = new BasicDBObject("timestamp", recordDateString);

                long count = db.getCollection("datastreams").count(query);
                if (count > 0) {

                    DailyStats dailyStats = new DailyStats();
                    dailyStats.setCount(count);
                    dailyStats.setSysDate(recordDate.format(formatter));
                    dailyStatsList.add(dailyStats);

                }

            } catch (ParseException ex) {
                Logger.getLogger(DataStreamsImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return dailyStatsList;
    }

    public List<DailyStats> getCount2(Datastore datastore) {

        ArrayList<DailyStats> dailyStatsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Getting System Date in UTC
        Instant now = new Date().toInstant();
        ZonedDateTime localDateTime = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);

        //Getting 6 days before date in UTC
        ZonedDateTime monthZonedDateTime = localDateTime.minusDays(30);
        ZonedDateTime recordDate = monthZonedDateTime;

        for (int i = 0; i <= 30; i++) {

            if (i != 0) {

                recordDate = monthZonedDateTime.plusDays(1);
                monthZonedDateTime = recordDate;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(recordDate.toEpochSecond() * 1000);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startDate = calendar.getTime();

            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);

            Date endDate = calendar.getTime();

            Query<DataStreams> query = datastore.createQuery(DataStreams.class);

            query.field("timestamp").greaterThan(startDate);
            query.field("timestamp").lessThan(endDate);

            long dataCount = query.countAll();
            if (dataCount > 0) {
                DailyStats dailyStats = new DailyStats();
                dailyStats.setCount(dataCount);
                dailyStats.setSysDate(recordDate.format(formatter));
                dailyStatsList.add(dailyStats);
            }

        }
        return dailyStatsList;
    }
    
//    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
//
//		System.out.println("Writing '" + fileName + "' to zip file");
//
//		File file = new File(fileName);
//		FileInputStream fis = new FileInputStream(file);
//		ZipEntry zipEntry = new ZipEntry(fileName);
//		zos.putNextEntry(zipEntry);
//
//		byte[] bytes = new byte[1024];
//		int length;
//		while ((length = fis.read(bytes)) >= 0) {
//			zos.write(bytes, 0, length);
//		}
//
//		zos.closeEntry();
//		fis.close();
//	}
}
