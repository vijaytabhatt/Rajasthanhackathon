/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity.wrapper;

import com.novitek.iot.gdm.entity.DataStreams;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AjayS
 */
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class DataStreamWrapper {
        
    private List<DataStreams> dataStreamses;

    public List<DataStreams> getDataStreamses() {
        return dataStreamses;
    }

    public void setDataStreamses(List<DataStreams> dataStreamses) {
        this.dataStreamses = dataStreamses;
    }
    
    
}
