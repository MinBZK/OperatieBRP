/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;

/**
 * Class om matchende IST-stapels aan elkaar te koppelen en de onderlinge verschillen te kunnen borgen.
 */
public final class IstStapelMatch {

    private final StapelDecorator stapel;
    private List<StapelDecorator> matchingStapels = new ArrayList<>();
    private StapelMatchType type;

    /**
     * Constructor voor stapelmatching resultaat.
     * @param stapel stapel waar voor mogelijk matchende stapels zijn gevonden
     * @param matchingStapels stapels waarmee stapel een match heeft
     * @param matchType type van de match
     */
    public IstStapelMatch(final StapelDecorator stapel, final List<StapelDecorator> matchingStapels, final StapelMatchType matchType) {
        this.stapel = stapel;
        this.matchingStapels.addAll(matchingStapels);
        type = matchType;
    }

    /**
     * Geeft een instantie van {@link IstStapelMatchSorter} terug waarmee een set van {@link IstStapelMatch} gesorteerd
     * kan worden.
     * @return een nieuwe instantie van een Comparator specifiek voor {@link IstStapelMatch}
     */
    public static Comparator<IstStapelMatch> getSorteerder() {
        return new IstStapelMatchSorter();
    }

    /**
     * Veranderd het type naar {@link StapelMatchType#NON_UNIQUE_MATCH} als de andere stapel meer dan 1 matching stapel
     * heeft.
     */
    public void makeMatchNonUnique() {
        type = StapelMatchType.NON_UNIQUE_MATCH;
    }

    /**
     * Geeft de stapel.
     *
     * @return De stapel.
     */

    /**
     * Geeft de matchende stapels.
     * @return De matchende stapels.
     */
    public List<StapelDecorator> getMatchingStapels() {
        return matchingStapels;
    }

    /**
     * Geeft de matching stapel terug. Deze methode alleen gebruiken als het zeker is dat er maar 1 matching stapel is
     * (type = {@link StapelMatchType#MATCHED}).
     * @return de machting stapel of de eerste uit de lijst
     * @throws IllegalStateException Als aan het type = {@link StapelMatchType#MATCHED} niet voldaan wordt.
     */
    public StapelDecorator getMatchingStapel() {
        if (StapelMatchType.MATCHED.equals(type)) {
            return matchingStapels.iterator().next();
        }
        throw new IllegalStateException("StapelMatchType is niet gelijk aan MATCHED. Methode niet toegestaan");
    }

    /**
     * @return De stapel.
     */
    public StapelDecorator getStapel() {
        return stapel;
    }

    /**
     * Geeft het matchende type.
     * @return Het matchende type.
     */
    public StapelMatchType getMatchType() {
        return type;
    }

    /**
     * Sorteert de IstStapelMatch objecten op basis van categorienummer en daarbinnen op stapelnummer.
     */
    private static class IstStapelMatchSorter implements Comparator<IstStapelMatch>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final IstStapelMatch o1, final IstStapelMatch o2) {
            final StapelDecorator stapel1 = o1.getStapel();
            final StapelDecorator stapel2 = o2.getStapel();
            final int stapelCat1 = stapel1.getActueelCategorienummer();
            final int stapelCat2 = stapel2.getActueelCategorienummer();

            final int result;
            if (stapelCat1 == stapelCat2) {
                final int stapelNrCat1 = stapel1.getStapelNummer();
                final int stapelNrCat2 = stapel2.getStapelNummer();
                if (stapelNrCat1 == stapelNrCat2) {
                    result = o1.getMatchType().getSorteerVolgorde() - o2.getMatchType().getSorteerVolgorde();
                } else {
                    result = stapel1.getStapelNummer() - stapel2.getStapelNummer();
                }
            } else {
                result = stapelCat1 - stapelCat2;
            }
            return result;
        }
    }
}
