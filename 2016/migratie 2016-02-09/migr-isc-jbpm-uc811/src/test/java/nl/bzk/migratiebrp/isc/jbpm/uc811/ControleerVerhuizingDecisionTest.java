/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerVerhuizingDecisionTest {

    private ControleerVerhuizingDecision subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerVerhuizingDecision();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOk() {
        final BlokkeringInfoAntwoordBericht antwoord = new BlokkeringInfoAntwoordBericht();
        antwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testNok() {
        final BlokkeringInfoAntwoordBericht antwoord = new BlokkeringInfoAntwoordBericht();
        antwoord.setPersoonsaanduiding(null);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final String result = subject.execute(parameters);
        Assert.assertEquals("13e. Geen verhuizing", result);
    }
}
