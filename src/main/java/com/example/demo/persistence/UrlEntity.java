package com.example.demo.persistence;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class that represent the table were URLs and its hashes are stored.
 */
@Data
@Entity
@Table(name = "TBL_SHORTEN_URLS")
public class UrlEntity {

    /**
     * The Entity identifier.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Hash that identify an URL.
     */
    @Column(name = "hash", length = 8, unique = true)
    private String hash;

    /**
     * The original URL.
     */
    @Column(name = "original_url", length = 2000)
    private String originalUrl;

    /**
     * Date/Time this entity was created.
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Date/Time this entity will expire.
     */
    @Column
    private LocalDateTime expiredAt;

    public static UrlEntity of(String hash, String url, int timeToLive) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(url);
        urlEntity.setHash(hash);
        urlEntity.setCreatedAt(LocalDateTime.now());
        urlEntity.setExpiredAt(LocalDateTime.now().plusMinutes(timeToLive));

        return urlEntity;
    }
}
