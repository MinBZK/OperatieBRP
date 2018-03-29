/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * BerichtElementAttribuut.
 */
public final class BerichtElementAttribuut {

    private String naam;
    private String waarde;

    /**
     * @param naam   naam
     * @param waarde waarde
     */
    public BerichtElementAttribuut(final String naam, final String waarde) {
        this.naam = naam;
        this.waarde = waarde;
    }

    public String getNaam() {
        return naam;
    }

    public String getWaarde() {
        return waarde;
    }

    /**
     * @return builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @param naam   naam
     * @param waarde waarde
     * @return builder/
     */
    public static Builder maakBuilder(final String naam, final String waarde) {
        return BerichtElementAttribuut.builder().metNaam(naam).metWaarde(waarde);
    }

    /**
     * De bericht builder.
     */
    public static final class Builder {

        private String naam;
        private String waarde;

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
         * @param waardeBuild waarde
         * @return de builder.
         */
        public Builder metWaarde(final String waardeBuild) {
            this.waarde = waardeBuild;
            return this;
        }

        /**
         * @return bericht element attribuut
         */
        public BerichtElementAttribuut build() {
            return new BerichtElementAttribuut(naam, waarde);
        }

        /**
         * @param naamBuild   naam
         * @param waardeBuild waarde
         * @return bericht element attribuut
         */
        public BerichtElementAttribuut build(final String naamBuild, final String waardeBuild) {
            return new BerichtElementAttribuut(naamBuild, waardeBuild);
        }
    }
}
