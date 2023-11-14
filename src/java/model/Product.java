/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Product {
    private int id;
    private int phase;
    private float kw;
    private int speed;
    private String country;
    private float price;
    private int quantity;

    public Product() {
    }

    public Product(int id, int phase, float kw, int speed, String country, float price, int quantity) {
        this.id = id;
        this.phase = phase;
        this.kw = kw;
        this.speed = speed;
        this.country = country;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int phase, float kw, int speed, String country, float price, int quantity) {
        this.phase = phase;
        this.kw = kw;
        this.speed = speed;
        this.country = country;
        this.price = price;
        this.quantity = quantity;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public float getKw() {
        return kw;
    }

    public void setKw(float kw) {
        this.kw = kw;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
