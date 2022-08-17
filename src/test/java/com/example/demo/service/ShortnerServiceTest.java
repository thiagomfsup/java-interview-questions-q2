package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.net.MalformedURLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;

@SpringBootTest
@ContextConfiguration(classes = { ShortnerServiceImpl.class })
public class ShortnerServiceTest {

    @Autowired
    private ShortnerService shortnerService;

    @MockBean
    private UrlHashService urlHashService;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    public void givenAnInvalidURL_whenShorteningAnUrl_thenThrowMalformedURLException() {
        String malformedUrl = "unknownProtocol://url.fake";

        assertThrows(MalformedURLException.class, () -> {
            shortnerService.shortURL(malformedUrl);
        });
    }

    @Test
    public void givenAValidURL_whenShorteningAnUrl_thenGenerateHashAndSaveToRepository() throws MalformedURLException {
        String url = "http://avalid.url";
        String expectedHash = "1234ABCD";

        when(urlHashService.generateHashForURL(Mockito.any())).thenReturn(expectedHash);
        when(urlRepository.existsByHash(expectedHash)).thenReturn(false);

        // when
        String hash = shortnerService.shortURL(url);

        assertEquals(expectedHash, hash);

        ArgumentCaptor<UrlEntity> urlEntityCaptor = ArgumentCaptor.forClass(UrlEntity.class);
        verify(urlRepository).save(urlEntityCaptor.capture());
        assertEquals(url, urlEntityCaptor.getValue().getOriginalUrl());
        assertEquals(expectedHash, urlEntityCaptor.getValue().getHash());
    }

    @Test
    public void givenAValidUrlAlreadyAdded_whenShorteningAnUrl_thenReturnAlreadyCreatedHash()
            throws MalformedURLException {
        String alreadyAddedUrl = "http://avalid.url";
        String expectedHash = "1234ABCD";

        when(urlHashService.generateHashForURL(Mockito.any())).thenReturn(expectedHash);
        when(urlRepository.existsByHash(expectedHash)).thenReturn(true);

        // when
        String hash = shortnerService.shortURL(alreadyAddedUrl);

        assertEquals(expectedHash, hash);

        ArgumentCaptor<UrlEntity> urlEntityCaptor = ArgumentCaptor.forClass(UrlEntity.class);
        verify(urlRepository, times(0)).save(urlEntityCaptor.capture());
    }

    @Test
    public void givenValidHash_whenRetrievingURL_thenReturnSavedUrl() {
        String hash = "1234ABCD";
        String alreadyAddedUrl = "http://avalid.url";
        UrlEntity urlEntity = UrlEntity.of(hash, alreadyAddedUrl, 0);

        when(urlRepository.findByHash(hash)).thenReturn(Optional.of(urlEntity));

        // when
        Optional<String> retrieveUrlByHash = shortnerService.retrieveUrlByHash(hash);

        assertEquals(alreadyAddedUrl, retrieveUrlByHash.get());
    }
    
    @Test
    public void givenInvalidHash_whenRetrievingURL_thenReturnSavedUrl() {
        String hash = "1234ABCD";

        when(urlRepository.findByHash(hash)).thenReturn(Optional.empty());

        // when
        Optional<String> retrieveUrlByHash = shortnerService.retrieveUrlByHash(hash);

        assertFalse(retrieveUrlByHash.isPresent());
    }

}
