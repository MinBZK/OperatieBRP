/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de LO3 categorie Verblijfstitel (10).
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class Lo3VerblijfstitelInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3910)
    @Element(name = "aanduidingVerblijfstitelCode", required = false)
    private final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3920)
    @Element(name = "datumEindeVerblijfstitel", required = false)
    private final Lo3Datum datumEindeVerblijfstitel;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_3930)
    @Element(name = "datumAanvangVerblijfstitel", required = false)
    private final Lo3Datum datumAanvangVerblijfstitel;

    /**
     * Default constructor (alles null).
     */
    public Lo3VerblijfstitelInhoud() {
        this(null, null, null);
    }

    /**
     * Maakt een Lo3VerblijfstitelInhoud object.
     *
     * @param aanduidingVerblijfstitelCode
     *            de aanduiding verblijfstitel code, mag niet null zijn
     * @param datumEindeVerblijfstitel
     *            de datum einde verblijftitel, mag null zijn
     * @param datumAanvangVerblijfstitel
     *            de datum aanvang verblijfstitel, mag niet null zijn
     */
    public Lo3VerblijfstitelInhoud(
        @Element(name = "aanduidingVerblijfstitelCode", required = false) final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode,
        @Element(name = "datumEindeVerblijfstitel", required = false) final Lo3Datum datumEindeVerblijfstitel,
        @Element(name = "datumAanvangVerblijfstitel", required = false) final Lo3Datum datumAanvangVerblijfstitel)
    {
        this.aanduidingVerblijfstitelCode = aanduidingVerblijfstitelCode;
        this.datumEindeVerblijfstitel = datumEindeVerblijfstitel;
        this.datumAanvangVerblijfstitel = datumAanvangVerblijfstitel;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel, datumAanvangVerblijfstitel);
    }

    /**
     * Geef de waarde van aanduiding verblijfstitel code.
     *
     * @return the aanduidingVerblijfstitelCode
     */
    public Lo3AanduidingVerblijfstitelCode getAanduidingVerblijfstitelCode() {
        return aanduidingVerblijfstitelCode;
    }

    /**
     * Geef de waarde van datum einde verblijfstitel.
     *
     * @return the datumEindeVerblijfstitel, of null
     */
    public Lo3Datum getDatumEindeVerblijfstitel() {
        return datumEindeVerblijfstitel;
    }

    /**
     * Geef de waarde van aanvangsdatum verblijfstitel.
     *
     * @return the datumAanvangVerblijfstitel
     */
    public Lo3Datum getDatumAanvangVerblijfstitel() {
        return datumAanvangVerblijfstitel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3VerblijfstitelInhoud)) {
            return false;
        }
        final Lo3VerblijfstitelInhoud castOther = (Lo3VerblijfstitelInhoud) other;
        return new EqualsBuilder().append(aanduidingVerblijfstitelCode, castOther.aanduidingVerblijfstitelCode)
                                  .append(datumEindeVerblijfstitel, castOther.datumEindeVerblijfstitel)
                                  .append(datumAanvangVerblijfstitel, castOther.datumAanvangVerblijfstitel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingVerblijfstitelCode).append(datumEindeVerblijfstitel).append(datumAanvangVerblijfstitel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aanduidingVerblijfstitelCode", aanduidingVerblijfstitelCode)
                                                                          .append("datumEindeVerblijfstitel", datumEindeVerblijfstitel)
                                                                          .append("datumAanvangVerblijfstitel", datumAanvangVerblijfstitel)
                                                                          .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode;
        private Lo3Datum datumEindeVerblijfstitel;
        private Lo3Datum datumAanvangVerblijfstitel;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         *
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final Lo3VerblijfstitelInhoud inhoud) {
            aanduidingVerblijfstitelCode = inhoud.aanduidingVerblijfstitelCode;
            datumEindeVerblijfstitel = inhoud.datumEindeVerblijfstitel;
            datumAanvangVerblijfstitel = inhoud.datumAanvangVerblijfstitel;
        }

        /**
         * Build.
         *
         * @return inhoud
         */
        public Lo3VerblijfstitelInhoud build() {
            return new Lo3VerblijfstitelInhoud(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel, datumAanvangVerblijfstitel);
        }

        /**
         * Zet de waarde van aanduiding verblijfstitel code.
         *
         * @param aanduidingVerblijfstitelCode
         *            the aanduidingVerblijfstitelCode to set
         */
        public void setAanduidingVerblijfstitelCode(final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode) {
            this.aanduidingVerblijfstitelCode = aanduidingVerblijfstitelCode;
        }

        /**
         * Zet de waarde van datum einde verblijfstitel.
         *
         * @param datumEindeVerblijfstitel
         *            the datumEindeVerblijfstitel to set
         */
        public void setDatumEindeVerblijfstitel(final Lo3Datum datumEindeVerblijfstitel) {
            this.datumEindeVerblijfstitel = datumEindeVerblijfstitel;
        }

        /**
         * Zet de waarde van Aanvangsdatum verblijfstitel.
         *
         * @param datumAanvangVerblijfstitel
         *            the datumAanvangVerblijfstitel to set
         */
        public void setDatumAanvangVerblijfstitel(final Lo3Datum datumAanvangVerblijfstitel) {
            this.datumAanvangVerblijfstitel = datumAanvangVerblijfstitel;
        }
    }

}
