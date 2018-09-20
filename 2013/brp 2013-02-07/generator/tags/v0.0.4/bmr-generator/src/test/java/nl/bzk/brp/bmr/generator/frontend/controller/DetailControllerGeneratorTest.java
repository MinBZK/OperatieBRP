/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend.controller;

import junit.framework.Assert;
import nl.bzk.brp.bmr.generator.frontend.AbstractFrontEndTest;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.DetailControllerGenerator;
import org.junit.Test;


public class DetailControllerGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetStamGegevensVariabelNaam() {
        Assert.assertEquals("enum-Soort", DetailControllerGenerator.getStamGegevensVariabelNaam(getVeld(1)));
    }

    @Test
    public void testGetControllerMethodRequestMappingUrl() {
        Assert.assertEquals("/frame1.html", DetailControllerGenerator.getControllerMethodRequestMappingUrl(getFrame(0)));
    }

    @Test
    public void testGetRequestUrlVoorController() {
        Assert.assertEquals("/beheren/formulier1/frame1.html",
                DetailControllerGenerator.getRequestUrlVoorController(getFrame(0)));
    }

    @Test
    public void testGetControllerMethodRequestParamToevoegen() {
        Assert.assertEquals("_toevoegenObjectType-Partij",
                DetailControllerGenerator.getControllerMethodRequestParamToevoegen(getFrame(0)));
    }

    @Test
    public void testGetControllerMethodRequestParamVerwijderen() {
        Assert.assertEquals("_verwijderenObjectType-Partij",
                DetailControllerGenerator.getControllerMethodRequestParamVerwijderen(getFrame(0)));
    }
}
