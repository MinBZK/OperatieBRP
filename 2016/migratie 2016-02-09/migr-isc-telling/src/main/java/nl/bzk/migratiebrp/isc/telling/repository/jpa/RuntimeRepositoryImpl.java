/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.net.UnknownHostException;
import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.bzk.migratiebrp.isc.telling.entiteit.Runtime;
import nl.bzk.migratiebrp.isc.telling.repository.RuntimeRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Runtime Repository.
 */
@Repository
public final class RuntimeRepositoryImpl implements RuntimeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String RUNTIME_NAAM_PARAM = "runtimeNaam";
    private static final int MAXIMUM_LENGTH_CLIENT_NAAM = 60;

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public boolean voegRuntimeToe(final String runtimeNaam) {

        final Runtime runtime = new Runtime();
        runtime.setRuntimeNaam(runtimeNaam);
        runtime.setStartDatum(new Date(System.currentTimeMillis()));
        try {
            runtime.setClientNaam(java.net.InetAddress.getLocalHost().toString());
        } catch (final UnknownHostException e) {
            LOGGER.warn("Kan die ClientNaam niet zetten: Fout bij het resolven van de hostname.", e);
            /*
             * De foutmelding bevat een hint van de te resolven local host name.
             */
            runtime.setClientNaam(StringUtils.left(e.getMessage(), MAXIMUM_LENGTH_CLIENT_NAAM));
        }

        em.persist(runtime);
        return true;
    }

    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void verwijderRuntime(final String runtimeNaam) {
        final Query updateQuery = em.createQuery("DELETE FROM Runtime r WHERE r.runtimeNaam = :runtimeNaam ");
        updateQuery.setParameter(RUNTIME_NAAM_PARAM, runtimeNaam);
        updateQuery.executeUpdate();
    }

}
