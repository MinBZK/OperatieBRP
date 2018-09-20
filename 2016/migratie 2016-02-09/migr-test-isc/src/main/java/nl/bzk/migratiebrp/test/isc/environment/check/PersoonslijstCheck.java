/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.check;

import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Persoonslijst check.
 */
public final class PersoonslijstCheck implements Check {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean check(final CheckContext context, final Bericht bericht, final TestBericht testBericht, final String config) throws TestException {
        final PersoonslijstExtractor extractor = new PersoonslijstExtractorFactory().getPersoonlijstExtractor(config);
        final Persoonslijst ontvangenPersoonslijst = extractor.extractPersoonslijst(bericht.getInhoud());
        final Persoonslijst verwachtePersoonslijst = extractor.extractPersoonslijst(testBericht.getInhoud());

        final boolean result;

        if (verwachtePersoonslijst instanceof Lo3Persoonslijst) {
            LOGGER.info("Vergelijk Lo3Persoonlijsten");
            if (ontvangenPersoonslijst instanceof Lo3Persoonslijst) {
                final StringBuilder log = new StringBuilder();
                result = Lo3StapelHelper.vergelijkPersoonslijst(log, (Lo3Persoonslijst) verwachtePersoonslijst, (Lo3Persoonslijst) ontvangenPersoonslijst);
                LOGGER.info("Log lo3: {}", log);
            } else {
                LOGGER.info("Ontvangen persoonlijst niet van type Lo3Persoonslijst");
                result = false;
            }
        } else if (verwachtePersoonslijst instanceof BrpPersoonslijst) {
            LOGGER.info("Vergelijk BrpPersoonlijsten");
            if (ontvangenPersoonslijst instanceof BrpPersoonslijst) {
                final StringBuilder log = new StringBuilder();
                result =
                        BrpVergelijker.vergelijkPersoonslijsten(
                            log,
                            (BrpPersoonslijst) verwachtePersoonslijst,
                            (BrpPersoonslijst) ontvangenPersoonslijst,
                            true,
                            true);
                LOGGER.info("Log brp: {}", log);
            } else {
                LOGGER.info("Ontvangen persoonlijst niet van type BrpPersoonslijst");
                result = false;
            }
        } else {
            throw new TestException("Verwachte persoonlijst van onbekend type: " + verwachtePersoonslijst);
        }

        return result;
    }

    /**
     * Factory implementatie voor de persoonslijst extractor op basis van de configuratie.
     */
    private static final class PersoonslijstExtractorFactory {
        public PersoonslijstExtractor getPersoonlijstExtractor(final String config) throws TestException {
            final PersoonslijstExtractor resultaat;
            switch (config.toLowerCase()) {
                case "leesuitbrpantwoord.brp":
                    resultaat = new LeesUitBrpAntwoordBrpPersoonslijstExtractor();
                    break;
                case "leesuitbrpantwoord.lo3":
                    resultaat = new LeesUitBrpAntwoordLo3PersoonslijstExtractor();
                    break;
                case "leesuitbrpantwoord.lo3xml":
                    resultaat = new LeesUitBrpAntwoordLo3XmlPersoonslijstExtractor();
                    break;
                default:
                    throw new TestException("Onbekend config bij PersoonslijstCheck: " + config);
            }

            return resultaat;
        }
    }

    /**
     * Interface voor de persoonslijst extractor.
     */
    private interface PersoonslijstExtractor {
        Persoonslijst extractPersoonslijst(String bericht);
    }

    /**
     * Implementatie voor de persoonslijst extractor voor het type LO3.
     */
    private static class LeesUitBrpAntwoordLo3PersoonslijstExtractor implements PersoonslijstExtractor {
        @Override
        public Persoonslijst extractPersoonslijst(final String bericht) {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            final LeesUitBrpAntwoordBericht leesUitBrpAntwoord = (LeesUitBrpAntwoordBericht) syncBericht;
            return leesUitBrpAntwoord.getLo3Persoonslijst();
        }
    }

    /**
     * Implementatie voor de persoonslijst extractor voor het type LO3 XML.
     */
    private static class LeesUitBrpAntwoordLo3XmlPersoonslijstExtractor implements PersoonslijstExtractor {
        @Override
        public Persoonslijst extractPersoonslijst(final String bericht) {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            final LeesUitBrpAntwoordBericht leesUitBrpAntwoord = (LeesUitBrpAntwoordBericht) syncBericht;
            return leesUitBrpAntwoord.getLo3PersoonslijstFromXml();
        }
    }

    /**
     * Implementatie voor de persoonslijst extractor voor het type BRP.
     */
    private static class LeesUitBrpAntwoordBrpPersoonslijstExtractor implements PersoonslijstExtractor {
        @Override
        public Persoonslijst extractPersoonslijst(final String bericht) {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            final LeesUitBrpAntwoordBericht leesUitBrpAntwoord = (LeesUitBrpAntwoordBericht) syncBericht;
            return leesUitBrpAntwoord.getBrpPersoonslijst();
        }
    }
}
