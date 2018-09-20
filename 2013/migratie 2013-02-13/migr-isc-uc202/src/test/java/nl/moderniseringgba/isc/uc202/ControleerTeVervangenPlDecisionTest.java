/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;

import org.junit.Assert;
import org.junit.Test;

public class ControleerTeVervangenPlDecisionTest {

    private final ContrleerTeVervangenPLDecision subject = new ContrleerTeVervangenPLDecision();

    @Test
    public void testOk() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("plActie", SearchResultaatType.VERVANGEN);

        final String result = subject.execute(parameters);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testNok() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("plActie", SearchResultaatType.NEGEREN);

        final String result = subject.execute(parameters);
        Assert.assertEquals("4g, 18b. Geen te vervangen PL", result);
    }
}
