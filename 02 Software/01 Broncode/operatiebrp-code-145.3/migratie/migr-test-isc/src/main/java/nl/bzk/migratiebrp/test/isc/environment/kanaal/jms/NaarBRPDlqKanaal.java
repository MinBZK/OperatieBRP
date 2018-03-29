/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * Dead Letter Queue van HornetQ.
 */
public class NaarBRPDlqKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "naarbrp_dlq";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public NaarBRPDlqKanaal() {
        super(new NaarBRPDlqKanaal.Worker(),
                new Configuration("classpath:configuratie.xml", "classpath:infra-queues-hornetq.xml", "classpath:infra-jms-hornetq.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final int CLEANUP_TIMEOUT = 500;

        @Inject
        @Named("naarBrpDlqQueue")
        private Destination destination;

        @Inject
        private JmsTemplate jmsTemplate;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {

            final Message message;
            jmsTemplate.setReceiveTimeout(10000);
            message = jmsTemplate.receive(destination);

            if (message == null) {
                throw new KanaalException("Geen bericht ontvangen");
            } else {
                final Bericht ontvangenBericht = mapMessage(message);
                LOG.info("Received bericht: {}", ontvangenBericht.getInhoud());

                return ontvangenBericht;
            }
        }

        private Bericht mapMessage(final Message message) {
            final Bericht bericht = new Bericht();

            try {
                bericht.setBerichtReferentie(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
                bericht.setCorrelatieReferentie(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));

                if (message instanceof TextMessage) {
                    bericht.setInhoud(((TextMessage) message).getText());
                } else {
                    throw new IllegalArgumentException("Onbekend bericht type: " + message.getClass().getName());
                }
            } catch (final JMSException e) {
                throw new IllegalArgumentException(e);
            }

            return bericht;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // uitgaand naar dlq?
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
        }

        @Override
        public void voorTestcase(final TestCasusContext testCasus) {
            naTestcase(testCasus);
        }

        @Override
        public List<Bericht> naTestcase(final TestCasusContext testCasus) {

            final long timeout = jmsTemplate.getReceiveTimeout();
            final List<Bericht> result = new ArrayList<>();

            try {
                jmsTemplate.setReceiveTimeout(CLEANUP_TIMEOUT);
                Message message;
                while ((message = jmsTemplate.receive(destination)) != null) {
                    LOG.info("Onverwacht bericht op queue {}:\n{}.", destination, message);
                    result.add(mapMessage(message));
                }
            } finally {
                jmsTemplate.setReceiveTimeout(timeout);
            }

            return result;
        }

    }
}
