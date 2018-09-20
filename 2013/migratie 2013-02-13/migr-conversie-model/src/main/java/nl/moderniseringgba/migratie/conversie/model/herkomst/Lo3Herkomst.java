/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.herkomst;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * De herkomst vanuit een lo3 bericht wordt vastgelegd door de categorie, stapelnummer en categorievoorkomen.
 */
public final class Lo3Herkomst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Element(name = "categorie")
    private final Lo3CategorieEnum categorie;
    @Element(name = "stapel")
    private final int stapel;
    @Element(name = "voorkomen")
    private final int voorkomen;

    /**
     * Constructor.
     * 
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel
     * @param voorkomen
     *            voorkomen
     */
    public Lo3Herkomst(
            @Element(name = "categorie") final Lo3CategorieEnum categorie,
            @Element(name = "stapel") final int stapel,
            @Element(name = "voorkomen") final int voorkomen) {
        super();
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
    }

    public Lo3CategorieEnum getCategorie() {
        return categorie;
    }

    public int getStapel() {
        return stapel;
    }

    public int getVoorkomen() {
        return voorkomen;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Herkomst)) {
            return false;
        }
        final Lo3Herkomst castOther = (Lo3Herkomst) other;
        return new EqualsBuilder().append(categorie, castOther.categorie).append(stapel, castOther.stapel)
                .append(voorkomen, castOther.voorkomen).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie).append(stapel).append(voorkomen).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("categorie", categorie).append("stapel", stapel).append("voorkomen", voorkomen).toString();
    }

}
