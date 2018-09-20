/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

public class ReisdocumentGenerator {
    private static final Random RANDOM = new Random();

    private static final List<String> SOORT = Arrays.asList("NI", "NN", "PD", "PN");
    private static final List<String> AUTORITEIT = Arrays.asList(
        "B0518",
        "B0001",
        "B0002",
        "B0003",
        "B0004",
        "B0005",
        "B0006",
        "B0007",
        "B0008",
        "B0009");

    public Lo3SoortNederlandsReisdocument genereerSoort() {
        return new Lo3SoortNederlandsReisdocument(SOORT.get(RANDOM.nextInt(SOORT.size())));
    }

    public String genereerNummer() {
        return String.format("PAS%06d", RANDOM.nextInt(999999));
    }

    public Lo3AutoriteitVanAfgifteNederlandsReisdocument genereerAutoriteit() {
        return new Lo3AutoriteitVanAfgifteNederlandsReisdocument(AUTORITEIT.get(RANDOM.nextInt(AUTORITEIT.size())));
    }
}
