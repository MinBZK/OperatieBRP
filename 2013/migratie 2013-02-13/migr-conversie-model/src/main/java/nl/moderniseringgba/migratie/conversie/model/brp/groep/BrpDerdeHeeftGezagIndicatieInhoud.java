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
 * Deze class representeert de inhoud van de BRP indicatie 'Derde heeft gezag'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpDerdeHeeftGezagIndicatieInhoud extends AbstractBrpGroepInhoud implements
        BrpIndicatieGroepInhoud {
    @Element(name = "derdeHeeftGezag", required = false)
    private final Boolean derdeHeeftGezag;

    /**
     * Maakt een BrpDerdeHeeftGezagIndicatieInhoud object.
     * 
     * @param derdeHeeftGezag
     *            mag null zijn
     */
    public BrpDerdeHeeftGezagIndicatieInhoud(
            @Element(name = "derdeHeeftGezag", required = false) final Boolean derdeHeeftGezag) {
        this.derdeHeeftGezag = derdeHeeftGezag;
    }

    public Boolean getDerdeHeeftGezag() {
        return derdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getHeeftIndicatie() {
        return getDerdeHeeftGezag();
    }

    @Override
    public boolean isLeeg() {
        return derdeHeeftGezag == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDerdeHeeftGezagIndicatieInhoud)) {
            return false;
        }
        final BrpDerdeHeeftGezagIndicatieInhoud castOther = (BrpDerdeHeeftGezagIndicatieInhoud) other;
        return new EqualsBuilder().append(derdeHeeftGezag, castOther.derdeHeeftGezag).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(derdeHeeftGezag).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("derdeHeeftGezag", derdeHeeftGezag).toString();
    }
}
