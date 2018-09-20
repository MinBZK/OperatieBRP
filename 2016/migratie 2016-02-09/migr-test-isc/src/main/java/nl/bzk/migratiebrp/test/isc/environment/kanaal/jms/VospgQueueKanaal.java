/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Vospg queue kanaal.
 */
public final class VospgQueueKanaal extends LazyLoadingKanaal {
    /** Kanaal naam. */
    public static final String KANAAL = "vospg";

    /**
     * Constructor.
     */
    public VospgQueueKanaal() {
        super(new Worker(), new Configuration(
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
        @Named("voiscOntvangstQueue")
        private Destination uitgaandDestination;

        @Inject
        @Named("voiscVerzendenQueue")
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
         *
         * @return false
         */
        @Override
        protected boolean negeerPartijen() {
            return false;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal#getStandaardVerzendendePartij()
         */
        @Override
        public String getStandaardVerzendendePartij() {
            return "0599";
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal#getStandaardOntvangendePartij()
         */
        @Override
        public String getStandaardOntvangendePartij() {
            return "0600";
        }

        @Override
        protected void postProcessQueueBericht(final Bericht queueBericht) {
            if (queueBericht.getMsSequenceNumber() == null) {
                queueBericht.setMsSequenceNumber(queueBericht.getBerichtReferentie());
            } else if ("".equals(queueBericht.getMsSequenceNumber())) {
                queueBericht.setMsSequenceNumber(null);
            }
        }

    }
}
