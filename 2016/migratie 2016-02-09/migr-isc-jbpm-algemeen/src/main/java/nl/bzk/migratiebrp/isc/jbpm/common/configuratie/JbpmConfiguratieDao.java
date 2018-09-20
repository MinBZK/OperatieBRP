/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.configuratie;

import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceUtil;
import org.jbpm.calendar.Duration;

/**
 * Proxy (gebruikt in scripts) om via de spring service de configuratie dao aan te roepen.
 */
public final class JbpmConfiguratieDao implements ConfiguratieDao {

    /**
     * Static beschikbare instantie van de configuratie dao (voor integratie in de processen).
     */
    public static final JbpmConfiguratieDao INSTANCE = new JbpmConfiguratieDao();

    private JbpmConfiguratieDao() {
        // Niet extern instantieerbaar
    }

    @Override
    public String getConfiguratie(final String configuratie) {
        return SpringServiceUtil.getBean(ConfiguratieDao.class).getConfiguratie(configuratie);
    }

    @Override
    public Integer getConfiguratieAsInteger(final String configuratie) {
        return SpringServiceUtil.getBean(ConfiguratieDao.class).getConfiguratieAsInteger(configuratie);
    }

    @Override
    public Duration getConfiguratieAsDuration(final String configuratie) {
        return SpringServiceUtil.getBean(ConfiguratieDao.class).getConfiguratieAsDuration(configuratie);
    }

}
