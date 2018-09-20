/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;

import org.junit.Test;

public class MaakNullActionTest {
    @Test
    public void testMaakNullAction() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tv01Bericht", new Tv01Bericht());
        parameters = new MaakNullAction().execute(parameters);
        assertNotNull(parameters.get("nullBericht")); // ietwat ironisch maar wel juist
    }
}
