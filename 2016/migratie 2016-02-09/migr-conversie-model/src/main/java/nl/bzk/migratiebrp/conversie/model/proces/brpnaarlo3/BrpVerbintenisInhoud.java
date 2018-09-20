/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class geeft weer wat het type van een relatie is.
 */
public final class BrpVerbintenisInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "soortRelatieCode", required = false)
    private final BrpSoortRelatieCode soortRelatieCode;

    /**
     * Maakt een BrpVerbintenisInhoud object.
     * 
     * @param soortRelatieCode
     *            soort relatie
     */
    public BrpVerbintenisInhoud(@Element(name = "soortRelatieCode", required = false) final BrpSoortRelatieCode soortRelatieCode) {
        this.soortRelatieCode = soortRelatieCode;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    /**
     * Geef de waarde van soort relatie code.
     *
     * @return the soortRelatieCode
     */
    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerbintenisInhoud)) {
            return false;
        }
        final BrpVerbintenisInhoud castOther = (BrpVerbintenisInhoud) other;
        return new EqualsBuilder().append(soortRelatieCode, castOther.soortRelatieCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortRelatieCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortRelatieCode", soortRelatieCode).toString();
    }

}
