/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import org.jbpm.graph.exe.ExecutionContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ControleerHerhalingenWa01DecisionTest {

    @Mock
    private ExecutionContext executionContext;

    @InjectMocks
    private ControleerHerhalingenWa01Decision subject;

    @Before
    public void setupExecutionContext() {
        ExecutionContext.pushCurrentContext(executionContext);
    }

    @After
    public void teardownExecutionContext() {
        ExecutionContext.popCurrentContext(executionContext);
    }

    @Test
    public void testMaximumNietBereikt() {
        Mockito.when(executionContext.getVariable("wa01Herhaling")).thenReturn(1);
        Mockito.when(executionContext.getVariable("wa01HerhalingMaxHerhalingen")).thenReturn(4);
        Assert.assertNull(subject.execute(null));
    }

    @Test
    public void testMaximumNietBereiktNull() {
        Mockito.when(executionContext.getVariable("wa01Herhaling")).thenReturn(null);
        Mockito.when(executionContext.getVariable("wa01HerhalingMaxHerhalingen")).thenReturn(4);
        Assert.assertNull(subject.execute(null));
    }

    @Test
    public void testMaximumBereikt() {
        Mockito.when(executionContext.getVariable("wa01Herhaling")).thenReturn(5);
        Mockito.when(executionContext.getVariable("wa01HerhalingMaxHerhalingen")).thenReturn(4);
        Assert.assertEquals("4a. Maximum herhalingen", subject.execute(null));
    }
}
