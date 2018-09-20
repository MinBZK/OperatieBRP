/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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

    // 05 Nationaliteit
    @Element(name = "nationaliteitCode", required = false)
    private final Lo3NationaliteitCode nationaliteitCode;

    // 63 Verkrijging Nederlanderschap
    @Element(name = "redenVerkrijgingNederlandschapCode", required = false)
    private final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode;

    // 64 Verlies Nederlanderschap
    @Element(name = "redenVerliesNederlandschapCode", required = false)
    private final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode;

    // 65 Bijzonder Nederlanderschap
    @Element(name = "aanduidingBijzonderNederlandschap", required = false)
    private final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap;

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
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie04Nationaliteit}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie04Nationaliteit}
     */
    public Lo3NationaliteitInhoud(
            @Element(name = "nationaliteitCode", required = false) final Lo3NationaliteitCode nationaliteitCode,
            @Element(name = "redenVerkrijgingNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode,
            @Element(name = "redenVerliesNederlandschapCode", required = false) final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode,
            @Element(name = "aanduidingBijzonderNederlandschap", required = false) final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap) {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        this.aanduidingBijzonderNederlandschap = aanduidingBijzonderNederlandschap;
    }

    /*
     * Een Lo3NationaliteitInhoud is leeg als nationaliteitCode, redenVerkrijgingNederlandschapCode en
     * aanduidingBijzonderNederlandschap velden leeg zijn, redenVerliesNederlandschapCode mag wel gevuld zijn.
     */
    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(nationaliteitCode, redenVerkrijgingNederlandschapCode,
                aanduidingBijzonderNederlandschap);
    }

    /**
     * @return de nationaliteit code of null
     */
    public Lo3NationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * @return de reden verkrijging nederlandschap code, of null
     */
    public Lo3RedenNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    /**
     * @return de reden verlies nederlandschap code, of null
     */
    public Lo3RedenNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
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
                .append(aanduidingBijzonderNederlandschap, castOther.aanduidingBijzonderNederlandschap).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nationaliteitCode).append(redenVerkrijgingNederlandschapCode)
                .append(redenVerliesNederlandschapCode).append(aanduidingBijzonderNederlandschap).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("nationaliteitCode", nationaliteitCode)
                .append("redenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode)
                .append("redenVerliesNederlandschapCode", redenVerliesNederlandschapCode)
                .append("aanduidingBijzonderNederlandschap", aanduidingBijzonderNederlandschap).toString();
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
         * Maak een buildeer gevud met de gegeven inhoud.
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
            return new Lo3NationaliteitInhoud(nationaliteitCode, redenVerkrijgingNederlandschapCode,
                    redenVerliesNederlandschapCode, aanduidingBijzonderNederlandschap);
        }

        /**
         * @param nationaliteitCode
         *            the nationaliteitCode to set
         */
        public void setNationaliteitCode(final Lo3NationaliteitCode nationaliteitCode) {
            this.nationaliteitCode = nationaliteitCode;
        }

        /**
         * @param redenVerkrijgingNederlandschapCode
         *            the redenVerkrijgingNederlandschapCode to set
         */
        public void setRedenVerkrijgingNederlandschapCode(
                final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode) {
            this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        }

        /**
         * @param redenVerliesNederlandschapCode
         *            the redenVerliesNederlandschapCode to set
         */
        public void
                setRedenVerliesNederlandschapCode(final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode) {
            this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        }

        /**
         * @param aanduidingBijzonderNederlandschap
         *            the aanduidingBijzonderNederlandschap to set
         */
        public void setAanduidingBijzonderNederlandschap(
                final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap) {
            this.aanduidingBijzonderNederlandschap = aanduidingBijzonderNederlandschap;
        }

    }

}
