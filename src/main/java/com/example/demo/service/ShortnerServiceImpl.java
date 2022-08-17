package com.example.demo.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;

@Service
public class ShortnerServiceImpl implements ShortnerService {

    @Autowired
    private UrlHashService urlHashService;

    @Autowired
    private UrlRepository urlRepository;

    @Value("${shortenUrl.timeToLive}")
    private int timeToLive;

    @Override
    public String shortURL(String url) throws MalformedURLException {

        String hash = urlHashService.generateHashForURL(new URL(url));

        // save to database if it doesn't exist already
        if (!urlRepository.existsByHash(hash))
            urlRepository.save(UrlEntity.of(hash, url, timeToLive));

        // return 8 first values
        return hash;
    }

    @Override
    public Optional<String> retrieveUrlByHash(String hash) {
        Optional<UrlEntity> urlEntity = urlRepository.findByHash(hash);

        return Optional.ofNullable(urlEntity.isPresent() ? urlEntity.get().getOriginalUrl() : null);
    }

}
