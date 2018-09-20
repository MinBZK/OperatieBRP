/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import junit.framework.Assert;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcControllerGenerator;
import org.junit.Test;


public class MvcControllerGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetControllerRequestMappingUrl() {
        Assert.assertEquals("/beheren/formulier1",
                MvcControllerGenerator.getControllerRequestMappingUrl(getFormulier()));
    }

    @Test
    public void getPackage() {
        Assert.assertEquals("nl.bzk.brp.beheer.web.controller.formulier1",
                MvcControllerGenerator.getPackage(getFormulier()));
    }

    @Test
    public void getPackageLokatie() {
        Assert.assertEquals("nl/bzk/brp/beheer/web/controller/formulier1",
                MvcControllerGenerator.getPackageLokatie(getFormulier()));
    }

    @Test
    public void getModelImports() {
        // Niet de hoofd frame
        // Zonder persistent classes
        Assert.assertEquals("import nl.bzk.brp.domein.schemanaam.ObjectType-Partij;\n",
                MvcControllerGenerator.getModelImports(getFrame(0)));

        // Hoofd frame
        Assert.assertEquals(
                "import nl.bzk.brp.domein.schemanaam.ObjectType-Bericht;\nimport nl.bzk.brp.domein.schemanaam.ObjectType-Partij;\n",
                MvcControllerGenerator.getModelImports(getFrame(1)));

    }
}
