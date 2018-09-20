/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class geeft de inhoud weer van een LO3 Categorie 04/54 Nationaliteit.
 * 
 * Deze class is immutable en threadsafe.
 * 
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

    /**
     * Maak een lege nationaliteit aan.
     */
    public Lo3NationaliteitInhoud() {
        this(null, null, null, null);
    }

    /**
     * Maakt een Lo3NationaliteitInhoud object.
     * 
     * @param nationaliteitCode
     *            nationaliteit code 05.10 of null
     * @param redenVerkrijgingNederlandschapCode
     *            reden verkrijging Nederlandschap 63.10 of null
     * @param redenVerliesNederlandschapCode
     *            reden verlies nederlandschap 64.10 of null
     * @param aanduidingBijzonderNederlandschap
     *            aanduiding bijzonder nederlandschap of null
     */
    public Lo3NationaliteitInhoud(@Element(name = "nationaliteitCode", required = false) final Lo3NationaliteitCode nationaliteitCode, @Element(
            name = "redenVerkrijgingNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode, @Element(
            name = "redenVerliesNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode, @Element(
            name = "aanduidingBijzonderNederlandschap", required = false) final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap)
    {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        this.aanduidingBijzonderNederlandschap = aanduidingBijzonderNederlandschap;
    }

    private Lo3NationaliteitInhoud(final Builder builder) {
        nationaliteitCode = builder.nationaliteitCode;
        redenVerkrijgingNederlandschapCode = builder.redenVerkrijgingNederlandschapCode;
        redenVerliesNederlandschapCode = builder.redenVerliesNederlandschapCode;
        aanduidingBijzonderNederlandschap = builder.aanduidingBijzonderNederlandschap;
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
        return !Validatie.isEenParameterGevuld(nationaliteitCode, redenVerkrijgingNederlandschapCode, aanduidingBijzonderNederlandschap);
    }

    /**
     * Geef de waarde van nationaliteit code.
     *
     * @return de nationaliteit code of null
     */
    public Lo3NationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * Geef de waarde van reden verkrijging nederlandschap code.
     *
     * @return de reden verkrijging nederlandschap code, of null
     */
    public Lo3RedenNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    /**
     * Geef de waarde van reden verlies nederlandschap code.
     *
     * @return de reden verlies nederlandschap code, of null
     */
    public Lo3RedenNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
     * Geef de waarde van aanduiding bijzonder nederlandschap.
     *
     * @return de aanduiding bijzonder nederlandschap, of null
     */
    public Lo3AanduidingBijzonderNederlandschap getAanduidingBijzonderNederlandschap() {
        return aanduidingBijzonderNederlandschap;
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
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nationaliteitCode)
                                    .append(redenVerkrijgingNederlandschapCode)
                                    .append(redenVerliesNederlandschapCode)
                                    .append(aanduidingBijzonderNederlandschap)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("nationaliteitCode", nationaliteitCode)
                                                                          .append("redenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode)
                                                                          .append("redenVerliesNederlandschapCode", redenVerliesNederlandschapCode)
                                                                          .append("aanduidingBijzonderNederlandschap", aanduidingBijzonderNederlandschap)
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

        /**
         * Maak een lege builder.
         */
        public Builder() {
        }

        /**
         * Maak een builder gevuld met de gegeven inhoud.
         * 
         * @param inhoud
         *            inhoud
         */
        public Builder(final Lo3NationaliteitInhoud inhoud) {
            nationaliteitCode = inhoud.nationaliteitCode;
            redenVerkrijgingNederlandschapCode = inhoud.redenVerkrijgingNederlandschapCode;
            redenVerliesNederlandschapCode = inhoud.redenVerliesNederlandschapCode;
            aanduidingBijzonderNederlandschap = inhoud.aanduidingBijzonderNederlandschap;
        }

        /**
         * Maak de inhoud.
         * 
         * @return inhoud
         */
        public Lo3NationaliteitInhoud build() {
            return new Lo3NationaliteitInhoud(this);
        }

        /**
         * @param param
         *            the nationaliteitCode to set
         * @return builder object
         */
        public Builder nationaliteitCode(final Lo3NationaliteitCode param) {
            this.nationaliteitCode = param;
            return this;
        }

        /**
         * @param param
         *            the redenVerkrijgingNederlandschapCode to set
         * @return builder object
         */
        public Builder redenVerkrijgingNederlandschapCode(final Lo3RedenNederlandschapCode param) {
            this.redenVerkrijgingNederlandschapCode = param;
            return this;
        }

        /**
         * @param param
         *            the redenVerliesNederlandschapCode to set
         * @return builder object
         */
        public Builder redenVerliesNederlandschapCode(final Lo3RedenNederlandschapCode param) {
            this.redenVerliesNederlandschapCode = param;
            return this;
        }

        /**
         * @param param
         *            the aanduidingBijzonderNederlandschap to set
         * @return builder object
         */
        public Builder aanduidingBijzonderNederlandschap(final Lo3AanduidingBijzonderNederlandschap param) {
            this.aanduidingBijzonderNederlandschap = param;
            return this;
        }

        /**
         * Reset categorie 05, 63, 64 en 65. Aan te roepen alvorens een van deze categorien wordt gevuld. Zie
         * ORANJE-1817
         */
        public void resetElkaarUitsluitendeVelden() {
            nationaliteitCode = null;
            redenVerkrijgingNederlandschapCode = null;
            redenVerliesNederlandschapCode = null;
            aanduidingBijzonderNederlandschap = null;
        }
    }
}
