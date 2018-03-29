/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Test van de VoorwaardeRegelNaarEnkelvoudigeVoorwaarde
 */
public class VoorwaardeRegelNaarEnkelvoudigeVoorwaardeTest {

    /**
     * Test of getVoorwaardeRegel method, of class VoorwaardeRegelNaarEnkelvoudigeVoorwaarde.
     */
    @Test
    public void testGetVoorwaardeRegel() {
        final String gbaVoorwaarde =
                "((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD (01.03.10 KDOG1 19.89.20 - 0054))";
        final VoorwaardeRegelNaarEnkelvoudigeVoorwaarde instance = new VoorwaardeRegelNaarEnkelvoudigeVoorwaarde(gbaVoorwaarde);
        final String expResult = "(K00 EN K01) OF (K02 EN K03)";
        final String result = instance.getVoorwaardeRegel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGbaVoorwaardeMap method, of class VoorwaardeRegelNaarEnkelvoudigeVoorwaarde.
     */
    @Test
    public void testGetGbaVoorwaardeMap() {
        final String gbaVoorwaarde =
                "((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD (01.03.10 KDOG1 19.89.20 - 0054))";
        final VoorwaardeRegelNaarEnkelvoudigeVoorwaarde instance = new VoorwaardeRegelNaarEnkelvoudigeVoorwaarde(gbaVoorwaarde);
        final Map<String, String> expResult = new HashMap<>();
        expResult.put("K00", "01.04.10 GA1 \"V\"");
        expResult.put("K01", "01.03.10 KDOG1 19.89.20 - 0029");
        expResult.put("K02", "01.04.10 GA1 \"M\"");
        expResult.put("K03", "01.03.10 KDOG1 19.89.20 - 0054");
        final Map<String, String> result = instance.getGbaVoorwaardeMap();
        assertEquals(expResult, result);
    }

    @Test
    public void testVoorwaardeRegelZonderEnOf() {
        final String gbaVoorwaarde = "01.03.10 KDOG1 18.59.30 - 0030";
        final VoorwaardeRegelNaarEnkelvoudigeVoorwaarde instance = new VoorwaardeRegelNaarEnkelvoudigeVoorwaarde(gbaVoorwaarde);
        final String expResult = "K00";
        final String result = instance.getVoorwaardeRegel();
        assertEquals(expResult, result);

        final Map<String, String> expMapResult = new HashMap<>();
        expMapResult.put("K00", "01.03.10 KDOG1 18.59.30 - 0030");
        assertEquals(expMapResult, instance.getGbaVoorwaardeMap());
    }
}
