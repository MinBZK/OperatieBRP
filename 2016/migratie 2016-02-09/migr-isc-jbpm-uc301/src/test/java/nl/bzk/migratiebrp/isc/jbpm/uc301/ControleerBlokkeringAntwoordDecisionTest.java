/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerBlokkeringAntwoordDecisionTest {

    private ControleerBlokkeringAntwoordDecision subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerBlokkeringAntwoordDecision();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOk() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.OK);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testFoutReedsGeblokkeerd() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.GEBLOKKEERD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals("6c. Geblokkeerd", subject.execute(parameters));
    }

    @Test
    public void testAndereFout() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals("6b. Fout", subject.execute(parameters));
    }

    @Test
    public void testWaarschuwing() {
        final BlokkeringAntwoordBericht antwoord = new BlokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.WAARSCHUWING);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("blokkeringAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals("6b. Fout", subject.execute(parameters));
    }
}
