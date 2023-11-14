/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;


public class Employee {

    private int id;
    private String lastname;
    private String firstname;
    private boolean gender;
    private Date dob;
    private Date hiredate;
    private int salary;
    private String address;
    private String phone;
    private String mail;
    private Account account;
    private ArrayList<Timekeeping> timekeeping = new ArrayList<>();

    public Employee(int id, String lastname, String firstname, boolean gender, Date dob, Date hiredate, int salary, String address, String phone, String mail) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.dob = dob;
        this.hiredate = hiredate;
        this.salary = salary;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
    }

    public Employee( String lastname, String firstname, boolean gender, Date dob, Date hiredate, int salary, String address, String phone, String mail) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.dob = dob;
        this.hiredate = hiredate;
        this.salary = salary;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
    public Date getHiredate() {
        return hiredate;
    }

    public ArrayList<Timekeeping> getTimekeeping() {
        return timekeeping;
    }

    public void setTimekeeping(ArrayList<Timekeeping> timekeeping) {
        this.timekeeping = timekeeping;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
