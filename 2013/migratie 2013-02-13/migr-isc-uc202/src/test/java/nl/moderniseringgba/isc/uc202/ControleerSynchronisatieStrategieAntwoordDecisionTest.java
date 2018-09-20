/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerSynchronisatieStrategieAntwoordDecisionTest {

    private final ControleerSynchronisatieStrategieAntwoordDecision subject = new ControleerSynchronisatieStrategieAntwoordDecision();

    @Test
    public void testOk() {
        final SynchronisatieStrategieAntwoordBericht antwoord = new SynchronisatieStrategieAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(SearchResultaatType.VERVANGEN);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", antwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testNokFout() {
        final SynchronisatieStrategieAntwoordBericht antwoord = new SynchronisatieStrategieAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", antwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals("4b. Fout", result);
    }

    @Test
    public void testNokNegeren() {
        final SynchronisatieStrategieAntwoordBericht antwoord = new SynchronisatieStrategieAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(SearchResultaatType.NEGEREN);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", antwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals("4e. Negeren", result);
    }

    @Test
    public void testNokOnduidelijk() {
        final SynchronisatieStrategieAntwoordBericht antwoord = new SynchronisatieStrategieAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(SearchResultaatType.ONDUIDELIJK);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", antwoord);

        final String result = subject.execute(parameters);
        Assert.assertEquals("4c. Onduidelijke situatie", result);
    }

    @Test
    public void testOkOnduidelijk() {
        final SynchronisatieStrategieAntwoordBericht antwoord = new SynchronisatieStrategieAntwoordBericht();
        antwoord.setStatus(StatusType.OK);
        antwoord.setResultaat(SearchResultaatType.ONDUIDELIJK);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", antwoord);
        parameters.put("beheerderKeuze", SearchResultaatType.VERVANGEN);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }
}
