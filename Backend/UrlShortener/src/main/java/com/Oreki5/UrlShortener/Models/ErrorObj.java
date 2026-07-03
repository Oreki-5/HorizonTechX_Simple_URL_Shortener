package com.Oreki5.UrlShortener.Models;

public class ErrorObj {

    private String error;

    ErrorObj(String error){
        this.error = error;
    }

    public String getError(){
        return error;
    }

    public void setError(String error){
        this.error = error;
    }
}
