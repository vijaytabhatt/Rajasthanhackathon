/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import java.util.Date;
import java.util.logging.Level;
import org.mongodb.morphia.annotations.Transient;

/**
 *
 * @author AjayS
 */
public class Logging {

    private String level;

    private String errorInClass;

    private String errorMessage;

    private Date date;

    public Logging() {
    }

    public Logging(String level, String errorInClass, String errorMessage, Date date) {
        this.level = level;
        this.errorInClass = errorInClass;
        this.errorMessage = errorMessage;
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getErrorInClass() {
        return errorInClass;
    }

    public void setErrorInClass(String errorInClass) {
        this.errorInClass = errorInClass;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
