package com.example.demo.service;

import java.net.URL;

/**
 * Interface that should be implemented by classes that wants to generate a Hash
 * value to a given URL.
 * <p>
 * Implementors are free to use any method to generate a hash value for the
 * given URL. However, they must guarantee that Hashs generates for the same URL
 * are equal, i.e., if the {@link #generateHashForURL(URL)} method is called
 * several times for the same URL, it must generate the same Hash for each of
 * those method call.
 */
public interface UrlHashService {

    /**
     * Given a URL, this method generates a hash representation to it.
     * 
     * @param originalUrl The original URL
     * @return A String representing a Hash the identify the original URL.
     */
    public String generateHashForURL(URL originalUrl);

}
