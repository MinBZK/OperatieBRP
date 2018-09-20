/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import junit.framework.Assert;

import org.junit.Test;


public class GeneratorUtilsTest {

    @Test
    public void shouldNormalizeHighAsciiCharacters() {
        String test = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô";
        String result = GeneratorUtils.normalise(test);
        Assert.assertEquals('E', result.charAt(0));
        Assert.assertEquals("o", GeneratorUtils.normalise("ô"));
    }

    @Test
    public void shouldFilterNonValidJavaCharacters() {
        String input = "Categorie soort, actie?";
        String expected = "categorieSoortActie";

        Assert.assertEquals(expected, GeneratorUtils.toValidJavaVariableName(input));
    }

    @Test
    public void shouldTransformToCamelCase() {
        String input = "dit is een lowercase voorbeeld";
        String expected = "Dit Is Een Lowercase Voorbeeld";

        Assert.assertEquals(expected, GeneratorUtils.camelCase(input));
    }

    @Test
    public void shouldRemoveIllegalJavaCharacters() {
        String input = "Wat zou je, of jij doen?";
        String expected = "Wat zou je of jij doen";

        Assert.assertEquals(expected, GeneratorUtils.cleanUpInvalidJavaCharacters(input));
    }
}
