/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import java.lang.reflect.Field;
import junit.framework.TestCase;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.ReflectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class LeveringBerichtArchiveringInterceptorTest extends TestCase {

    private LeveringBerichtArchiveringInterceptor interceptor;

    @Before
    public void setup() {
        interceptor = new LeveringBerichtArchiveringInterceptor();
    }

    @Test
    public void testZetBerichtinhoud() throws Exception {
        final StringBuilder sb = new StringBuilder();
        final CachedOutputStream cos = new CachedOutputStream();
        final Field field = ReflectionUtils.findField(PhaseInterceptorChain.class, "CURRENT_MESSAGE");
        assertNotNull(field);
        ReflectionUtils.makeAccessible(field);
        final ThreadLocal<Message> threadLocal = (ThreadLocal<Message>) field.get(ReflectionUtils.class);
        final Message messageMock = Mockito.mock(Message.class);
        threadLocal.set(messageMock);

        interceptor.writePayload(sb, cos, "UTF-8", "application/xml");

        final Message currentMessage = PhaseInterceptorChain.getCurrentMessage();
        assertNotNull(currentMessage);

        Mockito.verify(messageMock, Mockito.only()).put(Mockito.eq("berichtinhoud"), Mockito.any());
    }
}
