/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.junit.Assert;
import org.junit.Test;

public class MaakVerwijderenAfnemersindicatieBerichtActionTest {

    private static final String AFNEMER = "059901";
    private static final String BSN = "123456789";

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakVerwijderenAfnemersindicatieBerichtAction subject = new MaakVerwijderenAfnemersindicatieBerichtAction(berichtenDao);

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.PERSOON_BSN, BSN);
        Av01Bericht av01Bericht = new Av01Bericht();
        av01Bericht.setBronPartijCode(AFNEMER);
        Long input = berichtenDao.bewaarBericht(av01Bericht);
        parameters.put("input", input);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final VerwijderAfnemersindicatieVerzoekBericht verwijderAfnemersindicatieVerzoekBericht =
                (VerwijderAfnemersindicatieVerzoekBericht) berichtenDao.leesBericht((Long) result.get("verwijderenAfnIndBericht"));

        Assert.assertNotNull(verwijderAfnemersindicatieVerzoekBericht);
        Assert.assertEquals(AFNEMER, verwijderAfnemersindicatieVerzoekBericht.getPartijCode());
        Assert.assertEquals(BSN, verwijderAfnemersindicatieVerzoekBericht.getBsn());
    }
}
