/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StraatnaamGenerator {
    private static final Random RANDOM = new Random();

    private static final List<String> NAMEN = Arrays.asList("Kortestraat", "Langestraat", "Pimpelweg");

    public String genereer() {
        return NAMEN.get(RANDOM.nextInt(NAMEN.size()));
    }
}
