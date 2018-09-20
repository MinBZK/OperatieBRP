/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * Deze test test alleen 'negatieve' flows. De 'positieve' flow wordt in zijn geheel getest in BerichtLezerTest.
 */
public class AMSchrijverTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOriginator() throws IOException {
        new AMSchrijver(File.createTempFile("test", null)).schrijfStartRecord("12343");
    }
}
