/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;

/**
 * Voert de matching op voorkomen-niveau uit. Matching gebeurt op basis van {@link Lo3Herkomst}.
 */
public final class VoorkomenMatcher {

    private VoorkomenMatcher() {
    }

    /**
     * Matcht de voorkomens op basis van hun {@link Lo3Herkomst}.
     * 
     * @param stapelMatch
     *            De match van stapels waarbij deze uniek zijn gematched
     * @param <T>
     *            moet een implementatie zijn van {@link Lo3CategorieInhoud}
     * @return een lijst van {@link nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch} objecten
     */
    public static <T extends Lo3CategorieInhoud> List<VoorkomenMatch<T>> matchVoorkomens(final StapelMatch<T> stapelMatch) {
        final List<VoorkomenMatch<T>> matching = new ArrayList<>();
        if (stapelMatch != null) {
            // Er is maar 1 stapel. Anders waren deze NON_UNIQUE_MATCHED
            final List<Lo3Categorie<T>> lo3Voorkomens = stapelMatch.getLo3Stapels().get(0).getCategorieen();
            final List<Lo3Categorie<T>> brpLo3Voorkomens = stapelMatch.getBrpLo3Stapels().get(0).getCategorieen();

            matchVoorkomens(lo3Voorkomens, matching, true);
            matchVoorkomens(brpLo3Voorkomens, matching, false);
        }
        return matching;
    }

    private static <T extends Lo3CategorieInhoud> void matchVoorkomens(
        final List<Lo3Categorie<T>> voorkomens,
        final List<VoorkomenMatch<T>> matching,
        final boolean isLo3Voorkomen)
    {
        for (int index = 0; index < voorkomens.size(); index++) {
            final Lo3Categorie<T> voorkomen = voorkomens.get(index);

            final Lo3Herkomst herkomst = voorkomen.getLo3Herkomst();
            VoorkomenMatch<T> voorkomenMatch = new VoorkomenMatch<>(herkomst);
            final int matchIndex = matching.indexOf(voorkomenMatch);
            if (matchIndex == -1) {
                // Nog geen voorkomen match voor deze herkomst
                matching.add(voorkomenMatch);
            } else {
                voorkomenMatch = matching.get(matchIndex);
            }

            if (isLo3Voorkomen) {
                voorkomenMatch.addLo3Voorkomen(voorkomen);
                voorkomenMatch.setIsLo3Actueel(herkomst.isLo3ActueelVoorkomen());
            } else {
                voorkomenMatch.addBrpLo3Voorkomen(voorkomen);
                final boolean isBerekendActueel = index == voorkomens.size() - 1;
                voorkomenMatch.setIsBrpLo3Actueel(herkomst.isLo3ActueelVoorkomen() && isBerekendActueel);
            }
        }
    }

}
