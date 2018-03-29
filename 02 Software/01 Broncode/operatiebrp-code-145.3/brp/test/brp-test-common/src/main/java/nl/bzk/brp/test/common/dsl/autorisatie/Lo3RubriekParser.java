/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;

/**
 * Representatie van lo3rubriek in de dsl.
 */
final class Lo3RubriekParser {

    private final Function<Set<String>, List<Lo3Rubriek>> rubriekResolver;

    Lo3RubriekParser(
            final Function<Set<String>, List<Lo3Rubriek>> rubriekResolver) {
        this.rubriekResolver = rubriekResolver;
    }

    void parse(final Dienstbundel dienstbundel, final String rubriekString) {

        try (Scanner scanner = new Scanner(rubriekString)) {
            scanner.useDelimiter(",");

            final Set<String> rubriekStringList = Sets.newHashSet();
            while (scanner.hasNext()) {
                rubriekStringList.add(scanner.next().trim());
            }

            final List<Lo3Rubriek> rubriekList = rubriekResolver.apply(rubriekStringList);
            for (Lo3Rubriek lo3Rubriek : rubriekList) {
                final DienstbundelLo3Rubriek dienstbundelLo3Rubriek = new DienstbundelLo3Rubriek(dienstbundel, lo3Rubriek);
                dienstbundel.addDienstbundelLo3RubriekSet(dienstbundelLo3Rubriek);
            }
        }
    }
}
