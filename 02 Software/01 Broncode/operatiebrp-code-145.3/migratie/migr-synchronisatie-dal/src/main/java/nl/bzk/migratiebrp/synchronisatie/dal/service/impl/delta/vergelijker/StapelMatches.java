/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.StapelMatchType;

/**
 * Houdt de koppeling bij tussen gegevens.
 * @param <T> Type waar voor er stapel matches gemaakt worden.
 */
public final class StapelMatches<T> {
    private Map<T, List<T>> bestaandeStapelMatches = new HashMap<>();
    private Map<T, List<T>> nieuweStapelMatches = new HashMap<>();

    /**
     * Legt een koppeling vast tussen de bestaande en de nieuwe stapel. Als 1 van beide of allebei null zijn, dan wordt
     * er geen koppeling vast gelegd.
     * @param bestaandeStapel de bestaande stapel. Kan null zijn
     * @param nieuweStapel de nieuwe stapel. Kan null zijn
     */
    public void toevoegenMatch(final T bestaandeStapel, final T nieuweStapel) {
        toevoegen(bestaandeStapelMatches, bestaandeStapel, nieuweStapel);
        toevoegen(nieuweStapelMatches, nieuweStapel, bestaandeStapel);
    }

    private void toevoegen(final Map<T, List<T>> teGebruikenMap, final T basisStapel, final T matchStapel) {
        if (basisStapel == null) {
            return;
        }

        final List<T> matches;
        if (teGebruikenMap.containsKey(basisStapel)) {
            matches = teGebruikenMap.get(basisStapel);
        } else {
            matches = new ArrayList<>();
            teGebruikenMap.put(basisStapel, matches);
        }
        if (matchStapel != null) {
            matches.add(matchStapel);
        }
    }

    /**
     * Controleert of de nieuwe stapel al mogelijke matches heeft.
     * @param stapel de stapel waarvan gecontroleerd moet worden of deze al mogelijke matches heeft
     * @param isBestaandeStapel true als het de bestaande stapel betreft
     * @return true als de stapel al mogelijke matches heeft.
     */
    public boolean bevatKoppelingVoorStapel(final T stapel, final boolean isBestaandeStapel) {
        return isBestaandeStapel ? bestaandeStapelMatches.containsKey(stapel) : nieuweStapelMatches.containsKey(stapel);
    }

    /**
     * @return Geeft de bestaande stapels terug waarvoor een evt. koppeling is vastgelegd.
     */
    public Set<T> getBestaandeStapels() {
        return bestaandeStapelMatches.keySet();
    }

    /**
     * @param stapel de stapel waarvoor de matches worden terug gegeven.
     * @return Geeft de matches terug voor de bestaande stapel. Null als er geen matches zijn.
     */
    public List<T> getMatchesVoorBestaandeStapel(final T stapel) {
        return bestaandeStapelMatches.get(stapel);
    }

    /**
     * @return Geeft de nieuwe stapels terug waarvoor een evt. koppeling is vastgelegd.
     */
    public Set<T> getNieuweStapels() {
        return nieuweStapelMatches.keySet();
    }

    /**
     * @param stapel de stapel waarvoor de matches worden terug gegeven.
     * @return Geeft de matches terug voor de nieuwe stapel. Null als er geen matches zijn.
     */
    public List<T> getMatchesVoorNieuweStapel(final T stapel) {
        return nieuweStapelMatches.get(stapel);
    }

    /**
     * Bepaalt de {@link StapelMatchType} voor de meegegeven stapel. Als een stapel meer dan 1 match heeft of als de
     * matchende stapel meer dan 1 match heeft, is het type {@link StapelMatchType#NON_UNIQUE_MATCH}. Als er geen
     * matches zijn dan wordt het {@link StapelMatchType#STAPEL_NIEUW} als het een nieuwe stapel betrof, anders
     * {@link StapelMatchType#STAPEL_VERWIJDERD}. In het geval dat zowel de meegegeven stapel 1 match heeft als zijn
     * matchende stapel 1 match heeft, dan wordt het {@link StapelMatchType#MATCHED}.
     * @param stapel de stapel waarvoor het {@link StapelMatchType} bepaalt moet worden.
     * @param isBestaandeStapel true als het de bestaande stapel betreft
     * @return een waarde van {@link StapelMatchType}.
     */
    public StapelMatchType bepaalMatchType(final T stapel, final boolean isBestaandeStapel) {

        if (!bestaandeStapelMatches.containsKey(stapel) && !nieuweStapelMatches.containsKey(stapel)) {
            throw new IllegalStateException("Stapel niet geregistreerd voor matching");
        }

        final List<T> matches = isBestaandeStapel ? bestaandeStapelMatches.get(stapel) : nieuweStapelMatches.get(stapel);
        final StapelMatchType matchType;
        if (matches.isEmpty()) {
            matchType = isBestaandeStapel ? StapelMatchType.STAPEL_VERWIJDERD : StapelMatchType.STAPEL_NIEUW;
        } else if (matches.size() == 1) {
            final T matchStapel = matches.get(0);
            final List<T> matchingStapels = isBestaandeStapel ? nieuweStapelMatches.get(matchStapel) : bestaandeStapelMatches.get(matchStapel);
            matchType = matchingStapels.size() == 1 ? StapelMatchType.MATCHED : StapelMatchType.NON_UNIQUE_MATCH;
        } else {
            matchType = StapelMatchType.NON_UNIQUE_MATCH;
        }

        return matchType;
    }

    /**
     * Geeft de matchting stapel voor de meegegeven stapel terug. Of NULL als er geen matchende stapel is.
     * @param stapel de stapel waarvoor de matchende stapel opgevraagd wordt.
     * @param isBestaandeStapel true als het de bestaande stapel betreft
     * @return de matchende stapel
     * @throws IllegalStateException Als er meer dan 1 matchende stapel is gevonden
     */
    public T getMatchingStapel(final T stapel, final boolean isBestaandeStapel) {
        final List<T> matchingStapels = isBestaandeStapel ? bestaandeStapelMatches.get(stapel) : nieuweStapelMatches.get(stapel);
        T resultaat = null;
        if (matchingStapels != null) {
            if (matchingStapels.size() > 1) {
                throw new IllegalStateException("Meer dan 1 match gevonden.");
            } else if (matchingStapels.size() == 1) {
                resultaat = matchingStapels.get(0);
            }
        }
        return resultaat;
    }
}
