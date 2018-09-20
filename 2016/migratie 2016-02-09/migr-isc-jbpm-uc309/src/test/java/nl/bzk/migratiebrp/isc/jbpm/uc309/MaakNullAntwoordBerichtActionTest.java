/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 */
public class MaakNullAntwoordBerichtActionTest {

    private MaakNullAntwoordBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setUp() throws Exception {
        subject = new MaakNullAntwoordBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testMaakNullBericht() throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        bericht.setBronGemeente("2222");
        bericht.setDoelGemeente("3333");
        bericht.setMessageId("12345");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(bericht));
        final Map<String, Object> result = subject.execute(parameters);
        assertFalse("Resultaat mag niet leeg zijn", result.isEmpty());
        final NullBericht nullBericht = (NullBericht) berichtenDao.leesBericht((Long) result.get("nullBericht"));
        assertEquals("Doelgemeente dient gelijk te zijn aan de brongemeente van het tb02 bericht", bericht.getBronGemeente(), nullBericht.getDoelGemeente());
        assertEquals("Brongemeente dient gelijk te zijn aan de doelgemeente van het tb02 bericht", bericht.getDoelGemeente(), nullBericht.getBronGemeente());
        assertEquals("Het correlatieId moet gelijk zijn aan het messageId van het tb02 bericht", bericht.getMessageId(), nullBericht.getCorrelationId());
    }
}