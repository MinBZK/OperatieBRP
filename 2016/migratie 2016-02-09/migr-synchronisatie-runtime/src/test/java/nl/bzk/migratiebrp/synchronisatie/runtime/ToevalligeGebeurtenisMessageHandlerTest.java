/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Test de Toevalligegebeurtenismessagehandler
 */
@RunWith(MockitoJUnitRunner.class)
public class ToevalligeGebeurtenisMessageHandlerTest {

    @Mock
    private Destination destination;
    @Mock
    private JmsTemplate jmsTemplate;
    @InjectMocks
    private ToevalligeGebeurtenisMessageHandler subject;

    @Mock
    private Session session;
    @Mock
    private TextMessage syncAntwoordMessage;

    @Test
    public void testSluitingHuwelijk() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                               + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                               + "><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                               + ":referentienummer>ce025753-493a-4983-afdf-6632585d3b76</b"
                               + "rp:referentienummer><brp:crossReferentienummer>65e5f78d-32a9-40ed-a620-828000000023</brp:crossReferentienummer><brp"
                               + ":tijdstipVerzending>2015-12-07T08:54:11.680+00:00</brp:tijd"
                               + "stipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:bijhouding>Verwerkt</brp"
                               + ":bijhouding><brp:hoogsteMeldingsniveau>Geen</brp:hoogs"
                               + "teMeldingsniveau></brp:resultaat><brp:gBASluitingHuwelijkGeregistreerdPartnerschap brp:objecttype=\"AdministratieveHandeling\" "
                               + "brp:objectSleutel=\"2\"><brp:partijCode>059901</brp"
                               + ":partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                               + ".222+00:00</brp:tijdstipRegistratie></brp:gBASluitingHuwelijkGeregistreerdPartnerschap></brp:isc_migRegistreerHuwelijk"
                               + "GeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
        System.out.println(antwoord.format());
        Assert.assertEquals("Status moet OK zijn", StatusType.OK, antwoord.getStatus());
    }

    @Test
    public void testOkBerichtVanBijhouding() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                               + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instanc"
                               + "e\"><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                               + ":referentienummer>1b8d8f6f-3be9-4714-8820-480c7f92817b"
                               + "</brp:referentienummer><brp:crossReferentienummer>83970566-2d0e-42c4-9297-d0d8eac7443a</brp:crossReferentienummer><brp"
                               + ":tijdstipVerzending>2016-01-04T09:39:45.958+00:00</brp:"
                               + "tijdstipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:bijhouding>Verwerkt</brp"
                               + ":bijhouding><brp:hoogsteMeldingsniveau>Geen</brp:"
                               + "hoogsteMeldingsniveau></brp:resultaat><brp:gBASluitingHuwelijkGeregistreerdPartnerschap "
                               + "brp:objecttype=\"AdministratieveHandeling\" brp:objectSleutel=\"4\"><brp:partijCode>05990"
                               + "1</brp:partijCode><brp:tijdstipRegistratie>2016-01-04T09:39:45"
                               + ".548+00:00</brp:tijdstipRegistratie></brp:gBASluitingHuwelijkGeregistreerdPartnerschap></brp:isc_migRegistreerH"
                               + "uwelijkGeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
        System.out.println(antwoord.format());
        Assert.assertEquals("Status moet OK zijn", StatusType.OK, antwoord.getStatus());
    }

    @Test
    public void testOmzettingHuwelijk() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                               + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                               + "><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                               + ":referentienummer>ce025753-493a-4983-afdf-6632585d3b76</b"
                               + "rp:referentienummer><brp:crossReferentienummer>65e5f78d-32a9-40ed-a620-828000000023</brp:crossReferentienummer><brp"
                               + ":tijdstipVerzending>2015-12-07T08:54:11.680+00:00</brp:tijd"
                               + "stipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:bijhouding>Verwerkt</brp"
                               + ":bijhouding><brp:hoogsteMeldingsniveau>Geen</brp:hoogs"
                               + "teMeldingsniveau></brp:resultaat><brp:gBAOmzettingHuwelijkGeregistreerdPartnerschap brp:objecttype=\"AdministratieveHandeling\" "
                               + "brp:objectSleutel=\"2\"><brp:partijCode>059901</brp"
                               + ":partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                               + ".222+00:00</brp:tijdstipRegistratie></brp:gBAOmzettingHuwelijkGeregistreerdPartnerschap></brp:isc_migRegistreerHuwelijk"
                               + "GeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
        Assert.assertEquals("Status moet OK zijn", StatusType.OK, antwoord.getStatus());
    }

    @Test
    public void testOntbindingHuwelijk() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                               + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                               + "><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                               + ":referentienummer>ce025753-493a-4983-afdf-6632585d3b76</b"
                               + "rp:referentienummer><brp:crossReferentienummer>65e5f78d-32a9-40ed-a620-828000000023</brp:crossReferentienummer><brp"
                               + ":tijdstipVerzending>2015-12-07T08:54:11.680+00:00</brp:tijd"
                               + "stipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Geslaagd</brp:verwerking><brp:bijhouding>Verwerkt</brp"
                               + ":bijhouding><brp:hoogsteMeldingsniveau>Geen</brp:hoogs"
                               + "teMeldingsniveau></brp:resultaat><brp:gBAOntbindingHuwelijkGeregistreerdPartnerschap brp:objecttype=\"AdministratieveHandeling\" "
                               + "brp:objectSleutel=\"2\"><brp:partijCode>059901</brp"
                               + ":partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                               + ".222+00:00</brp:tijdstipRegistratie></brp:gBAOntbindingHuwelijkGeregistreerdPartnerschap></brp:isc_migRegistreerHuwelijk"
                               + "GeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
        Assert.assertEquals("Status moet OK zijn", StatusType.OK, antwoord.getStatus());
    }

    @Test(expected = ServiceException.class)
    public void testFoutBericht() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" xmlns:xsi=\"http://www.w3"
                               + ".org/2001/XMLSchema-instance\"><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem"
                               + "><brp:referentienummer>597d2e08-14f6-4f81-aba5-5d3935e99933</brp:referentienummer><brp:crossReferentienummer>a20b046b-b96d-4a3c-b12e"
                               + "-98123a2842e4</brp:crossReferentienummer><brp:tijdstipVerzending>2015-12-22T07:55:12"
                               + ".576+00:00</brp:tijdstipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Foutief</brp:verwerking><brp:hoogsteMeldingsniveau>Fout"
                               + "</brp:hoogsteMeldingsniveau></brp:resultaat><brp:meldingen><brp:melding "
                               + "brp:objecttype=\"Melding\"><brp:regelCode>ALG0001</brp:regelCode><brp:soortNaam>Fout</brp:soortNaam><brp:melding>Onbekende fout "
                               + "opgetreden</brp:melding></brp:melding></brp:meldingen><brp:gBASluitingHuwelijkGeregistreerdPartnerschap "
                               + "brp:objecttype=\"AdministratieveHandeling\"><brp:partijCode>059901</brp:partijCode><brp:tijdstipRegistratie>2015-12-22T07:55:10"
                               + ".920+00:00</brp:tijdstipRegistratie></brp:gBASluitingHuwelijkGeregistreerdPartnerschap></brp"
                               + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        //
        // SynchronisatieFoutBericht antwoord = (SynchronisatieFoutBericht) antwoordObject;
        // Assert.assertTrue("Moet een SynchronisatieFoutBericht zijn", antwoordObject instanceof
        // SynchronisatieFoutBericht);
    }

    private Object verwerkBericht(final TextMessage message, final String bericht) throws JMSException {
        Mockito.when(message.getText()).thenReturn(bericht);

        subject.onMessage(message);

        // Capture message creator
        final ArgumentCaptor<MessageCreator> messageCreatorCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        Mockito.verify(jmsTemplate).send(Matchers.same(destination), messageCreatorCaptor.capture());

        // Execute message creator
        Mockito.when(session.createTextMessage(Matchers.anyString())).thenReturn(syncAntwoordMessage);

        final MessageCreator messageCreator = messageCreatorCaptor.getValue();
        messageCreator.createMessage(session);

        final ArgumentCaptor<String> syncTextCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncMsgIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> syncCorrIdCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(session).createTextMessage(syncTextCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.BERICHT_REFERENTIE), syncMsgIdCaptor.capture());
        Mockito.verify(syncAntwoordMessage).setStringProperty(Matchers.eq(JMSConstants.CORRELATIE_REFERENTIE), syncCorrIdCaptor.capture());

        return SyncBerichtFactory.SINGLETON.getBericht(syncTextCaptor.getValue());
    }
}
