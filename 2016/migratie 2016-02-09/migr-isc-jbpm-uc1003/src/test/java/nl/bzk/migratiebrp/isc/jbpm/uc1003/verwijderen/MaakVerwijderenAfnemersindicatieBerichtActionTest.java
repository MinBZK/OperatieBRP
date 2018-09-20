/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakVerwijderenAfnemersindicatieBerichtActionTest {

    private static final String AFNEMER = "580001";
    private MaakVerwijderenAfnemersindicatieBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakVerwijderenAfnemersindicatieBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void test() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(AfnemersIndicatieJbpmConstants.TOEGANGLEVERINGSAUTORISATIEID_KEY, Integer.valueOf(666));
        parameters.put(AfnemersIndicatieJbpmConstants.VERWIJDEREN_DIENSTID_KEY, Integer.valueOf(69));
        parameters.put(AfnemersIndicatieJbpmConstants.PERSOONID_KEY, Integer.valueOf(20));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final VerwijderAfnemersindicatieVerzoekBericht verwijderAfnemersindicatieVerzoekBericht =
                (VerwijderAfnemersindicatieVerzoekBericht) berichtenDao.leesBericht((Long) result.get("verwijderenAfnIndBericht"));

        Assert.assertNotNull(verwijderAfnemersindicatieVerzoekBericht);
        Assert.assertEquals(Integer.valueOf(666), verwijderAfnemersindicatieVerzoekBericht.getToegangLeveringsautorisatieId());
        Assert.assertEquals(Integer.valueOf(69), verwijderAfnemersindicatieVerzoekBericht.getDienstId());
        Assert.assertEquals(Integer.valueOf(20), verwijderAfnemersindicatieVerzoekBericht.getPersoonId());
    }
}
