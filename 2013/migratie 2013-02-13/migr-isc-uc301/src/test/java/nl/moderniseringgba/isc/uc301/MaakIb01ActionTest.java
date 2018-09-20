/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;

import org.junit.Assert;
import org.junit.Test;

public class MaakIb01ActionTest {

    private final MaakIb01Action subject = new MaakIb01Action();

    @Test
    public void test() {
        // II01
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");

        // Lo3 PL
        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstBuilder().build();

        // Query response
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", lo3Persoonlijst);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Ib01Bericht ib01Bericht = (Ib01Bericht) result.get("ib01Bericht");
        Assert.assertNotNull(ib01Bericht);
        Assert.assertEquals(lo3Persoonlijst, ib01Bericht.getLo3Persoonslijst());
        Assert.assertEquals("5678", ib01Bericht.getBronGemeente());
        Assert.assertEquals("1234", ib01Bericht.getDoelGemeente());
        Assert.assertEquals(ii01Bericht.getMessageId(), ib01Bericht.getCorrelationId());
    }

    @Test
    public void testHerhaling() {
        // II01
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");

        // IB01
        final Ib01Bericht ib01 = new Ib01Bericht();

        // Lo3 PL
        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstBuilder().build();

        // Query response
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht("dummy_correlation_id", lo3Persoonlijst);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("leesUitBrpAntwoordBericht", queryResponse);
        parameters.put("ib01Bericht", ib01);
        parameters.put("ib01Herhaling", "3");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final Ib01Bericht ib01Bericht = (Ib01Bericht) result.get("ib01Bericht");
        Assert.assertNotNull(ib01Bericht);
        Assert.assertEquals(lo3Persoonlijst, ib01Bericht.getLo3Persoonslijst());
        Assert.assertEquals("5678", ib01Bericht.getBronGemeente());
        Assert.assertEquals("1234", ib01Bericht.getDoelGemeente());
        Assert.assertEquals(ii01Bericht.getMessageId(), ib01Bericht.getCorrelationId());
        Assert.assertEquals("3", ib01Bericht.getHeader(Lo3HeaderVeld.HERHALING));
        Assert.assertEquals(ib01.getMessageId(), ib01Bericht.getMessageId());
    }
}
