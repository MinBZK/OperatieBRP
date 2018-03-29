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
 * Unittest voor {@link Zoekbereik} voor de methodes die niet door de
 * {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class ZoekbereikTest {
    @Test
    public void getOmschrijving() throws Exception {
        assertEquals("Zoekcriteria toepassen op materiële periode", Zoekbereik.MATERIELE_PERIODE.getOmschrijving());
    }

    @Test
    public void getByNaam() {
        try {
            Zoekbereik.getByNaam(null);
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }
        try {
            Zoekbereik.getByNaam("PipoDeClown");
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }

        Assert.assertEquals(Zoekbereik.PEILMOMENT, Zoekbereik.getByNaam("Peilmoment"));
        Assert.assertEquals(Zoekbereik.MATERIELE_PERIODE, Zoekbereik.getByNaam("Materiele periode"));

    }
}
