/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.junit.Before;
import org.junit.Test;

public class OinHandlerTest {

    private OinHandler subject;
    private PartijRepository partijRepository;

    @Before
    public void setup() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor c = OinHandler.class.getDeclaredConstructors()[0];
        c.setAccessible(true);
        subject = (OinHandler) c.newInstance();
        partijRepository = mock(PartijRepository.class);
        subject.setPartijRepository(partijRepository);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void setHeaders() {
        Partij partij = new Partij("Partij1", "001234");
        partij.setOin("oinkoink");
        when(partijRepository.findByCode(anyString())).thenReturn(partij);

        subject.onApplicationEvent(null);
        Message message = new MessageImpl();
        subject.handleMessage(message);
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        assertEquals(2, headers.size());
        assertEquals(Collections.singletonList("oinkoink"), headers.get("oin-ondertekenaar"));
        assertEquals(Collections.singletonList("oinkoink"), headers.get("oin-transporteur"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void overschrijfHeadersNiet() {
        Partij partij1 = new Partij("Partij1", "001234");
        partij1.setOin("oinkoink");
        when(partijRepository.findByCode(anyString())).thenReturn(partij1);
        subject.onApplicationEvent(null);

        Partij partij2 = new Partij("Partij1", "001234");
        partij2.setOin("bladiebla");
        when(partijRepository.findByCode(anyString())).thenReturn(partij2);
        subject.onApplicationEvent(null);

        Message message = new MessageImpl();
        subject.handleMessage(message);
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        assertEquals(2, headers.size());
        assertEquals(Collections.singletonList("oinkoink"), headers.get("oin-ondertekenaar"));
        assertEquals(Collections.singletonList("oinkoink"), headers.get("oin-transporteur"));
    }
}
