/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import org.junit.Assert;
import org.junit.Test;

/**
 * testcase voor AanduidingOuder entity inclusief de enumeratie ouder1 / ouder 2.
 */
public class Lo3AanduidingOuderTest {

    /**
     * testcase.
     */
    @Test
    public void testAanduidingOuder() {
        Betrokkenheid betrokkenOuder = new Betrokkenheid();
        Lo3AanduidingOuder ouder = new Lo3AanduidingOuder(AanduidingOuder.OUDER_1, betrokkenOuder);

        Assert.assertEquals("eerste ouder", (short) 1, ouder.getOuderAanduiding().getId());
        ouder = new Lo3AanduidingOuder(AanduidingOuder.OUDER_2, betrokkenOuder);
        Assert.assertEquals("tweede ouder", (short) 2, ouder.getOuderAanduiding().getId());

        ouder.setId(1L);
        Assert.assertNotNull(ouder.getOuder());
        Assert.assertEquals("id check, just set and read", new Long(1), ouder.getId());
    }

    /**
     * gooit IllegalArgument exceptie.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testillegalArgument() {
        Lo3AanduidingOuder ouder = new Lo3AanduidingOuder();
        ouder.getOuderAanduiding();
    }
}
