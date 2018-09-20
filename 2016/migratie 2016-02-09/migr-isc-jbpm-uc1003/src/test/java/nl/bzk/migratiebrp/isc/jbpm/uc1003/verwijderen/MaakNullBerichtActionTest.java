/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakNullBerichtActionTest {

    private static final String A_NUMMER = "1234567890";
    private MaakNullBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakNullBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testMakenNullBericht() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", A_NUMMER);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final NullBericht nullBericht = (NullBericht) berichtenDao.leesBericht((Long) result.get("nullBericht"));

        Assert.assertNotNull(nullBericht);
        Assert.assertEquals(av01Bericht.getDoelGemeente(), nullBericht.getBronGemeente());
        Assert.assertEquals(av01Bericht.getBronGemeente(), nullBericht.getDoelGemeente());

        Assert.assertEquals(av01Bericht.getMessageId(), nullBericht.getCorrelationId());
        Assert.assertNotNull(nullBericht.getMessageId());
    }
}
