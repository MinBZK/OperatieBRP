/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import javax.inject.Inject;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Transitie controle.
 */
public class TransCheckKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "transcheck";

    /**
     * Constructor.
     */
    public TransCheckKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-isc.xml",
            "classpath:infra-jms-isc.xml",
            "classpath:infra-jbpm.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        private static final String TRANSITIE_NIET_DOORLOPEN = "Proces heeft verwachte transitie niet doorlopen.";

        @Inject
        private Correlator correlator;

        @Inject
        private JbpmHelper jbpmHelper;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            if (verwerkInkomend(testCasus, bericht) != null) {
                throw new KanaalException(TRANSITIE_NIET_DOORLOPEN);
            }
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            final String berichtId;

            if (verwachtBericht.getCorrelatieReferentie() == null) {
                berichtId = correlator.getLastBerichtReferentie();
            } else {
                berichtId = correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie());
                if (berichtId == null) {
                    throw new KanaalException("Bericht gerefereerd aan bericht met volgnummer "
                                              + verwachtBericht.getCorrelatieReferentie()
                                              + ", maar geen bericht gevonden.");
                }
            }

            final String[] parts = verwachtBericht.getInhoud().replaceAll("\r", "").split("\n");
            if (parts.length < 1) {
                throw new KanaalException("Transitie check kanaal verwacht een bericht met minimaal een regel; "
                                          + "de eerste regel bevat de node naam; de tweede regel de transitie; de derde regel het aantal herhalingen.");
            }

            try {
                final String nodeNaam = parts[0];
                final String transitieNaam = parts.length <= 1 ? "" : parts[1];
                final Integer aantal = parts.length <= 2 ? 1 : Integer.parseInt(parts[2]);

                if (jbpmHelper.checkTransActie(berichtId, nodeNaam, transitieNaam, aantal)) {
                    return null;
                } else {
                    final Bericht result = new Bericht();
                    result.setInhoud(TRANSITIE_NIET_DOORLOPEN);
                    return result;
                }
            } catch (final JbpmHelperException e) {
                throw new KanaalException("Fout tijdens verwerking.", e);
            }
        }
    }
}
