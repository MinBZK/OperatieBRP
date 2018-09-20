/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service.bericht;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Test RelateerPersoonBerichtTest.
 */
public class RelateerPersoonBerichtTest {

    @Test
    public void testGetTeRelaterenPersoonIds() {
        assertEquals(Arrays.asList(1, 2, 3, 4), new RelateerPersoonBericht(Arrays.asList(1, 2, 3, 4)).getTeRelaterenPersoonIds());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeRelaterenPersoonIdsCreatieFoutNull() {
        new RelateerPersoonBericht(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeRelaterenPersoonIdsCreatieFoutLeeg() {
        new RelateerPersoonBericht(Collections.<Integer>emptyList());
    }

    @Test
    public void testJsonReadAndWrite() throws IOException {
        final RelateerPersoonBericht berichtIn = new RelateerPersoonBericht(Arrays.asList(1, 2, 3, 4));
        final String berichtAlsString = berichtIn.writeValueAsString();
        final RelateerPersoonBericht berichtUit = RelateerPersoonBericht.readValue(berichtAlsString);
        assertEquals(berichtIn.getTeRelaterenPersoonIds(), berichtUit.getTeRelaterenPersoonIds());
    }
}
