/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import javax.jms.MessageNotWriteableException;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.selectie.schrijver.SelectieFragmentSchrijfTaakVerwerkerService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieSchrijfTaakQueueMessageListenerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieSchrijfTaakQueueMessageListenerTest {

    @Mock
    private SelectieFragmentSchrijfTaakVerwerkerService selectieFragmentSchrijfTaakVerwerkerService;

    @InjectMocks
    private SelectieSchrijfTaakQueueMessageListener selectieSchrijfTaakQueueMessageListener;

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();


    @Test
    public void testHappyFlowTypeFragment() throws MessageNotWriteableException {
        final ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        final SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht = new SelectieFragmentSchrijfBericht();
        selectieFragmentSchrijfBericht.setDienstId(1);
        activeMQTextMessage.setText(JSON_STRING_SERIALISEERDER.serialiseerNaarString(selectieFragmentSchrijfBericht));
        selectieSchrijfTaakQueueMessageListener.onMessage(activeMQTextMessage);
    }

}


