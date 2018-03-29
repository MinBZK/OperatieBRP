/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.isc;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.factory.IscBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSynchronisatievraagCommand;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm.JbpmHelper;

/**
 * ISC kanaal.
 */
public class IscKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "isc";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public IscKanaal() {
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
        private CommandClient commandoClient;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            final IscBericht iscBericht = IscBerichtFactory.SINGLETON.getBericht(bericht.getInhoud());

            final JbpmSynchronisatievraagCommand commando =
                    new JbpmSynchronisatievraagCommand(
                            getProcessDefinitionName(iscBericht),
                            bericht.getInhoud(),
                            bericht.getOntvangendePartij(),
                            iscBericht.getBerichtType());

            final Long processInstanceId;
            try {
                processInstanceId = commandoClient.executeCommand(commando);
            } catch (final CommandException e) {
                throw new KanaalException("Kan proces niet starten via JbpmSynchronisatievraagCommand", e);
            }

            // Opzoeken bericht ID obv process instance id
            final String messageId = jbpmHelper.bepaalBericht(processInstanceId, iscBericht.getBerichtType());
            correlator.registreerUitgaand(bericht.getBerichtReferentie(), getKanaal(), messageId);
            LOG.info("iscKanaal: uitgaand; referentie: {}, messageId: {}", bericht.getBerichtReferentie(), messageId);
        }

        private String getProcessDefinitionName(final IscBericht iscBericht) throws KanaalException {
            if (iscBericht instanceof Uc811Bericht) {
                return "uc811";
            } else if (iscBericht instanceof Uc812Bericht) {
                return "uc812";
            }
            throw new KanaalException("Onbekend bericht; niet te bepalen welk proces gestart moet worden.");
        }

    }

}
