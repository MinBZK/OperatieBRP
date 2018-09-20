/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.testutils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;

/**
 * Stapel utils.
 */
public class StapelUtils {

    private static final Comparator<Lo3Categorie<?>> LO3_CATEGORIE_COMPARATOR = new Lo3CategorieComparator();

    private StapelUtils() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static <T extends Lo3CategorieInhoud> Lo3Stapel<T> createStapel(final List<Lo3Categorie<T>> categorieen) {
        Collections.sort(categorieen, LO3_CATEGORIE_COMPARATOR);
        return new Lo3Stapel<T>(categorieen);
    }

    private static class Lo3CategorieComparator implements Comparator<Lo3Categorie<?>> {

        @Override
        public int compare(final Lo3Categorie<?> categorie1, final Lo3Categorie<?> categorie2) {
            final Lo3Historie historie1 = categorie1.getHistorie();
            final Lo3Historie historie2 = categorie2.getHistorie();

            return historie1.getDatumVanOpneming().compareTo(historie2.getDatumVanOpneming());
        }
    }
}
