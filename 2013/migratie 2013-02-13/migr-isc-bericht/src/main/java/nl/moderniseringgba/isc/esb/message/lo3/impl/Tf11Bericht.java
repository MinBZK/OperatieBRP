/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Tf11.
 */
public final class Tf11Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER);

    private String aNummer;

    /**
     * Constructor.
     */
    public Tf11Bericht() {
        super(HEADER);
    }

    @Override
    public String getBerichtType() {
        return "Tf11";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        final Lo3CategorieWaarde categorie01 = getCategorie(categorieen, Lo3CategorieEnum.CATEGORIE_01);

        if (categorie01 != null) {
            aNummer = categorie01.getElement(Lo3ElementEnum.ELEMENT_0110);
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, -1, -1);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, aNummer);

        return Collections.singletonList(categorie01);
    }

    /* ************************************************************************************************************* */

    public void setANummer(final String aNummer) {
        this.aNummer = aNummer;
    }

    public String getANummer() {
        return aNummer;
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Tf11Bericht)) {
            return false;
        }
        final Tf11Bericht castOther = (Tf11Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(aNummer, castOther.aNummer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(aNummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("aNummer", aNummer).toString();
    }

}
