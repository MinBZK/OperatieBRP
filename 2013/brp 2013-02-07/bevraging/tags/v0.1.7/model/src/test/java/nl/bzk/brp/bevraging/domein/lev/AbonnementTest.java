/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.domein.GegevensElement;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class AbonnementTest {

    @Test
    public void testGetGegevensElementen() {
        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        AbonnementGegevensElement abonnementGegevensElement = new AbonnementGegevensElement(abonnement, GegevensElement.VOORNAMEN);
        Set<AbonnementGegevensElement> abonnementGegevensElementen = new HashSet<AbonnementGegevensElement>();
        abonnementGegevensElementen.add(abonnementGegevensElement);
        ReflectionTestUtils.setField(abonnement, "abonnementGegevensElementen", abonnementGegevensElementen);

        Assert.assertEquals(1, abonnement.getGegevensElementen().size());
        Assert.assertSame(GegevensElement.VOORNAMEN, abonnement.getGegevensElementen().iterator().next());
    }

}
