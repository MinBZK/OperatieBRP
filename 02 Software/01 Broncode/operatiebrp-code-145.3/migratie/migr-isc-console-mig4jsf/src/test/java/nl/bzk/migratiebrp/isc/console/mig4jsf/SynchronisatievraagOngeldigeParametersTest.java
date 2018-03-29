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

public class SynchronisatievraagOngeldigeParametersTest extends AbstractTagTest {

    private void setup(final String gemeente, final String anummer, final String bulk) throws Exception {
        addTagAttribute("gemeente", gemeente);
        addTagAttribute("aNummer", anummer);
        addTagAttribute("bulkBestand", bulk == null ? null : bulk.getBytes());
        addTagAttribute("target", null);

        setupDatabase(
                "/sql/mig-drop.sql",
                "/sql/jbpm-drop.sql",
                "/sql/jbpm-create.sql",
                "/sql/mig-create.sql",
                "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-berichten.sql");

        // Execute
        final JbpmActionListener subject = initializeSubject(SynchronisatievraagHandler.class);
        Assert.assertEquals("synchronisatievraag", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);
    }

    @Test
    public void testGeenParameters() throws Exception {
        setup(null, null, null);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testAlleenGemeente() throws Exception {
        setup("0599", null, null);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testOngeldigAnummer() throws Exception {
        setup("0599", "1231231233", null);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testAlleenAnummer() throws Exception {
        setup(null, "4398684193", null);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testGemeenteEnBulk() throws Exception {
        setup("0599", null, "0599,1231231232");

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testAnummerEnBulk() throws Exception {
        setup(null, "4398684193", "0599,1231231232");

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

    @Test
    public void testGemeenteEnAnummerEnBulk() throws Exception {
        setup("0599", "4398684193", "0599,1231231232");

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.atLeast(1)).setError(Matchers.anyString());
        Assert.assertEquals(null, getExpressionValues().get("target"));
    }

}
