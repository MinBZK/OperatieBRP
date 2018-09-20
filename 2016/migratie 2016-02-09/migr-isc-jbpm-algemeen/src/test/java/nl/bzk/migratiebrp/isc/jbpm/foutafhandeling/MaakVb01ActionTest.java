/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakVb01ActionTest {
    private MaakVb01Action subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakVb01Action();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void test() {

        final Lo3Bericht pf03Bericht = new Pf03Bericht();
        pf03Bericht.setBronGemeente("0399");
        pf03Bericht.setDoelGemeente("0499");

        final String fout = "U";
        final String foutmelding = "esb.proces.statusFout";

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_PF, berichtenDao.bewaarBericht(pf03Bericht));
        variabelen.put(FoutafhandelingConstants.FOUT, fout);
        variabelen.put(FoutafhandelingConstants.FOUTMELDING, foutmelding);

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Vb01Bericht vb01Bericht = (Vb01Bericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_VB01));
        Assert.assertNotNull(vb01Bericht);
        Assert.assertNull("Correlatie is niet leeg", vb01Bericht.getCorrelationId());
        Assert.assertEquals("Brongemeente komt niet overeen", pf03Bericht.getBronGemeente(), vb01Bericht.getBronGemeente());
        Assert.assertEquals("Doelgemeente komt niet overeen", pf03Bericht.getDoelGemeente(), vb01Bericht.getDoelGemeente());
    }

    @Test(expected = IllegalStateException.class)
    public void testGeenBericht() {
        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.FOUT, "U");
        variabelen.put(FoutafhandelingConstants.FOUTMELDING, "De gezochte PL is niet uniek. Controleer de zoekparameters.");
        subject.execute(variabelen);

        Assert.fail("Exception should be thrown");
    }
}
