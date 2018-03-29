/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test.
 */
public class OfWaardeTest {

    @Test
    public void testOfWaarde() {
        final ElementWaarde elementWaarde1 = new ElementWaarde(new Criterium("element1", new KVOperator(), null));
        final ElementWaarde elementWaarde2 = new ElementWaarde(new Criterium("element2", new KVOperator(), null));
        final String verwacht = "(KV(element1) OF KV(element2))";
        final OfWaarde subject = new OfWaarde(elementWaarde1, elementWaarde2);
        Assert.assertEquals(verwacht, subject.getBrpExpressie());
        Assert.assertEquals(2, subject.getCriteria().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testGeenewxspressie(){
        final OfWaarde of = new OfWaarde();
        of.getBrpExpressie();
    }
}
