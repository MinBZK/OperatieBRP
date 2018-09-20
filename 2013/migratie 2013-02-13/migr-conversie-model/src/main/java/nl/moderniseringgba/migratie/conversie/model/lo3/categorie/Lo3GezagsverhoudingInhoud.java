/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de LO3 categorie Gezagsverhouding (11).
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3GezagsverhoudingInhoud implements Lo3CategorieInhoud {

    // 32 Gezag minderjarige
    @Element(name = "indicatieGezagMinderjarige", required = false)
    private final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige;

    // 33 Curatele
    @Element(name = "indicatieCurateleregister", required = false)
    private final Lo3IndicatieCurateleregister indicatieCurateleregister;

    /**
     * Default constructor (alles null).
     */
    public Lo3GezagsverhoudingInhoud() {
        this(null, null);
    }

    /**
     * Maakt een Lo3GezagsverhoudingInhoud.
     * 
     * @param indicatieGezagMinderjarige
     *            indicatie gezag minderjarige
     * @param indicatieCurateleregister
     *            indicatie curatele register
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie11Gezagsverhouding}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie11Gezagsverhouding}
     */
    public Lo3GezagsverhoudingInhoud(
            @Element(name = "indicatieGezagMinderjarige", required = false) final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige,
            @Element(name = "indicatieCurateleregister", required = false) final Lo3IndicatieCurateleregister indicatieCurateleregister) {
        this.indicatieGezagMinderjarige = indicatieGezagMinderjarige;
        this.indicatieCurateleregister = indicatieCurateleregister;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(indicatieCurateleregister, indicatieGezagMinderjarige);
    }

    public Lo3IndicatieCurateleregister getIndicatieCurateleregister() {
        return indicatieCurateleregister;
    }

    public Lo3IndicatieGezagMinderjarige getIndicatieGezagMinderjarige() {
        return indicatieGezagMinderjarige;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3GezagsverhoudingInhoud)) {
            return false;
        }
        final Lo3GezagsverhoudingInhoud castOther = (Lo3GezagsverhoudingInhoud) other;
        return new EqualsBuilder().append(indicatieGezagMinderjarige, castOther.indicatieGezagMinderjarige)
                .append(indicatieCurateleregister, castOther.indicatieCurateleregister).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieGezagMinderjarige).append(indicatieCurateleregister)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("indicatieGezagMinderjarige", indicatieGezagMinderjarige)
                .append("indicatieCurateleregister", indicatieCurateleregister).toString();
    }

    /** Builder. */
    public static final class Builder {

        private Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige;
        private Lo3IndicatieCurateleregister indicatieCurateleregister;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initeel gevulde builder.
         * 
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final Lo3GezagsverhoudingInhoud inhoud) {
            indicatieGezagMinderjarige = inhoud.indicatieGezagMinderjarige;
            indicatieCurateleregister = inhoud.indicatieCurateleregister;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3GezagsverhoudingInhoud build() {
            return new Lo3GezagsverhoudingInhoud(indicatieGezagMinderjarige, indicatieCurateleregister);
        }

        /**
         * @param indicatieGezagMinderjarige
         *            the indicatieGezagMinderjarige to set
         */
        public void setIndicatieGezagMinderjarige(final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige) {
            this.indicatieGezagMinderjarige = indicatieGezagMinderjarige;
        }

        /**
         * @param indicatieCurateleregister
         *            the indicatieCurateleregister to set
         */
        public void setIndicatieCurateleregister(final Lo3IndicatieCurateleregister indicatieCurateleregister) {
            this.indicatieCurateleregister = indicatieCurateleregister;
        }

    }
}
