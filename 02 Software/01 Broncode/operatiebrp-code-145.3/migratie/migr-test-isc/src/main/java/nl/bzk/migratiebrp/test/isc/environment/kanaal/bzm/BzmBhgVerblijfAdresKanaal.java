/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.BzmBrpService;

/**
 * BZM kanaal voor bijhouding verblijfadres.
 */
public class BzmBhgVerblijfAdresKanaal extends LazyLoadingKanaal {
    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "bzmBhgVerblijfAdres";

    /**
     * Constructor.
     */
    public BzmBhgVerblijfAdresKanaal() {
        super(new Worker(), new Configuration(
                "classpath:configuratie.xml",
                "classpath:infra-bzm.xml",
                "classpath:bzm-soap.xml",
                "classpath:infra-db-brp.xml",
                "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractBzmKanaal {

        @Inject
        @Named("bhgVerblijfAdres")
        private BzmBrpService bzmBrpService;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.AbstractBzmKanaal#getBzmBrpService()
         */
        @Override
        protected BzmBrpService getBzmBrpService() {
            return bzmBrpService;
        }

    }
}
