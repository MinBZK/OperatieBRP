/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import org.springframework.util.Assert;

/**
 * De bericht parameters.
 */
public final class BerichtParameters {

    private SoortSynchronisatie soortSynchronisatie;
    private Dienst dienst;

    /**
     * @return de soortsynchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    /**
     * @return de dienst waarvoor geleverd wordt
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * De bericht parameters builder.
     */
    public static final class Builder {

        private BasisBerichtGegevens.Builder berichtBuilder;
        private SoortSynchronisatie soortSynchronisatie;
        private Dienst dienst;

        /**
         * Constructor.
         *
         * @param builder een bericht builder
         */
        public Builder(final BasisBerichtGegevens.Builder builder) {
            this.berichtBuilder = builder;
        }

        /**
         * Voeg de soort synchronisatie toe aan de builder.
         *
         * @param metSoortSynchronisatie de soort synchronisatie
         * @return de builder
         */
        public Builder metSoortSynchronisatie(final SoortSynchronisatie metSoortSynchronisatie) {
            this.soortSynchronisatie = metSoortSynchronisatie;
            return this;
        }

        /**
         * Voeg de dienst toe aan de builder.
         *
         * @param metDienst de metDienst
         * @return de builder
         */
        public Builder metDienst(final Dienst metDienst) {
            this.dienst = metDienst;
            return this;
        }

        /**
         * Sluit de builder af.
         *
         * @return de bericht builder
         */
        public BasisBerichtGegevens.Builder eindeParameters() {
            Assert.notNull(soortSynchronisatie, "SoortSynchronisatie moet gevuld zijn");
            Assert.notNull(dienst, "Dienst moet gevuld zijn");
            return berichtBuilder;
        }

        /**
         * Bouw de bericht parameters.
         *
         * @return de bericht parameters
         */
        public BerichtParameters build() {
            eindeParameters();
            final BerichtParameters parameters = new BerichtParameters();
            parameters.dienst = this.dienst;
            parameters.soortSynchronisatie = soortSynchronisatie;
            return parameters;
        }
    }

}
