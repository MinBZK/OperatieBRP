/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van LO3 categorie Kiesrecht.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3KiesrechtInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3110)
    @Element(name = "aanduidingEuropeesKiesrecht", required = false)
    private final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3120)
    @Element(name = "datumEuropeesKiesrecht", required = false)
    private final Lo3Datum datumEuropeesKiesrecht;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3130)
    @Element(name = "einddatumUitsluitingEuropeesKiesrecht", required = false)
    private final Lo3Datum einddatumUitsluitingEuropeesKiesrecht;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3810)
    @Element(name = "aanduidingUitgeslotenKiesrecht", required = false)
    private final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3820)
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
     * @param aanduidingEuropeesKiesrecht aanduiding europees kiesrecht
     * @param datumEuropeesKiesrecht datum europees kiesrect
     * @param einddatumUitsluitingEuropeesKiesrecht einddatum uitsluiting europees kiesrecht
     * @param aanduidingUitgeslotenKiesrecht aanduiding uitgesloten kiesrecht
     * @param einddatumUitsluitingKiesrecht einddatum uitsluiting kiesrecht
     * @throws IllegalArgumentException als niet aan inhoudelijke voorwaarden is voldaan Lo3CategorieValidator#valideerCategorie13Kiesrecht
     * @throws NullPointerException als verplichte velden niet aanwezig zijn Lo3CategorieValidator#valideerCategorie13Kiesrecht
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

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(
                aanduidingEuropeesKiesrecht,
                datumEuropeesKiesrecht,
                einddatumUitsluitingEuropeesKiesrecht,
                aanduidingUitgeslotenKiesrecht,
                einddatumUitsluitingKiesrecht);
    }

    /**
     * Geef de waarde van aanduiding europees kiesrecht van Lo3KiesrechtInhoud.
     * @return de waarde van aanduiding europees kiesrecht van Lo3KiesrechtInhoud
     */
    public Lo3AanduidingEuropeesKiesrecht getAanduidingEuropeesKiesrecht() {
        return aanduidingEuropeesKiesrecht;
    }

    /**
     * Geef de waarde van aanduiding uitgesloten kiesrecht van Lo3KiesrechtInhoud.
     * @return de waarde van aanduiding uitgesloten kiesrecht van Lo3KiesrechtInhoud
     */
    public Lo3AanduidingUitgeslotenKiesrecht getAanduidingUitgeslotenKiesrecht() {
        return aanduidingUitgeslotenKiesrecht;
    }

    /**
     * Geef de waarde van datum europees kiesrecht van Lo3KiesrechtInhoud.
     * @return de waarde van datum europees kiesrecht van Lo3KiesrechtInhoud
     */
    public Lo3Datum getDatumEuropeesKiesrecht() {
        return datumEuropeesKiesrecht;
    }

    /**
     * Geef de waarde van einddatum uitsluiting europees kiesrecht van Lo3KiesrechtInhoud.
     * @return de waarde van einddatum uitsluiting europees kiesrecht van Lo3KiesrechtInhoud
     */
    public Lo3Datum getEinddatumUitsluitingEuropeesKiesrecht() {
        return einddatumUitsluitingEuropeesKiesrecht;
    }

    /**
     * Geef de waarde van einddatum uitsluiting kiesrecht van Lo3KiesrechtInhoud.
     * @return de waarde van einddatum uitsluiting kiesrecht van Lo3KiesrechtInhoud
     */
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
                .append(einddatumUitsluitingKiesrecht, castOther.einddatumUitsluitingKiesrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingEuropeesKiesrecht)
                .append(datumEuropeesKiesrecht)
                .append(einddatumUitsluitingEuropeesKiesrecht)
                .append(aanduidingUitgeslotenKiesrecht)
                .append(einddatumUitsluitingKiesrecht)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aanduidingEuropeesKiesrecht", aanduidingEuropeesKiesrecht)
                .append("datumEuropeesKiesrecht", datumEuropeesKiesrecht)
                .append(
                        "einddatumUitsluitingEuropeesKiesrecht",
                        einddatumUitsluitingEuropeesKiesrecht)
                .append("aanduidingUitgeslotenKiesrecht", aanduidingUitgeslotenKiesrecht)
                .append("einddatumUitsluitingKiesrecht", einddatumUitsluitingKiesrecht)
                .toString();
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
            //lege builder
        }

        /**
         * Maak een buildeer gevud met de gegeven inhoud.
         * @param inhoud inhoud
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
         * @return inhoud
         */
        public Lo3KiesrechtInhoud build() {
            return new Lo3KiesrechtInhoud(
                    aanduidingEuropeesKiesrecht,
                    datumEuropeesKiesrecht,
                    einddatumUitsluitingEuropeesKiesrecht,
                    aanduidingUitgeslotenKiesrecht,
                    einddatumUitsluitingKiesrecht);
        }

        /**
         * Zet de waarden voor aanduiding europees kiesrecht van Lo3KiesrechtInhoud.
         * @param aanduidingEuropeesKiesrecht de nieuwe waarde voor aanduiding europees kiesrecht van Lo3KiesrechtInhoud
         */
        public void setAanduidingEuropeesKiesrecht(final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht) {
            this.aanduidingEuropeesKiesrecht = aanduidingEuropeesKiesrecht;
        }

        /**
         * Zet de waarden voor datum europees kiesrecht van Lo3KiesrechtInhoud.
         * @param datumEuropeesKiesrecht de nieuwe waarde voor datum europees kiesrecht van Lo3KiesrechtInhoud
         */
        public void setDatumEuropeesKiesrecht(final Lo3Datum datumEuropeesKiesrecht) {
            this.datumEuropeesKiesrecht = datumEuropeesKiesrecht;
        }

        /**
         * Zet de waarden voor einddatum uitsluiting europees kiesrecht van Lo3KiesrechtInhoud.
         * @param einddatumUitsluitingEuropeesKiesrecht de nieuwe waarde voor einddatum uitsluiting europees kiesrecht van Lo3KiesrechtInhoud
         */
        public void setEinddatumUitsluitingEuropeesKiesrecht(final Lo3Datum einddatumUitsluitingEuropeesKiesrecht) {
            this.einddatumUitsluitingEuropeesKiesrecht = einddatumUitsluitingEuropeesKiesrecht;
        }

        /**
         * Zet de waarden voor aanduiding uitgesloten kiesrecht van Lo3KiesrechtInhoud.
         * @param aanduidingUitgeslotenKiesrecht de nieuwe waarde voor aanduiding uitgesloten kiesrecht van Lo3KiesrechtInhoud
         */
        public void setAanduidingUitgeslotenKiesrecht(final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht) {
            this.aanduidingUitgeslotenKiesrecht = aanduidingUitgeslotenKiesrecht;
        }

        /**
         * Zet de waarden voor einddatum uitsluiting kiesrecht van Lo3KiesrechtInhoud.
         * @param einddatumUitsluitingKiesrecht de nieuwe waarde voor einddatum uitsluiting kiesrecht van Lo3KiesrechtInhoud
         */
        public void setEinddatumUitsluitingKiesrecht(final Lo3Datum einddatumUitsluitingKiesrecht) {
            this.einddatumUitsluitingKiesrecht = einddatumUitsluitingKiesrecht;
        }

    }

}
