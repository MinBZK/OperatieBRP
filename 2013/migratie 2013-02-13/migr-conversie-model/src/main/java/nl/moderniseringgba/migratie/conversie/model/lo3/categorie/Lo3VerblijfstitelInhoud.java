/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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

    // 39 Verblijfstitel
    @Element(name = "aanduidingVerblijfstitelCode", required = false)
    private final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode;
    @Element(name = "datumEindeVerblijfstitel", required = false)
    private final Lo3Datum datumEindeVerblijfstitel;
    @Element(name = "ingangsdatumVerblijfstitel", required = false)
    private final Lo3Datum ingangsdatumVerblijfstitel;

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
     * @param ingangsdatumVerblijfstitel
     *            de ingangsdatum aanduiding verblijfstitel, mag niet null zijn
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie10Verblijfstitel}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie10Verblijfstitel}
     */
    public Lo3VerblijfstitelInhoud(
            @Element(name = "aanduidingVerblijfstitelCode", required = false) final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode,
            @Element(name = "datumEindeVerblijfstitel", required = false) final Lo3Datum datumEindeVerblijfstitel,
            @Element(name = "ingangsdatumVerblijfstitel", required = false) final Lo3Datum ingangsdatumVerblijfstitel) {
        this.aanduidingVerblijfstitelCode = aanduidingVerblijfstitelCode;
        this.datumEindeVerblijfstitel = datumEindeVerblijfstitel;
        this.ingangsdatumVerblijfstitel = ingangsdatumVerblijfstitel;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel,
                ingangsdatumVerblijfstitel);
    }

    /**
     * @return the aanduidingVerblijfstitelCode
     */
    public Lo3AanduidingVerblijfstitelCode getAanduidingVerblijfstitelCode() {
        return aanduidingVerblijfstitelCode;
    }

    /**
     * @return the datumEindeVerblijfstitel, of null
     */
    public Lo3Datum getDatumEindeVerblijfstitel() {
        return datumEindeVerblijfstitel;
    }

    /**
     * @return the ingangsdatumVerblijfstitel
     */
    public Lo3Datum getIngangsdatumVerblijfstitel() {
        return ingangsdatumVerblijfstitel;
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
                .append(ingangsdatumVerblijfstitel, castOther.ingangsdatumVerblijfstitel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingVerblijfstitelCode).append(datumEindeVerblijfstitel)
                .append(ingangsdatumVerblijfstitel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("aanduidingVerblijfstitelCode", aanduidingVerblijfstitelCode)
                .append("datumEindeVerblijfstitel", datumEindeVerblijfstitel)
                .append("ingangsdatumVerblijfstitel", ingangsdatumVerblijfstitel).toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode;
        private Lo3Datum datumEindeVerblijfstitel;
        private Lo3Datum ingangsdatumVerblijfstitel;

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
            ingangsdatumVerblijfstitel = inhoud.ingangsdatumVerblijfstitel;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3VerblijfstitelInhoud build() {
            return new Lo3VerblijfstitelInhoud(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel,
                    ingangsdatumVerblijfstitel);
        }

        /**
         * @param aanduidingVerblijfstitelCode
         *            the aanduidingVerblijfstitelCode to set
         */
        public void
                setAanduidingVerblijfstitelCode(final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode) {
            this.aanduidingVerblijfstitelCode = aanduidingVerblijfstitelCode;
        }

        /**
         * @param datumEindeVerblijfstitel
         *            the datumEindeVerblijfstitel to set
         */
        public void setDatumEindeVerblijfstitel(final Lo3Datum datumEindeVerblijfstitel) {
            this.datumEindeVerblijfstitel = datumEindeVerblijfstitel;
        }

        /**
         * @param ingangsdatumVerblijfstitel
         *            the ingangsdatumVerblijfstitel to set
         */
        public void setIngangsdatumVerblijfstitel(final Lo3Datum ingangsdatumVerblijfstitel) {
            this.ingangsdatumVerblijfstitel = ingangsdatumVerblijfstitel;
        }
    }

}
