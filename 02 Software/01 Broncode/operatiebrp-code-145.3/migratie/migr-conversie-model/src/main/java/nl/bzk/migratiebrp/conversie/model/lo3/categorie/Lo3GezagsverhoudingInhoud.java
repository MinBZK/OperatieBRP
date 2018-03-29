/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de LO3 categorie Gezagsverhouding (11).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3GezagsverhoudingInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3210)
    @Element(name = "indicatieGezagMinderjarige", required = false)
    private final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3310)
    @Element(name = "indicatieCurateleregister", required = false)
    private final Lo3IndicatieCurateleregister indicatieCurateleregister;

    private boolean isVoorkomenGebruiktVoorOuder1;
    private boolean isVoorkomenGebruiktVoorOuder2;

    /**
     * Default constructor (alles null).
     */
    public Lo3GezagsverhoudingInhoud() {
        this(null, null);
    }

    /**
     * Maakt een Lo3GezagsverhoudingInhoud.
     * @param indicatieGezagMinderjarige indicatie gezag minderjarige
     * @param indicatieCurateleregister indicatie curatele register
     * @throws IllegalArgumentException als niet aan inhoudelijke voorwaarden is voldaan Lo3CategorieValidator#valideerCategorie11Gezagsverhouding
     * @throws NullPointerException als verplichte velden niet aanwezig zijn Lo3CategorieValidator#valideerCategorie11Gezagsverhouding
     */
    public Lo3GezagsverhoudingInhoud(
            @Element(name = "indicatieGezagMinderjarige", required = false) final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige,
            @Element(name = "indicatieCurateleregister", required = false) final Lo3IndicatieCurateleregister indicatieCurateleregister) {
        this.indicatieGezagMinderjarige = indicatieGezagMinderjarige;
        this.indicatieCurateleregister = indicatieCurateleregister;
        this.isVoorkomenGebruiktVoorOuder1 = false;
        this.isVoorkomenGebruiktVoorOuder2 = false;
    }

    private Lo3GezagsverhoudingInhoud(final Builder builder) {
        indicatieGezagMinderjarige = builder.indicatieGezagMinderjarige;
        indicatieCurateleregister = builder.indicatieCurateleregister;
        this.isVoorkomenGebruiktVoorOuder1 = false;
        this.isVoorkomenGebruiktVoorOuder2 = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(indicatieCurateleregister, indicatieGezagMinderjarige);
    }

    /**
     * Geef de waarde van indicatie curateleregister van Lo3GezagsverhoudingInhoud.
     * @return de waarde van indicatie curateleregister van Lo3GezagsverhoudingInhoud
     */
    public Lo3IndicatieCurateleregister getIndicatieCurateleregister() {
        return indicatieCurateleregister;
    }

    /**
     * Geef de waarde van indicatie gezag minderjarige van Lo3GezagsverhoudingInhoud.
     * @return de waarde van indicatie gezag minderjarige van Lo3GezagsverhoudingInhoud
     */
    public Lo3IndicatieGezagMinderjarige getIndicatieGezagMinderjarige() {
        return indicatieGezagMinderjarige;
    }

    public boolean isVoorkomenGebruiktVoorOuder1() {
        return isVoorkomenGebruiktVoorOuder1;
    }

    public boolean isVoorkomenGebruiktVoorOuder2() {
        return isVoorkomenGebruiktVoorOuder2;
    }

    /**
     * Geeft aan of deze inhoud gebruikt is tijdens conversie voor ouder1.
     */
    public void setVoorkomenGebruiktVoorOuder1() {
        this.isVoorkomenGebruiktVoorOuder1 = true;
    }

    /**
     * Geeft aan of deze inhoud gebruikt is tijdens conversie voor ouder2.
     */
    public void setVoorkomenGebruiktVoorOuder2() {
        this.isVoorkomenGebruiktVoorOuder2 = true;
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
                .append(indicatieCurateleregister, castOther.indicatieCurateleregister)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieGezagMinderjarige).append(indicatieCurateleregister).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("indicatieGezagMinderjarige", indicatieGezagMinderjarige)
                .append("indicatieCurateleregister", indicatieCurateleregister)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {

        private Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige;
        private Lo3IndicatieCurateleregister indicatieCurateleregister;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            // Een lege builder.
        }

        /**
         * Maak een initeel gevulde builder.
         * @param inhoud initiele vulling
         */
        public Builder(final Lo3GezagsverhoudingInhoud inhoud) {
            indicatieGezagMinderjarige = inhoud.indicatieGezagMinderjarige;
            indicatieCurateleregister = inhoud.indicatieCurateleregister;
        }

        /**
         * Build.
         * @return inhoud
         */
        public Lo3GezagsverhoudingInhoud build() {
            return new Lo3GezagsverhoudingInhoud(this);
        }

        /**
         * @param param the indicatieGezagMinderjarige to set
         * @return de builder
         */
        public Builder indicatieGezagMinderjarige(final Lo3IndicatieGezagMinderjarige param) {
            indicatieGezagMinderjarige = param;
            return this;
        }

        /**
         * @param param the indicatieCurateleregister to set
         * @return de builder
         */
        public Builder indicatieCurateleregister(final Lo3IndicatieCurateleregister param) {
            indicatieCurateleregister = param;
            return this;
        }

    }
}
