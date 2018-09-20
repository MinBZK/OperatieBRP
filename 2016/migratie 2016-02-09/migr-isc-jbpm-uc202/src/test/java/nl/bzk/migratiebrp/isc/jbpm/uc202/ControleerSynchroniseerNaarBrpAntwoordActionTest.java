/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerSynchroniseerNaarBrpAntwoordActionTest {

    private ControleerSynchroniseerNaarBrpAntwoordAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerSynchroniseerNaarBrpAntwoordAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testToegevoegd() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.TOEGEVOEGD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testVervangen() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.VERVANGEN);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testGenegeerd() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.GENEGEERD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("5f. Genegeerd", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testFout() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("5h. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testBerichtParseFout() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.AFGEKEURD);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("5g. Afgekeurd", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testBerichtOnduidelijk() {
        final SynchroniseerNaarBrpAntwoordBericht antwoord = new SynchroniseerNaarBrpAntwoordBericht();
        antwoord.setCorrelationId("1231564651");
        antwoord.setStatus(StatusType.ONDUIDELIJK);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("synchroniseerNaarBrpAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("5e. Onduidelijk", result.get(SpringActionHandler.TRANSITION_RESULT));
    }
}
