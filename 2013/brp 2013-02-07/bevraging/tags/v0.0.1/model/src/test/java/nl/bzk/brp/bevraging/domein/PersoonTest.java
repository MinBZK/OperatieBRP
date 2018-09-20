/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.bzk.brp.bevraging.domein;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author emalotau
 */
public class PersoonTest {

    /**
     * Test of toString method, of class Persoon.
     */
    @Test
    public void testToString() {
        Persoon persoon = new Persoon();
        persoon.setBsn(123456789);
        persoon.setGeslachtsnaam("Einstein");
        Assert.assertTrue(persoon.toString().startsWith("Persoon["));
        Assert.assertTrue(persoon.toString().contains("Einstein"));
        Assert.assertTrue(persoon.toString().contains("123456789"));
    }
}
