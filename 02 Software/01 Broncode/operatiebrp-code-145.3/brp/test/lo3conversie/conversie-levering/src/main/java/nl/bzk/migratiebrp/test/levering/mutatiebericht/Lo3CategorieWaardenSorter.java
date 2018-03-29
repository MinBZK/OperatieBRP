/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Sortering voor Lo3 categorie waaarden.
 */
public final class Lo3CategorieWaardenSorter {

    private Lo3CategorieWaardenSorter() {
        // Niet instantieerbaar
    }

    /**
     * Sorteert de categorieen.
     *
     * @param categorieen
     *            De te sorteren categorieen.
     * @return De gesorteerde categorieen.
     */
    public static List<Lo3CategorieWaarde> sorteer(final List<Lo3CategorieWaarde> categorieen) {
        if (categorieen == null) {
            return null;
        }

        final List<Lo3CategorieWaarde> sorted = new ArrayList<>(categorieen.size());

        for (final Lo3CategorieWaarde categorie : categorieen) {
            sorted.add(new Lo3CategorieWaarde(
                categorie.getCategorie(),
                categorie.getStapel(),
                categorie.getVoorkomen(),
                new TreeMap<Lo3ElementEnum, String>(categorie.getElementen())));

        }

        Collections.sort(sorted, new Lo3CategorieWaardeComparator());

        return sorted;
    }

    /**
     * Lo3 categorie volgorde.
     */
    public static final class Lo3CategorieWaardeComparator implements Comparator<Lo3CategorieWaarde> {

        @Override
        public int compare(final Lo3CategorieWaarde one, final Lo3CategorieWaarde two) {
            int result = one.getCategorie().compareTo(two.getCategorie());
            if (result == 0) {
                result = Integer.compare(one.getStapel(), two.getStapel());
            }
            if (result == 0) {
                result = Integer.compare(one.getVoorkomen(), two.getVoorkomen());
            }
            return result;
        }
    }

}
