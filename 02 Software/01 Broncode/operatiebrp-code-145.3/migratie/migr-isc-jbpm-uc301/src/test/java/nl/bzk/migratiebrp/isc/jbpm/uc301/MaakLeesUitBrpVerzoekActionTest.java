/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class MaakLeesUitBrpVerzoekActionTest {

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakLeesUitBrpVerzoekAction subject = new MaakLeesUitBrpVerzoekAction(berichtenDao);

    @Test
    public void testOk() throws BerichtSyntaxException, BerichtInhoudException {
        final ZoekPersoonAntwoordBericht antwoord = new ZoekPersoonAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        antwoord.setPersoonId(1L);
        antwoord.setAnummer("8172387435");
        antwoord.setGemeente("1900");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoekPersoonBinnenGemeenteAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final LeesUitBrpVerzoekBericht queryBericht = (LeesUitBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("leesUitBrpVerzoekBericht"));
        Assert.assertNotNull(queryBericht);
        Assert.assertEquals("8172387435", queryBericht.getANummer());
    }
}
