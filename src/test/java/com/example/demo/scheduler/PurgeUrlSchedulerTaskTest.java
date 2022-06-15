package com.example.demo.scheduler;

import com.example.demo.persistence.UrlEntity;
import com.example.demo.persistence.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PurgeUrlSchedulerTaskTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private PurgeUrlScheduledTask purgeUrlScheduledTask;

    @Test
    public void testRemoveExpiredUrls() {
        urlRepository.save(UrlEntity.of("ABCD0001", "http://avalid.url", -35));
        urlRepository.save(UrlEntity.of("ABCD0002", "http://avalid.url", -40));
        urlRepository.save(UrlEntity.of("ABCD0003", "http://avalid.url", -15));
        urlRepository.save(UrlEntity.of("ABCD0004", "http://avalid.url", -5));
        urlRepository.save(UrlEntity.of("ABCD0005", "http://avalid.url", 0));
        urlRepository.save(UrlEntity.of("ABCD0006", "http://avalid.url", 3));
        urlRepository.save(UrlEntity.of("ABCD0007", "http://avalid.url", 10));

        purgeUrlScheduledTask.purgeExpiredUrls();

        // hash that should be purged
        assertTrue(urlRepository.findByHash("ABCD0001").isEmpty());
        assertTrue(urlRepository.findByHash("ABCD0002").isEmpty());
        assertTrue(urlRepository.findByHash("ABCD0003").isEmpty());
        assertTrue(urlRepository.findByHash("ABCD0004").isEmpty());
        assertTrue(urlRepository.findByHash("ABCD0005").isEmpty());

        // hash that is still present in repository
        assertTrue(urlRepository.findByHash("ABCD0006").isPresent());
        assertTrue(urlRepository.findByHash("ABCD0007").isPresent());
    }
}
