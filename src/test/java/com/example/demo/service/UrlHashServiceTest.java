package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { UrlHashServiceImpl.class })
public class UrlHashServiceTest {

    @Autowired
    private UrlHashService urlHashService;

    @Test
    public void givenAUrl_whenGeneratingHash_thenGenerateAHashValueWith8Characters() throws MalformedURLException {
        URL url = new URL(
                "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json");

        String generatedHash = urlHashService.generateHashForURL(url);

        assertNotNull(generatedHash);
        assertEquals(8, generatedHash.length());
    }

    /**
     * This test guarantee that calling
     * {@link UrlHashService#generateHashForURL(URL)} method several time for the
     * same URL, the same Hash will be generated.
     * 
     * @throws MalformedURLException
     */
    @Test
    public void givenAUrl_whenGeneratingHashSeveralTimes_thenGenerateHashAreEqual() throws MalformedURLException {
        URL url = new URL("http://avalid.url");

        String hash1 = urlHashService.generateHashForURL(url);
        String hash2 = urlHashService.generateHashForURL(url);

        assertEquals(hash1, hash2);
    }

    @Test
    public void givenNullURL_whenGeneratingHash_thenThrowNullPointerException() {
        final URL nullURl = null;

        assertThrows(NullPointerException.class, () -> {
            urlHashService.generateHashForURL(nullURl);
        });
    }
}
