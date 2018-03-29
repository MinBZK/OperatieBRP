/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class geeft de inhoud weer van een LO3 Categorie 04/54 Nationaliteit.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3NationaliteitInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0510)
    @Element(name = "nationaliteitCode", required = false)
    private final Lo3NationaliteitCode nationaliteitCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6310)
    @Element(name = "redenVerkrijgingNederlandschapCode", required = false)
    private final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6410)
    @Element(name = "redenVerliesNederlandschapCode", required = false)
    private final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6510)
    @Element(name = "aanduidingBijzonderNederlandschap", required = false)
    private final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7310)
    @Element(name = "buitenlandsPersoonsnummer", required = false)
    private final Lo3String buitenlandsPersoonsnummer;

    /**
     * Maak een lege nationaliteit aan.
     */
    public Lo3NationaliteitInhoud() {
        this(null, null, null, null, null);
    }

    /**
     * Maakt een Lo3NationaliteitInhoud object.
     * @param nationaliteitCode nationaliteit code 05.10 of null
     * @param redenVerkrijgingNederlandschapCode reden verkrijging Nederlandschap 63.10 of null
     * @param redenVerliesNederlandschapCode reden verlies nederlandschap 64.10 of null
     * @param aanduidingBijzonderNederlandschap aanduiding bijzonder nederlandschap of null
     * @param buitenlandsPersoonsnummer het EU persoonsnummer
     */
    public Lo3NationaliteitInhoud(
            @Element(name = "nationaliteitCode", required = false) final Lo3NationaliteitCode nationaliteitCode,
            @Element(name = "redenVerkrijgingNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode,
            @Element(name = "redenVerliesNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode,
            @Element(name = "aanduidingBijzonderNederlandschap", required = false) final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap,
            @Element(name = "buitenlandsPersoonsnummer", required = false) final Lo3String buitenlandsPersoonsnummer) {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        this.aanduidingBijzonderNederlandschap = aanduidingBijzonderNederlandschap;
        this.buitenlandsPersoonsnummer = buitenlandsPersoonsnummer;
    }

    private Lo3NationaliteitInhoud(final Builder builder) {
        nationaliteitCode = builder.nationaliteitCode;
        redenVerkrijgingNederlandschapCode = builder.redenVerkrijgingNederlandschapCode;
        redenVerliesNederlandschapCode = builder.redenVerliesNederlandschapCode;
        aanduidingBijzonderNederlandschap = builder.aanduidingBijzonderNederlandschap;
        buitenlandsPersoonsnummer = builder.buitenlandsPersoonsnummer;
    }

    /*
     * Een Lo3NationaliteitInhoud is leeg als nationaliteitCode, redenVerkrijgingNederlandschapCode en
     * aanduidingBijzonderNederlandschap velden leeg zijn, redenVerliesNederlandschapCode mag wel gevuld zijn.
     */
    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(
                nationaliteitCode,
                redenVerkrijgingNederlandschapCode,
                aanduidingBijzonderNederlandschap,
                buitenlandsPersoonsnummer);
    }

    /**
     * Geef de waarde van nationaliteit code van Lo3NationaliteitInhoud.
     * @return de waarde van nationaliteit code van Lo3NationaliteitInhoud
     */
    public Lo3NationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * Geef de waarde van reden verkrijging nederlandschap code van Lo3NationaliteitInhoud.
     * @return de waarde van reden verkrijging nederlandschap code van Lo3NationaliteitInhoud
     */
    public Lo3RedenNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    /**
     * Geef de waarde van reden verlies nederlandschap code van Lo3NationaliteitInhoud.
     * @return de waarde van reden verlies nederlandschap code van Lo3NationaliteitInhoud
     */
    public Lo3RedenNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
     * Geef de waarde van aanduiding bijzonder nederlandschap van Lo3NationaliteitInhoud.
     * @return de waarde van aanduiding bijzonder nederlandschap van Lo3NationaliteitInhoud
     */
    public Lo3AanduidingBijzonderNederlandschap getAanduidingBijzonderNederlandschap() {
        return aanduidingBijzonderNederlandschap;
    }

    /**
     * Geeft de waarde van het EU persoonsnummer terug.
     * @return de waarde van het EU persoonsnummer
     */
    public Lo3String getBuitenlandsPersoonsnummer() {
        return buitenlandsPersoonsnummer;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3NationaliteitInhoud)) {
            return false;
        }
        final Lo3NationaliteitInhoud castOther = (Lo3NationaliteitInhoud) other;
        return new EqualsBuilder().append(nationaliteitCode, castOther.nationaliteitCode)
                .append(redenVerkrijgingNederlandschapCode, castOther.redenVerkrijgingNederlandschapCode)
                .append(redenVerliesNederlandschapCode, castOther.redenVerliesNederlandschapCode)
                .append(aanduidingBijzonderNederlandschap, castOther.aanduidingBijzonderNederlandschap)
                .append(buitenlandsPersoonsnummer, castOther.buitenlandsPersoonsnummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nationaliteitCode)
                .append(redenVerkrijgingNederlandschapCode)
                .append(redenVerliesNederlandschapCode)
                .append(aanduidingBijzonderNederlandschap)
                .append(buitenlandsPersoonsnummer)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("nationaliteitCode", nationaliteitCode)
                .append("redenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode)
                .append("redenVerliesNederlandschapCode", redenVerliesNederlandschapCode)
                .append("aanduidingBijzonderNederlandschap", aanduidingBijzonderNederlandschap)
                .append("buitenlandsPersoonsnummer", buitenlandsPersoonsnummer)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3NationaliteitCode nationaliteitCode;
        private Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode;
        private Lo3RedenNederlandschapCode redenVerliesNederlandschapCode;
        private Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap;
        private Lo3String buitenlandsPersoonsnummer;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            //lege builder
        }

        /**
         * Maak een builder gevuld met de gegeven inhoud.
         * @param inhoud inhoud
         */
        public Builder(final Lo3NationaliteitInhoud inhoud) {
            nationaliteitCode = inhoud.nationaliteitCode;
            redenVerkrijgingNederlandschapCode = inhoud.redenVerkrijgingNederlandschapCode;
            redenVerliesNederlandschapCode = inhoud.redenVerliesNederlandschapCode;
            aanduidingBijzonderNederlandschap = inhoud.aanduidingBijzonderNederlandschap;
            buitenlandsPersoonsnummer = inhoud.buitenlandsPersoonsnummer;
        }

        /**
         * Maak de inhoud.
         * @return inhoud
         */
        public Lo3NationaliteitInhoud build() {
            return new Lo3NationaliteitInhoud(this);
        }

        /**
         * @param param the nationaliteitCode to set
         * @return builder object
         */
        public Builder nationaliteitCode(final Lo3NationaliteitCode param) {
            nationaliteitCode = param;
            return this;
        }

        /**
         * @param param the redenVerkrijgingNederlandschapCode to set
         * @return builder object
         */
        public Builder redenVerkrijgingNederlandschapCode(final Lo3RedenNederlandschapCode param) {
            redenVerkrijgingNederlandschapCode = param;
            return this;
        }

        /**
         * @param param the redenVerliesNederlandschapCode to set
         * @return builder object
         */
        public Builder redenVerliesNederlandschapCode(final Lo3RedenNederlandschapCode param) {
            redenVerliesNederlandschapCode = param;
            return this;
        }

        /**
         * @param param the aanduidingBijzonderNederlandschap to set
         * @return builder object
         */
        public Builder aanduidingBijzonderNederlandschap(final Lo3AanduidingBijzonderNederlandschap param) {
            aanduidingBijzonderNederlandschap = param;
            return this;
        }

        /**
         * @param param het buitenlands persoonsnummer
         * @return builder object
         */
        public Builder buitenlandsPersoonsnummer(final Lo3String param) {
            buitenlandsPersoonsnummer = param;
            return this;
        }

        /**
         * Reset categorie 05, 63, 64, 65 en 73. Aan te roepen alvorens een van deze categorien wordt gevuld. Zie
         * ORANJE-1817
         */
        public void resetElkaarUitsluitendeVelden() {
            nationaliteitCode = null;
            redenVerkrijgingNederlandschapCode = null;
            redenVerliesNederlandschapCode = null;
            aanduidingBijzonderNederlandschap = null;
            buitenlandsPersoonsnummer = null;
        }
    }
}
