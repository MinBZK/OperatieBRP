/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import javax.servlet.ServletContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;


@RunWith(MockitoJUnitRunner.class)
public class BrpApplicatieStartListenerTest {

    private BrpApplicatieStartListener brpApplicatieStartListener = new BrpApplicatieStartListener();

    @Mock
    private ContextStartedEvent contextStartedEvent;

    @Mock
    private ContextClosedEvent contextClosedEvent;

    @Mock
    private ContextRefreshedEvent contextRefreshedEvent;

    @Mock
    private ApplicationContext applicationContextEvent;

    @Mock
    private XmlWebApplicationContext xmlWebApplicationContext;

    @Mock
    private ServletContext servletContext;

    @Before
    public final void init() {
        Mockito.when(contextStartedEvent.getApplicationContext()).thenReturn(xmlWebApplicationContext);
        Mockito.when(contextClosedEvent.getApplicationContext()).thenReturn(xmlWebApplicationContext);
        Mockito.when(contextRefreshedEvent.getApplicationContext()).thenReturn(xmlWebApplicationContext);
        Mockito.when(xmlWebApplicationContext.getId()).thenReturn("123");
        Mockito.when(xmlWebApplicationContext.getDisplayName()).thenReturn("testname");
        Mockito.when(xmlWebApplicationContext.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getServletContextName()).thenReturn("servletcontext/name");
    }

    @Test
    public final void startSuccesvol() {
        brpApplicatieStartListener.onApplicationEvent(contextStartedEvent);
    }

    @Test
    public final void startContextClosedEvent() {
        brpApplicatieStartListener.onApplicationEvent(contextClosedEvent);
    }

    @Test
    public final void startContextRefreshedEvent() {
        brpApplicatieStartListener.onApplicationEvent(contextRefreshedEvent);
    }

    @Test
    public final void startGeenXmlWebApplicationContext() {
        Mockito.when(contextStartedEvent.getApplicationContext()).thenReturn(applicationContextEvent);
        brpApplicatieStartListener.onApplicationEvent(contextStartedEvent);
    }

    @Test
    public final void startContextStartMetSlash() {
        Mockito.when(servletContext.getServletContextName()).thenReturn("/servletcontextname");
        brpApplicatieStartListener.onApplicationEvent(contextStartedEvent);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void startExceptie() {
        Mockito.when(servletContext.getServletContextName()).thenThrow(ApplicationContextException.class);
        brpApplicatieStartListener.onApplicationEvent(contextStartedEvent);
    }
}
