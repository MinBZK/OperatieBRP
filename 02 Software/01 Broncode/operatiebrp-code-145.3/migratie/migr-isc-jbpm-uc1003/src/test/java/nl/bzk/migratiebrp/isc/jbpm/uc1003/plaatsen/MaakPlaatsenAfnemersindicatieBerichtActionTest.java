/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.junit.Assert;
import org.junit.Test;

public class MaakPlaatsenAfnemersindicatieBerichtActionTest {

    private static final String AFNEMER = "059901";
    private static final String BSN = "123456789";

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private MaakPlaatsenAfnemersindicatieBerichtAction subject = new MaakPlaatsenAfnemersindicatieBerichtAction(berichtenDao);

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.PERSOON_BSN, BSN);
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put("input", berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final PlaatsAfnemersindicatieVerzoekBericht plaatsAfnemersindicatieVerzoekBericht =
                (PlaatsAfnemersindicatieVerzoekBericht) berichtenDao.leesBericht((Long) result.get("plaatsenAfnIndBericht"));

        Assert.assertNotNull(plaatsAfnemersindicatieVerzoekBericht);
        Assert.assertEquals(AFNEMER, plaatsAfnemersindicatieVerzoekBericht.getPartijCode());
        Assert.assertEquals(BSN, plaatsAfnemersindicatieVerzoekBericht.getBsn());
        Assert.assertEquals(ap01Bericht.getMessageId(), plaatsAfnemersindicatieVerzoekBericht.getReferentie());
    }
}
