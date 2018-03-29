/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

/**
 * Check SQL result kanaal.
 */
public final class CheckSqlResultKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "check_sql_result";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final AtomicInteger HERHAAL_COUNTER = new AtomicInteger(0);

    /**
     * Constructor.
     */
    public CheckSqlResultKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        private SqlHelper sqlHelper;

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
            final Herhaal herhaal = verwachtBericht.getTestBericht().maakHerhaling();
            // Reset de counter voor deze herhaal instantie.
            HERHAAL_COUNTER.set(0);

            try {
                return herhaal.herhaal(() -> {
                    LOG.info("Controleren SQL (herhaling {} van {})", HERHAAL_COUNTER.incrementAndGet(), herhaal.getMaximumAantalPogingen());
                    final Bericht ontvangenBericht = sqlHelper.checkSqlResult(verwachtBericht);

                    if (vergelijkInhoud(verwachtBericht.getInhoud(), ontvangenBericht.getInhoud())) {
                        LOG.info("Controleren SQL: resultaat correct");
                        return ontvangenBericht;
                    } else {
                        LOG.info("Controleren SQL: resultaat niet correct");
                        throw new LaatsteBerichtException(ontvangenBericht);
                    }

                });
            } catch (final HerhaalException e) {
                final List<Exception> pogingExcepties = e.getPogingExcepties();
                final Exception laatstePoging = pogingExcepties.get(pogingExcepties.size() - 1);
                if (laatstePoging instanceof LaatsteBerichtException) {
                    return ((LaatsteBerichtException) laatstePoging).getOntvangenBericht();
                } else {
                    throw new KanaalException("Probleem", e);
                }
            }
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
        }
    }

    /**
     * Wrapper om laatste bericht te bewaren via lijst van herhalingen.
     */
    public static final class LaatsteBerichtException extends Exception {

        private static final long serialVersionUID = 1L;

        private final Bericht ontvangenBericht;

        /**
         * Constructor.
         * @param ontvangenBericht bericht
         */
        public LaatsteBerichtException(final Bericht ontvangenBericht) {
            this.ontvangenBericht = ontvangenBericht;
        }

        /**
         * Geef de waarde van ontvangen bericht.
         * @return ontvangen bericht
         */
        public Bericht getOntvangenBericht() {
            return ontvangenBericht;
        }

    }

}
