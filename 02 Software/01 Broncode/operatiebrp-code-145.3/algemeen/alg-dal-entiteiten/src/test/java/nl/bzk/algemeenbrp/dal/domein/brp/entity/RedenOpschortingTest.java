/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import org.junit.Test;

/**
 * Unittest voor {@link RedenOpschorting} voor de getters/setters die niet door {@link nl.bzk.algemeenbrp.dal.domein.EntityGetterSetterTest} wordt
 * ondervangen. Dit zijn de getters/setters waarbij er een enumeratie waarde wordt doorgegeven, maar de ID wordt opgeslagen.
 */
public class RedenOpschortingTest {

    private static final RedenOpschorting ENTITY = new RedenOpschorting();

    @Test
    public void testGetterSetterNadereBijhoudingsaard() {
        ENTITY.setRedenOpschorting(NadereBijhoudingsaard.EMIGRATIE);
        assertEquals(NadereBijhoudingsaard.EMIGRATIE, ENTITY.getRedenOpschorting());
    }

    @Test(expected = NullPointerException.class)
    public void testSetterNadereBijhoudingsaardNull(){
        ENTITY.setRedenOpschorting(null);
    }
}
