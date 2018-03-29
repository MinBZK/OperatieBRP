/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;

public class GezagGenerator {
    private static final Random RANDOM = new Random();

    private static final List<String> GEZAG = Arrays.asList("1", "2", "D", "1D", "2D", "12");

    public Lo3IndicatieGezagMinderjarige genereer() {
        return new Lo3IndicatieGezagMinderjarige(GEZAG.get(RANDOM.nextInt(GEZAG.size())));
    }
}
