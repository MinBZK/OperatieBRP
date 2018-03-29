/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unittest voor {@link VerwerkingsResultaat} voor de methodes die niet door de {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class VerwerkingsResultaatTest {
    @Test
    public void getOmschrijving() throws Exception {
        assertEquals("Verwerking geslaagd", VerwerkingsResultaat.GESLAAGD.getOmschrijving());
    }

    @Test
    public void parseNaam() throws Exception {
        assertEquals(VerwerkingsResultaat.GESLAAGD, VerwerkingsResultaat.parseNaam("Geslaagd"));
    }
}
