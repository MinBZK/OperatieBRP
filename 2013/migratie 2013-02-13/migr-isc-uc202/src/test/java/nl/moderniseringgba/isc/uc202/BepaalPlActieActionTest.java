/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class BepaalPlActieActionTest {

    private final BepaalPlActieAction subject = new BepaalPlActieAction();

    @Test
    public void testToevoegen() {
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setResultaat(SearchResultaatType.TOEVOEGEN);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", searchResponse);
        parameters.put("beheerderKeuze", SearchResultaatType.NEGEREN);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SearchResultaatType.TOEVOEGEN, result.get("plActie"));
    }

    @Test
    public void testOnduidelijkMetKeuze() {
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setResultaat(SearchResultaatType.ONDUIDELIJK);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", searchResponse);
        parameters.put("beheerderKeuze", SearchResultaatType.NEGEREN);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SearchResultaatType.NEGEREN, result.get("plActie"));
    }

    @Test
    public void testOnduidelijkZonderKeuze() {
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setResultaat(SearchResultaatType.ONDUIDELIJK);

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("synchronisatieStrategieAntwoordBericht", searchResponse);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(null, result.get("plActie"));
    }
}
