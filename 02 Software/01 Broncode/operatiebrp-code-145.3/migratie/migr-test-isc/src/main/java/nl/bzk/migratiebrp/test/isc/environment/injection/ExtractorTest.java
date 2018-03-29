/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.io.File;
import java.io.IOException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Extractor voor test informatie.
 *
 * Ondersteund de keys: - testdir - expectdir - outputdir - testfile - expectfile - outputfile
 */
public class ExtractorTest implements Extractor {

    @Override
    public final String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        try {
            final String resultaat;

            switch (key) {
                case "testdir":
                    resultaat = bericht.getTestBericht().getInputFile().getParentFile().getCanonicalPath();
                    break;
                case "testfile":
                    resultaat = bericht.getTestBericht().getInputFile().getCanonicalPath();
                    break;
                case "expectdir":
                    resultaat = bericht.getTestBericht().getExpectedFile().getParentFile().getCanonicalPath();
                    break;
                case "expectfile":
                    resultaat = bericht.getTestBericht().getExpectedFile().getCanonicalPath();
                    break;
                case "outputdir":
                    resultaat = bericht.getTestBericht().getOutputFile().getParentFile().getCanonicalPath();
                    break;
                case "outputfile":
                    resultaat = bericht.getTestBericht().getOutputFile().getCanonicalPath();
                    break;
                case "fileseparator":
                    resultaat = File.separator;
                    break;
                default:
                    throw new TestException("Key '" + key + "' onbekend in header extractor.");
            }

            return resultaat;
        } catch (final IOException e) {
            throw new TestException("Onverwacht probleem", e);
        }
    }

}
