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
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.test.common.util.SelectorBuilder;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.id.IdGenerator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Initiele vulling afnemers indicatie kanaal.
 */
public final class NaarLo3AltKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "naarlo3_alt";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public NaarLo3AltKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-queues-hornetq.xml", "classpath:infra-jms-hornetq.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final int AANTAL_LOOPS = 10;

        private static final int CLEANUP_TIMEOUT = 500;

        private static final String BERICHTREFERENTIE = "bericht.referentie";
        private static final String BERICHTCORRELATIE = "bericht.correlatie";

        @Inject
        @Named("naarLo3")
        private Destination uitgaandDestination;

        @Inject
        @Named("naarLo3Antwoord")
        private Destination inkomendDestination;

        @Inject
        private JmsTemplate jmsTemplate;

        @Inject
        private Correlator correlator;

        @Inject
        private IdGenerator idGenerator;

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
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            // Controleer correlatie
            if (verwachtBericht.getCorrelatieReferentie() != null) {
                correlator.controleerUitgaand(verwachtBericht.getCorrelatieReferentie(), NaarLo3AltKanaal.KANAAL);

                // Hack(?)
                verwachtBericht.setCorrelatieReferentie(correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie()));
            }

            // Opbouwen specifieke selector
            final SelectorBuilder selector = new SelectorBuilder();
            if (verwachtBericht.getCorrelatieReferentie() != null) {
                selector.addEqualsCriteria(JMSConstants.CORRELATIE_REFERENTIE, verwachtBericht.getCorrelatieReferentie());
            }

            final long originalTimeout = jmsTemplate.getReceiveTimeout();
            Message message = null;
            try {
                jmsTemplate.setReceiveTimeout(verwachtBericht.getTestBericht().getHerhalingVertragingInMillis());

                int count = 0;
                while (count++ < verwachtBericht.getTestBericht().getHerhalingAantalPogingen() && message == null) {
                    LOG.info(" count: {}", count);

                    if (selector.isEmpty()) {
                        message = jmsTemplate.receive(inkomendDestination);
                    } else {
                        message = jmsTemplate.receiveSelected(inkomendDestination, selector.toString());
                    }
                }

            } finally {
                jmsTemplate.setReceiveTimeout(originalTimeout);
            }

            if (message == null) {
                throw new KanaalException("Geen bericht ontvangen");
            } else {
                final Bericht ontvangenBericht = mapMessage(message);
                LOG.info("Received bericht: {}", ontvangenBericht.getInhoud());

                // Registreer bericht referentie
                correlator.registreerInkomend(verwachtBericht.getBerichtReferentie(), NaarLo3AltKanaal.KANAAL, ontvangenBericht.getBerichtReferentie());

                return ontvangenBericht;
            }
        }

        @Override
        protected boolean basisValidatie(final String inhoud) {
            if (inhoud == null || "".equals(inhoud)) {
                return true;
            }

            final SyncBericht bericht = SyncBerichtFactory.SINGLETON.getBericht(inhoud);

            return !(bericht instanceof OngeldigBericht);
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

            // Genereer ID
            final String berichtReferentie;
            if (bericht.getTestBericht().getTestBerichtProperty(BERICHTREFERENTIE) == null) {
                if (correlator.getBerichtReferentie(bericht.getBerichtReferentie()) != null) {
                    berichtReferentie = correlator.getBerichtReferentie(bericht.getBerichtReferentie());
                } else {
                    berichtReferentie = idGenerator.generateId();
                }
            } else {
                berichtReferentie = bericht.getTestBericht().getTestBerichtProperty(BERICHTREFERENTIE);
            }
            correlator.registreerUitgaand(bericht.getBerichtReferentie(), NaarLo3AltKanaal.KANAAL, berichtReferentie);
            final String berichtCorrelatie = bericht.getTestBericht().getTestBerichtProperty(BERICHTCORRELATIE);

            jmsTemplate.send(uitgaandDestination, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final Message message = session.createTextMessage(bericht.getInhoud());

                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, berichtReferentie);
                    if (berichtCorrelatie != null) {
                        message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, berichtCorrelatie);
                    }
                    return message;
                }
            });
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
                while ((message = jmsTemplate.receive(inkomendDestination)) != null) {
                    LOG.info("Onverwacht bericht op queue {}:\n{}.", inkomendDestination, message);
                    result.add(mapMessage(message));
                }
            } finally {
                jmsTemplate.setReceiveTimeout(timeout);
            }

            return result;
        }

    }
}
