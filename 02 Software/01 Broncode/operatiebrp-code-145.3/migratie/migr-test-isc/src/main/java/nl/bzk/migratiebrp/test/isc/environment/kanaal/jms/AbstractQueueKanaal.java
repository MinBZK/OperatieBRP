/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.common.util.SelectorBuilder;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.id.IdGenerator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Abstract implementatie voor JMS kanalen.
 */
public abstract class AbstractQueueKanaal extends AbstractKanaal {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int CLEANUP_TIMEOUT = 500;

    private static final String PROPERTY_BERICHT_REFERENTIE = "bericht.referentie";
    private static final String PROPERTY_BERICHT_CORRELATIE = "bericht.correlatie";

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private Correlator correlator;

    @Inject
    private IdGenerator idGenerator;

    private final List<Bericht> berichten = new ArrayList<>();

    /**
     * Geef de correlatie identifier. Alle kanalen met dezelfde correlatie identifier kunnen messageid/correlatieid
     * delen.
     * @return correlatie identifier (null, als geen correlatie gebruikt kan worden.
     */
    protected abstract String getCorrelatieIdentifier();

    /**
     * Geef de queue voor inkomende berichten.
     * @return queue (null, als inkomende berichten niet gebruikt worden)
     */
    protected abstract Destination getInkomendDestination();

    /**
     * Geef de queue voor uitgaande berichten.
     * @return queue, (null, als uitgaande berichten niet gebruikt worden)
     */
    protected abstract Destination getUitgaandDestination();

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ***** INKOMEND ********************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public final Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        // Controleer correlatie
        if (getCorrelatieIdentifier() != null && verwachtBericht.getCorrelatieReferentie() != null) {
            correlator.controleerUitgaand(verwachtBericht.getCorrelatieReferentie(), getCorrelatieIdentifier());

            // Hack, waarde wordt later gebruikt in vergelijking
            verwachtBericht.setCorrelatieReferentie(correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie()));
        }

        // Opbouwen specifieke selector
        final SelectorBuilder selector = new SelectorBuilder();
        if (verwachtBericht.getCorrelatieReferentie() != null) {
            selector.addEqualsCriteria(JMSConstants.CORRELATIE_REFERENTIE, verwachtBericht.getCorrelatieReferentie());
        }

        Bericht ontvangenBericht = zoekBericht(testCasus, berichten, verwachtBericht);
        while (ontvangenBericht == null) {
            final Bericht berichtVanQueue = ontvangBericht(selector.toString(), verwachtBericht.getTestBericht().getHerhalingVertragingInMillis(), verwachtBericht.getTestBericht().getHerhalingAantalPogingen());
            if (berichtVanQueue == null) {
                break;
            }

            berichten.add(berichtVanQueue);
            ontvangenBericht = zoekBericht(testCasus, berichten, verwachtBericht);
        }

        if (ontvangenBericht != null) {
            if (getCorrelatieIdentifier() != null) {
                // Registreer bericht referentie
                correlator.registreerInkomend(verwachtBericht.getBerichtReferentie(), getCorrelatieIdentifier(), ontvangenBericht.getBerichtReferentie());
            }
        } else {
            if (verwachtBericht.getInhoud() != null && !"".equals(verwachtBericht.getInhoud())) {
                throw new KanaalException("Bericht verwacht, maar niet ontvangen.");
            }
        }

        LOG.info("verwerkInkomend: ontvangenBericht={}", ontvangenBericht);
        return ontvangenBericht;
    }

    private Bericht ontvangBericht(final String selector, final long vertraging, final int maxHerhaling) throws KanaalException {
        final Destination destination = getInkomendDestination();
        if (destination == null) {
            throw new KanaalException("Geen inkomende queue gedefinieerd; inkomende berichten niet toegestaan.");
        }

        final long origineleTimeout = jmsTemplate.getReceiveTimeout();
        LOG.info("Orginele timeout: {}", origineleTimeout);
        //long herhalingTimeout =origineleTimeout / maxHerhaling;
        LOG.info("Herhaling timeout: {}", vertraging);
        try {
            jmsTemplate.setReceiveTimeout(vertraging);

            int herhaling = 0;
            while (++herhaling <= maxHerhaling) {
                LOG.info("Ontvangen bericht (selector={}); herhaling: {}", selector, herhaling);
                final Message message;
                if (selector.isEmpty()) {
                    message = jmsTemplate.receive(getInkomendDestination());
                } else {
                    message = jmsTemplate.receiveSelected(getInkomendDestination(), selector);
                }

                if (message != null) {
                    return mapMessage(message);
                }
            }
        } finally {
            jmsTemplate.setReceiveTimeout(origineleTimeout);
        }
        return null;

    }

    private Bericht mapMessage(final Message message) {
        final Bericht bericht = new Bericht();

        try {
            bericht.setBerichtReferentie(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
            bericht.setCorrelatieReferentie(message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE));

            if (message instanceof TextMessage) {
                bericht.setInhoud(((TextMessage) message).getText());
                LOG.info("Received bericht: {}", bericht.getInhoud());
            } else {
                throw new IllegalArgumentException("Onbekend bericht type: " + message.getClass().getName());
            }
        } catch (final JMSException e) {
            throw new IllegalArgumentException(e);
        }

        return bericht;
    }

    private Bericht zoekBericht(final TestCasusContext testCasus, final List<Bericht> ontvangenBerichten, final Bericht verwachtBericht) {
        final Iterator<Bericht> berichtenIterator = ontvangenBerichten.iterator();

        while (berichtenIterator.hasNext()) {
            final Bericht ontvangenBericht = berichtenIterator.next();
            if (controleerInkomend(testCasus, verwachtBericht, ontvangenBericht)) {
                berichtenIterator.remove();
                return ontvangenBericht;
            }
        }

        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ***** UITGAAND ********************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        // Bericht referentie
        final String berichtReferentie;
        if (bericht.getTestBericht().getTestBerichtProperty(PROPERTY_BERICHT_REFERENTIE) != null) {
            // Vaste waarde uit .properties
            berichtReferentie = bericht.getTestBericht().getTestBerichtProperty(PROPERTY_BERICHT_REFERENTIE);
            if (getCorrelatieIdentifier() != null) {
                correlator.registreerUitgaand(bericht.getBerichtReferentie(), getCorrelatieIdentifier(), berichtReferentie);
            }
        } else {
            if (getCorrelatieIdentifier() != null) {
                if (correlator.getBerichtReferentie(bericht.getBerichtReferentie()) != null) {
                    // Waarde op basis van referentie
                    berichtReferentie = correlator.getBerichtReferentie(bericht.getBerichtReferentie());
                } else {
                    berichtReferentie = idGenerator.generateId();
                }

                correlator.registreerUitgaand(bericht.getBerichtReferentie(), getCorrelatieIdentifier(), berichtReferentie);
            } else {
                // Genereer ID
                berichtReferentie = idGenerator.generateId();
            }
        }

        // Correlatie referentie
        final String correlatieReferentie;
        if (bericht.getTestBericht().getTestBerichtProperty(PROPERTY_BERICHT_CORRELATIE) != null) {
            // Vaste waarde uit .properties
            correlatieReferentie = bericht.getTestBericht().getTestBerichtProperty(PROPERTY_BERICHT_CORRELATIE);
        } else {
            if (getCorrelatieIdentifier() != null) {
                if (bericht.getCorrelatieReferentie() != null) {
                    correlator.controleerInkomend(bericht.getCorrelatieReferentie(), getCorrelatieIdentifier());
                    correlatieReferentie = correlator.getBerichtReferentie(bericht.getCorrelatieReferentie());
                } else {
                    correlatieReferentie = null;
                }
            } else {
                correlatieReferentie = null;
            }
        }

        final Destination destination = getUitgaandDestination();
        if (destination == null) {
            throw new KanaalException("Geen uitgaande queue gedefinieerd; uitgaande berichten niet toegestaan.");
        }

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(bericht.getInhoud());
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, berichtReferentie);
                if (correlatieReferentie != null) {
                    message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, correlatieReferentie);
                }
                if (bericht.getVerzendendePartij() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, bericht.getVerzendendePartij());
                }
                if (bericht.getOntvangendePartij() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, bericht.getOntvangendePartij());
                }
                if (bericht.getMsSequenceNumber() != null) {
                    message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, bericht.getMsSequenceNumber());
                }

                return message;
            }
        });
    }

    @Override
    public final void voorTestcase(final TestCasusContext testCasus) {
        naTestcase(testCasus);
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testCasus) {
        final List<Bericht> result = new ArrayList<>();
        result.addAll(berichten);
        berichten.clear();
        if (getUitgaandDestination() != null) {
            result.addAll(cleanUp(getUitgaandDestination()));
        }
        if (getInkomendDestination() != null) {
            result.addAll(cleanUp(getInkomendDestination()));
        }
        if ("false".equalsIgnoreCase(testCasus.getTestcaseConfiguratie().getProperty((getKanaal() + ".na.controle").toLowerCase()))) {
            LOG.info("Controle op bericht(en) na testcase is uitgezet. " + result.size() + " bericht(en) worden genegeerd.");
            result.clear();
        }

        return result;
    }

    /**
     * Alle berichten van een queue lezen.
     * @param destination queue
     * @return gelezen berichten
     */
    protected List<Bericht> cleanUp(final Destination destination) {
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
