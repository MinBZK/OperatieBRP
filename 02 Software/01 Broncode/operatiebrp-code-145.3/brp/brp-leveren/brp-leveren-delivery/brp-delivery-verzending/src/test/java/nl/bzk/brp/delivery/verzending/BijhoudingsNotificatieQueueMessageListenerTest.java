/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.algemeenbrp.util.common.serialisatie.SerialisatieExceptie;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsNotificatieQueueMessageListenerTest {

    private final JsonStringSerializer serialiseerderBijhoudingsplan = new JsonStringSerializer();

    @InjectMocks
    private BijhoudingsNotificatieQueueMessageListener bijhoudingsNotificatieMessageListener;

    @Mock
    private Verzending.VerzendingService verzendingService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void test() throws JMSException, ParseException, IOException, WriteException {

        final ActiveMQTextMessage message = maakMessage();

        bijhoudingsNotificatieMessageListener.onMessage(message);

        Mockito.verify(verzendingService).verwerkBijhoudingsNotificatieBericht(Mockito.any());
    }

    @Test
    public void testFoutGeenTextMessageInstantie() throws JMSException, JsonProcessingException {

        final ActiveMQObjectMessage message = new ActiveMQObjectMessage();

        bijhoudingsNotificatieMessageListener.onMessage(message);

        Mockito.verifyZeroInteractions(verzendingService);
    }

    @Test
    public void testJmsFoutBijVerwerking() throws JMSException {
        expectedException.expect(VerzendExceptie.class);
        expectedException.expectMessage("Deserialisatiefout : Het ontvangen van een JMS bericht met een bijhoudingsnotificatiebericht is mislukt");

        final ActiveMQTextMessage message = Mockito.mock(ActiveMQTextMessage.class);
        Mockito.doThrow(JMSException.class).when(message).getText();

        bijhoudingsNotificatieMessageListener.onMessage(message);
    }

    @Test
    public void testFoutBijDeserialisatie() throws JMSException, JsonProcessingException {
        expectedException.expect(SerialisatieExceptie.class);
        expectedException.expectMessage("Het deserialiseren van het object is mislukt.");

        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("invalid");

        bijhoudingsNotificatieMessageListener.onMessage(message);
    }


    @Test(expected = VerzendExceptie.class)
    public void testFoutBijVerwerking() throws MessageNotWriteableException, IOException, ParseException {
        final ActiveMQTextMessage message = maakMessage();
        Mockito.doThrow(VerzendExceptie.class).when(verzendingService).verwerkBijhoudingsNotificatieBericht(Mockito.any());

        bijhoudingsNotificatieMessageListener.onMessage(message);
    }


    private ActiveMQTextMessage maakMessage() throws MessageNotWriteableException, ParseException, IOException {
        final ActiveMQTextMessage message = new ActiveMQTextMessage();
        String bijhoudingsplanNotificatieBericht = BijhoudingsNotificatieBerichtTestUtil.maakBijhoudingsplanNotificatieBerichtJson();
        message.setText(bijhoudingsplanNotificatieBericht);
        return message;
    }

}
