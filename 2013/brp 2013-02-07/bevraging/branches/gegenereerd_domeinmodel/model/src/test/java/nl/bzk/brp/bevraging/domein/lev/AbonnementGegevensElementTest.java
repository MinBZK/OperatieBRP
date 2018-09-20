/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import static org.junit.Assert.assertSame;

import nl.bzk.brp.bevraging.domein.GegevensElement;
import org.junit.Test;


/**
 * Unit test voor de {@link AbonnementGegevensElement} class.
 */
public class AbonnementGegevensElementTest {

    @Test
    public void testConstructor() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        GegevensElement gegevensElement = new GegevensElement(null, "Test");
        AbonnementGegevensElement age = new AbonnementGegevensElement(abonnement, gegevensElement);
        assertSame(abonnement, age.getAbonnement());
        assertSame(gegevensElement, age.getGegevensElement());
    }

}
