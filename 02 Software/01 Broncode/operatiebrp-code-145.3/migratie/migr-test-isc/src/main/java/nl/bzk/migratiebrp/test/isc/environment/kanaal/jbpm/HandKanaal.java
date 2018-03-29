/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmTaakCommand;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Hand kanaal.
 */
public class HandKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "hand";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public HandKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-isc.xml", "classpath:infra-jms-isc.xml",
                "classpath:infra-jmx-isc.xml", "classpath:infra-jbpm.xml"));
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

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {

            final String berichtId;
            if (bericht.getCorrelatieReferentie() == null) {
                berichtId = correlator.getLastBerichtReferentie();
            } else {
                berichtId = correlator.getBerichtReferentie(bericht.getCorrelatieReferentie());
            }
            LOGGER.info("berichtId: {}", berichtId);

            Long taskId;
            try {
                taskId = jbpmHelper.getTaskId(berichtId);
            } catch (final JbpmHelperException e) {
                throw new KanaalException("Kan task instance id niet bepalen. ", e);
            }
            if (taskId == null) {
                throw new KanaalException("Kan task instance id niet bepalen.");
            }
            LOGGER.info("taskId: {}", taskId);

            final Map<String, Serializable> variabelen = new HashMap<>();
            variabelen.put(FoutafhandelingConstants.RESTART, bericht.getInhoud());

            final JbpmTaakCommand command = new JbpmTaakCommand(taskId, null, variabelen, null, "ok");
            try {
                commandClient.executeCommand(command);
            } catch (final CommandException e) {
                throw new KanaalException("Fout bij uitvoeren taak", e);
            }
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            final String teCorrelerenId;
            if (verwachtBericht.getCorrelatieReferentie() == null) {
                teCorrelerenId = correlator.getFirstBerichtReferentie();
            } else {
                teCorrelerenId = correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie());
            }

            try {
                if (jbpmHelper.controleerOpHandmatigeActie(teCorrelerenId)) {
                    return null;
                } else {
                    final Bericht result = new Bericht();
                    result.setInhoud("Handmatige actie gewenst, maar niet gevonden.");
                    return result;

                }
            } catch (final JbpmHelperException e) {
                throw new KanaalException("Fout tijdens verwerking inkomend bericht .", e);
            }
        }
    }
}
