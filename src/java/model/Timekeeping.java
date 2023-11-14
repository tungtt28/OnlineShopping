/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;


public class Timekeeping {
    private int id;
    private Date day;

    public Timekeeping() {
    }

    public Timekeeping(int id, Date day) {
        this.id = id;
        this.day = day;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
    
}
