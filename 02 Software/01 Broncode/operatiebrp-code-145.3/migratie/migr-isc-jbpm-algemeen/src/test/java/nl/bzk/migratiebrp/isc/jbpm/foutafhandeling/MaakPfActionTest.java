/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeInhoudBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakPfActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakPfAction subject = new MaakPfAction(berichtenDao);

    @Test
    public void testPf01() {
        final Lo3Bericht lo3Bericht = new OnbekendBericht("00000000Xx2100000", "onbekend");

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(lo3Bericht));

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Pf01Bericht pfBericht = (Pf01Bericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_PF));
        Assert.assertNotNull(pfBericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pfBericht.getCorrelationId());
    }

    @Test
    public void testOngeldigPf02() {
        final Lo3Bericht lo3Bericht = new OngeldigeSyntaxBericht("sdas", "ongeldig");

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(lo3Bericht));

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Pf02Bericht pfBericht = (Pf02Bericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_PF));
        Assert.assertNotNull(pfBericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pfBericht.getCorrelationId());
    }

    @Test
    public void testOngeldigPf03() {
        final Lo3Bericht lo3Bericht = new OngeldigeInhoudBericht("sdas", "ongeldig");

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(lo3Bericht));

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Pf03Bericht pfBericht = (Pf03Bericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_PF));
        Assert.assertNotNull(pfBericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pfBericht.getCorrelationId());
    }

    @Test
    public void testPf03() {
        final Lo3Bericht lo3Bericht = new NullBericht();

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_LO3, berichtenDao.bewaarBericht(lo3Bericht));

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final Pf03Bericht pf03Bericht = (Pf03Bericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_PF));
        Assert.assertNotNull(pf03Bericht);
        Assert.assertEquals(lo3Bericht.getMessageId(), pf03Bericht.getCorrelationId());
    }
}
