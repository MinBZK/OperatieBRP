/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Kanaal om migr-init-naarvoisc mee uit te voeren.
 */
public class IvNaarVoiscKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "iv_naarvoisc";

    /**
     * Constructor.
     */
    public IvNaarVoiscKanaal() {
        super(new Worker(),
              new Configuration(
                  "classpath:configuratie.xml",
                  "classpath:infra-db-gbav.xml",
                  "classpath:infra-db-isc.xml",
                  "classpath:infra-db-voisc.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        @Named("gbavDataSource")
        private DataSource gbavDataSource;

        @Inject
        @Named("voiscDataSource")
        private DataSource voiscDataSource;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            final Bericht resultaat = new Bericht();
            resultaat.setInhoud(verwerk());
            return resultaat;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            verwerk();
        }

        private String verwerk() throws KanaalException {
            try {
                return new IvUc105Helper("/sql/extract-voisc-mailbox.sql", gbavDataSource, voiscDataSource).execute();
            } catch (final IOException e) {
                throw new KanaalException("Fout bij het uitvoeren van UC105: /sql/extract-isc-verzender.sql", e);
            }
        }

    }
}
