/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.Random;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;

public class HuisnummerGenerator {
    private static final Random RANDOM = new Random();

    public Lo3Huisnummer genereer() {
        return new Lo3Huisnummer(RANDOM.nextInt(9999) + 1);
    }
}
