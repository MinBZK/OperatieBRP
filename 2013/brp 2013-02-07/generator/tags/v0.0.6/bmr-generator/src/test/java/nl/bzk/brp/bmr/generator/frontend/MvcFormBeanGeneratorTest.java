/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import junit.framework.Assert;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcFormBeanGenerator;
import org.junit.Test;


public class MvcFormBeanGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetAttribuutPad() {
        //pad vanuit de primaire frame
        Assert.assertEquals("objectType-Partij.attribuut-Partij", MvcFormBeanGenerator.getAttribuutPad(getVeld(0)));

        //pad voor secundaire frame
        Assert.assertEquals("objectType-Partij.objectType-Partijen", MvcFormBeanGenerator.getAttribuutPad(getFrame(1).getVelden().get(0)));
    }

    @Test
    public void testGetPadNaarToeTeVoegenObjectAttribuut() {
        Assert.assertEquals("toeTeVoegenObjectType-Partij.attribuut-Partij", MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(getVeld(0)));
    }

    @Test
    public void testGetPadNaarTeVerwijderenObjectAttribuut() {
        Assert.assertEquals("teVerwijderenObjectType-Partij", MvcFormBeanGenerator.getPadNaarTeVerwijderenObjectAttribuut(getFrame(0)));
    }

    @Test
    public void testGetGetterTeVerwijderenAttribuut() {
        Assert.assertEquals("getTeVerwijderenObjectType-Partij()", MvcFormBeanGenerator.getGetterTeVerwijderenAttribuut(getFrame(0)));
    }

    @Test
    public void testGetFormBeanNaam() {
        Assert.assertEquals("frame1FormBean", MvcFormBeanGenerator.getFormBeanNaam(getFrame(0)));
    }
}
