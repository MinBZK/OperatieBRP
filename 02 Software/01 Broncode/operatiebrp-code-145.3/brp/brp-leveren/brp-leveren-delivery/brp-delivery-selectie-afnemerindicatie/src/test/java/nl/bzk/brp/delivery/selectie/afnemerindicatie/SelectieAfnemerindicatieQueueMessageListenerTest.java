/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.afnemerindicatie;

import static org.mockito.Mockito.times;

import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.algemeenbrp.util.common.serialisatie.SerialisatieExceptie;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.service.selectie.afnemerindicatie.VerwerkAfnemerindicatieService;
import nl.bzk.brp.service.selectie.verwerker.SelectieTaakVerwerker;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieTaakQueueMessageListenerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieAfnemerindicatieQueueMessageListenerTest {

    @Mock
    private SelectieTaakVerwerker selectieTaakVerwerker;

    @Mock
    private VerwerkAfnemerindicatieService verwerkAfnemerindicatieService;

    @InjectMocks
    private SelectieAfnemerindicatieQueueMessageListener selectieTaakQueueMessageListener;

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();


    @Test
    public void testHappyFlow() throws MessageNotWriteableException {

        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final SelectieAfnemerindicatieTaak selectieTaak = new SelectieAfnemerindicatieTaak();
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(new SelectieAfnemerindicatieTaak[]{selectieTaak}));
        selectieTaakQueueMessageListener.onMessage(activeMQTextMessage);
    }

    @Test(expected = SerialisatieExceptie.class)
    public void testFoutFlowSerialisatie() throws MessageNotWriteableException {

        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString("x"));
        selectieTaakQueueMessageListener.onMessage(activeMQTextMessage);

        Mockito.verify(selectieTaakVerwerker, times(0)).verwerkSelectieTaak(Mockito.any());
    }

    @Test
    public void testFoutFlowJMSfout() throws JMSException {

        final TextMessage mock = Mockito.mock(TextMessage.class);
        Mockito.when(mock.getText()).thenThrow(new JMSException("fout"));
        selectieTaakQueueMessageListener.onMessage(mock);

        Mockito.verify(selectieTaakVerwerker, times(0)).verwerkSelectieTaak(Mockito.any());
    }


}
