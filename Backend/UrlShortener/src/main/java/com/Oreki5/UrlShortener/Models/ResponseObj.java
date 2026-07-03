package com.Oreki5.UrlShortener.Models;

public class ResponseObj {
    private String url;
    private Exception error;

    public ResponseObj(String url, Exception error) {
        this.url = url;
        this.error = error;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public Exception getError() {
        return error;
    }

}
