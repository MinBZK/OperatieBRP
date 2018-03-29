/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.ZonedDateTime;

/**
 * Representeert een afnemerindicatie element binnen een onderhoud afnemerindicatie responsebericht.
 */
public final class BerichtAfnemerindicatie {

    private ZonedDateTime tijdstipRegistratie;
    private String partijCode;
    private String bsn;

    private BerichtAfnemerindicatie() {
    }

    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public String getPartijCode() {
        return partijCode;
    }

    public String getBsn() {
        return bsn;
    }

    /**
     * @return een nieuwe {@link BerichtAfnemerindicatie}.{@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder om {@link BerichtAfnemerindicatie} mee te construeren.
     */
    public static final class Builder {
        private ZonedDateTime tijdstipRegistratie;
        private String partijCode;
        private String bsn;

        /**
         * Voeg een tijdstip registratie toe aan de builder.
         *
         * @param metTijdstipRegistratie de tijdstip registratie
         * @return de builder
         */
        public Builder metTijdstipRegistratie(final ZonedDateTime metTijdstipRegistratie) {
            this.tijdstipRegistratie = metTijdstipRegistratie;
            return this;
        }

        /**
         * Voeg een partijcode toe aan de builder.
         *
         * @param metPartijCode de partijcode
         * @return de builder
         */
        public Builder metPartijCode(final String metPartijCode) {
            this.partijCode = metPartijCode;
            return this;
        }

        /**
         * Voeg een bsn toe aan de builder.
         *
         * @param metBsn de bsn
         * @return de builder
         */
        public Builder metBsn(final String metBsn) {
            this.bsn = metBsn;
            return this;
        }

        /**
         * Construeer de nieuwe {@link BerichtAfnemerindicatie}.
         *
         * @return de nieuwe {@link BerichtAfnemerindicatie}
         */
        public BerichtAfnemerindicatie build() {
            final BerichtAfnemerindicatie berichtAfnemerindicatie = new BerichtAfnemerindicatie();
            berichtAfnemerindicatie.tijdstipRegistratie = tijdstipRegistratie;
            berichtAfnemerindicatie.partijCode = partijCode;
            berichtAfnemerindicatie.bsn = bsn;
            return berichtAfnemerindicatie;
        }
    }
}
