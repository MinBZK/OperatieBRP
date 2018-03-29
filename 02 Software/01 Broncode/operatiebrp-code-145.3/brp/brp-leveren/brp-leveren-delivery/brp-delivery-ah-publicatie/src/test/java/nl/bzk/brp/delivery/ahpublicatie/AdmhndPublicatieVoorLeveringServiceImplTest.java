/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.ahpublicatie;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * AdmhndPublicatieVoorLeveringServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class AdmhndPublicatieVoorLeveringServiceImplTest {

    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private Session sessionMock;
    @Mock
    private MessageProducer messageProducer;

    @Test
    public void testPubliceer() {
        final AdmhndPublicatieVoorLeveringServiceImpl service = new AdmhndPublicatieVoorLeveringServiceImpl();
        service.setPublicatieTemplate(jmsTemplate);
        //
        final Set<String> handelingen = Sets.newHashSet("handeling1", "handeling2");

        doAnswer(a -> {
            final ProducerCallback<Object> o = (ProducerCallback<Object>) a.getArguments()[0];
            o.doInJms(sessionMock, messageProducer);
            verify(messageProducer, times(2)).send(any());
            verify(sessionMock).createTextMessage("handeling1");
            verify(sessionMock).createTextMessage("handeling2");
            return null;
        }).when(jmsTemplate).execute(any(ProducerCallback.class));

        service.publiceerHandelingen(handelingen);

        Mockito.verify(jmsTemplate, times(1)).execute(Matchers.<ProducerCallback<Object>>any());
    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void testPubliceerFout() {
        final AdmhndPublicatieVoorLeveringServiceImpl service = new AdmhndPublicatieVoorLeveringServiceImpl();
        service.setPublicatieTemplate(jmsTemplate);
        //
        final Set<String> handelingen = Sets.newHashSet("handeling1", "handeling2");

        Mockito.when(jmsTemplate.execute(Matchers.<ProducerCallback<Object>>any())).thenThrow(new DummyJmsException("fout"));

        service.publiceerHandelingen(handelingen);

        Mockito.verify(jmsTemplate, times(1)).execute(Matchers.<ProducerCallback<Object>>any());
    }

    private class DummyJmsException extends JmsException {

        private static final long serialVersionUID = -22293795047117416L;

        DummyJmsException(final String msg) {
            super(msg);
        }
    }
}
