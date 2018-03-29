/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP DienstBundelLo3RubriekInhoud.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstbundelLo3RubriekInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "isLeeg", required = false)
    private final Boolean isLeeg;

    /**
     * Maak een Brp DienstBundelLo3RubriekInhoud object.
     * @param isLeeg isLeeg
     */
    public BrpDienstbundelLo3RubriekInhoud(@Element(name = "isLeeg", required = false) final Boolean isLeeg) {
        super();
        this.isLeeg = isLeeg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return isLeeg;
    }

    /**
     * Geef de waarde van checks if is leeg van BrpDienstbundelLo3RubriekInhoud.
     * @return de waarde van checks if is leeg van BrpDienstbundelLo3RubriekInhoud
     */
    public Boolean getIsLeeg() {
        return isLeeg;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelLo3RubriekInhoud)) {
            return false;
        }
        final BrpDienstbundelLo3RubriekInhoud castOther = (BrpDienstbundelLo3RubriekInhoud) other;
        return new EqualsBuilder().append(isLeeg, castOther.isLeeg).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isLeeg).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("historieAttribuutId", isLeeg).toString();
    }
}
