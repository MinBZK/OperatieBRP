/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;

import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;

/**
 * Kanaal om berichten te lezen en te schrijven op de BRP queue: gbaAfnemersindicaties.
 */
public class GbaToevalligeGebeurtenissenKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "gbaToevalligeGebeurtenissen";

    /**
     * Constructor.
     */
    public GbaToevalligeGebeurtenissenKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-jms-brp.xml", "classpath:infra-queues-brp.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractQueueKanaal {

        @Inject
        @Named("gbaToevalligeGebeurtenissenQueue")
        private Destination destination;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        protected String getCorrelatieIdentifier() {
            return GbaToevalligeGebeurtenissenKanaal.KANAAL;
        }

        @Override
        protected Destination getInkomendDestination() {
            return destination;
        }

        @Override
        protected Destination getUitgaandDestination() {
            return destination;
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
        }

        @Override
        protected boolean basisValidatie(final String inhoud) {
            if (inhoud == null || "".equals(inhoud)) {
                return true;
            }

            final BrpBericht bericht = BrpBerichtFactory.SINGLETON.getBericht(inhoud);

            return !(bericht instanceof OngeldigBericht);
        }
    }
}
