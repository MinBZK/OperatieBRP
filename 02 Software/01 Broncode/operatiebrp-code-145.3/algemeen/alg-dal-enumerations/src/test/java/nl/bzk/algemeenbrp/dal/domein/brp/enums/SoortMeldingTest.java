/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unittest voor {@link SoortMelding} voor de methodes die niet door de {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class SoortMeldingTest {
    @Test
    public void getOmschrijving() throws Exception {
        assertEquals("Geen meldingen aangetroffen", SoortMelding.GEEN.getOmschrijving());
    }

    @Test
    public void parseNaam() {
        assertEquals(SoortMelding.INFORMATIE, SoortMelding.parseNaam("Informatie"));
    }

    @Test
    public void testGetMeldingNiveau() {
        assertEquals(1, SoortMelding.GEEN.getMeldingNiveau());
        assertEquals(2, SoortMelding.INFORMATIE.getMeldingNiveau());
        assertEquals(3, SoortMelding.WAARSCHUWING.getMeldingNiveau());
        assertEquals(4, SoortMelding.DEBLOKKEERBAAR.getMeldingNiveau());
        assertEquals(5, SoortMelding.FOUT.getMeldingNiveau());
    }

}
