/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.verzender.VerzendendeInstantieDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BepaalVerzendendeInstantieActionTest {

    @Mock
    private VerzendendeInstantieDao verzendendeInstantieDao;

    @InjectMocks
    private BepaalVerzendendeInstantieAction subject;

    @Test
    public void testOk() {
        // Setup
        Mockito.when(verzendendeInstantieDao.bepaalVerzendendeInstantie(599)).thenReturn(3000200L);

        // Execute
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("doelGemeente", "0599");

        final Map<String, Object> result = subject.execute(parameters);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(result.get("bronGemeente"), "3000200");
    }

    @Test
    public void testNok() {
        // Setup
        Mockito.when(verzendendeInstantieDao.bepaalVerzendendeInstantie(599)).thenReturn(null);

        // Execute
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("doelGemeente", "0599");

        final Map<String, Object> result = subject.execute(parameters);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(result.get("transition"), "3b. Fout");
        Assert.assertEquals(result.get("actieFoutmelding"), "Geen verzendende instantie gevonden voor '0599'.");
    }
}
