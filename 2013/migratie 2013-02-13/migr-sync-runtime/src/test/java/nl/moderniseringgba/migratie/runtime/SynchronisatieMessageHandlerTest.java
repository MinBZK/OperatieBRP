/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testcase voor het testen van de MessageHandler voor synchronisatie berichten.
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchronisatieMessageHandlerTest {

    private static final long ANUMMER = 14L;
    private static final BrpPersoonslijst DUMMY_BRP_PL = new BrpPersoonslijstBuilder().build();
    private static final Lo3Persoonslijst DUMMY_LO3_PL = new Lo3PersoonslijstBuilder().build();

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConversieService conversieService;
    @Mock
    private ConnectionFactory connectionFactory;
    @Mock
    private Connection connection;
    @Mock
    private Session session;
    @InjectMocks
    private SynchronisatieMessageHandler messageHandler;

    // ------------------------------------SETUP-----------------------------------

    @Before
    public void setup() {
        when(brpDalService.bevraagPersoonslijst(ANUMMER)).thenReturn(DUMMY_BRP_PL);
        when(brpDalService.zoekPersoonOpAnummer(ANUMMER)).thenReturn(DUMMY_BRP_PL);

        when(conversieService.converteerBrpPersoonslijst(DUMMY_BRP_PL)).thenReturn(DUMMY_LO3_PL);
        try {
            when(conversieService.converteerLo3Persoonslijst(DUMMY_LO3_PL)).thenReturn(DUMMY_BRP_PL);
        } catch (final InputValidationException e) {
            // DUMMY_LO3_PL veroorzaakt GEEN InputValidationException
        }
    }

    private void setupConnectionFactory(
            final VraagAntwoordPaar vraagEnAntwoordPaar,
            final MessageProducer messageProducer) throws JMSException {

        try {
            when(session.createTextMessage(vraagEnAntwoordPaar.getVraag().format())).thenReturn(
                    vraagEnAntwoordPaar.getVraagAlsTextMessage());
            when(session.createTextMessage(vraagEnAntwoordPaar.getAntwoord().format())).thenReturn(
                    vraagEnAntwoordPaar.getAntwoordAlsTextMessage());
        } catch (final BerichtInhoudException exceptie) {
            throw new JMSException(exceptie.getMessage());
        }

        final ArgumentMatcher<String> customMather = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(final Object argument) {
                try {
                    final String vraag = vraagEnAntwoordPaar.getVraag().format();
                    final String antwoord = vraagEnAntwoordPaar.getAntwoord().format();
                    return !vraag.equals(argument) && !antwoord.equals(argument);
                } catch (final BerichtInhoudException exceptie) {
                    return false;
                }
            }
        };

        when(session.createTextMessage(argThat(customMather))).thenThrow(
                new AssertionError("Unexpected sync bericht."));

        when(session.createProducer(null)).thenReturn(messageProducer);
        when(connection.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
        when(connectionFactory.createConnection()).thenReturn(connection);

        messageHandler.setConnectionFactory(connectionFactory);
        messageHandler.setInboundQueueName(this.getClass().getName());
    }

    // ------------------------------------TESTS-----------------------------------

    @Test
    public void testOnMessageBerichtMetMessageIdNull() throws JMSException, BerichtSyntaxException {
        // create testdata
        final LeesUitBrpVerzoekBericht queryBericht = new LeesUitBrpVerzoekBericht(ANUMMER);
        queryBericht.setMessageId(null);
        final VraagAntwoordPaar vraagEnAntwoordPaar =
                new VraagAntwoordPaar(queryBericht, new LeesUitBrpAntwoordBericht(queryBericht.getMessageId(),
                        DUMMY_LO3_PL));

        verifyAntwoorOpVraag(vraagEnAntwoordPaar, false);
    }

    @Test
    public void testOnMessageBerichtMetMessageIdLeeg() throws JMSException, BerichtSyntaxException {
        // create testdata
        final LeesUitBrpVerzoekBericht queryBericht = new LeesUitBrpVerzoekBericht(ANUMMER);
        queryBericht.setMessageId("");
        final VraagAntwoordPaar vraagEnAntwoordPaar =
                new VraagAntwoordPaar(queryBericht, new LeesUitBrpAntwoordBericht(queryBericht.getMessageId(),
                        DUMMY_LO3_PL));

        verifyAntwoorOpVraag(vraagEnAntwoordPaar, false);
    }

    // --------------------------------Private methods-----------------------------

    private void verifyAntwoorOpVraag(final VraagAntwoordPaar vraagEnAntwoordPaar, final boolean verwachtAntwoord)
            throws JMSException {
        // create mock objects
        final MessageProducer messageProducer = mock(MessageProducer.class);

        // setup connectionfactory voor dit vraag & antwoord paar
        setupConnectionFactory(vraagEnAntwoordPaar, messageProducer);

        // call method to test
        messageHandler.onMessage(vraagEnAntwoordPaar.getVraagAlsTextMessage());

        // verify result
        if (verwachtAntwoord) {
            verify(messageProducer, times(1)).send(vraagEnAntwoordPaar.getAntwoordAlsTextMessage());
        } else {
            verify(messageProducer, never()).send(vraagEnAntwoordPaar.getAntwoordAlsTextMessage());
        }
    }

    private static final class VraagAntwoordPaar {
        private final SyncBericht vraag;
        private final SyncBericht antwoord;
        private final TextMessage vraagAlsTextMessage;
        private final TextMessage antwoordAlsTextMessage;

        public VraagAntwoordPaar(final SyncBericht vraag, final SyncBericht antwoord) throws JMSException {
            this.vraag = vraag;
            this.antwoord = antwoord;
            vraagAlsTextMessage = createSyncBerichtTextMessage(vraag);
            antwoordAlsTextMessage = createSyncBerichtTextMessage(antwoord);
        }

        public SyncBericht getVraag() {
            return vraag;
        }

        public SyncBericht getAntwoord() {
            return antwoord;
        }

        public TextMessage getVraagAlsTextMessage() {
            return vraagAlsTextMessage;
        }

        public TextMessage getAntwoordAlsTextMessage() {
            return antwoordAlsTextMessage;
        }

        private TextMessage createSyncBerichtTextMessage(final SyncBericht syncBericht) throws JMSException {
            try {
                final TextMessage result = mock(TextMessage.class);

                when(result.getStringProperty(JMSConstants.BERICHT_REFERENTIE))
                        .thenReturn(syncBericht.getMessageId());
                when(result.getText()).thenReturn(syncBericht.format());

                return result;
            } catch (final BerichtInhoudException exceptie) {
                throw new JMSException(exceptie.getMessage());
            }
        }
    }
}
