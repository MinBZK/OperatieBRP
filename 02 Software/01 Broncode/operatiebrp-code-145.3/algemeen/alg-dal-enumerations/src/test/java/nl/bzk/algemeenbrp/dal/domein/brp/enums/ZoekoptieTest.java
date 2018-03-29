/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unittest voor {@link Zoekoptie} voor de methodes die niet door de
 * {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class ZoekoptieTest {

    @Test
    public void getOmschrijving() throws Exception {
        assertEquals("Zoeken na omzetting in kleine letters zonder diakritische tekens", Zoekoptie.KLEIN.getOmschrijving());
    }

    @Test
    public void getByNaam() {
        try {
            Zoekoptie.getByNaam(null);
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }
        try {
            Zoekoptie.getByNaam("PipoDeClown");
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }

        Assert.assertEquals(Zoekoptie.VANAF_KLEIN, Zoekoptie.getByNaam("Vanaf klein"));
        Assert.assertEquals(Zoekoptie.VANAF_EXACT, Zoekoptie.getByNaam("Vanaf exact"));
        Assert.assertEquals(Zoekoptie.EXACT, Zoekoptie.getByNaam("Exact"));

    }

}
