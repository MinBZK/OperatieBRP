/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerPlaatsenAfnemersindicatieActionTest {

    private ControleerPlaatsenAfnemersindicatieAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerPlaatsenAfnemersindicatieAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testGeplaatst() {
        final VerwerkAfnemersindicatieAntwoordBericht verwerkAfnIndAntwoord = PlaatsenAfnIndTestUtil.maakVerwerkAfnemersindicatieAntwoordBericht(null);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("plaatsenAfnemersindicatieAntwoordBericht", berichtenDao.bewaarBericht(verwerkAfnIndAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testNietGeplaatst() {
        final VerwerkAfnemersindicatieAntwoordBericht verwerkAfnIndAntwoord = PlaatsenAfnIndTestUtil.maakVerwerkAfnemersindicatieAntwoordBericht("H");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("plaatsenAfnemersindicatieAntwoordBericht", berichtenDao.bewaarBericht(verwerkAfnIndAntwoord));

        final Map<String, Object> result = subject.execute(parameters);

        Assert.assertEquals("7d. Niet geplaatst (beeindigen)", result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals("H", result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
    }

}
