/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;

/**
 * Voert de matching op stapel-niveau uit. Matching gebeurt op basis van {@link Lo3Herkomst}.
 */
final class StapelMatcher {

    private StapelMatcher() {
    }

    private static <T extends Lo3CategorieInhoud> void matchStapels(final List<Lo3Stapel<T>> stapels, final List<StapelMatch<T>> matching,
                                                                    final boolean isLo3Stapel) {
        for (final Lo3Stapel<T> stapel : stapels) {
            // Altijd het eerste voorkomen uit de stapel pakken voor de herkomst.
            final Lo3Herkomst herkomst = stapel.get(0).getLo3Herkomst();

            // Nog geen stapel match voor deze herkomst
            StapelMatch<T> stapelMatch = new StapelMatch<>(herkomst);
            final int index = matching.indexOf(stapelMatch);
            if (index == -1) {
                matching.add(stapelMatch);
            } else {
                stapelMatch = matching.get(index);
            }
            if (isLo3Stapel) {
                stapelMatch.addLo3Stapel(stapel);
            } else {
                stapelMatch.addBrpLo3Stapel(stapel);
            }
        }
    }

    /**
     * Matcht de stapels op basis van hun {@link Lo3Herkomst}.
     * @param lo3Stapels de stapels van een bepaalde categorie uit de LO3 PL
     * @param brpLo3Stapels de stapels van bepaalde categorie uit de teruggeconverteerde BRP PL
     * @param <T> moet een implementatie zijn van {@link Lo3CategorieInhoud}
     * @return een lijst van {@link StapelMatch} objecten
     */
    static <T extends Lo3CategorieInhoud> List<StapelMatch<T>> matchStapels(final List<Lo3Stapel<T>> lo3Stapels, final List<Lo3Stapel<T>> brpLo3Stapels) {
        final List<StapelMatch<T>> matching = new ArrayList<>();
        if (lo3Stapels != null) {
            matchStapels(lo3Stapels, matching, true);
        }

        if (brpLo3Stapels != null) {
            matchStapels(brpLo3Stapels, matching, false);
        }

        return matching;
    }
}
