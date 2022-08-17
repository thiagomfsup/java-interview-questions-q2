package com.example.demo.service;

import java.net.URL;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * A {@link Service} class responsible to generate Hashes for URLs.
 * <p>
 * This {@link UrlHashService} implementation generates a MD5 hash of the
 * original URL and returns the first 8 HEX character in upper case.
 * 
 * @author Thiago M. Ferreira
 *
 */
@Service
public class UrlHashServiceImpl implements UrlHashService {

    private static final int HASH_LENGTH = 8;

    @Override
    public String generateHashForURL(URL originalUrl) {
        Objects.requireNonNull(originalUrl, "URL cannot be null");

        return DigestUtils
                .md5DigestAsHex(originalUrl.toString().getBytes())
                .substring(0, HASH_LENGTH)
                .toUpperCase();
    }

}
