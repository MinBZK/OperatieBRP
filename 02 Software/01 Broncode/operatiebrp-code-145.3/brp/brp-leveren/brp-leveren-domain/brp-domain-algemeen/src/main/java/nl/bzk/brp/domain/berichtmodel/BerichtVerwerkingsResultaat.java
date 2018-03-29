/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;

/**
 * Class die het verwerkings resultaat van een bericht bevat.
 */
public final class BerichtVerwerkingsResultaat {
    private String verwerking;
    private String hoogsteMeldingsniveau;

    private BerichtVerwerkingsResultaat() {
    }

    /**
     * @return de verwerking.
     */
    public String getVerwerking() {
        return verwerking;
    }

    /**
     * @return het hoogste meldingniveau
     */
    public String getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BerichtVerwerkingsResultaat that = (BerichtVerwerkingsResultaat) o;
        return Objects.equals(verwerking, that.verwerking)
                && Objects.equals(hoogsteMeldingsniveau, that.hoogsteMeldingsniveau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verwerking, hoogsteMeldingsniveau);
    }

    /**
     * @return maakt een nieuwe builder
     */
    public static BerichtVerwerkingsResultaat.Builder builder() {
        return new Builder();
    }

    /**
     * Builder voor het berichtresultaat.
     */
    public static final class Builder {
        private BerichtVerwerkingsResultaat berichtResultaat = new BerichtVerwerkingsResultaat();

        private Builder() {
        }

        /**
         * Setter voor de verwerking.
         *
         * @param verwerking de verwerking
         * @return deze builder
         */
        public Builder metVerwerking(final VerwerkingsResultaat verwerking) {
            berichtResultaat.verwerking = verwerking.getNaam();
            return this;
        }

        /**
         * Setter voor het hoogste melding niveau.
         *
         * @param hoogsteMeldingsniveau het meldingsniveau
         * @return deze builder
         */
        public Builder metHoogsteMeldingsniveau(final String hoogsteMeldingsniveau) {
            berichtResultaat.hoogsteMeldingsniveau = hoogsteMeldingsniveau;
            return this;
        }

        /**
         * Bouwt het berichtresultaat.
         *
         * @return het resultaat object
         */
        public BerichtVerwerkingsResultaat build() {
            return berichtResultaat;
        }
    }
}
