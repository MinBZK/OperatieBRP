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
public class ElementWaardeTest {

    @Test
    public void testElementWaarde() {
        final Criterium criterium = new Criterium("element", new KVOperator(), null);
        final String verwacht = "KV(element)";
        final ElementWaarde subject = new ElementWaarde(criterium);
        Assert.assertEquals(verwacht, subject.getBrpExpressie());
        Assert.assertEquals(1, subject.getCriteria().size());
    }
}
