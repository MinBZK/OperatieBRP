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
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakPlaatsenAfnemersindicatieBerichtActionTest {

    private MaakPlaatsenAfnemersindicatieBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakPlaatsenAfnemersindicatieBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.TOEGANGLEVERINGSAUTORISATIEID_KEY, Integer.valueOf(5555));
        parameters.put(AfnemersIndicatieJbpmConstants.PLAATSEN_DIENSTID_KEY, Integer.valueOf(10));
        parameters.put(AfnemersIndicatieJbpmConstants.PERSOONID_KEY, Integer.valueOf(20));
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht("518010");
        parameters.put("input", berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final PlaatsAfnemersindicatieVerzoekBericht plaatsAfnemersindicatieVerzoekBericht =
                (PlaatsAfnemersindicatieVerzoekBericht) berichtenDao.leesBericht((Long) result.get("plaatsenAfnIndBericht"));

        Assert.assertNotNull(plaatsAfnemersindicatieVerzoekBericht);
        Assert.assertEquals(Integer.valueOf(5555), plaatsAfnemersindicatieVerzoekBericht.getToegangLeveringsautorisatieId());
        Assert.assertEquals(Integer.valueOf(10), plaatsAfnemersindicatieVerzoekBericht.getDienstId());
        Assert.assertEquals(Integer.valueOf(20), plaatsAfnemersindicatieVerzoekBericht.getPersoonId());
        Assert.assertEquals(ap01Bericht.getMessageId(), plaatsAfnemersindicatieVerzoekBericht.getReferentie());
    }
}
