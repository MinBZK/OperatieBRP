/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import junit.framework.Assert;
import nl.bzk.brp.bmr.generator.generators.frontend.TilesDefinitieGenerator;
import org.junit.Test;


public class TilesDefinitieGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetViewNaamVoorFrame() {
        Assert.assertEquals("beheren/formulier1/frame1", TilesDefinitieGenerator.getViewNaam(getFrame(0)));
    }

    @Test
    public void testGetViewNaamVoorFormulier() {
        Assert.assertEquals("beheren/formulier1/overzicht", TilesDefinitieGenerator.getViewNaam(getFormulier()));
    }
}
