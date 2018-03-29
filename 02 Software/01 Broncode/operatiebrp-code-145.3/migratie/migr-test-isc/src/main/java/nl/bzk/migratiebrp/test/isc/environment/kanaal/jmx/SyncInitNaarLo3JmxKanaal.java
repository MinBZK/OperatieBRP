/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jmx;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanServerConnection;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Sync Init Naar Lo3 JMX Kanaal.
 */
public final class SyncInitNaarLo3JmxKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public SyncInitNaarLo3JmxKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-jmx-sync-init.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractJmxKanaal {

        @Inject
        @Named("syncInitNaarLo3JmxConnector")
        private MBeanServerConnection connection;

        @Override
        public String getKanaal() {
            return "jmx_sync_init_lo3";
        }

        @Override
        protected MBeanServerConnection getConnection() {
            return connection;
        }
    }
}
