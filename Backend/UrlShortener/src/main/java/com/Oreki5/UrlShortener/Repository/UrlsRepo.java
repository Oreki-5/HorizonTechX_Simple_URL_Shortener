package com.Oreki5.UrlShortener.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Oreki5.UrlShortener.Models.Urls;

public interface UrlsRepo extends JpaRepository<Urls, Integer> {
    //custom interface to fetch url object by its shortUrl
    Urls findByShortUrl(String shortUrl);
    Urls findByLongUrl(String longUrl);
}
