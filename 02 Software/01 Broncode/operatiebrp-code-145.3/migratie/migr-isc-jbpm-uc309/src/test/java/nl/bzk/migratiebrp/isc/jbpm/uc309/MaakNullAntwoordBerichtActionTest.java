/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Test;

/**
 */
public class MaakNullAntwoordBerichtActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakNullAntwoordBerichtAction subject = new MaakNullAntwoordBerichtAction(berichtenDao);

    @Test
    public void testMaakNullBericht() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        bericht.setBronPartijCode("2222");
        bericht.setDoelPartijCode("3333");
        bericht.setMessageId("12345");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(bericht));
        final Map<String, Object> result = subject.execute(parameters);
        assertFalse("Resultaat mag niet leeg zijn", result.isEmpty());
        final NullBericht nullBericht = (NullBericht) berichtenDao.leesBericht((Long) result.get("nullBericht"));
        assertEquals("DoelPartijCode dient gelijk te zijn aan de bronPartijCode van het tb02 bericht", bericht.getBronPartijCode(), nullBericht.getDoelPartijCode());
        assertEquals("BronPartijCode dient gelijk te zijn aan de doelPartijCode van het tb02 bericht", bericht.getDoelPartijCode(), nullBericht.getBronPartijCode());
        assertEquals("Het correlatieId moet gelijk zijn aan het messageId van het tb02 bericht", bericht.getMessageId(), nullBericht.getCorrelationId());
    }
}
