/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpIndicatieGroepInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP indicatie 'Onder curatele'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpOnderCurateleIndicatieInhoud extends AbstractBrpGroepInhoud implements BrpIndicatieGroepInhoud {
    @Element(name = "onderCuratele", required = false)
    private final Boolean onderCuratele;

    /**
     * Maakt een BrpOnderCurateleIndicatieInhoud object.
     * 
     * @param onderCuratele
     *            is er een indicatie onder Curatele, mag null zijn
     */
    public BrpOnderCurateleIndicatieInhoud(
            @Element(name = "onderCuratele", required = false) final Boolean onderCuratele) {
        this.onderCuratele = onderCuratele;
    }

    public Boolean getOnderCuratele() {
        return onderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getHeeftIndicatie() {
        return getOnderCuratele();
    }

    @Override
    public boolean isLeeg() {
        return onderCuratele == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOnderCurateleIndicatieInhoud)) {
            return false;
        }
        final BrpOnderCurateleIndicatieInhoud castOther = (BrpOnderCurateleIndicatieInhoud) other;
        return new EqualsBuilder().append(onderCuratele, castOther.onderCuratele).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(onderCuratele).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("onderCuratele", onderCuratele).toString();
    }
}
