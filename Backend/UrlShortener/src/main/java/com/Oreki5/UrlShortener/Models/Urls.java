package com.Oreki5.UrlShortener.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Urls {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String longUrl;
    
    @Column
    private String shortUrl;


    /*
        New knowledge:
        JPA requires a no-arg constructor and creates one automatically if you dont provide one. 
        In this case I did create a separate arg constructor and hence need to provide default one manually
    */
    public Urls(){

    }

    public Urls(String longUrl){
        this.longUrl = longUrl;
    }

    public int getId() {
        return id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public boolean isEmpty(){
        return longUrl == null;
    }
    


}
