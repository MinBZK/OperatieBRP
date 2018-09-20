/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import nl.bzk.brp.bmr.generator.generators.frontend.JspGenerator;
import org.junit.Assert;
import org.junit.Test;

public class JspGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetAttribuutPadVanObjectVeld() {
        Assert.assertEquals("attribuut-Certificaat1.type-Certificaat1otAttribuut2", JspGenerator.getAttribuutPadVanObjectVeld(getVeld(2), 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAttribuutPadVanObjectVeldOngeldigeVeld() {
        JspGenerator.getAttribuutPadVanObjectVeld(getVeld(0), 1);
    }
}
