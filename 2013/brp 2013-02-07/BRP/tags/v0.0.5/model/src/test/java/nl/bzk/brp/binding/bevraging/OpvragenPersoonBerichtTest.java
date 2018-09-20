/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Partij;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test class voor de {@link OpvragenPersoonBericht} class.
 */
public class OpvragenPersoonBerichtTest {

    @Test
    public void testPartijId() {
        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();

        Partij partij = null;
        ReflectionTestUtils.setField(bericht, "afzender", null);
        Assert.assertNull(bericht.getPartijId());

        partij = new Partij();
        partij.setId(Integer.valueOf(2));
        ReflectionTestUtils.setField(bericht, "afzender", partij);
        Assert.assertEquals(Integer.valueOf(2), bericht.getPartijId());
    }

}
