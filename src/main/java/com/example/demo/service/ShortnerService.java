package com.example.demo.service;

import java.net.MalformedURLException;
import java.util.Optional;

public interface ShortnerService {

    /**
     * Save an URL and create a hash representation to it.
     * 
     * @param originalUrl Original URL to save.
     * @return Hash representation to the original URL.
     * @throws MalformedURLException Thrown when the {@code originalUrl} parameter
     *                               is not a valid URL.
     */
    String shortURL(String originalUrl) throws MalformedURLException;

    /**
     * Retrieve the original URL from its hash.
     * 
     * @param hash
     * @return {@link Optional}
     */
    Optional<String> retrieveUrlByHash(String hash);

}
