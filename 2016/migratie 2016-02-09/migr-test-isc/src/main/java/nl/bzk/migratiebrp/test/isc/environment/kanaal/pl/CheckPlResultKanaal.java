/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.pl;

import javax.inject.Inject;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Controleer de PL (zoals gelezen door BRP SYNC SERVICE en gemapped naar MIGRATIE BRP MODEL).
 */
public class CheckPlResultKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "check_pl_result";

    /**
     * Constructor.
     */
    public CheckPlResultKanaal() {
        super(new Worker(),
              new Configuration(
                  "classpath:configuratie.xml",
                  "classpath:infra-db-sync.xml",
                  "classpath:infra-em-sync.xml",
                  "classpath:infra-jta.xml",
                  "classpath:beans-sync.xml",
                  "classpath:infra-pl.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private PlHelper plHelper;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            return plHelper.checkPlResult(verwachtBericht);
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXmlMetActies(verwachteInhoud, ontvangenInhoud);
        }
    }
}
