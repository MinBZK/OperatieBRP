/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import org.junit.Test;

public class ScenarioResultTest {
    @Test
    public void geeftPathGoed() {
        ScenarioResult result = new ScenarioResult("foo");
        result.setStory("path/path2/filename.ext");
        assert result.getPath().equals("filename/foo");
    }

}
