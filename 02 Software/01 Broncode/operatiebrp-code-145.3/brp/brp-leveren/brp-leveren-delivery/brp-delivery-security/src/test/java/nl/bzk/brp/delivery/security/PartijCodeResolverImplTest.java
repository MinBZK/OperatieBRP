/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.security;

import static org.mockito.Mockito.mock;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.service.algemeen.PartijCodeResolver;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class PartijCodeResolverImplTest {

    private PartijCodeResolver resolver = new PartijCodeResolverImpl();

    @Test
    public void testGet() {
        final String partijcode = "0626";
        Map<String, List<String>> headers = ImmutableMap.of(
                PartijCodeResolver.HEADER.PARTIJ_CODE.getNaam(), Collections.singletonList(partijcode));
        final Message message = mock(Message.class);
        Mockito.when(message.get(Message.PROTOCOL_HEADERS)).thenReturn(headers);

        setCurrentMessage(message);

        Assert.assertEquals(partijcode, resolver.get().orElse(null));
    }

    private static void setCurrentMessage(Message message) {
        ThreadLocal<Message> currentMessage = (ThreadLocal<Message>) ReflectionTestUtils.getField(PhaseInterceptorChain.class, "CURRENT_MESSAGE");
        currentMessage.set(message);
    }
}
