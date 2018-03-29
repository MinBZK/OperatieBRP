/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;

/**
 * Representatie van stuurgegevens in een brp basis-bericht.
 */
public final class BerichtStuurgegevens {

    private Partij zendendePartij;
    private String zendendeSysteem;
    private Partij ontvangendePartij;
    private String referentienummer;
    private String crossReferentienummer;
    private ZonedDateTime tijdstipVerzending;

    public Partij getZendendePartij() {
        return zendendePartij;
    }

    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    public Partij getOntvangendePartij() {
        return ontvangendePartij;
    }

    public String getReferentienummer() {
        return referentienummer;
    }

    public String getCrossReferentienummer() {
        return crossReferentienummer;
    }

    public ZonedDateTime getTijdstipVerzending() {
        return tijdstipVerzending;
    }

    /**
     * De builder voor BerichtStuurgegevens.
     */
    public static final class Builder {

        private BasisBerichtGegevens.Builder berichtBuilder;

        private String zendendeSysteem = BasisBerichtGegevens.BRP_SYSTEEM;
        private Partij zendendePartij;
        private Partij ontvangendePartij;
        private String referentienummer;
        private String crossReferentienummer;
        private ZonedDateTime tijdstipVerzending;

        /**
         * Constructor.
         * @param builder de bericht builder
         */
        public Builder(final BasisBerichtGegevens.Builder builder) {
            this.berichtBuilder = builder;
        }

        /**
         * Voeg de zendende partij toe aan de builder.
         * @param partij de zendende partij
         * @return de builder
         */
        public Builder metZendendePartij(final Partij partij) {
            this.zendendePartij = partij;
            return this;
        }

        /**
         * Voeg de ontvangende partij toe aan de builder.
         * @param partij de ontvangende partij
         * @return de builder
         */
        public Builder metOntvangendePartij(final Partij partij) {
            this.ontvangendePartij = partij;
            return this;
        }

        /**
         * Voeg het referentienummer toe aan de builder.
         * @param metReferentienummer het referentienummer
         * @return de builder
         */
        public Builder metReferentienummer(final String metReferentienummer) {
            this.referentienummer = metReferentienummer;
            return this;
        }

        /**
         * Voeg het crossreferentienummer toe aan de builder.
         * @param metCrossReferentienummer het crossreferentienummer
         * @return de builder
         */
        public Builder metCrossReferentienummer(final String metCrossReferentienummer) {
            this.crossReferentienummer = metCrossReferentienummer;
            return this;
        }

        /**
         * Voeg het tijdstip van verzending toe aan de builder.
         * @param metTijdstipVerzending het tijdstip van verzending
         * @return de builder
         */
        public Builder metTijdstipVerzending(final ZonedDateTime metTijdstipVerzending) {
            this.tijdstipVerzending = metTijdstipVerzending;
            return this;
        }

        /**
         * Voeg het zendende systeem toe aan de builder.
         * @param metZendendeSysteem het zendende systeem
         * @return de builder
         */
        public Builder metZendendeSysteem(final String metZendendeSysteem) {
            this.zendendeSysteem = metZendendeSysteem;
            return this;
        }

        /**
         * Sluit de stuurgegevens af door de berichtbuilder te retourneren.
         * @return de builder.
         */
        public BasisBerichtGegevens.Builder eindeStuurgegevens() {
            return berichtBuilder;
        }

        /**
         * Bouw de bericht stuurgegevens.
         * @return de bericht stuurgegevens
         */
        public BerichtStuurgegevens build() {
            eindeStuurgegevens();
            final BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
            stuurgegevens.zendendePartij = zendendePartij;
            stuurgegevens.zendendeSysteem = zendendeSysteem;
            stuurgegevens.ontvangendePartij = ontvangendePartij;
            stuurgegevens.referentienummer = referentienummer;
            stuurgegevens.crossReferentienummer = crossReferentienummer;
            stuurgegevens.tijdstipVerzending = tijdstipVerzending;
            return stuurgegevens;
        }
    }
}
