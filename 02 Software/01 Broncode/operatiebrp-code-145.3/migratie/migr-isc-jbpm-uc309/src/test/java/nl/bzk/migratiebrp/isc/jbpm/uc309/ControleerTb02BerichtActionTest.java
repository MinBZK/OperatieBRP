/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import org.junit.Assert;
import org.junit.Test;

public class ControleerTb02BerichtActionTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private ControleerTb02BerichtAction subject = new ControleerTb02BerichtAction(tb02Factory.getPartijService(), berichtenDao);

    @Test
    public void testExecuteMetCorrecteRelatieSluiting() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakSluitingTb02Bericht()));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue("Resultaatmap moet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetFouteBronPartijCode() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setBronPartijCode("8888");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull("Resultaat mag niet null zijn", result);
        Assert.assertEquals("Transitie naar fout flow verwacht", "2a. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals("Foutmelding incorrect", "Verzendende partij is geen GBA gemeente.", result.get("actieFoutmelding"));
    }

    @Test
    public void testExecuteMetFouteDoelPartijCode() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("8888");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull("Resultaat mag niet null zijn", result);
        Assert.assertEquals("Transitie naar fout flow verwacht", "2a. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals("Foutmelding incorrect", "Ontvangende partij is geen BRP gemeente.", result.get("actieFoutmelding"));
    }
}
