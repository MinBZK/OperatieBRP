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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Voisc queue kanaal.
 */
public final class VoiscQueueKanaal extends LazyLoadingKanaal {
    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "voisc";

    /**
     * Constructor.
     */
    public VoiscQueueKanaal() {
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
        @Named("voiscOntvangstQueue")
        private Destination uitgaandDestination;

        @Inject
        @Named("voiscVerzendenQueue")
        private Destination inkomendDestination;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public Destination getUitgaandDestination() {
            return uitgaandDestination;
        }

        @Override
        public Destination getInkomendDestination() {
            return inkomendDestination;
        }

        @Override
        protected boolean negeerPartijen() {
            return false;
        }

        @Override
        protected boolean negeerRequestNonReceiptNotification() {
            return false;
        }

        @Override
        public String getStandaardVerzendendePartij() {
            return "0599";
        }

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
