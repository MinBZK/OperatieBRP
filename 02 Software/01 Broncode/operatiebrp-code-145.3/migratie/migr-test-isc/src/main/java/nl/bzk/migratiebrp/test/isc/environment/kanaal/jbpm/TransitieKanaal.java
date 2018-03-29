/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import javax.inject.Inject;

import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmTransitionCommand;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Transitie kanaal.
 */
public class TransitieKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "trans";

    /**
     * Constructor.
     */
    public TransitieKanaal() {
        super(new Worker(),
                new Configuration(
                        "classpath:configuratie.xml",
                        "classpath:infra-db-isc.xml",
                        "classpath:infra-jms-isc.xml",
                        "classpath:infra-jmx-isc.xml",
                        "classpath:infra-jbpm.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        @Inject
        private Correlator correlator;
        @Inject
        private JbpmHelper jbpmHelper;
        @Inject
        private CommandClient commandClient;

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

            final String berichtId;
            final boolean bepaalTokenOpCorrelatie;

            if (bericht.getCorrelatieReferentie() == null) {
                berichtId = correlator.getLastBerichtReferentie();
                bepaalTokenOpCorrelatie = true;
            } else {
                final String inkomendBerichtId = correlator.getInkomendBerichtReferentie(bericht.getCorrelatieReferentie());
                if (inkomendBerichtId != null) {
                    berichtId = inkomendBerichtId;
                    bepaalTokenOpCorrelatie = true;
                } else {
                    berichtId = correlator.getBerichtReferentie(bericht.getCorrelatieReferentie());

                    if (berichtId == null) {
                        throw new KanaalException(
                                "Bericht gerefereerd aan bericht met volgnummer " + bericht.getCorrelatieReferentie() + ", maar geen bericht gevonden.");
                    }

                    bepaalTokenOpCorrelatie = false;
                }

            }

            final Long tokenId = jbpmHelper.bepaalToken(berichtId, bepaalTokenOpCorrelatie);
            if (tokenId == null) {
                throw new KanaalException("Kan token id niet bepalen.");
            }

            final JbpmTransitionCommand command = new JbpmTransitionCommand(tokenId, bericht.getInhoud());
            try {
                commandClient.executeCommand(command);
            } catch (final CommandException e) {
                throw new KanaalException("Fout bij uitvoeren transitie", e);
            }
        }
    }
}
