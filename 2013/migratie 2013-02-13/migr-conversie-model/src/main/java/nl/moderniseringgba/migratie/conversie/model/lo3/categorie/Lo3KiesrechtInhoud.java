/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van LO3 categorie Kiesrecht.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3KiesrechtInhoud implements Lo3CategorieInhoud {

    // 31 Europees kiesrecht
    @Element(name = "aanduidingEuropeesKiesrecht", required = false)
    private final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht;
    @Element(name = "datumEuropeesKiesrecht", required = false)
    private final Lo3Datum datumEuropeesKiesrecht;
    @Element(name = "einddatumUitsluitingEuropeesKiesrecht", required = false)
    private final Lo3Datum einddatumUitsluitingEuropeesKiesrecht;

    // 38 Uitsluiting kiesrecht
    @Element(name = "aanduidingUitgeslotenKiesrecht", required = false)
    private final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht;
    @Element(name = "einddatumUitsluitingKiesrecht", required = false)
    private final Lo3Datum einddatumUitsluitingKiesrecht;

    /**
     * Default constructor (alles null).
     */
    public Lo3KiesrechtInhoud() {
        this(null, null, null, null, null);
    }

    /**
     * Maakt een Lo3KiesrechtInhoud.
     * 
     * @param aanduidingEuropeesKiesrecht
     *            aanduiding europees kiesrecht
     * @param datumEuropeesKiesrecht
     *            datum europees kiesrect
     * @param einddatumUitsluitingEuropeesKiesrecht
     *            einddatum uitsluiting europees kiesrecht
     * @param aanduidingUitgeslotenKiesrecht
     *            aanduiding uitgesloten kiesrecht
     * @param einddatumUitsluitingKiesrecht
     *            einddatum uitsluiting kiesrecht
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie13Kiesrecht}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn {@link Lo3CategorieValidator#valideerCategorie13Kiesrecht}
     */
    public Lo3KiesrechtInhoud(
            @Element(name = "aanduidingEuropeesKiesrecht", required = false) final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht,
            @Element(name = "datumEuropeesKiesrecht", required = false) final Lo3Datum datumEuropeesKiesrecht,
            @Element(name = "einddatumUitsluitingEuropeesKiesrecht", required = false) final Lo3Datum einddatumUitsluitingEuropeesKiesrecht,
            @Element(name = "aanduidingUitgeslotenKiesrecht", required = false) final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht,
            @Element(name = "einddatumUitsluitingKiesrecht", required = false) final Lo3Datum einddatumUitsluitingKiesrecht) {
        this.aanduidingEuropeesKiesrecht = aanduidingEuropeesKiesrecht;
        this.datumEuropeesKiesrecht = datumEuropeesKiesrecht;
        this.einddatumUitsluitingEuropeesKiesrecht = einddatumUitsluitingEuropeesKiesrecht;
        this.aanduidingUitgeslotenKiesrecht = aanduidingUitgeslotenKiesrecht;
        this.einddatumUitsluitingKiesrecht = einddatumUitsluitingKiesrecht;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(aanduidingEuropeesKiesrecht, datumEuropeesKiesrecht,
                einddatumUitsluitingEuropeesKiesrecht, aanduidingUitgeslotenKiesrecht, einddatumUitsluitingKiesrecht);
    }

    public Lo3AanduidingEuropeesKiesrecht getAanduidingEuropeesKiesrecht() {
        return aanduidingEuropeesKiesrecht;
    }

    public Lo3AanduidingUitgeslotenKiesrecht getAanduidingUitgeslotenKiesrecht() {
        return aanduidingUitgeslotenKiesrecht;
    }

    public Lo3Datum getDatumEuropeesKiesrecht() {
        return datumEuropeesKiesrecht;
    }

    public Lo3Datum getEinddatumUitsluitingEuropeesKiesrecht() {
        return einddatumUitsluitingEuropeesKiesrecht;
    }

    public Lo3Datum getEinddatumUitsluitingKiesrecht() {
        return einddatumUitsluitingKiesrecht;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3KiesrechtInhoud)) {
            return false;
        }
        final Lo3KiesrechtInhoud castOther = (Lo3KiesrechtInhoud) other;
        return new EqualsBuilder().append(aanduidingEuropeesKiesrecht, castOther.aanduidingEuropeesKiesrecht)
                .append(datumEuropeesKiesrecht, castOther.datumEuropeesKiesrecht)
                .append(einddatumUitsluitingEuropeesKiesrecht, castOther.einddatumUitsluitingEuropeesKiesrecht)
                .append(aanduidingUitgeslotenKiesrecht, castOther.aanduidingUitgeslotenKiesrecht)
                .append(einddatumUitsluitingKiesrecht, castOther.einddatumUitsluitingKiesrecht).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingEuropeesKiesrecht).append(datumEuropeesKiesrecht)
                .append(einddatumUitsluitingEuropeesKiesrecht).append(aanduidingUitgeslotenKiesrecht)
                .append(einddatumUitsluitingKiesrecht).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("aanduidingEuropeesKiesrecht", aanduidingEuropeesKiesrecht)
                .append("datumEuropeesKiesrecht", datumEuropeesKiesrecht)
                .append("einddatumUitsluitingEuropeesKiesrecht", einddatumUitsluitingEuropeesKiesrecht)
                .append("aanduidingUitgeslotenKiesrecht", aanduidingUitgeslotenKiesrecht)
                .append("einddatumUitsluitingKiesrecht", einddatumUitsluitingKiesrecht).toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht;
        private Lo3Datum datumEuropeesKiesrecht;
        private Lo3Datum einddatumUitsluitingEuropeesKiesrecht;
        private Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht;
        private Lo3Datum einddatumUitsluitingKiesrecht;

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
        public Builder(final Lo3KiesrechtInhoud inhoud) {
            aanduidingEuropeesKiesrecht = inhoud.aanduidingEuropeesKiesrecht;
            datumEuropeesKiesrecht = inhoud.datumEuropeesKiesrecht;
            einddatumUitsluitingEuropeesKiesrecht = inhoud.einddatumUitsluitingEuropeesKiesrecht;
            aanduidingUitgeslotenKiesrecht = inhoud.aanduidingUitgeslotenKiesrecht;
            einddatumUitsluitingKiesrecht = inhoud.einddatumUitsluitingKiesrecht;
        }

        /**
         * Maak de inhoud.
         * 
         * @return inhoud
         */
        public Lo3KiesrechtInhoud build() {
            return new Lo3KiesrechtInhoud(aanduidingEuropeesKiesrecht, datumEuropeesKiesrecht,
                    einddatumUitsluitingEuropeesKiesrecht, aanduidingUitgeslotenKiesrecht,
                    einddatumUitsluitingKiesrecht);
        }

        /**
         * @param aanduidingEuropeesKiesrecht
         *            the aanduidingEuropeesKiesrecht to set
         */
        public void setAanduidingEuropeesKiesrecht(final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht) {
            this.aanduidingEuropeesKiesrecht = aanduidingEuropeesKiesrecht;
        }

        /**
         * @param datumEuropeesKiesrecht
         *            the datumEuropeesKiesrecht to set
         */
        public void setDatumEuropeesKiesrecht(final Lo3Datum datumEuropeesKiesrecht) {
            this.datumEuropeesKiesrecht = datumEuropeesKiesrecht;
        }

        /**
         * @param einddatumUitsluitingEuropeesKiesrecht
         *            the einddatumUitsluitingEuropeesKiesrecht to set
         */
        public void setEinddatumUitsluitingEuropeesKiesrecht(final Lo3Datum einddatumUitsluitingEuropeesKiesrecht) {
            this.einddatumUitsluitingEuropeesKiesrecht = einddatumUitsluitingEuropeesKiesrecht;
        }

        /**
         * @param aanduidingUitgeslotenKiesrecht
         *            the aanduidingUitgeslotenKiesrecht to set
         */
        public void setAanduidingUitgeslotenKiesrecht(
                final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht) {
            this.aanduidingUitgeslotenKiesrecht = aanduidingUitgeslotenKiesrecht;
        }

        /**
         * @param einddatumUitsluitingKiesrecht
         *            the einddatumUitsluitingKiesrecht to set
         */
        public void setEinddatumUitsluitingKiesrecht(final Lo3Datum einddatumUitsluitingKiesrecht) {
            this.einddatumUitsluitingKiesrecht = einddatumUitsluitingKiesrecht;
        }

    }

}
