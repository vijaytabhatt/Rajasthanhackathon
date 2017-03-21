/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novitek.iot.gdm.entity;

import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author AjayS
 */
@Entity(value = "layout", noClassnameStored = true)
public class Layout {
    
    private String theme;
    private boolean asideHover;
    private boolean isFloat;
    private boolean horizontal;
    private boolean isRTL;
    private boolean isBoxed;
    private boolean isCollapsed;
    private boolean isFixed;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isAsideHover() {
        return asideHover;
    }

    public void setAsideHover(boolean asideHover) {
        this.asideHover = asideHover;
    }

    public boolean isIsFloat() {
        return isFloat;
    }

    public void setIsFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean isIsRTL() {
        return isRTL;
    }

    public void setIsRTL(boolean isRTL) {
        this.isRTL = isRTL;
    }

    public boolean isIsBoxed() {
        return isBoxed;
    }

    public void setIsBoxed(boolean isBoxed) {
        this.isBoxed = isBoxed;
    }

    public boolean isIsCollapsed() {
        return isCollapsed;
    }

    public void setIsCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    public boolean isIsFixed() {
        return isFixed;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }
    
    
    
    
}
