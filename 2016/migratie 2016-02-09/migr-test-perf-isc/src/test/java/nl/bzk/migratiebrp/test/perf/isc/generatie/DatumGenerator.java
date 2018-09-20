/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.generatie;

import java.util.Random;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;

public class DatumGenerator {
    private static final Random RANDOM = new Random();

    public Lo3Datum genereer() {
        final int datum = (RANDOM.nextInt(80) + 1900) * 10000 + (RANDOM.nextInt(11) + 1) * 100 + RANDOM.nextInt(27) + 1;
        return new Lo3Datum(datum);

    }
}
