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
 * Unittest voor {@link HistorieVorm} voor de methodes die niet door de
 * {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class HistorieVormTest {
    @Test
    public void getOmschrijving() throws Exception {
        assertEquals("Geen historie; alleen actuele gegevens leveren", HistorieVorm.GEEN.getOmschrijving());
    }

    @Test
    public void getByNaam() {
        try {
            HistorieVorm.getByNaam(null);
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }
        try {
            HistorieVorm.getByNaam("PipoDeClown");
            Assert.fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            // Ok
        }

        Assert.assertEquals(HistorieVorm.GEEN, HistorieVorm.getByNaam("Geen"));
        Assert.assertEquals(HistorieVorm.MATERIEEL_FORMEEL, HistorieVorm.getByNaam("MaterieelFormeel"));

    }
}
