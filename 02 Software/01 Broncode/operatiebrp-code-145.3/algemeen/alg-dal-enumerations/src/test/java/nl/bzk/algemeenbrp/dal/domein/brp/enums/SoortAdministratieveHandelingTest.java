/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.algemeenbrp.dal.domein.EnumeratieTest;
import org.junit.Test;

/**
 * Uitbreiding van {@link EnumeratieTest} voor {@link SoortAdministratieveHandeling}.
 */
public class SoortAdministratieveHandelingTest {

    @Test
    public void testGetCategorie() {
        assertEquals(CategorieAdministratieveHandeling.ACTUALISERING, SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getCategorie());
    }

    @Test
    public void testGetModule() {
        assertEquals(BurgerzakenModule.ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getModule());
    }

    @Test
    public void testGetAlias() {
        assertNull(SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getAlias());
        assertEquals(SoortAdministratieveHandeling.GEBOORTE, SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND.getAlias());
    }

    @Test
    public void testGetKoppelvlak() {
        assertEquals(Koppelvlak.BIJHOUDING, SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getKoppelvlak());
    }
}
