/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Job;
import org.jbpm.job.SignalTokenJob;
import org.jbpm.msg.MessageService;
import org.jbpm.svc.Services;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;

public class JbpmSynchronisatievraagCommandTest {

    @Test
    public void test() {
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        // Process instance
        final ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
        Mockito.when(jbpmContext.newProcessInstance("uc899")).thenReturn(processInstance);
        Mockito.when(processInstance.getId()).thenReturn(5432L);
        final ContextInstance contextInstance = Mockito.mock(ContextInstance.class);
        Mockito.when(processInstance.getContextInstance()).thenReturn(contextInstance);
        final Token token = Mockito.mock(Token.class);
        Mockito.when(processInstance.getRootToken()).thenReturn(token);

        // Bericht
        final BerichtenDao berichtenDao = Mockito.mock(BerichtenDao.class);
        Mockito.when(
            berichtenDao.bewaar(
                Matchers.eq("ISC"),
                Matchers.eq(Direction.INKOMEND),
                Matchers.anyString(),
                (String) Matchers.isNull(),
                Matchers.eq("inhoud"),
                (String) Matchers.isNull(),
                Matchers.eq("doel"),
                (Long) Matchers.isNull())).thenReturn(1234L);

        // Services
        final Services services = Mockito.mock(Services.class);
        final MessageService messageService = Mockito.mock(MessageService.class);
        Mockito.when(jbpmContext.getServices()).thenReturn(services);
        Mockito.when(services.getMessageService()).thenReturn(messageService);

        final BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
        final SpringService springService = new SpringService(beanFactory);
        Mockito.when(services.getService(SpringServiceFactory.SERVICE_NAME)).thenReturn(springService);
        Mockito.when(beanFactory.getBean(BerichtenDao.class)).thenReturn(berichtenDao);

        // Execute
        final JbpmSynchronisatievraagCommand subject = new JbpmSynchronisatievraagCommand("uc899", "inhoud", "doel", "naam");
        subject.doInContext(jbpmContext);

        // Verify
        Mockito.verify(jbpmContext).newProcessInstance("uc899");
        Mockito.verify(berichtenDao).bewaar(
            Matchers.eq("ISC"),
            Matchers.eq(Direction.INKOMEND),
            Matchers.anyString(),
            (String) Matchers.isNull(),
            Matchers.eq("inhoud"),
            (String) Matchers.isNull(),
            Matchers.eq("doel"),
            (Long) Matchers.isNull());
        Mockito.verify(berichtenDao).updateNaam(1234L, "naam");
        Mockito.verify(berichtenDao).updateProcessInstance(1234L, 5432L);
        Mockito.verify(contextInstance).setVariable("input", 1234L);
        final ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
        Mockito.verify(messageService).send(jobCaptor.capture());
        final Job job = jobCaptor.getValue();
        Assert.assertTrue(job instanceof SignalTokenJob);
        Assert.assertEquals(token, job.getToken());
    }
}
