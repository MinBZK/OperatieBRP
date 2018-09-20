/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerBlokkeringInfoAntwoordActionTest {

    private ControleerBlokkeringInfoAntwoordAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerBlokkeringInfoAntwoordAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOkLeeg() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(null);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(blokkeringInfoAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testOkVerhuizendNaarGba() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(blokkeringInfoAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testOkVerhuizendNaarRni() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(blokkeringInfoAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testNokVerhuizendNaarBrp() {
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setStatus(StatusType.OK);
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringInfoAntwoordBericht", berichtenDao.bewaarBericht(blokkeringInfoAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("8e. Fout: ongeldige blokkeringssituatie", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

}
