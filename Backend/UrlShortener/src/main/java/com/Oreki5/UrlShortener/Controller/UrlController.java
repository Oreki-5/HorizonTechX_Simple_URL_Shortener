package com.Oreki5.UrlShortener.Controller;

import java.io.IOException;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Oreki5.UrlShortener.Models.ResponseObj;
import com.Oreki5.UrlShortener.Models.Urls;
import com.Oreki5.UrlShortener.Service.ShorteningService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private ShorteningService shortenService;

    @GetMapping("/{short_url}")
    public void redirectToLongUrl(@PathVariable String short_url, HttpServletResponse response) throws IOException {
        response.sendRedirect(shortenService.getLongUrl(short_url));
    }

    @PostMapping("/shorten")
    public ResponseObj shortenUrl(@RequestBody Urls longUrl) {
        // System.out.println("its working");
        if(!longUrl.isEmpty()){
            ResponseObj res = shortenService.shortenUrl(longUrl);
            return res;
        }
        return new ResponseObj(null, new AttributeNotFoundException("Attribute key was Invalid"));
        
        // return ;
        // save the URLs Object in DB
        // then pass the id of the object to Base62 encoder function
        // use that return value to set the short url for the object

        // will return ResponseObj
        
    }
}
