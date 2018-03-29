/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Voisc queue kanaal.
 */
public final class VoiscAltQueueKanaal extends LazyLoadingKanaal {
    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "voisc_alt";

    /**
     * Constructor.
     */
    public VoiscAltQueueKanaal() {
        super(new Worker(),
                new Configuration(
                        "classpath:configuratie.xml",
                        "classpath:infra-jms-isc.xml",
                        "classpath:infra-queues-isc-voisc.xml",
                        "classpath:infra-jmx-routering.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractIscQueueKanaal {
        @Inject
        @Named("voiscVerzendenQueue")
        private Destination uitgaandDestination;

        @Inject
        @Named("voiscOntvangstQueue")
        private Destination inkomendDestination;

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
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.jms.AbstractIscQueueKanaal#getUitgaandDestination()
         */
        @Override
        public Destination getUitgaandDestination() {
            return uitgaandDestination;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.jms.AbstractIscQueueKanaal#getInkomendDestination()
         */
        @Override
        public Destination getInkomendDestination() {
            return inkomendDestination;
        }

        /**
         * Verzendende en ontvangende partij ook vergelijken bij inkomend bericht.
         * @return false
         */
        @Override
        protected boolean negeerPartijen() {
            return false;
        }

        /**
         * Request non receipt notification ook vergelijken bij inkomend bericht.
         * @return false
         */
        @Override
        protected boolean negeerRequestNonReceiptNotification() {
            return false;
        }

        @Override
        protected boolean basisValidatie(final String inhoud) {
            if (inhoud == null || "".equals(inhoud)) {
                return true;
            }

            final Lo3Bericht bericht = new Lo3BerichtFactory().getBericht(inhoud);

            return !(bericht instanceof AbstractOngeldigLo3Bericht || bericht instanceof OnbekendBericht);
        }
    }
}
