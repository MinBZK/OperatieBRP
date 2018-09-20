/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend.jsp;

import nl.bzk.brp.bmr.generator.frontend.AbstractFrontEndTest;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabInhoudGenerator;
import org.junit.Assert;
import org.junit.Test;


public class JspTabInhoudGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetJspTabInhoudFileNaam() {
        Assert.assertEquals("WEB-INF/jsp/beheren/formulier1/frame1.jsp",
                JspTabInhoudGenerator.getJspTabInhoudFileNaam(getFrame(0)));
    }
}
