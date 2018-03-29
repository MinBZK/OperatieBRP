/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.RegelConversietabel;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test de Toevalligegebeurtenismessagehandler
 */
@RunWith(MockitoJUnitRunner.class)
public class ToevalligeGebeurtenisMessageHandlerTest {

    @Mock
    private Destination destination;
    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Session session;

    @Mock
    private TextMessage syncAntwoordMessage;

    @Mock
    private ConversietabelFactory conversietabelFactory;

    private ToevalligeGebeurtenisMessageHandler subject;

    @Before
    public void setUp() {
        Mockito.when(conversietabelFactory.createRegelConversietabel()).thenReturn(new RegelConversietabel());

        subject = new ToevalligeGebeurtenisMessageHandler(destination, Mockito.mock(ConnectionFactory.class));
        ReflectionTestUtils.setField(subject, AbstractMessageHandler.class, "jmsTemplate", jmsTemplate, JmsTemplate.class);
    }

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
                        + "teMeldingsniveau></brp:resultaat><brp:gBAVoltrekkingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\">"
                        + "<brp:partijCode>059901</brp"
                        + ":partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                        + ".222+00:00</brp:tijdstipRegistratie></brp:gBAVoltrekkingHuwelijkInNederland></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
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
                        + "hoogsteMeldingsniveau></brp:resultaat><brp:gBAVoltrekkingHuwelijkInNederland "
                        + "brp:objecttype=\"AdministratieveHandeling\"><brp:partijCode>05990"
                        + "1</brp:partijCode><brp:tijdstipRegistratie>2016-01-04T09:39:45"
                        + ".548+00:00</brp:tijdstipRegistratie></brp:gBAVoltrekkingHuwelijkInNederland></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
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
                        + "teMeldingsniveau></brp:resultaat><brp:gBAOmzettingGeregistreerdPartnerschapInHuwelijk brp:objecttype=\"AdministratieveHandeling\">"
                        + "<brp:partijCode>059901</brp:partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                        + ".222+00:00</brp:tijdstipRegistratie></brp:gBAOmzettingGeregistreerdPartnerschapInHuwelijk></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
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
                        + "teMeldingsniveau></brp:resultaat><brp:gBAOntbindingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\">"
                        + "<brp:partijCode>059901</brp:partijCode><brp:tijdstipRegistratie>2015-12-07T08:54:09"
                        + ".222+00:00</brp:tijdstipRegistratie></brp:gBAOntbindingHuwelijkInNederland></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        final Object antwoordObject = verwerkBericht(message, bericht);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = (VerwerkToevalligeGebeurtenisAntwoordBericht) antwoordObject;
        Assert.assertEquals("Status moet OK zijn", StatusType.OK, antwoord.getStatus());
    }

    @Test(expected = ServiceException.class)
    public void testFoutBericht() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String
                bericht =
                "<?xml version=\"1.0\"?><brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                        + "xmlns:xsi=\"http://www.w3"
                        + ".org/2001/XMLSchema-instance\"><brp:stuurgegevens><brp:zendendePartij>199903</brp:zendendePartij><brp:zendendeSysteem>BRP</brp"
                        + ":zendendeSysteem"
                        + "><brp:referentienummer>597d2e08-14f6-4f81-aba5-5d3935e99933</brp:referentienummer><brp:crossReferentienummer>a20b046b-b96d-4a3c-b12e"
                        + "-98123a2842e4</brp:crossReferentienummer><brp:tijdstipVerzending>2015-12-22T07:55:12"
                        + ".576+00:00</brp:tijdstipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Foutief</brp:verwerking><brp"
                        + ":hoogsteMeldingsniveau>Fout"
                        + "</brp:hoogsteMeldingsniveau></brp:resultaat><brp:meldingen><brp:melding brp:referentieID=\"34545-24522\" "
                        + "brp:objecttype=\"Melding\"><brp:regelCode>ALG0001</brp:regelCode><brp:soortNaam>Fout</brp:soortNaam><brp:melding>Onbekende fout "
                        + "opgetreden</brp:melding></brp:melding></brp:meldingen><brp:gBAVoltrekkingHuwelijkInNederland "
                        + "brp:objecttype=\"AdministratieveHandeling\"><brp:partijCode>059901</brp:partijCode><brp:tijdstipRegistratie>2015-12-22T07:55:10"
                        + ".920+00:00</brp:tijdstipRegistratie></brp:gBAVoltrekkingHuwelijkInNederland></brp"
                        + ":isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        verwerkBericht(message, bericht);
    }

    @Test(expected = ServiceException.class)
    public void testFoutBerichtVanBijhouding() throws Exception {
        final TextMessage message = Mockito.mock(TextMessage.class);
        final String bericht =
                "<?xml version=\"1.0\"?>\n"
                        + "<brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" xmlns:xsi=\"http://www"
                        + ".w3.org/2001/XMLSchema-instance\">\n"
                        + "    <brp:stuurgegevens>\n"
                        + "        <brp:zendendePartij>199903</brp:zendendePartij>\n"
                        + "        <brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
                        + "        <brp:referentienummer>f6cf9ea0-8ce7-42a3-ab0a-08b7fcab6b31</brp:referentienummer>\n"
                        + "        <brp:crossReferentienummer>0cce132e-b1d1-40e1-9324-602e8279714b</brp:crossReferentienummer>\n"
                        + "        <brp:tijdstipVerzending>2016-03-03T13:07:25.740+00:00</brp:tijdstipVerzending>\n"
                        + "    </brp:stuurgegevens>\n"
                        + "    <brp:resultaat>\n"
                        + "        <brp:verwerking>Foutief</brp:verwerking>\n"
                        + "        <brp:bijhouding>Verwerkt</brp:bijhouding>\n"
                        + "        <brp:hoogsteMeldingsniveau>Geen</brp:hoogsteMeldingsniveau>\n"
                        + "    </brp:resultaat>\n"
                        + "    <brp:meldingen>\n"
                        + "     <brp:melding brp:objecttype=\"Melding\" brp:referentieID=\"3423-45423\">\n"
                        + "     <brp:regelCode>NTB0003</brp:regelCode>\n"
                        + "     <brp:soortNaam>Fout</brp:soortNaam>\n"
                        + "     <brp:melding>De gevonden persoon is opgeschort met reden ‘E’ of ‘M’.</brp:melding>\n"
                        + "     </brp:melding>\n"
                        + "    </brp:meldingen>\n"
                        + "    <brp:gBAVoltrekkingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\">\n"
                        + "        <brp:partijCode>051801</brp:partijCode>\n"
                        + "        <brp:tijdstipRegistratie>2016-03-03T13:07:19.002+00:00</brp:tijdstipRegistratie>\n"
                        + "    </brp:gBAVoltrekkingHuwelijkInNederland>\n"
                        + "</brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R>";
        verwerkBericht(message, bericht);
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
