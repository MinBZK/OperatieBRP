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
import nl.bzk.migratiebrp.util.common.operatie.ExceptionWrapper;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import nl.bzk.migratiebrp.util.common.operatie.StopHerhalingExceptionWrapper;

/**
 * Node check kanaal.
 */
public class NodeCheckKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "nodecheck";

    /**
     * Constructor.
     */
    public NodeCheckKanaal() {
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
        private static final String ERROR_NIET_VERWACHTE_NODE = "Proces is niet op verwachte node.";

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
                throw new KanaalException(ERROR_NIET_VERWACHTE_NODE);
            }
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            final String berichtId;

            if (verwachtBericht.getCorrelatieReferentie() == null) {
                berichtId = correlator.getLastBerichtReferentie();
            } else {
                berichtId = correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie());
            }

            Bericht result;
            try {
                final Herhaal herhaal = verwachtBericht.getTestBericht().maakHerhaling();
                final NodeCheckActie nodeCheckActie = new NodeCheckActie(berichtId, verwachtBericht.getInhoud());
                herhaal.herhaal(nodeCheckActie);
                result = nodeCheckActie.getResult();

            } catch (final StopHerhalingExceptionWrapper e) {
                throw new KanaalException("Fout tijdens verwerking. ", e);
            } catch (final HerhaalException e) {
                result = new Bericht();
                result.setInhoud(ERROR_NIET_VERWACHTE_NODE);
            }

            return result;
        }

        /**
         * Node Check Actie.
         */
        public final class NodeCheckActie implements Runnable {
            private final String berichtId;
            private final String node;
            private Bericht result;

            /**
             * Constructor.
             * @param berichtId Id van het bericht
             * @param node De verwachte node
             */
            public NodeCheckActie(final String berichtId, final String node) {
                this.berichtId = berichtId;
                this.node = node;
            }

            @Override
            public void run() {
                try {
                    if (jbpmHelper.checkNodeActie(berichtId, node)) {
                        result = null;
                    } else {
                        result = new Bericht();
                        result.setInhoud(ERROR_NIET_VERWACHTE_NODE);
                        throw new ExceptionWrapper(new KanaalException(ERROR_NIET_VERWACHTE_NODE));
                    }

                } catch (final JbpmHelperException e) {
                    throw new ExceptionWrapper(new KanaalException("Fout tijdens verwerking.", e));
                }

            }

            /**
             * Geef de waarde van result.
             * @return result
             */
            public Bericht getResult() {
                return result;
            }
        }
    }

}
