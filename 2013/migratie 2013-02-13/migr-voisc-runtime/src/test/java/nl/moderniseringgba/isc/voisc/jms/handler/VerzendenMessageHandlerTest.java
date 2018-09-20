/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms.handler;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.voisc.VoaService;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.jms.TestOkMessage;
import nl.moderniseringgba.isc.voisc.jms.VerzendenMessageHandler;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class VerzendenMessageHandlerTest {

    private static final String ORIG_CODE = "2345";
    private static final String RECIP_CODE = "5432";
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Mock
    private VoaService voiscService;
    @Mock
    private VoiscDbProxy voiscDbProxy;

    @InjectMocks
    private VerzendenMessageHandler handler;

    private Mailbox originatorMb;
    private Mailbox recipientMb;

    @Before
    public void setUp() {
        originatorMb = new Mailbox();
        originatorMb.setGemeentecode(ORIG_CODE);
        originatorMb.setMailboxnr(ORIG_CODE + "010");

        recipientMb = new Mailbox();
        recipientMb.setGemeentecode(ORIG_CODE);
        recipientMb.setMailboxnr(RECIP_CODE + "010");
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    @Test
    public void onMessageSuccesTest() {
        final String messageId = "3832803548";
        final String correlationId = "123-UUID";
        final Ib01Bericht bericht = new Ib01Bericht();
        bericht.setCorrelationId(correlationId);
        bericht.setMessageId(messageId);
        try {
            bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            fail("kon geen ib01 aanmaken");
        }
        // CHECKSTYLE:ON

        try {
            final TestOkMessage message = new TestOkMessage();
            message.setText(bericht.format());
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
            message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, correlationId);
            message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, "2345");
            message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, "5432");

            Mockito.when(voiscDbProxy.getMailboxByGemeentecode(Matchers.anyString())).thenAnswer(
                    new Answer<Mailbox>() {

                        @Override
                        public Mailbox answer(final InvocationOnMock invocation) throws Throwable {
                            final String gemeenteCode = (String) invocation.getArguments()[0];
                            if (ORIG_CODE.equals(gemeenteCode)) {
                                return originatorMb;
                            } else if (RECIP_CODE.equals(gemeenteCode)) {
                                return recipientMb;
                            }
                            return null;
                        }
                    });
            handler.onMessage(message);

            Mockito.verify(voiscDbProxy, Mockito.times(2)).getMailboxByGemeentecode(Matchers.anyString());
        } catch (final JMSException e) {
            Assert.fail("Er zou geen JMSException gegooid moeten worden");
        }
    }

    @Test
    public void noMessageIdMessageTest() {
        final String correlationId = "3832803548";
        final SyncBericht bericht =
                new SynchroniseerNaarBrpAntwoordBericht(correlationId, StatusType.FOUT, "Fout opgetreden PRE001");

        try {
            final TestOkMessage message = new TestOkMessage();
            try {
                message.setText(bericht.format());
            } catch (final BerichtInhoudException exceptie) {
                throw new VoaException("Berichtinhoud foutief", null, exceptie);
            }

            Mockito.when(voiscDbProxy.getBerichtToSendMBS(Matchers.anyString())).thenReturn(new ArrayList<Bericht>());
            handler.onMessage(message);
            Mockito.verify(voiscDbProxy, Mockito.times(1)).sendEsbErrorBericht(null, "JMS bericht is niet valide.");
        } catch (final JMSException e) {
            Assert.fail("Geen JMSException verwacht");
        } catch (final VoaException e) {
            Assert.fail("Geen VoaException verwacht");
        }
    }

    @Test
    public void noTextMessageTest() {
        try {
            final TestNokMessage message = new TestNokMessage();
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, "123456");
            Mockito.when(voiscDbProxy.getBerichtToSendMBS(Matchers.anyString())).thenReturn(new ArrayList<Bericht>());
            handler.onMessage(message);
            Mockito.verify(voiscDbProxy, Mockito.times(1)).sendEsbErrorBericht(null, "JMS bericht is niet valide.");
        } catch (final JMSException e) {
            fail("Er zou geen JMSException moeten optreden.");
        } catch (final VoaException e) {
            Assert.fail("Geen VoaException verwacht");
        }
    }

    @Test
    public void voaExceptionTest() {
        final TestOkMessage message = new TestOkMessage();
        try {
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, "123456");
        } catch (final JMSException e1) {
            fail("Geen JMSException verwacht.");
        }
        Mockito.when(voiscDbProxy.getMailboxByGemeentecode(Matchers.anyString())).thenAnswer(new Answer<Mailbox>() {

            @Override
            public Mailbox answer(final InvocationOnMock invocation) throws Throwable {
                return recipientMb;
            }
        });
        Mockito.when(voiscDbProxy.getBerichtToSendMBS(Matchers.anyString())).thenReturn(new ArrayList<Bericht>());

        try {
            Mockito.doAnswer(new Answer<Mailbox>() {

                @Override
                public Mailbox answer(final InvocationOnMock invocation) throws Throwable {
                    throw new VoaException("Fout opgetreden", null);
                }
            }).when(voiscDbProxy)
                    .saveBericht(Matchers.any(Bericht.class), Matchers.anyBoolean(), Matchers.anyBoolean());

            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws Throwable {
                    Assert.assertEquals(((LogboekRegel) invocation.getArguments()[0]).getFoutmelding(),
                            "[MELDING-Fout opgetreden]: ???Fout opgetreden??? {0}; {1}; {2}; {3}; {4}; {5}; {6}; {7}.");
                    return null;
                }
            }).when(voiscDbProxy).saveLogboekRegel(Matchers.any(LogboekRegel.class));

            handler.onMessage(message);
        } catch (final VoaException e) {
            fail("zou niet hier gegooid mogen worden.");
        }
        Mockito.verify(voiscDbProxy, Mockito.times(1)).saveLogboekRegel(Matchers.any(LogboekRegel.class));
    }
}
