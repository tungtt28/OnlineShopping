/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Account {
    private String username;
    private String password;
    private String displayname;
    private int eid;

    public Account() {
    }

    public Account(String username, String password, String displayname) {
        this.username = username;
        this.password = password;
        this.displayname = displayname;
    }
    public Account(String username, String password, String displayname, int eid) {
        this.username = username;
        this.password = password;
        this.displayname = displayname;
        this.eid = eid;
    }

    public Account( String password, String displayname) {
        this.password = password;
        this.displayname = displayname;
    }
    
    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int pid) {
        this.eid = pid;
    }
  
    
}
