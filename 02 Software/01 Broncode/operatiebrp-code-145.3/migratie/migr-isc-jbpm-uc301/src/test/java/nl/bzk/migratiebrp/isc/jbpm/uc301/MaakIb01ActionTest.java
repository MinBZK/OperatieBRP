/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ib01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakIb01ActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakIb01Action subject = new MaakIb01Action(berichtenDao);

    @Test
    public void test() {
        // II01
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("1234");
        ii01Bericht.setDoelPartijCode("5678");

        // Lo3 PL
        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstBuilder().build();

        // Query response
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", lo3Persoonlijst);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Ib01Bericht ib01Bericht = (Ib01Bericht) berichtenDao.leesBericht((Long) result.get("ib01Bericht"));
        Assert.assertNotNull(ib01Bericht);
        Assert.assertEquals(lo3Persoonlijst, ib01Bericht.getLo3Persoonslijst());
        Assert.assertEquals("5678", ib01Bericht.getBronPartijCode());
        Assert.assertEquals("1234", ib01Bericht.getDoelPartijCode());
        Assert.assertEquals(ii01Bericht.getMessageId(), ib01Bericht.getCorrelationId());
    }

    @Test
    public void testHerhaling() {
        // II01
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("1234");
        ii01Bericht.setDoelPartijCode("5678");

        // IB01
        final Ib01Bericht ib01 = new Ib01Bericht();

        // Lo3 PL
        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstBuilder().build();

        // Query response
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", lo3Persoonlijst);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));
        parameters.put("ib01Bericht", berichtenDao.bewaarBericht(ib01));
        parameters.put("ib01Herhaling", "3");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Ib01Bericht ib01Bericht = (Ib01Bericht) berichtenDao.leesBericht((Long) result.get("ib01Bericht"));
        Assert.assertNotNull(ib01Bericht);
        Assert.assertEquals(lo3Persoonlijst, ib01Bericht.getLo3Persoonslijst());
        Assert.assertEquals("5678", ib01Bericht.getBronPartijCode());
        Assert.assertEquals("1234", ib01Bericht.getDoelPartijCode());
        Assert.assertEquals(ii01Bericht.getMessageId(), ib01Bericht.getCorrelationId());
        Assert.assertEquals("3", ib01Bericht.getHeaderWaarde(Lo3HeaderVeld.HERHALING));
        Assert.assertEquals(ib01.getMessageId(), ib01Bericht.getMessageId());
    }
}
