/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.datatoegang;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Inject
    @Named("masterDataSource")
    private DataSource dataSource;

    @PostConstruct
    public void postConstruct() {
        ((BasicDataSource) dataSource).setDefaultAutoCommit(false);
    }

    @Override
    @Transactional
    public void voerUitMetTransactie(final Runnable runnable) {
        runnable.run();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
