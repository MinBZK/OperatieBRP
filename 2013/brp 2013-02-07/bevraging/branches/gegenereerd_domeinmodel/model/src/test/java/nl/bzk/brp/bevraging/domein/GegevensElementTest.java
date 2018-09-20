/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;


/**
 * Unit test voor de {@link GegevensElement} class.
 */
public class GegevensElementTest {

    private BRMFactory brmFactory;

    @Test
    public void testConstructor() {
        GegevensElement gegevensElementOuder = new GegevensElement(null, "Test1");
        GegevensElement gegevensElement = new GegevensElement(gegevensElementOuder, "Test2");
        assertSame(gegevensElementOuder, gegevensElement.getOuder());
        assertEquals("Test2", gegevensElement.getJavaIdentifier());
    }

}
