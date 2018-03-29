/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.gba.domain.levering.LeveringQueue;
import nl.bzk.brp.gba.domain.notificatie.NotificatieQueue;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.cache.PartijCache;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.InvalidDestinationException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class GBAStelselServiceImplTest {

    private final TextMessage textMessage = new ActiveMQTextMessage();
    private final Session sessionMock = mock(Session.class);

    @InjectMocks
    private GBAStelselServiceImpl gbaStelselService;

    @Mock
    private JmsTemplate lo3JmsTemplate;

    @Mock
    private PartijCache partijCache;

    @Before
    public void voorTest() throws JMSException {
        when(sessionMock.createTextMessage(anyString())).then(answer -> {
            textMessage.setText(answer.getArguments()[0].toString());
            return textMessage;
        });
    }

    @Test
    public final void verzendLo3BerichtSuccesvol() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(1234L);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 5555);
        archiveringOpdracht.setCrossReferentienummer("crossReferentienummer");
        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();
        assertCallback(berichtGegevens);
        Partij mockedPartij = mock(Partij.class);
        when(partijCache.geefPartijMetId((short) 5555)).thenReturn(mockedPartij);
        when(partijCache.geefPartij("005555")).thenReturn(mockedPartij);
        when(mockedPartij.getCode()).thenReturn("005555");
        when(mockedPartij.getId()).thenReturn((short) 5555);
        gbaStelselService.verzendLo3Bericht(berichtGegevens);

        Mockito.verify(lo3JmsTemplate).send(eq(LeveringQueue.NAAM.getQueueNaam()), any(MessageCreator.class));
    }

    @Test
    public final void verzendVrijBerichtSuccesvol() throws Exception {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 5555);
        archiveringOpdracht.setZendendePartijId((short) 6666);
        archiveringOpdracht.setReferentienummer("Referentienummer");

        Partij ontvangendePartij = mock(Partij.class);
        when(partijCache.geefPartijMetId((short) 5555)).thenReturn(ontvangendePartij);
        when(ontvangendePartij.getCode()).thenReturn("005555");
        Partij verzendendePartij = mock(Partij.class);
        when(partijCache.geefPartijMetId((short) 6666)).thenReturn(verzendendePartij);
        when(verzendendePartij.getCode()).thenReturn("006666");

        final VrijBerichtGegevens berichtGegevens = VrijBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metBrpEndpointUrl("/endpoint")
                .metStelsel(Stelsel.GBA)
                .metPartij(ontvangendePartij)
                .build();

        assertCallback(berichtGegevens, "bericht");

        gbaStelselService.verzendVrijBericht(berichtGegevens);

        Mockito.verify(lo3JmsTemplate).send(eq(NotificatieQueue.NAAM.getQueueNaam()), any(MessageCreator.class));
    }

    @Test(expected = VerzendExceptie.class)
    public final void testProcessGeenOntvangendePartij() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(1234L);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("bericht");
        archiveringOpdracht.setCrossReferentienummer("crossReferentienummer");
        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();
        assertCallback(berichtGegevens);
        gbaStelselService.verzendLo3Bericht(berichtGegevens);

        Mockito.verifyZeroInteractions(lo3JmsTemplate);
    }

    @Test(expected = VerzendExceptie.class)
    public final void testProcessGeenPartijGevondenBijOntvPartijId() throws Exception {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(1234L);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(1);
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("bericht");
        archiveringOpdracht.setOntvangendePartijId((short) 5555);
        archiveringOpdracht.setCrossReferentienummer("crossReferentienummer");
        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();
        assertCallback(berichtGegevens);

        when(partijCache.geefPartijMetId((short) 5555)).thenReturn(null);
        gbaStelselService.verzendLo3Bericht(berichtGegevens);

        Mockito.verifyZeroInteractions(lo3JmsTemplate);
    }

    @Test(expected = VerzendExceptie.class)
    public final void testProcessSuccesvolZonderAdmhndEnCrossref() throws Exception {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setData("bericht");
        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht).build();
        assertCallback(berichtGegevens);

        gbaStelselService.verzendLo3Bericht(berichtGegevens);

        Mockito.verifyZeroInteractions(lo3JmsTemplate);
    }

    @Test(expected = VerzendExceptie.class)
    public final void testProcessMetNullLeveringBericht() throws Exception {
        gbaStelselService.verzendLo3Bericht(SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now()))
                .build());
        Mockito.verify(lo3JmsTemplate, never()).send(eq(LeveringQueue.NAAM.getQueueNaam()), any(MessageCreator.class));
    }

    @Test(expected = VerzendExceptie.class)
    public final void stuurBerichtMaarJmsTemplateGeeftFout() throws Exception {
        Mockito.doThrow(new InvalidDestinationException(new javax.jms.InvalidDestinationException("Test exceptie!")))
                .when(lo3JmsTemplate).send(any(MessageCreator.class));

        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now())).build();
        gbaStelselService.verzendLo3Bericht(berichtGegevens);
        Mockito.verify(lo3JmsTemplate).send(eq(LeveringQueue.NAAM.getQueueNaam()), any(MessageCreator.class));
    }

    private void assertCallback(final SynchronisatieBerichtGegevens berichtGegevens) {
        doAnswer(a -> {
            final MessageCreator o = (MessageCreator) a.getArguments()[1];
            final Message message = o.createMessage(sessionMock);
            assertThat(message.getStringProperty("administratieveHandelingId"),
                    is((berichtGegevens.getProtocolleringOpdracht() == null
                            || berichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId() == null) ? null :
                            String.valueOf(berichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId())));
            assertThat(message.getStringProperty("iscCorrelatieReferentie"),
                    is(berichtGegevens.getArchiveringOpdracht().getCrossReferentienummer()));
            assertThat(partijCache.geefPartij(message.getStringProperty("voaRecipient")).getId(),
                    is(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
            return null;
        }).when(lo3JmsTemplate).send(eq(LeveringQueue.NAAM.getQueueNaam()), any());
    }

    private void assertCallback(final VrijBerichtGegevens berichtGegevens, final String inhoud) {
        doAnswer(a -> {
            final MessageCreator o = (MessageCreator) a.getArguments()[1];
            final Message message = o.createMessage(sessionMock);
            assertEquals(berichtGegevens.getArchiveringOpdracht().getReferentienummer(), message.getStringProperty("iscBerichtReferentie"));
            assertEquals(inhoud, ((TextMessage) message).getText());
            return null;
        }).when(lo3JmsTemplate).send(eq(NotificatieQueue.NAAM.getQueueNaam()), any());
    }
}
