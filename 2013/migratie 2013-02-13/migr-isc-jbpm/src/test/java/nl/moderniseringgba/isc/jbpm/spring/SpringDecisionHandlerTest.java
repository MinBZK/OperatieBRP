/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("SpringDecisionHandlerTestBeans.xml")
public class SpringDecisionHandlerTest {

    private SpringDecisionHandler subject;

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private DummyDecision testDecision;

    @Before
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        subject = new SpringDecisionHandler();
        subject.setBean("testDecision");

        final Field applicationContextField = SpringHandler.class.getDeclaredField("applicationContext");
        applicationContextField.setAccessible(true);
        applicationContextField.set(null, applicationContext);
    }

    @Test
    public void test() throws Exception {
        testDecision.clearExecutedParameters();
        Assert.assertEquals(0, testDecision.getExecutedParameters().size());

        final ExecutionContext executionContext = Mockito.mock(ExecutionContext.class);
        final ContextInstance contextInstance = Mockito.mock(ContextInstance.class);
        final Map<String, Object> variables = new HashMap<String, Object>();

        Mockito.when(executionContext.getContextInstance()).thenReturn(contextInstance);
        Mockito.when(contextInstance.getVariables()).thenReturn(variables);

        Assert.assertEquals("testie", subject.decide(executionContext));

        Assert.assertEquals(1, testDecision.getExecutedParameters().size());
        Assert.assertSame(variables, testDecision.getExecutedParameters().get(0));

    }

    public static final class DummyDecision implements SpringDecision {

        private final List<Map<String, Object>> executedParameters = new ArrayList<Map<String, Object>>();

        @Override
        public String execute(final Map<String, Object> parameters) {
            executedParameters.add(parameters);

            return "testie";
        }

        public void clearExecutedParameters() {
            executedParameters.clear();
        }

        public List<Map<String, Object>> getExecutedParameters() {
            return executedParameters;
        }
    }
}
