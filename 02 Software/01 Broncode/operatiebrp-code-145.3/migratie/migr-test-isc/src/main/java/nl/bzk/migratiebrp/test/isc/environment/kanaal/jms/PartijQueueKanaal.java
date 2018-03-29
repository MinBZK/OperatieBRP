/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Sync queue kanaal.
 */
public final class PartijQueueKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "partij";

    /**
     * Constructor.
     */
    public PartijQueueKanaal() {
        super(new Worker(), new Configuration(
                "classpath:configuratie.xml",
                "classpath:infra-jms-isc.xml",
                "classpath:infra-jmx-routering.xml",
                "classpath:infra-queues-isc-partij.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractIscQueueKanaal {

        @Inject
        @Named("partijRegister")
        private Destination uitgaandDestination;

        @Inject
        @Named("partijVerzoek")
        private Destination inkomendDestination;

        /**
         * Default constructor worker.
         */
        public Worker() {
            super();
            setRegistreerInkomendeCorrelatie(false);
            setRegistreerUitgaandeCorrelatie(false);
        }

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

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
        }
    }

}
