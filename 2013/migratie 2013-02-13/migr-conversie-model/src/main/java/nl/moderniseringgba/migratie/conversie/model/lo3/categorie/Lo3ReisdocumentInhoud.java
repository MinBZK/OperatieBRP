/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class geeft de inhoud weer van een LO3 Categorie 12 Reisdocument.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3ReisdocumentInhoud implements Lo3CategorieInhoud {

    // 35 Nederlands reisdocument
    @Element(name = "soortNederlandsReisdocument", required = false)
    private final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument;
    @Element(name = "nummerNederlandsReisdocument", required = false)
    private final String nummerNederlandsReisdocument;
    @Element(name = "datumUitgifteNederlandsReisdocument", required = false)
    private final Lo3Datum datumUitgifteNederlandsReisdocument;
    @Element(name = "autoriteitVanAfgifteNederlandsReisdocument", required = false)
    private final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument;
    @Element(name = "datumEindeGeldigheidNederlandsReisdocument", required = false)
    private final Lo3Datum datumEindeGeldigheidNederlandsReisdocument;
    @Element(name = "datumInhoudingVermissingNederlandsReisdocument", required = false)
    private final Lo3Datum datumInhoudingVermissingNederlandsReisdocument;
    @Element(name = "aanduidingInhoudingNederlandsReisdocument", required = false)
    private final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
    @Element(name = "lengteHouder", required = false)
    private final Lo3LengteHouder lengteHouder;

    // 36 Signalering
    @Element(name = "signalering", required = false)
    private final Lo3Signalering signalering;

    // 37 Buitenlands reisdocument
    @Element(name = "aanduidingBezitBuitenlandsReisdocument", required = false)
    private final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument;

    /**
     * Default constructor (alles null).
     */
    public Lo3ReisdocumentInhoud() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3ReisdocumentInhoud object.
     * 
     * @param soortNederlandsReisdocument
     *            soort Nederlands reisdocument 35.10 of null
     * @param nummerNederlandsReisdocument
     *            nummer Nederlands reisdocument 35.20 of null
     * @param datumUitgifteNederlandsReisdocument
     *            datum uitgifte Nederlands reisdocument 35.30 of null
     * @param autoriteitVanAfgifteNederlandsReisdocument
     *            autoriteit van afgifte Nederlands reisdocument 35.40 of null
     * @param datumEindeGeldigheidNederlandsReisdocument
     *            datum einde geldigheid Nederlands reisdocument 35.50 of null
     * @param datumInhoudingVermissingNederlandsReisdocument
     *            datum inhouding dan wel vermissing Nederlands reisdocument 35.60 of null
     * @param aanduidingInhoudingNederlandsReisdocument
     *            aanduiding inhouding dan wel vermissing Nederlands reisdocument 35.70 of null
     * @param lengteHouder
     *            lengte houder 35.80 of null
     * @param signalering
     *            signalering met betrekking tot het verstrekken van een Nederlands reisdocument 36.10 of null
     * @param aanduidingBezitBuitenlandsReisdocument
     *            aanduiding bezit buitenlands reisdocument 37.10
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie12Reisdocument}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie12Reisdocument}
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3ReisdocumentInhoud(
            @Element(name = "soortNederlandsReisdocument", required = false) final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument,
            @Element(name = "nummerNederlandsReisdocument", required = false) final String nummerNederlandsReisdocument,
            @Element(name = "datumUitgifteNederlandsReisdocument", required = false) final Lo3Datum datumUitgifteNederlandsReisdocument,
            @Element(name = "autoriteitVanAfgifteNederlandsReisdocument", required = false) final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument,
            @Element(name = "datumEindeGeldigheidNederlandsReisdocument", required = false) final Lo3Datum datumEindeGeldigheidNederlandsReisdocument,
            @Element(name = "datumInhoudingVermissingNederlandsReisdocument", required = false) final Lo3Datum datumInhoudingVermissingNederlandsReisdocument,
            @Element(name = "aanduidingInhoudingNederlandsReisdocument", required = false) final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument,
            @Element(name = "lengteHouder", required = false) final Lo3LengteHouder lengteHouder,
            @Element(name = "signalering", required = false) final Lo3Signalering signalering,
            @Element(name = "aanduidingBezitBuitenlandsReisdocument", required = false) final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument) {
        // CHECKSTYLE:ON
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
        this.nummerNederlandsReisdocument = nummerNederlandsReisdocument;
        this.datumUitgifteNederlandsReisdocument = datumUitgifteNederlandsReisdocument;
        this.autoriteitVanAfgifteNederlandsReisdocument = autoriteitVanAfgifteNederlandsReisdocument;
        this.datumEindeGeldigheidNederlandsReisdocument = datumEindeGeldigheidNederlandsReisdocument;
        this.datumInhoudingVermissingNederlandsReisdocument = datumInhoudingVermissingNederlandsReisdocument;
        this.aanduidingInhoudingNederlandsReisdocument = aanduidingInhoudingNederlandsReisdocument;
        this.lengteHouder = lengteHouder;
        this.signalering = signalering;
        this.aanduidingBezitBuitenlandsReisdocument = aanduidingBezitBuitenlandsReisdocument;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(soortNederlandsReisdocument, nummerNederlandsReisdocument,
                datumUitgifteNederlandsReisdocument, autoriteitVanAfgifteNederlandsReisdocument,
                datumEindeGeldigheidNederlandsReisdocument, datumInhoudingVermissingNederlandsReisdocument,
                aanduidingInhoudingNederlandsReisdocument, lengteHouder, signalering,
                aanduidingBezitBuitenlandsReisdocument);
    }

    /**
     * @return the soortNederlandsReisdocument
     */
    public Lo3SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * @return the nummerNederlandsReisdocument
     */
    public String getNummerNederlandsReisdocument() {
        return nummerNederlandsReisdocument;
    }

    /**
     * @return the datumUitgifteNederlandsReisdocument
     */
    public Lo3Datum getDatumUitgifteNederlandsReisdocument() {
        return datumUitgifteNederlandsReisdocument;
    }

    /**
     * @return the autoriteitVanAfgifteNederlandsReisdocument
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument getAutoriteitVanAfgifteNederlandsReisdocument() {
        return autoriteitVanAfgifteNederlandsReisdocument;
    }

    /**
     * @return the datumEindeGeldigheidNederlandsReisdocument
     */
    public Lo3Datum getDatumEindeGeldigheidNederlandsReisdocument() {
        return datumEindeGeldigheidNederlandsReisdocument;
    }

    /**
     * @return the datumInhoudingVermissingNederlandsReisdocument
     */
    public Lo3Datum getDatumInhoudingVermissingNederlandsReisdocument() {
        return datumInhoudingVermissingNederlandsReisdocument;
    }

    /**
     * @return the aanduidingInhoudingVermissingNederlandsReisdocument
     */
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument
            getAanduidingInhoudingVermissingNederlandsReisdocument() {
        return aanduidingInhoudingNederlandsReisdocument;
    }

    /**
     * @return the lengteHouder
     */
    public Lo3LengteHouder getLengteHouder() {
        return lengteHouder;
    }

    /**
     * @return the signalering
     */
    public Lo3Signalering getSignalering() {
        return signalering;
    }

    /**
     * @return the aanduidingBezitBuitenlandsReisdocument
     */
    public Lo3AanduidingBezitBuitenlandsReisdocument getAanduidingBezitBuitenlandsReisdocument() {
        return aanduidingBezitBuitenlandsReisdocument;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3ReisdocumentInhoud)) {
            return false;
        }
        final Lo3ReisdocumentInhoud castOther = (Lo3ReisdocumentInhoud) other;
        return new EqualsBuilder()
                .append(soortNederlandsReisdocument, castOther.soortNederlandsReisdocument)
                .append(nummerNederlandsReisdocument, castOther.nummerNederlandsReisdocument)
                .append(datumUitgifteNederlandsReisdocument, castOther.datumUitgifteNederlandsReisdocument)
                .append(autoriteitVanAfgifteNederlandsReisdocument,
                        castOther.autoriteitVanAfgifteNederlandsReisdocument)
                .append(datumEindeGeldigheidNederlandsReisdocument,
                        castOther.datumEindeGeldigheidNederlandsReisdocument)
                .append(datumInhoudingVermissingNederlandsReisdocument,
                        castOther.datumInhoudingVermissingNederlandsReisdocument)
                .append(aanduidingInhoudingNederlandsReisdocument,
                        castOther.aanduidingInhoudingNederlandsReisdocument)
                .append(lengteHouder, castOther.lengteHouder).append(signalering, castOther.signalering)
                .append(aanduidingBezitBuitenlandsReisdocument, castOther.aanduidingBezitBuitenlandsReisdocument)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortNederlandsReisdocument).append(nummerNederlandsReisdocument)
                .append(datumUitgifteNederlandsReisdocument).append(autoriteitVanAfgifteNederlandsReisdocument)
                .append(datumEindeGeldigheidNederlandsReisdocument)
                .append(datumInhoudingVermissingNederlandsReisdocument)
                .append(aanduidingInhoudingNederlandsReisdocument).append(lengteHouder).append(signalering)
                .append(aanduidingBezitBuitenlandsReisdocument).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("soortNederlandsReisdocument", soortNederlandsReisdocument)
                .append("nummerNederlandsReisdocument", nummerNederlandsReisdocument)
                .append("datumUitgifteNederlandsReisdocument", datumUitgifteNederlandsReisdocument)
                .append("autoriteitVanAfgifteNederlandsReisdocument", autoriteitVanAfgifteNederlandsReisdocument)
                .append("datumEindeGeldigheidNederlandsReisdocument", datumEindeGeldigheidNederlandsReisdocument)
                .append("datumInhoudingVermissingNederlandsReisdocument",
                        datumInhoudingVermissingNederlandsReisdocument)
                .append("aanduidingInhoudingVermissingNederlandsReisdocument",
                        aanduidingInhoudingNederlandsReisdocument).append("lengteHouder", lengteHouder)
                .append("signalering", signalering)
                .append("aanduidingBezitBuitenlandsReisdocument", aanduidingBezitBuitenlandsReisdocument).toString();
    }

    /** Builder. */
    public static final class Builder {

        private Lo3SoortNederlandsReisdocument soortNederlandsReisdocument;
        private String nummerNederlandsReisdocument;
        private Lo3Datum datumUitgifteNederlandsReisdocument;
        private Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument;
        private Lo3Datum datumEindeGeldigheidNederlandsReisdocument;
        private Lo3Datum datumInhoudingVermissingNederlandsReisdocument;
        private Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
        private Lo3LengteHouder lengteHouder;
        private Lo3Signalering signalering;
        private Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         * 
         * @param inhoud
         *            initiele inhoud
         */
        public Builder(final Lo3ReisdocumentInhoud inhoud) {
            soortNederlandsReisdocument = inhoud.soortNederlandsReisdocument;
            nummerNederlandsReisdocument = inhoud.nummerNederlandsReisdocument;
            datumUitgifteNederlandsReisdocument = inhoud.datumUitgifteNederlandsReisdocument;
            autoriteitVanAfgifteNederlandsReisdocument = inhoud.autoriteitVanAfgifteNederlandsReisdocument;
            datumEindeGeldigheidNederlandsReisdocument = inhoud.datumEindeGeldigheidNederlandsReisdocument;
            datumInhoudingVermissingNederlandsReisdocument = inhoud.datumInhoudingVermissingNederlandsReisdocument;
            aanduidingInhoudingNederlandsReisdocument = inhoud.aanduidingInhoudingNederlandsReisdocument;
            lengteHouder = inhoud.lengteHouder;
            signalering = inhoud.signalering;
            aanduidingBezitBuitenlandsReisdocument = inhoud.aanduidingBezitBuitenlandsReisdocument;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3ReisdocumentInhoud build() {
            return new Lo3ReisdocumentInhoud(soortNederlandsReisdocument, nummerNederlandsReisdocument,
                    datumUitgifteNederlandsReisdocument, autoriteitVanAfgifteNederlandsReisdocument,
                    datumEindeGeldigheidNederlandsReisdocument, datumInhoudingVermissingNederlandsReisdocument,
                    aanduidingInhoudingNederlandsReisdocument, lengteHouder, signalering,
                    aanduidingBezitBuitenlandsReisdocument);

        }

        /**
         * @param soortNederlandsReisdocument
         *            the soortNederlandsReisdocument to set
         */
        public void setSoortNederlandsReisdocument(final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument) {
            this.soortNederlandsReisdocument = soortNederlandsReisdocument;
        }

        /**
         * @param nummerNederlandsReisdocument
         *            the nummerNederlandsReisdocument to set
         */
        public void setNummerNederlandsReisdocument(final String nummerNederlandsReisdocument) {
            this.nummerNederlandsReisdocument = nummerNederlandsReisdocument;
        }

        /**
         * @param datumUitgifteNederlandsReisdocument
         *            the datumUitgifteNederlandsReisdocument to set
         */
        public void setDatumUitgifteNederlandsReisdocument(final Lo3Datum datumUitgifteNederlandsReisdocument) {
            this.datumUitgifteNederlandsReisdocument = datumUitgifteNederlandsReisdocument;
        }

        /**
         * @param autoriteitVanAfgifteNederlandsReisdocument
         *            the autoriteitVanAfgifteNederlandsReisdocument to set
         */
        public void setAutoriteitVanAfgifteNederlandsReisdocument(
                final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument) {
            this.autoriteitVanAfgifteNederlandsReisdocument = autoriteitVanAfgifteNederlandsReisdocument;
        }

        /**
         * @param datumEindeGeldigheidNederlandsReisdocument
         *            the datumEindeGeldigheidNederlandsReisdocument to set
         */
        public void setDatumEindeGeldigheidNederlandsReisdocument(
                final Lo3Datum datumEindeGeldigheidNederlandsReisdocument) {
            this.datumEindeGeldigheidNederlandsReisdocument = datumEindeGeldigheidNederlandsReisdocument;
        }

        /**
         * @param datumInhoudingVermissingNederlandsReisdocument
         *            the datumInhoudingVermissingNederlandsReisdocument to set
         */
        public void setDatumInhoudingVermissingNederlandsReisdocument(
                final Lo3Datum datumInhoudingVermissingNederlandsReisdocument) {
            this.datumInhoudingVermissingNederlandsReisdocument = datumInhoudingVermissingNederlandsReisdocument;
        }

        /**
         * @param aanduiding
         *            the aanduidingInhoudingNederlandsReisdocument to set
         */
        public void setAanduidingInhoudingNederlandsReisdocument(
                final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduiding) {
            aanduidingInhoudingNederlandsReisdocument = aanduiding;
        }

        /**
         * @param lengteHouder
         *            the lengteHouder to set
         */
        public void setLengteHouder(final Lo3LengteHouder lengteHouder) {
            this.lengteHouder = lengteHouder;
        }

        /**
         * @param signalering
         *            the signalering to set
         */
        public void setSignalering(final Lo3Signalering signalering) {
            this.signalering = signalering;
        }

        /**
         * @param aanduidingBezitBuitenlandsReisdocument
         *            the aanduidingBezitBuitenlandsReisdocument to set
         */
        public void setAanduidingBezitBuitenlandsReisdocument(
                final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument) {
            this.aanduidingBezitBuitenlandsReisdocument = aanduidingBezitBuitenlandsReisdocument;
        }

    }

}
