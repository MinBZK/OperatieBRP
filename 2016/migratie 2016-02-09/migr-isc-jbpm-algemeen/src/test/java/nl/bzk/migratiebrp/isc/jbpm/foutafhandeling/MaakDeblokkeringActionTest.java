/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakDeblokkeringActionTest {
    private MaakDeblokkeringAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakDeblokkeringAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void test() {
        final BlokkeringVerzoekBericht blokkering = new BlokkeringVerzoekBericht();
        blokkering.setANummer("2349634257934");

        final Map<String, Object> variabelen = new HashMap<>();
        variabelen.put(FoutafhandelingConstants.BERICHT_BLOKKERING, berichtenDao.bewaarBericht(blokkering));

        final Map<String, Object> result = subject.execute(variabelen);
        Assert.assertEquals(1, result.size());
        final DeblokkeringVerzoekBericht deblokkering =
                (DeblokkeringVerzoekBericht) berichtenDao.leesBericht((Long) result.get(FoutafhandelingConstants.BERICHT_VERZOEK_DEBLOKKERING));
        Assert.assertNotNull(deblokkering);
        Assert.assertEquals(blokkering.getMessageId(), deblokkering.getCorrelationId());
        Assert.assertEquals(blokkering.getANummer(), deblokkering.getANummer());
    }
}
