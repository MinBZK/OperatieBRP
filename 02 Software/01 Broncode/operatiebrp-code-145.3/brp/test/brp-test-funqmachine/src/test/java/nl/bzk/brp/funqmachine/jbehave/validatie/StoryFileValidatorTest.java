/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Test;

public class StoryFileValidatorTest{

    @Test
    public void testValideStory() throws URISyntaxException {
        final URL valideStory = getClass().getResource("valide.story");
        assertTrue(new StoryFileValidator().valideer(valideStory));
    }

    @Test
    public void testNietValideStory () throws URISyntaxException {
        final URL nietValideStory = getClass().getResource("nietValide.story");
        assertFalse(new StoryFileValidator().valideer(nietValideStory));
    }

    @Test
    public void testNietBestaandeStory() {
        assertFalse(new StoryFileValidator().valideer(getClass().getResource("niet@%$#@Bestaand.story")));
    }
}
