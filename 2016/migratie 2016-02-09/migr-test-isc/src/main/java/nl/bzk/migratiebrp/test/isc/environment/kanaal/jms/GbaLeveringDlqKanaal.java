/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Kanaal om berichten te lezen: Gba Leveringen DLQ.
 */
public class GbaLeveringDlqKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "gbaLeveringDlq";

    /**
     * Constructor.
     */
    public GbaLeveringDlqKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-jms-brp.xml", "classpath:infra-queues-brp.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractQueueKanaal {
        @Inject
        @Named("gbaLeveringDlqQueue")
        private Destination destination;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        protected String getCorrelatieIdentifier() {
            return GbaLeveringKanaal.KANAAL;
        }

        @Override
        protected Destination getInkomendDestination() {
            return destination;
        }

        @Override
        protected Destination getUitgaandDestination() {
            return null;
        }
    }
}
