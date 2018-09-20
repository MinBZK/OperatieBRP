/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.installer.JbpmProcessInstaller;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class InstallProcessesActionTest {

    // Subject
    private InstallProcessesAction subject;

    // Dependencies
    private ConfigTree configTree;
    private JbpmProcessInstaller installer;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        installer = Mockito.mock(JbpmProcessInstaller.class);

        subject = new InstallProcessesAction(configTree);
        subject.setJbpmProcessInstaller(installer);
    }

    @Test
    public void testInitialise() {
        // Execute
        subject.initialise();

        // Verify
        Mockito.verify(installer).deployJbpmProcesses();
    }

    @Test
    public void testProcess() {
        // Expect
        final Message message = Mockito.mock(Message.class);

        // Execute
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(installer).deployJbpmProcesses();
    }

    @Test
    public void testNoOps() throws Exception {
        final Message message = Mockito.mock(Message.class);
        final Throwable throwable = Mockito.mock(Throwable.class);

        subject.processSuccess(message);
        subject.processException(message, throwable);
        subject.destroy();
    }

}
