package com.example.demo.scheduler;

import static java.time.LocalDateTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.persistence.UrlRepository;

/**
 * {@link PurgeUrlScheduledTask} is a utility class that controls a scheduled
 * task to purge expired shortened URLs.
 */
@Component
public class PurgeUrlScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(PurgeUrlScheduledTask.class);

    @Autowired
    private UrlRepository urlRepository;

    /**
     * Invoke a process that purge expired shortened URLs.
     * <p>
     * This method is annotated with Spring's {@link Scheduled} annotation and the
     * execution time is defined by a cron expression
     * (<code>${shortenUrl.scheduledTask.cronExpression}<code>).
     * <p>
     */
    @Scheduled(cron = "${shortenUrl.scheduledTask.cronExpression}")
    @Transactional
    public void purgeExpiredUrls() {
        LOG.info("Purging expired URLs");

        int numOfDeletedUrls = urlRepository.purgeExpiredUrlOldThen(now());

        LOG.info("{} URLs has been deleted. Done.", numOfDeletedUrls);
    }

}
