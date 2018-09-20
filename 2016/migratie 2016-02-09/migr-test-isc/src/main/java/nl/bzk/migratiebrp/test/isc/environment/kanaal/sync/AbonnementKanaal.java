/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Initiele vulling abonnement kanaal.
 */
public final class AbonnementKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public AbonnementKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-brp.xml",
            "classpath:infra-jta.xml",
            "classpath:infra-db-sync.xml",
            "classpath:infra-em-sync.xml",
            "classpath:beans-sync.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private AutorisatieReader autorisatieReader;

        @Inject
        private BrpDalService brpDalService;

        @Inject
        private ConverteerLo3NaarBrpService conversieService;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "abonnement";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            LOG.info("Verwerk bericht: " + bericht.getInhoud());
            try {
                final List<Lo3Autorisatie> lo3Autorisaties = autorisatieReader.read(new ByteArrayInputStream(bericht.getInhoud().getBytes()));
                LOG.info("Verwerk {} autorisaties: {}", lo3Autorisaties.size(), lo3Autorisaties);

                for (final Lo3Autorisatie lo3Autorisatie : lo3Autorisaties) {
                    LOG.info("Autorisatie voor partijcode: {} ", lo3Autorisatie.getAfnemersindicatie());
                    Logging.initContext();
                    try {
                        final BrpAutorisatie brpAutorisatie = conversieService.converteerLo3Autorisatie(lo3Autorisatie);
                        brpDalService.persisteerAutorisatie(brpAutorisatie);
                    } finally {
                        Logging.destroyContext();
                    }
                }

            } catch (final IOException e) {
                throw new KanaalException("Probleem met lezen autorisatie uit bericht", e);
            } catch (final Lo3SyntaxException e) {
                throw new KanaalException("Probleem met converteren autorisatie", e);
            }
        }
    }
}
