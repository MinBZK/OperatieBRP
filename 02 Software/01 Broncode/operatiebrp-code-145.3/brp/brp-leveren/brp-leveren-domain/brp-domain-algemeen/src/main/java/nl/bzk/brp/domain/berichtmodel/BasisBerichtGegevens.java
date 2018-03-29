/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Het bericht object.
 */
public final class BasisBerichtGegevens {

    /**
     * BRP systeem.
     */
    public static final String BRP_SYSTEEM = "BRP";

    private BerichtStuurgegevens stuurgegevens;
    private BerichtParameters parameters;
    private List<Melding> meldingen;
    private BerichtVerwerkingsResultaat resultaat;
    private String soortNaam;
    private String categorieNaam;
    private ZonedDateTime tijdstipRegistratie;
    private String partijCode;
    private Long administratieveHandelingId;

    /**
     * Conctructor.
     */
    private BasisBerichtGegevens() {
    }

    /**
     * Maak de builder.
     * @return de builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return de bericht stuurgegevens
     */
    public BerichtStuurgegevens getStuurgegevens() {
        return stuurgegevens;
    }


    /**
     * @return de bericht parameters
     */
    public BerichtParameters getParameters() {
        return parameters;
    }

    /**
     * @return lijst met bericht meldingen
     */
    public List<Melding> getMeldingen() {
        return meldingen;
    }


    public BerichtVerwerkingsResultaat getResultaat() {
        return resultaat;
    }

    /**
     * @return de soortnaam specifiek voor het bericht
     */
    public String getSoortNaam() {
        return soortNaam;
    }

    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public String getPartijCode() {
        return partijCode;
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public String getCategorieNaam() {
        return categorieNaam;
    }


    /**
     * De bericht builder.
     */
    public static final class Builder {

        private BerichtStuurgegevens.Builder stuurgegevensBuilder;
        private BerichtParameters.Builder parametersBuilder;
        private BerichtVerwerkingsResultaat resultaat;
        private String soortNaam;
        private String categorieNaam;
        private ZonedDateTime tijdstipRegistratie;
        private String partijCode;
        private Long administratieveHandelingId;


        private List<Melding> meldingen = Collections.emptyList();

        private Builder() {
        }

        /**
         * Voeg stuurgegevens toe aan de builder.
         * @return de stuurgegevens builder
         */
        public BerichtStuurgegevens.Builder metStuurgegevens() {
            stuurgegevensBuilder = new BerichtStuurgegevens.Builder(this);
            return stuurgegevensBuilder;
        }

        /**
         * Voeg parameters toe aan de builder.
         * @return de parameters builder
         */
        public BerichtParameters.Builder metParameters() {
            parametersBuilder = new BerichtParameters.Builder(this);
            return parametersBuilder;
        }

        /**
         * Voeg meldingen toe aan de builder.
         * @param metMeldingenBuild de meldingen
         * @return de builder
         */
        public Builder metMeldingen(final List<Melding> metMeldingenBuild) {
            this.meldingen = Collections.unmodifiableList(metMeldingenBuild);
            return this;
        }

        /**
         * Voeg het berichtresultaat toe aan de builder.
         * @param metResultaatBuild het berichtresultaat
         * @return de builder
         */
        public Builder metResultaat(final BerichtVerwerkingsResultaat metResultaatBuild) {
            this.resultaat = metResultaatBuild;
            return this;
        }

        /**
         * Zet de soortNaam.
         * @param soortNaam de soortnaam
         * @return de builder
         */
        public Builder metSoortNaam(final String soortNaam) {
            this.soortNaam = soortNaam;
            return this;
        }

        /**
         * Zet de categorieNaam
         * @param categorieNaam de categorienaam
         * @return de builder
         */
        public Builder metCategorieNaam(final String categorieNaam) {
            this.categorieNaam = categorieNaam;
            return this;
        }

        /**
         * Voeg het tijdstip van registratie toe aan de builder.
         * @param metTijdstipRegistratieBuild het tijdstip van registratie
         * @return de builder
         */
        public Builder metTijdstipRegistratie(final ZonedDateTime metTijdstipRegistratieBuild) {
            this.tijdstipRegistratie = metTijdstipRegistratieBuild;
            return this;
        }

        /**
         * Voeg de partijcode toe aan de builder.
         * @param metPartijCodeBuild de partijcode
         * @return de builder
         */
        public Builder metPartijCode(final String metPartijCodeBuild) {
            this.partijCode = metPartijCodeBuild;
            return this;
        }

        /**
         * Voeg de administratieve handeling id toe aan de builder.
         * @param metAdministratieveHandelingIdBuild de administratieve handeling id
         * @return de builder
         */
        public Builder metAdministratieveHandelingId(final Long metAdministratieveHandelingIdBuild) {
            this.administratieveHandelingId = metAdministratieveHandelingIdBuild;
            return this;
        }


        /**
         * Bouw het bericht.
         * @return het bericht
         */
        public BasisBerichtGegevens build() {

            final BasisBerichtGegevens bericht = new BasisBerichtGegevens();
            if (stuurgegevensBuilder != null) {
                bericht.stuurgegevens = stuurgegevensBuilder.build();
            }

            if (parametersBuilder != null) {
                bericht.parameters = parametersBuilder.build();
            }
            bericht.meldingen = meldingen;
            bericht.resultaat = resultaat;
            bericht.soortNaam = this.soortNaam;
            bericht.categorieNaam = this.categorieNaam;
            bericht.tijdstipRegistratie = tijdstipRegistratie;
            bericht.partijCode = partijCode;
            bericht.administratieveHandelingId = administratieveHandelingId;

            return bericht;
        }

    }
}
