package com.Oreki5.UrlShortener.Service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Oreki5.UrlShortener.Models.ResponseObj;
import com.Oreki5.UrlShortener.Models.Urls;
import com.Oreki5.UrlShortener.Repository.UrlsRepo;

@Service
public class ShorteningService {

    @Value("${HOST_URL}")
    private String hostUrl;

    @Autowired
    private UrlsRepo urlsRepo;

    public String getLongUrl(String shorturl) {
        return urlsRepo.findByShortUrl(shorturl).getLongUrl();
    }

    public ResponseObj shortenUrl(Urls url) {
        /*
         * Logic:
         * check for valid URL string
         * check for duplicate
         * calculate base62 encoded string
         * save record
         * return encoded string attached with hostUrl
         */

        // REGEX TO VALIDATE URL
        // Source - https://stackoverflow.com/a/163398
        // Posted by TomC, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-07-03, License - CC BY-SA 3.0
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        if (!Pattern.matches(regex, url.getLongUrl())) {
            return new ResponseObj(null, new IllegalArgumentException("Invalid URL"));
        }

        try {
            Urls existingUrl = urlsRepo.findByLongUrl(url.getLongUrl());
            //
            if (existingUrl == null) {
                Urls reqUrlObj = new Urls(url.getLongUrl());
                urlsRepo.saveAndFlush(reqUrlObj);
                Urls savedUrl = urlsRepo.findByLongUrl(reqUrlObj.getLongUrl());
                savedUrl.setShortUrl(encoder(savedUrl.getId()));
                urlsRepo.saveAndFlush(savedUrl);
                ResponseObj res = new ResponseObj(hostUrl + savedUrl.getShortUrl(), null);
                return res;
            }
            return new ResponseObj(hostUrl + existingUrl.getShortUrl(), null);
        } catch (Exception e) {
            return new ResponseObj(null, e);
        }

    }

    // base62 encoder function here
    public String encoder(int id) {
        /*
         * Logic:
            * take a string with 62 chars : encodeMap
            * while id > 0:
                * quotient = int/62
                * remainder = int%62
                * map remainder with encodeMap
                * append that char to returnString;
                * id /= 62
         */
        String encodingMap = "poiuytrewqlkjhgfdsamnbvcxz0987654321QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder();
        int q = id;
        while (id > 0) {
            q = Math.floorDiv(id, 62);
            int r = id % 62;
            // System.out.println("Quotient :" + q);
            // System.out.println("Remainder :" + r);
            sb.append(encodingMap.charAt(r));
            id = Math.floorDiv(id, 62);
        }
        return sb.toString();
    }

}
