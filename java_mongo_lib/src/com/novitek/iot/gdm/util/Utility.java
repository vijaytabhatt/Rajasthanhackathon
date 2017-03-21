/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.util;

import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is utility class for IOT
 * @author AjayS
 */
public class Utility {

    /**
     * This method creates the response structure on the object
     * either in JSON or XML depends on the mediaType passed
     * @param className
     * @param object
     * @return 
     * @throws JAXBException 
     */
    public String createResponse(Class className, Object object) throws JAXBException {


        JAXBContext contextObj = JAXBContextFactory.createContext(new Class[]{className}, null);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(MarshallerProperties.MEDIA_TYPE,MediaType.APPLICATION_JSON);
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringWriter stringWriter = new StringWriter();
        marshallerObj.marshal(object, stringWriter);

        return stringWriter.toString();
    }
    
    /**
     * Validate Email id Format
     * @param email
     * @return 
     */
    public boolean isValidateEmailFormat(String email){
        
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
     * This method is intended to return the error message
     * @param errorMessage
     * @return
     * @throws JSONException 
     */
    public String errorResponse(Exception errorMessage) {
        
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("message", errorMessage.getLocalizedMessage());
            return jSONObject.toString();
        } catch (JSONException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public String simpleMessage(String message) {
        
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("message", message);
            return jSONObject.toString();
        } catch (JSONException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    /**
     * This method generates the Complex random Session Token
     * @return 
     */
    public String generateComplexSessionToken(){
        
        UUID initialUuid = UUID.randomUUID();
        String initialUuidString = String.valueOf(initialUuid).replaceAll("-", "");
        Logger.getLogger(Utility.class.getName()).log(Level.INFO, "initialUuidString: "+initialUuidString);
        String paddedUuid = padUDID(initialUuidString);
        
        return paddedUuid;
    }

    /**
     * This method is used for padding the generated session token
     * @param currentUDID
     * @return 
     */
    private String padUDID(String currentUDID) {
    
        String newUDID ="";
        
        try{
        
            int remainingLength = 40 - currentUDID.length();
            int currentLength= currentUDID.length();
            
            UUID random = UUID.randomUUID();
            String randomUDID = String.valueOf(random).replaceAll("-","").toUpperCase();
            char[] randomUDIDArray = randomUDID.toCharArray();
            
            Logger.getLogger(Utility.class.getName()).log(Level.INFO, "Random UUID = "+String.valueOf(random).replaceAll("-","").toUpperCase() +" Length = "+ random.toString().length());
            Logger.getLogger(Utility.class.getName()).log(Level.INFO, "Random UUID Char array = " +randomUDIDArray +" Length = "+ randomUDIDArray.length);
            Logger.getLogger(Utility.class.getName()).log(Level.INFO, "Random UUID String = " +randomUDID +" Length = "+ randomUDID.length());
            
            String paddedUDIDString = "";
            
            for(int i =0; i<remainingLength ; i++){

                 paddedUDIDString = paddedUDIDString + randomUDID.charAt(i);
                
            }
            
             newUDID = currentUDID.concat(paddedUDIDString);
            
           
            System.out.println("Actual UDID = "+currentUDID +" Length = "+currentUDID.length());
            System.out.println("Padded UDID = "+paddedUDIDString +" Length = "+paddedUDIDString.length());
            System.out.println("New UDID = "+newUDID +" Length = " +newUDID.length());            
            
             String groupID = "";
            //Making Group
            int j=0,k=0;
            for(int i=0;i<40;i++){
            
                groupID = groupID + paddedUDIDString.charAt(k)+paddedUDIDString.charAt(k+1);
                k= k + 2;
                
                groupID = groupID + currentUDID.charAt(j)+ currentUDID.charAt(j+1)+ currentUDID.charAt(j+2)+ currentUDID.charAt(j+3)+ currentUDID.charAt(j+4)+ currentUDID.charAt(j+5)+ currentUDID.charAt(j+6)+ currentUDID.charAt(j+7);
                j = j + 8;
                
                i = j + k - 1;
            
            }
            
            newUDID = groupID;
            System.out.println("New Gruop UDID = "+groupID +" Length = " +groupID.length());            
        
        }
        catch(Exception e){
        
            e.printStackTrace();
        }
        
        return newUDID;
        
    }
    
   
}
