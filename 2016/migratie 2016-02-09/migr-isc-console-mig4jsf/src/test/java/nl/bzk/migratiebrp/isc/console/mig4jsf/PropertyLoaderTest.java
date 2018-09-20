/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class PropertyLoaderTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {

        final String property = "beheerapp.base.url";

        addTagAttribute("property", property);
        addTagAttribute("target", null);

        // Execute
        final JbpmActionListener subject = initializeSubject(PropertyLoaderHandler.class);
        Assert.assertEquals("propertyLoader", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        final String resultaat = (String) getExpressionValues().get("target");
        Assert.assertNotNull(resultaat);
        Assert.assertEquals("http://localhost:8280/administratievehandelingen", resultaat);
    }
}
