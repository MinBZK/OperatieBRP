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

public class BepaalBeheerderkeuzeActionTest {

    private final BepaalBeheerderkeuzeAction subject = new BepaalBeheerderkeuzeAction();

    @Test
    public void testToevoegen() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("restart", "plToevoegen");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SearchResultaatType.TOEVOEGEN, result.get("beheerderKeuze"));
    }

    @Test
    public void testVervangen() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("restart", "plVervangen");

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(SearchResultaatType.VERVANGEN, result.get("beheerderKeuze"));
    }
}
