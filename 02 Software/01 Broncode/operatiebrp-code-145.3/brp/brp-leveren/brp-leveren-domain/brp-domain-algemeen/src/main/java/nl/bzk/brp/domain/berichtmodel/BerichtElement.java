/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BerichtElement.
 */
public final class BerichtElement {
    private String naam;
    private String waarde;
    private List<BerichtElement> elementen = new ArrayList<>();
    private List<BerichtElementAttribuut> attributen = new ArrayList<>();

    /**
     * @param naam naam
     */
    public BerichtElement(final String naam) {
        this.naam = naam;
    }

    /**
     * @param naam   naam
     * @param waarde waarde
     */
    public BerichtElement(final String naam, final String waarde) {
        this.naam = naam;
        this.waarde = waarde;
    }

    public String getNaam() {
        return naam;
    }

    public String getWaarde() {
        return waarde;
    }

    public List<BerichtElement> getElementen() {
        return elementen;
    }

    public List<BerichtElementAttribuut> getAttributen() {
        return attributen;
    }

    /**
     * @return builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * De bericht builder.
     */
    public static final class Builder {

        private String naam;
        private String waarde;
        private List<BerichtElement.Builder> elementen = new ArrayList<>();
        private List<BerichtElementAttribuut.Builder> attributen = new ArrayList<>();

        private Builder() {
        }

        /**
         * @param naamBuild naam
         * @return de builder.
         */
        public Builder metNaam(final String naamBuild) {
            this.naam = naamBuild;
            return this;
        }

        /**
         * @param berichtElementBuild berichtElement
         * @return de builder.
         */
        public Builder metBerichtElement(final BerichtElement.Builder berichtElementBuild) {
            this.elementen.add(berichtElementBuild);
            return this;
        }

        /**
         * @param berichtElementenBuild berichtElementen
         * @return de builder.
         */
        public Builder metBerichtElementen(final List<BerichtElement.Builder> berichtElementenBuild) {
            this.elementen.addAll(berichtElementenBuild);
            return this;
        }

        /**
         * @param waardeBuild waarde
         * @return de builder.
         */
        public Builder metWaarde(final String waardeBuild) {
            this.waarde = waardeBuild;
            return this;
        }

        /**
         * @param berichtElementAttribuutBuild berichtElementAttribuut
         * @return de builder.
         */
        public Builder metBerichtElementAttribuut(final BerichtElementAttribuut.Builder berichtElementAttribuutBuild) {
            this.attributen.add(berichtElementAttribuutBuild);
            return this;
        }


        /**
         * @return bericht element.o
         */
        public BerichtElement build() {
            final BerichtElement berichtElement = new BerichtElement(naam, waarde);
            final List<BerichtElement> elementenTemp = new ArrayList<>(elementen.size());
            for (Builder builder : elementen) {
                elementenTemp.add(builder.build());
            }
            final List<BerichtElementAttribuut> attributenTemp = new ArrayList<>(elementen.size());
            for (BerichtElementAttribuut.Builder builder : attributen) {
                attributenTemp.add(builder.build());
            }
            berichtElement.elementen = Collections.unmodifiableList(elementenTemp);
            berichtElement.attributen = Collections.unmodifiableList(attributenTemp);
            return berichtElement;
        }
    }

}
