/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import nl.bzk.brp.bmr.generator.generators.frontend.MessagePropertiesGenerator;
import org.junit.Assert;
import org.junit.Test;

public class MessagePropertiesGeneratorTest extends AbstractFrontEndTest {

    @Test
    public void testGetEnumPrefix() {
        Assert.assertEquals("enum.attribuutType-Partij.", MessagePropertiesGenerator.getEnumPrefix(getVeld(0)));
    }

    @Test
    public void testGetMessagePropertieMenu() {
        Assert.assertEquals("menu.formulier1", MessagePropertiesGenerator.getMessagePropertieMenu(getFormulier()));
    }

    @Test
    public void testGetMessagePropertieTab() {
        Assert.assertEquals("tab.formulier1.frame1", MessagePropertiesGenerator.getMessagePropertieTab(getFrame(0)));
    }

    @Test
    public void testGetMessagePropertieEntity() {
        Assert.assertEquals("entity.ObjectType-Partij", MessagePropertiesGenerator.getMessagePropertieEntity(getFrame(0)));
    }

    @Test
    public void testGetMessagePropertieVeld() {
        Assert.assertEquals("veld.formulier1.frame1.Attribuut-Partij", MessagePropertiesGenerator.getMessagePropertieVeld(getVeld(0)));
    }
}
