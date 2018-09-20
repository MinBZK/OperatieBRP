/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import net.sf.ehcache.CacheManager;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stap die informatie haalt uit de gebruikte {@link SessionFactory}.
 */
@Component
public class HibernateReportStap implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateReportStap.class);

    @Inject
    private EntityManagerFactory entityManagerFactory;

    private Statistics statistics;

    @Override
    public boolean execute(final Context context) throws Exception {
        HibernateEntityManagerFactory hbmanagerfactory = (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactory sessionFactory = hbmanagerfactory.getSessionFactory();

        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        return false;
    }

    @Override
    public boolean postprocess(final Context context, final Exception exception) {
        LOGGER.info("Hibernate Statistics:\n {}", statistics);

        LOGGER.info("Ehcache Statistics:\n");
        CacheManager cacheManager = CacheManager.getInstance();

        for (String cacheName : cacheManager.getCacheNames()) {
            LOGGER.info("{}", cacheManager.getCache(cacheName).getStatistics());
        }

        return false;
    }
}
