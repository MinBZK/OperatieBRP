/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc808;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Het overzicht representeert de tabulaire data over de persoonslijsten.
 */
public final class PersoonslijstOverzicht implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<String> identificaties;
    private final List<Categorie> categorieen;

    /**
     * Constructor (voor builder).
     * @param identificaties identificaties
     * @param categorieen categorieen
     */
    PersoonslijstOverzicht(final List<String> identificaties, final List<Categorie> categorieen) {
        this.identificaties = identificaties;
        this.categorieen = categorieen;
    }

    /**
     * Geef het aantal PL-en.
     * @return aantal pl-en
     */
    public int getAantalPersoonslijsten() {
        return identificaties.size();
    }

    /**
     * Geef de waarde van identificaties.
     * @return identificaties
     */
    public List<String> getIdentificaties() {
        return identificaties;
    }

    /**
     * Geef de waarde van categorieen.
     * @return categorieen
     */
    public List<Categorie> getCategorieen() {
        return categorieen;
    }



    /**
     * Categorie data.
     */
    public static final class Categorie implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String titel;
        private final boolean historie;
        private final List<Element> elementen;
        private final List<Boolean> changedIndicators = new ArrayList<>();

        /**
         * Constructor (voor builder).
         * @param titel titel
         * @param historie is historie categorie
         * @param elementen elementen
         */
        Categorie(final String titel, final boolean historie, final List<Element> elementen) {
            this.titel = titel;
            this.historie = historie;
            this.elementen = elementen;

            if (!elementen.isEmpty()) {
                changedIndicators.addAll(elementen.get(0).getChangedIndicators());
                for (final Element element : this.elementen) {
                    verwerkElement(element);
                }
            }
        }

        private void verwerkElement(final Element element) {
            for (int i = 1; i < element.getChangedIndicators().size(); i++) {
                if (element.getChangedIndicators().get(i)) {
                    changedIndicators.set(i, true);
                }
            }
        }

        /**
         * Geef de waarde van titel.
         * @return titel
         */
        public String getTitel() {
            return titel;
        }

        /**
         * Geef de historie.
         * @return historie
         */
        public boolean isHistorie() {
            return historie;
        }

        /**
         * Geef de waarde van changed indicators.
         * @return changed indicators
         */
        public List<Boolean> getChangedIndicators() {
            return changedIndicators;
        }

        /**
         * Geef de waarde van elementen.
         * @return elementen
         */
        public List<Element> getElementen() {
            return elementen;
        }
    }

    /**
     * Element data.
     */
    public static final class Element implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String titel;
        private final List<String> waarden;
        private final List<Boolean> changedIndicators = new ArrayList<>();
        private final Boolean emptyIndicator;

        /**
         * Constructor (voor builder).
         * @param titel titel
         * @param waarden waarden
         */
        Element(final String titel, final List<String> waarden) {
            this.titel = titel;
            this.waarden = waarden;

            final String eersteWaarde = waarden.get(0);

            boolean empty = true;
            for (final String waarde : waarden) {
                changedIndicators.add(!isEquals(eersteWaarde, waarde));

                if (waarde != null && !"".equals(waarde)) {
                    empty = false;
                }
            }

            emptyIndicator = empty;
        }

        /**
         * Geef de waarde van titel.
         * @return titel
         */
        public String getTitel() {
            return titel;
        }

        /**
         * Geef de waarde van waarden.
         * @return waarden
         */
        public List<String> getWaarden() {
            return waarden;
        }

        /**
         * Geef de waarde van changed indicators.
         * @return changed indicators
         */
        public List<Boolean> getChangedIndicators() {
            return changedIndicators;
        }

        /**
         * Geef de waarde van empty indicator.
         * @return empty indicator
         */
        public Boolean getEmptyIndicator() {
            return emptyIndicator;
        }

        private static boolean isEquals(final Object first, final Object second) {
            if (first == null) {
                return second == null;
            } else {
                return first.equals(second);
            }
        }
    }
}
