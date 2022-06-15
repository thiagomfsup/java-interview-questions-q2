package com.example.demo.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    /**
     * Returns whether an {@link UrlEntity} entity with the given hash exists.
     * 
     * @param hash A hash identifier.
     * @return <code>True<code> if an entity with the given hash exists,
     *         <code>False<code> otherwise.
     */
    boolean existsByHash(String hash);

    /**
     * Retrieve an {@link UrlEntity} entity with the given <code>hash<code>
     * parameter.
     * 
     * @param hash A hash identifier.
     * @return An {@link Optional} describing the result of the query.
     */
    Optional<UrlEntity> findByHash(String hash);

    /**
     * Delete shortened URLs which <code>expiredAt<code> timestamp is less than or
     * equal to the given timestamp.
     * 
     * @param dateTime {@link LocalDateTime} object representing the superior limit.
     * @return Number of deleted URLs.
     */
    @Query("delete from UrlEntity ue where ue.expiredAt <= ?1")
    @Modifying
    int purgeExpiredUrlOldThen(LocalDateTime dateTime);

}
