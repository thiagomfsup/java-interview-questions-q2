package com.example.demo;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ShortnerService;

import lombok.extern.slf4j.Slf4j;

/**
 * A {@link RestController} class that exposes the service's APIs.
 * 
 * 
 * @author Thiago M. Ferreira
 *
 */
@RestController
@Slf4j
public class ShortenURLController {

    @Autowired
    private ShortnerService shortnerURLService;

    /**
     * Register an URL and returns a 8 character-long hash.
     * 
     * @param originalURL String representation of the URL to be shortened.
     * @return A 8 character-long hash that identifies the URL in the service.
     * @throws MalformedURLException Thrown when the {@code originalUrl} parameter
     *                               is not a valid URL.
     */
    @PostMapping("/short")
    public ResponseEntity<String> shortenURL(@RequestParam(name = "url") String originalURL)
            throws MalformedURLException {
        log.info("URL to shorten: {}", originalURL);

        return ResponseEntity.ok(shortnerURLService.shortURL(originalURL));
    }

    /**
     * Retrieve a previously registered URL.
     * 
     * @param tiny a 8 character-long hash that identify the URL in the service.
     * @return <code>HTTP 302 Found<code> with original URL in the
     *         <code>Location<code> URL.<br>
     *         If the given hash doesn't identify an URL, a <code>HTTP 404 Not
     *         Found<code> is returned.
     */
    @GetMapping("/long")
    public ResponseEntity<?> urlRecovery(@RequestParam(name = "tiny") String tiny) {
        Optional<String> originalUrl = shortnerURLService.retrieveUrlByHash(tiny);

        if (originalUrl.isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl.get())).build();

        return ResponseEntity.notFound().build();
    }
}
