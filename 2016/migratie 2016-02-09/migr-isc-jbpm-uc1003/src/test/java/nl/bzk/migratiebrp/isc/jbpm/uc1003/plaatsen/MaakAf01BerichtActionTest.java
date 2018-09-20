/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakAf01BerichtActionTest {

    private MaakAf01BerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakAf01BerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testFoutredenX() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht("580001", 1234567890L, 123456789, "Jansen", "1234AA");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X);

        parameters.put("input", berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Af01Bericht af01Bericht = (Af01Bericht) berichtenDao.leesBericht((Long) result.get("af01Bericht"));

        Assert.assertNotNull(af01Bericht);
        Assert.assertEquals(ap01Bericht.getCategorieen(), af01Bericht.getCategorieen());
        Assert.assertEquals(ap01Bericht.getDoelGemeente(), af01Bericht.getBronGemeente());
        Assert.assertEquals(ap01Bericht.getBronGemeente(), af01Bericht.getDoelGemeente());

        Assert.assertEquals(ap01Bericht.getMessageId(), af01Bericht.getCorrelationId());
        Assert.assertNotNull(af01Bericht.getMessageId());

        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X, af01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    public void testFoutredenB() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht("580001", 1234567890L, 123456789, "Jansen", "1234AA");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_B);
        parameters.put(AfnemersIndicatieJbpmConstants.AF0X_GEMEENTE_KEY, "0519");
        parameters.put(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY, "1234567890");

        parameters.put("input", berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Af01Bericht af01Bericht = (Af01Bericht) berichtenDao.leesBericht((Long) result.get("af01Bericht"));

        Assert.assertNotNull(af01Bericht);
        Assert.assertEquals(ap01Bericht.getCategorieen(), af01Bericht.getCategorieen());
        Assert.assertEquals(ap01Bericht.getDoelGemeente(), af01Bericht.getBronGemeente());
        Assert.assertEquals(ap01Bericht.getBronGemeente(), af01Bericht.getDoelGemeente());

        Assert.assertEquals(ap01Bericht.getMessageId(), af01Bericht.getCorrelationId());
        Assert.assertNotNull(af01Bericht.getMessageId());

        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_B, af01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0519", af01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("1234567890", af01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }
}
