/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.selectie.schrijver.MaakSelectieResultaatTaakVerwerkerService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieSchrijfTaakQueueMessageListenerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaakSelectieResultaatTaakQueueMessageListenerTest {

    @Mock(name = "maakSelectieResultaatTaakVerwerkerServiceImpl")
    private MaakSelectieResultaatTaakVerwerkerService maakSelectieResultaatTaakVerwerkerService;
    @Mock(name = "afnemerIndicatieMaakSelectieResultaatTaakVerwerkerServiceImpl")
    private MaakSelectieResultaatTaakVerwerkerService afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService;

    @InjectMocks
    private MaakSelectieResultaatTaakQueueMessageListener maakSelectieResultaatTaakQueueMessageListener;

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();


    @Test
    public void testModelSelectie() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        taak.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE);
        taak.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(taak));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verify(maakSelectieResultaatTaakVerwerkerService).verwerk(Mockito.any(MaakSelectieResultaatTaak.class));
        Mockito.verifyZeroInteractions(afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService);
    }

    @Test
    public void testStandaardSelectie() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        taak.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE);
        taak.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(taak));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verify(maakSelectieResultaatTaakVerwerkerService).verwerk(Mockito.any(MaakSelectieResultaatTaak.class));
        Mockito.verifyZeroInteractions(afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService);
    }

    @Test
    public void testSelectiePlaatsingAfnemerIndicatie() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        taak.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        taak.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(taak));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verify(afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService).verwerk(Mockito.any(MaakSelectieResultaatTaak.class));
        Mockito.verifyZeroInteractions(maakSelectieResultaatTaakVerwerkerService);
    }

    @Test
    public void testSelectieVerwijderingAfnemerIndicatie() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        taak.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        taak.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(taak));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verify(afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService).verwerk(Mockito.any(MaakSelectieResultaatTaak.class));
        Mockito.verifyZeroInteractions(maakSelectieResultaatTaakVerwerkerService);
    }

    @Test
    public void testGeenSoortSelectie() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht = new SelectieFragmentSchrijfBericht();
        selectieFragmentSchrijfBericht.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(selectieFragmentSchrijfBericht));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verifyZeroInteractions(maakSelectieResultaatTaakVerwerkerService);
        Mockito.verifyZeroInteractions(afnemerIndicatieMaakSelectieResultaatTaakVerwerkerService);
    }

    @Test
    public void jmsFout() throws MessageNotWriteableException {
        Mockito.doThrow(JMSException.class).when(maakSelectieResultaatTaakVerwerkerService).verwerk(Mockito.any(MaakSelectieResultaatTaak.class));

        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
        taak.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        taak.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(taak));

        maakSelectieResultaatTaakQueueMessageListener.onMessage(activeMQTextMessage);
    }
}


