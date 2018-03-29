/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.configuratie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Configuratie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.calendar.Duration;
import org.springframework.stereotype.Component;

/**
 * Configuratie DAO via JBPM connectie.
 */
@Component
public final class SessionConfiguratieDao implements ConfiguratieDao {

    private static final Logger LOG = LoggerFactory.getLogger();

    private SessionFactory sessionFactory;

    /**
     * Default constructor.
     * @param sessionFactory De meegegevens session factory
     */
    @Inject
    public SessionConfiguratieDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getConfiguratie(final String naam) {
        LOG.debug("getConfiguratie(naam={})", naam);
        final Session session = sessionFactory.getCurrentSession();

        final Configuratie configuratie = (Configuratie) session.get(Configuratie.class, naam);
        if (configuratie == null) {
            throw new IllegalArgumentException("Configuratie '" + naam + "' is onbekend.");
        }

        return configuratie.getWaarde();
    }

    @Override
    public Integer getConfiguratieAsInteger(final String configuratie) {
        final String waarde = getConfiguratie(configuratie);
        return waarde == null || "".equals(waarde) ? null : Integer.valueOf(getConfiguratie(configuratie));
    }

    @Override
    public Duration getConfiguratieAsDuration(final String configuratie) {
        final String waarde = getConfiguratie(configuratie);
        return waarde == null || "".equals(waarde) ? null : new Duration(waarde);
    }

}
