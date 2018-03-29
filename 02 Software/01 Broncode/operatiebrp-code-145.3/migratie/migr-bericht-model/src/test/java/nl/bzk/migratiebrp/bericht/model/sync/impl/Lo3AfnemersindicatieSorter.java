/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;

public final class Lo3AfnemersindicatieSorter {

    private Lo3AfnemersindicatieSorter() {
        // Niet instantieerbaar
    }

    public static Lo3Afnemersindicatie sorteer(final Lo3Afnemersindicatie afnemersindicaties) {
        return new Lo3Afnemersindicatie(afnemersindicaties.getANummer(), sorteerAfnemersindicaties(afnemersindicaties.getAfnemersindicatieStapels()));
    }

    private static List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> sorteerAfnemersindicaties(
            final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels) {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> result = new ArrayList<>();

        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel : afnemersindicatieStapels) {
            result.add(sorteerStapel(afnemersindicatieStapel));
        }

        Collections.sort(result, new Lo3AfnemersindicatieStapelComparator());
        return result;
    }

    private static Lo3Stapel<Lo3AfnemersindicatieInhoud> sorteerStapel(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        return stapel;
    }

    public static class Lo3AfnemersindicatieStapelComparator implements Comparator<Lo3Stapel<Lo3AfnemersindicatieInhoud>> {

        @Override
        public int compare(final Lo3Stapel<Lo3AfnemersindicatieInhoud> o1, final Lo3Stapel<Lo3AfnemersindicatieInhoud> o2) {
            return getAfnemerindicatie(o1).compareTo(getAfnemerindicatie(o2));
        }

        private String getAfnemerindicatie(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
            for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
                final Lo3AfnemersindicatieInhoud inhoud = categorie.getInhoud();
                final String afnemersindicatie = inhoud.getAfnemersindicatie();
                if (afnemersindicatie != null) {
                    return afnemersindicatie;
                }
            }
            return null;
        }

    }
}
