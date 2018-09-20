/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
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

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3510)
    @Element(name = "soortNederlandsReisdocument", required = false)
    private final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3520)
    @Element(name = "nummerNederlandsReisdocument", required = false)
    private final Lo3String nummerNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3530)
    @Element(name = "datumUitgifteNederlandsReisdocument", required = false)
    private final Lo3Datum datumUitgifteNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3540)
    @Element(name = "autoriteitVanAfgifteNederlandsReisdocument", required = false)
    private final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3550)
    @Element(name = "datumEindeGeldigheidNederlandsReisdocument", required = false)
    private final Lo3Datum datumEindeGeldigheidNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3560)
    @Element(name = "datumInhoudingVermissingNederlandsReisdocument", required = false)
    private final Lo3Datum datumInhoudingVermissingNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3570)
    @Element(name = "aanduidingInhoudingNederlandsReisdocument", required = false)
    private final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3610)
    @Element(name = "signalering", required = false)
    private final Lo3Signalering signalering;

    /**
     * Default constructor (alles null).
     */
    public Lo3ReisdocumentInhoud() {
        this(null, null, null, null, null, null, null, null);
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
     * @param signalering
     *            signalering met betrekking tot het verstrekken van een Nederlands reisdocument 36.10 of null
     */
    public Lo3ReisdocumentInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "soortNederlandsReisdocument", required = false) final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument,
        @Element(name = "nummerNederlandsReisdocument", required = false) final Lo3String nummerNederlandsReisdocument,
        @Element(name = "datumUitgifteNederlandsReisdocument", required = false) final Lo3Datum datumUitgifteNederlandsReisdocument,
        @Element(name = "autoriteitVanAfgifteNederlandsReisdocument", required = false) final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument,
        @Element(name = "datumEindeGeldigheidNederlandsReisdocument", required = false) final Lo3Datum datumEindeGeldigheidNederlandsReisdocument,
        @Element(name = "datumInhoudingVermissingNederlandsReisdocument", required = false) final Lo3Datum datumInhoudingVermissingNederlandsReisdocument,
        @Element(name = "aanduidingInhoudingNederlandsReisdocument", required = false) final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument,
        @Element(name = "signalering", required = false) final Lo3Signalering signalering)
    {
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
        this.nummerNederlandsReisdocument = nummerNederlandsReisdocument;
        this.datumUitgifteNederlandsReisdocument = datumUitgifteNederlandsReisdocument;
        this.autoriteitVanAfgifteNederlandsReisdocument = autoriteitVanAfgifteNederlandsReisdocument;
        this.datumEindeGeldigheidNederlandsReisdocument = datumEindeGeldigheidNederlandsReisdocument;
        this.datumInhoudingVermissingNederlandsReisdocument = datumInhoudingVermissingNederlandsReisdocument;
        this.aanduidingInhoudingNederlandsReisdocument = aanduidingInhoudingNederlandsReisdocument;
        this.signalering = signalering;
    }

    private Lo3ReisdocumentInhoud(final Builder builder) {
        soortNederlandsReisdocument = builder.soortNederlandsReisdocument;
        nummerNederlandsReisdocument = builder.nummerNederlandsReisdocument;
        datumUitgifteNederlandsReisdocument = builder.datumUitgifteNederlandsReisdocument;
        autoriteitVanAfgifteNederlandsReisdocument = builder.autoriteitVanAfgifteNederlandsReisdocument;
        datumEindeGeldigheidNederlandsReisdocument = builder.datumEindeGeldigheidNederlandsReisdocument;
        datumInhoudingVermissingNederlandsReisdocument = builder.datumInhoudingVermissingNederlandsReisdocument;
        aanduidingInhoudingNederlandsReisdocument = builder.aanduidingInhoudingNederlandsReisdocument;
        signalering = builder.signalering;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(
            soortNederlandsReisdocument,
            nummerNederlandsReisdocument,
            datumUitgifteNederlandsReisdocument,
            autoriteitVanAfgifteNederlandsReisdocument,
            datumEindeGeldigheidNederlandsReisdocument,
            datumInhoudingVermissingNederlandsReisdocument,
            aanduidingInhoudingNederlandsReisdocument,
            signalering);
    }

    /**
     * Geef de nederlands reisdocument.
     *
     * @return true als er elementen uit groep 35 zijn gevuld
     */
    @Definitie(Definities.DEF063)
    public boolean isNederlandsReisdocument() {
        return Validatie.isEenParameterGevuld(
            soortNederlandsReisdocument,
            nummerNederlandsReisdocument,
            datumUitgifteNederlandsReisdocument,
            autoriteitVanAfgifteNederlandsReisdocument,
            datumEindeGeldigheidNederlandsReisdocument,
            datumInhoudingVermissingNederlandsReisdocument,
            aanduidingInhoudingNederlandsReisdocument);
    }

    /**
     * Geef de belemmering verstrekking.
     *
     * @return true als er elementen uit groep 36 zijn gevuld
     */
    @Definitie(Definities.DEF065)
    public boolean isBelemmeringVerstrekking() {
        return Validatie.isEenParameterGevuld(signalering);
    }

    /**
     * Geef de waarde van soort nederlands reisdocument.
     *
     * @return the soortNederlandsReisdocument
     */
    public Lo3SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Geef de waarde van nummer nederlands reisdocument.
     *
     * @return the nummerNederlandsReisdocument
     */
    public Lo3String getNummerNederlandsReisdocument() {
        return nummerNederlandsReisdocument;
    }

    /**
     * Geef de waarde van datum uitgifte nederlands reisdocument.
     *
     * @return the datumUitgifteNederlandsReisdocument
     */
    public Lo3Datum getDatumUitgifteNederlandsReisdocument() {
        return datumUitgifteNederlandsReisdocument;
    }

    /**
     * Geef de waarde van autoriteit van afgifte nederlands reisdocument.
     *
     * @return the autoriteitVanAfgifteNederlandsReisdocument
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument getAutoriteitVanAfgifteNederlandsReisdocument() {
        return autoriteitVanAfgifteNederlandsReisdocument;
    }

    /**
     * Geef de waarde van datum einde geldigheid nederlands reisdocument.
     *
     * @return the datumEindeGeldigheidNederlandsReisdocument
     */
    public Lo3Datum getDatumEindeGeldigheidNederlandsReisdocument() {
        return datumEindeGeldigheidNederlandsReisdocument;
    }

    /**
     * Geef de waarde van datum inhouding vermissing nederlands reisdocument.
     *
     * @return the datumInhoudingVermissingNederlandsReisdocument
     */
    public Lo3Datum getDatumInhoudingVermissingNederlandsReisdocument() {
        return datumInhoudingVermissingNederlandsReisdocument;
    }

    /**
     * Geef de waarde van aanduiding inhouding vermissing nederlands reisdocument.
     *
     * @return the aanduidingInhoudingVermissingNederlandsReisdocument
     */
    public Lo3AanduidingInhoudingVermissingNederlandsReisdocument getAanduidingInhoudingVermissingNederlandsReisdocument() {
        return aanduidingInhoudingNederlandsReisdocument;
    }

    /**
     * Geef de waarde van signalering.
     *
     * @return the signalering
     */
    public Lo3Signalering getSignalering() {
        return signalering;
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
        return new EqualsBuilder().append(soortNederlandsReisdocument, castOther.soortNederlandsReisdocument)
                                  .append(nummerNederlandsReisdocument, castOther.nummerNederlandsReisdocument)
                                  .append(datumUitgifteNederlandsReisdocument, castOther.datumUitgifteNederlandsReisdocument)
                                  .append(autoriteitVanAfgifteNederlandsReisdocument, castOther.autoriteitVanAfgifteNederlandsReisdocument)
                                  .append(datumEindeGeldigheidNederlandsReisdocument, castOther.datumEindeGeldigheidNederlandsReisdocument)
                                  .append(datumInhoudingVermissingNederlandsReisdocument, castOther.datumInhoudingVermissingNederlandsReisdocument)
                                  .append(aanduidingInhoudingNederlandsReisdocument, castOther.aanduidingInhoudingNederlandsReisdocument)
                                  .append(signalering, castOther.signalering)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortNederlandsReisdocument)
                                    .append(nummerNederlandsReisdocument)
                                    .append(datumUitgifteNederlandsReisdocument)
                                    .append(autoriteitVanAfgifteNederlandsReisdocument)
                                    .append(datumEindeGeldigheidNederlandsReisdocument)
                                    .append(datumInhoudingVermissingNederlandsReisdocument)
                                    .append(aanduidingInhoudingNederlandsReisdocument)
                                    .append(signalering)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortNederlandsReisdocument", soortNederlandsReisdocument)
                                                                          .append("nummerNederlandsReisdocument", nummerNederlandsReisdocument)
                                                                          .append(
                                                                              "datumUitgifteNederlandsReisdocument",
                                                                              datumUitgifteNederlandsReisdocument)
                                                                          .append(
                                                                              "autoriteitVanAfgifteNederlandsReisdocument",
                                                                              autoriteitVanAfgifteNederlandsReisdocument)
                                                                          .append(
                                                                              "datumEindeGeldigheidNederlandsReisdocument",
                                                                              datumEindeGeldigheidNederlandsReisdocument)
                                                                          .append(
                                                                              "datumInhoudingVermissingNederlandsReisdocument",
                                                                              datumInhoudingVermissingNederlandsReisdocument)
                                                                          .append(
                                                                              "aanduidingInhoudingVermissingNederlandsReisdocument",
                                                                              aanduidingInhoudingNederlandsReisdocument)
                                                                          .append("signalering", signalering)
                                                                          .toString();
    }

    /** Builder. */
    public static final class Builder {

        private Lo3SoortNederlandsReisdocument soortNederlandsReisdocument;
        private Lo3String nummerNederlandsReisdocument;
        private Lo3Datum datumUitgifteNederlandsReisdocument;
        private Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument;
        private Lo3Datum datumEindeGeldigheidNederlandsReisdocument;
        private Lo3Datum datumInhoudingVermissingNederlandsReisdocument;
        private Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhoudingNederlandsReisdocument;
        private Lo3Signalering signalering;

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
            signalering = inhoud.signalering;
        }

        /**
         * Reset de velden voor Nederlands reisdocument.
         */
        public void resetNederlandsReisdocumentVelden() {
            soortNederlandsReisdocument = null;
            nummerNederlandsReisdocument = null;
            datumUitgifteNederlandsReisdocument = null;
            autoriteitVanAfgifteNederlandsReisdocument = null;
            datumEindeGeldigheidNederlandsReisdocument = null;
            datumInhoudingVermissingNederlandsReisdocument = null;
            aanduidingInhoudingNederlandsReisdocument = null;
        }

        /**
         * Reset de velden voor belemmering verstrekking (signalering).
         */
        public void resetSignalering() {
            signalering = null;
        }

        /**
         * Build.
         *
         * @return inhoud
         */
        public Lo3ReisdocumentInhoud build() {
            return new Lo3ReisdocumentInhoud(this);
        }

        /**
         * @param param
         *            the soortNederlandsReisdocument to set
         * @return builder object
         */
        public Builder soortNederlandsReisdocument(final Lo3SoortNederlandsReisdocument param) {
            soortNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the nummerNederlandsReisdocument to set
         * @return builder object
         */
        public Builder nummerNederlandsReisdocument(final Lo3String param) {
            nummerNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the datumUitgifteNederlandsReisdocument to set
         * @return builder object
         */
        public Builder datumUitgifteNederlandsReisdocument(final Lo3Datum param) {
            datumUitgifteNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the autoriteitVanAfgifteNederlandsReisdocument to set
         * @return builder object
         */
        public Builder autoriteitVanAfgifteNederlandsReisdocument(final Lo3AutoriteitVanAfgifteNederlandsReisdocument param) {
            autoriteitVanAfgifteNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the datumEindeGeldigheidNederlandsReisdocument to set
         * @return builder object
         */
        public Builder datumEindeGeldigheidNederlandsReisdocument(final Lo3Datum param) {
            datumEindeGeldigheidNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the datumInhoudingVermissingNederlandsReisdocument to set
         * @return builder object
         */
        public Builder datumInhoudingVermissingNederlandsReisdocument(final Lo3Datum param) {
            datumInhoudingVermissingNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the aanduidingInhoudingNederlandsReisdocument to set
         * @return builder object
         */
        public Builder aanduidingInhoudingNederlandsReisdocument(final Lo3AanduidingInhoudingVermissingNederlandsReisdocument param) {
            aanduidingInhoudingNederlandsReisdocument = param;
            return this;
        }

        /**
         * @param param
         *            the signalering to set
         * @return builder object
         */
        public Builder signalering(final Lo3Signalering param) {
            signalering = param;
            return this;
        }
    }
}
