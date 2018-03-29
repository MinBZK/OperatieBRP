/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.jms.afnemer;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;

/**
 * Test voor plaats queue bericht.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaatsAfnemerBerichtServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @InjectMocks
    private PlaatsAfnemerBerichtServiceImpl plaatsAfnemerBerichtService;

    @Mock
    @Named("afnemersJmsTemplate")
    private JmsOperations jmsOperations;

    @Test
    public void testHappyflow() throws JMSException {
        final AfnemerBericht afnemerBericht = maakAfnemerbericht();
        plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList(afnemerBericht));
        final ArgumentCaptor<ProducerCallback> callbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(jmsOperations, Mockito.times(1)).execute(callbackArgumentCaptor.capture());
        final ProducerCallback value = callbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);

        final TextMessage textMessageMock = Mockito.mock(TextMessage.class);
        Mockito.when(sessionMock.createTextMessage(new JsonStringSerializer()
                .serialiseerNaarString(afnemerBericht.getSynchronisatieBerichtGegevens()))).thenReturn(textMessageMock);

        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        value.doInJms(sessionMock, messageProducerMock);
        Mockito.verify(messageProducerMock, Mockito.times(1)).send(textMessageMock);
        Mockito.verify(textMessageMock).setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER,
                String.valueOf(afnemerBericht.getSynchronisatieBerichtGegevens().getArchiveringOpdracht().getOntvangendePartijId()));
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testJmsFout() throws JmsException, JMSException {
        final ArgumentCaptor<ProducerCallback> callbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.doThrow(new org.springframework.jms.IllegalStateException(new javax.jms.IllegalStateException("fubar")))
                .when(jmsOperations).execute(callbackArgumentCaptor.capture());
        plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList());
    }

    @Test
    public void testGeenBerichten() throws JMSException {
        plaatsAfnemerBerichtService.plaatsAfnemerberichten(Lists.newArrayList());
        final ArgumentCaptor<ProducerCallback> callbackArgumentCaptor = ArgumentCaptor.forClass(ProducerCallback.class);
        Mockito.verify(jmsOperations, Mockito.times(1)).execute(callbackArgumentCaptor.capture());
        final ProducerCallback value = callbackArgumentCaptor.getValue();
        final Session sessionMock = Mockito.mock(Session.class);
        final MessageProducer messageProducerMock = Mockito.mock(MessageProducer.class);
        value.doInJms(sessionMock, messageProducerMock);
        Mockito.verifyZeroInteractions(messageProducerMock);
    }

    private AfnemerBericht maakAfnemerbericht() {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.SYNCHRONISATIE_STAMGEGEVEN;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setOntvangendePartijId((short) 123);
        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .build();
        ;
        return new AfnemerBericht(berichtGegevens, tla);
    }
}
