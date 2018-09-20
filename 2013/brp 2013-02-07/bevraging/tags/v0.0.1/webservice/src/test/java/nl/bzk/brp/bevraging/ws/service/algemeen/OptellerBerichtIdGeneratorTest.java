/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.algemeen;

import static org.junit.Assert.assertEquals;

import nl.bzk.brp.bevraging.ws.service.BerichtIdGenerator;

import org.junit.Test;


/**
 * Unit test voor de {@link OptellerBerichtIdGenerator} class.
 */
public class OptellerBerichtIdGeneratorTest {

    /**
     * Unit test voor de {@link OptellerBerichtIdGenerator#volgendeId()} methode.
     */
    @Test
    public final void testVolgendeId() {
        BerichtIdGenerator generator = new OptellerBerichtIdGenerator();

        assertEquals("Onverwachte id gegenereerd", 0, generator.volgendeId());
        assertEquals("Onverwachte id gegenereerd", 1, generator.volgendeId());
        assertEquals("Onverwachte id gegenereerd", 2, generator.volgendeId());
        assertEquals("Onverwachte id gegenereerd", 3, generator.volgendeId());
    }

}
