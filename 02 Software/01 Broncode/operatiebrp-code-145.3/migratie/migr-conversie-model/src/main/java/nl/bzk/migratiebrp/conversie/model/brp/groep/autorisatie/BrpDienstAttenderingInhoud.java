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
 * Deze class representeert de inhoud van de groep BRP Dienst Attendering.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstAttenderingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "attenderingscriterium", required = false)
    private final String attenderingscriterium;

    /**
     * Maak een BrpDienstAttenderingInhoud object.
     * @param attenderingscriterium attenderingscriterium
     */
    public BrpDienstAttenderingInhoud(@Element(name = "attenderingscriterium", required = false) final String attenderingscriterium) {
        super();
        this.attenderingscriterium = attenderingscriterium;
    }

    /**
     * Geef de waarde van attenderingscriterium van BrpDienstAttenderingInhoud.
     * @return de waarde van attenderingscriterium van BrpDienstAttenderingInhoud
     */
    public String getAttenderingscriterium() {
        return attenderingscriterium;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstAttenderingInhoud)) {
            return false;
        }
        final BrpDienstAttenderingInhoud castOther = (BrpDienstAttenderingInhoud) other;
        return new EqualsBuilder().append(attenderingscriterium, castOther.attenderingscriterium).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(attenderingscriterium).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("attenderingscriterium", attenderingscriterium).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return attenderingscriterium == null;
    }
}
