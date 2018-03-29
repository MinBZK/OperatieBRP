/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep immigratie.
 *
 * Deze class is immutable en thread safe.
 */
public final class BrpMigratieInhoud extends AbstractBrpGroepInhoud {
    private static final int MAX_BUITENLANDS_ADRES_REGEL_LENGTE = 35;

    @Element(name = "soortMigratieCode", required = false)
    private final BrpSoortMigratieCode soortMigratieCode;
    @Element(name = "redenWijzigingMigratieCode", required = false)
    private final BrpRedenWijzigingVerblijfCode redenWijzigingMigratieCode;
    @Element(name = "aangeverMigratieCode", required = false)
    private final BrpAangeverCode aangeverMigratieCode;
    @Element(name = "landOfGebiedCodeMigratie", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCode;
    @Element(name = "buitenlandsAdresRegel1", required = false)
    private final BrpString buitenlandsAdresRegel1;
    @Element(name = "buitenlandsAdresRegel2", required = false)
    private final BrpString buitenlandsAdresRegel2;
    @Element(name = "buitenlandsAdresRegel3", required = false)
    private final BrpString buitenlandsAdresRegel3;
    @Element(name = "buitenlandsAdresRegel4", required = false)
    private final BrpString buitenlandsAdresRegel4;
    @Element(name = "buitenlandsAdresRegel5", required = false)
    private final BrpString buitenlandsAdresRegel5;
    @Element(name = "buitenlandsAdresRegel6", required = false)
    private final BrpString buitenlandsAdresRegel6;

    /**
     * Maak een BrpMigratieInhoud object.
     * @param soortMigratieCode soort migratie code
     * @param redenWijzigingMigratieCode reden wijziging code
     * @param aangeverMigratieCode aangever adreshouding code
     * @param landOfGebiedCode land of gebied
     * @param buitenlandsAdresRegel1 buitenlands adres regel 1
     * @param buitenlandsAdresRegel2 buitenlands adres regel 2
     * @param buitenlandsAdresRegel3 buitenlands adres regel 3
     * @param buitenlandsAdresRegel4 buitenlands adres regel 4
     * @param buitenlandsAdresRegel5 buitenlands adres regel 5
     * @param buitenlandsAdresRegel6 buitenlands adres regel 6
     */
    public BrpMigratieInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "soortMigratieCode", required = false) final BrpSoortMigratieCode soortMigratieCode,
        @Element(name = "redenWijzigingMigratieCode", required = false) final BrpRedenWijzigingVerblijfCode redenWijzigingMigratieCode,
        @Element(name = "aangeverMigratieCode", required = false) final BrpAangeverCode aangeverMigratieCode,
        @Element(name = "landOfGebiedCodeMigratie", required = false) final BrpLandOfGebiedCode landOfGebiedCode,
        @Element(name = "buitenlandsAdresRegel1", required = false) final BrpString buitenlandsAdresRegel1,
        @Element(name = "buitenlandsAdresRegel2", required = false) final BrpString buitenlandsAdresRegel2,
        @Element(name = "buitenlandsAdresRegel3", required = false) final BrpString buitenlandsAdresRegel3,
        @Element(name = "buitenlandsAdresRegel4", required = false) final BrpString buitenlandsAdresRegel4,
        @Element(name = "buitenlandsAdresRegel5", required = false) final BrpString buitenlandsAdresRegel5,
        @Element(name = "buitenlandsAdresRegel6", required = false) final BrpString buitenlandsAdresRegel6) {
        this.soortMigratieCode = soortMigratieCode;
        this.redenWijzigingMigratieCode = redenWijzigingMigratieCode;
        this.aangeverMigratieCode = aangeverMigratieCode;
        this.landOfGebiedCode = landOfGebiedCode;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    private BrpMigratieInhoud(final Builder builder) {
        soortMigratieCode = builder.soortMigratieCode;
        landOfGebiedCode = builder.landOfGebied;
        redenWijzigingMigratieCode = builder.redenWijzigingMigratieCode;
        aangeverMigratieCode = builder.aangeverMigratieCode;
        buitenlandsAdresRegel1 = builder.buitenlandsAdresRegel1;
        buitenlandsAdresRegel2 = builder.buitenlandsAdresRegel2;
        buitenlandsAdresRegel3 = builder.buitenlandsAdresRegel3;
        buitenlandsAdresRegel4 = builder.buitenlandsAdresRegel4;
        buitenlandsAdresRegel5 = builder.buitenlandsAdresRegel5;
        buitenlandsAdresRegel6 = builder.buitenlandsAdresRegel6;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(soortMigratieCode, landOfGebiedCode);
    }

    @Preconditie({SoortMeldingCode.PRE095, SoortMeldingCode.PRE096})
    @Override
    public void valideer() {
        // PRE095
        if (BrpString.unwrap(buitenlandsAdresRegel4) != null
                || BrpString.unwrap(buitenlandsAdresRegel5) != null
                || BrpString.unwrap(buitenlandsAdresRegel6) != null) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE095, this);
        }
        // PRE096
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel1));
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel2));
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel3));
    }

    private void valideerTekstLengte(final String tekst) {
        if (tekst == null) {
            return;
        }

        if (tekst.length() > MAX_BUITENLANDS_ADRES_REGEL_LENGTE) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE096, this);
        }
    }

    /**
     * Geef de waarde van land of gebied code van BrpMigratieInhoud.
     * @return de waarde van land of gebied code van BrpMigratieInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van soort migratie code van BrpMigratieInhoud.
     * @return de waarde van soort migratie code van BrpMigratieInhoud
     */
    public BrpSoortMigratieCode getSoortMigratieCode() {
        return soortMigratieCode;
    }

    /**
     * Geef de waarde van reden wijziging migratie code van BrpMigratieInhoud.
     * @return de waarde van reden wijziging migratie code van BrpMigratieInhoud
     */
    public BrpRedenWijzigingVerblijfCode getRedenWijzigingMigratieCode() {
        return redenWijzigingMigratieCode;
    }

    /**
     * Geef de waarde van aangever migratie code van BrpMigratieInhoud.
     * @return de waarde van aangever migratie code van BrpMigratieInhoud
     */
    public BrpAangeverCode getAangeverMigratieCode() {
        return aangeverMigratieCode;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel1 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel2 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel3 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel4 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel5 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 van BrpMigratieInhoud.
     * @return de waarde van buitenlands adres regel6 van BrpMigratieInhoud
     */
    public BrpString getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpMigratieInhoud)) {
            return false;
        }
        final BrpMigratieInhoud castOther = (BrpMigratieInhoud) other;
        return new EqualsBuilder().append(soortMigratieCode, castOther.soortMigratieCode)
                .append(redenWijzigingMigratieCode, castOther.redenWijzigingMigratieCode)
                .append(aangeverMigratieCode, castOther.aangeverMigratieCode)
                .append(landOfGebiedCode, castOther.landOfGebiedCode)
                .append(buitenlandsAdresRegel1, castOther.buitenlandsAdresRegel1)
                .append(buitenlandsAdresRegel2, castOther.buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3, castOther.buitenlandsAdresRegel3)
                .append(buitenlandsAdresRegel4, castOther.buitenlandsAdresRegel4)
                .append(buitenlandsAdresRegel5, castOther.buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6, castOther.buitenlandsAdresRegel6)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(landOfGebiedCode)
                .append(soortMigratieCode)
                .append(redenWijzigingMigratieCode)
                .append(aangeverMigratieCode)
                .append(buitenlandsAdresRegel1)
                .append(buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3)
                .append(buitenlandsAdresRegel4)
                .append(buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("soortMigratieCode", soortMigratieCode)
                .append("redenWijzigingMigratieCode", redenWijzigingMigratieCode)
                .append("aangeverMigratieCode", aangeverMigratieCode)
                .append("landOfGebiedCodeMigratie", landOfGebiedCode)
                .append("buitenlandsAdresRegel1", buitenlandsAdresRegel1)
                .append("buitenlandsAdresRegel2", buitenlandsAdresRegel2)
                .append("buitenlandsAdresRegel3", buitenlandsAdresRegel3)
                .append("buitenlandsAdresRegel4", buitenlandsAdresRegel4)
                .append("buitenlandsAdresRegel5", buitenlandsAdresRegel5)
                .append("buitenlandsAdresRegel6", buitenlandsAdresRegel6)
                .toString();
    }

    /**
     * Builder object voor BrpMigratieInhoud.
     */
    public static final class Builder {
        private final BrpSoortMigratieCode soortMigratieCode;
        private BrpRedenWijzigingVerblijfCode redenWijzigingMigratieCode;
        private BrpAangeverCode aangeverMigratieCode;
        private final BrpLandOfGebiedCode landOfGebied;
        private BrpString buitenlandsAdresRegel1;
        private BrpString buitenlandsAdresRegel2;
        private BrpString buitenlandsAdresRegel3;
        private BrpString buitenlandsAdresRegel4;
        private BrpString buitenlandsAdresRegel5;
        private BrpString buitenlandsAdresRegel6;

        /**
         * Default constructor met alle verplichte velden.
         * @param soortMigratieCode soort migratie code
         * @param landOfGebiedCode land of gebied code
         */
        public Builder(final BrpSoortMigratieCode soortMigratieCode, final BrpLandOfGebiedCode landOfGebiedCode) {
            this.soortMigratieCode = soortMigratieCode;
            landOfGebied = landOfGebiedCode;
        }

        /**
         * Zet de reden wijziging migratie.
         * @param param code
         * @return builder object
         */
        public Builder redenWijzigingMigratieCode(final BrpRedenWijzigingVerblijfCode param) {
            redenWijzigingMigratieCode = param;
            return this;
        }

        /**
         * Zet de aangever migratie.
         * @param param code
         * @return builder object
         */
        public Builder aangeverMigratieCode(final BrpAangeverCode param) {
            aangeverMigratieCode = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 1.
         * @param param het buitenlands adres regel 1
         * @return builder object
         */
        public Builder buitenlandsAdresRegel1(final BrpString param) {
            buitenlandsAdresRegel1 = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 2.
         * @param param het buitenlands adres regel 2
         * @return builder object
         */
        public Builder buitenlandsAdresRegel2(final BrpString param) {
            buitenlandsAdresRegel2 = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 3.
         * @param param het buitenlands adres regel 3
         * @return builder object
         */
        public Builder buitenlandsAdresRegel3(final BrpString param) {
            buitenlandsAdresRegel3 = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 4.
         * @param param het buitenlands adres regel 4
         * @return builder object
         */
        public Builder buitenlandsAdresRegel4(final BrpString param) {
            buitenlandsAdresRegel4 = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 5.
         * @param param het buitenlands adres regel 5
         * @return builder object
         */
        public Builder buitenlandsAdresRegel5(final BrpString param) {
            buitenlandsAdresRegel5 = param;
            return this;
        }

        /**
         * Zet buitenlands adres regel 6.
         * @param param het buitenlands adres regel 6
         * @return builder object
         */
        public Builder buitenlandsAdresRegel6(final BrpString param) {
            buitenlandsAdresRegel6 = param;
            return this;
        }

        /**
         * @return Geeft de {@link BrpMigratieInhoud} terug
         */
        public BrpMigratieInhoud build() {
            return new BrpMigratieInhoud(this);
        }
    }
}
