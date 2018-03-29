/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAfnemerIndicatiesService;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Initiele vulling afnemers indicatie kanaal.
 */
public final class AfnemersindicatieKanaal extends LazyLoadingKanaal {

    /**
     * Constructor.
     */
    public AfnemersindicatieKanaal() {
        super(new Worker(),
                new Configuration(
                        "classpath:configuratie.xml",
                        "classpath:infra-db-brp.xml",
                        "classpath:infra-em-sync.xml",
                        "classpath:infra-jta.xml",
                        "classpath:infra-db-sync.xml",
                        "classpath:beans-sync.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private BrpAfnemerIndicatiesService afnemerIndicatiesService;

        @Inject
        private ConverteerLo3NaarBrpService conversieService;

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "afnemersindicatie";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht.getInhoud());

            final Lo3Afnemersindicatie lo3Afnemersindicatie = getAfnemersindicaties(syncBericht);
            Logging.initContext();
            try {
                final BrpAfnemersindicaties brpAfnemersindicaties = conversieService.converteerLo3Afnemersindicaties(lo3Afnemersindicatie);
                afnemerIndicatiesService.persisteerAfnemersindicaties(brpAfnemersindicaties);
            } finally {
                Logging.destroyContext();
            }
        }

        private Lo3Afnemersindicatie getAfnemersindicaties(final SyncBericht syncBericht) throws KanaalException {
            if (syncBericht instanceof AfnemersindicatiesBericht) {
                return ((AfnemersindicatiesBericht) syncBericht).getAfnemersindicaties();
            } else {
                throw new KanaalException("Berichttype '" + syncBericht.getBerichtType() + "' niet ondersteund voor kanaal " + getKanaal() + ".");
            }
        }
    }
}
