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
 * Deze class representeert de inhoud van de groep BRP DienstBundelGroepAttribuutInhoud.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstbundelGroepAttribuutInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "attr", required = false)
    private final Integer historieAttribuutId;

    /**
     * Maak een BrpDienstBundelGroepAttribuutInhoud object.
     * @param historieAttribuutId historieAttribuutId
     */
    public BrpDienstbundelGroepAttribuutInhoud(@Element(name = "attr", required = false) final Integer historieAttribuutId) {
        super();
        this.historieAttribuutId = historieAttribuutId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return historieAttribuutId == null;
    }

    /**
     * Geef de waarde van historie attribuut id van BrpDienstbundelGroepAttribuutInhoud.
     * @return de waarde van historie attribuut id van BrpDienstbundelGroepAttribuutInhoud
     */
    public Integer getHistorieAttribuutId() {
        return historieAttribuutId;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelGroepAttribuutInhoud)) {
            return false;
        }
        final BrpDienstbundelGroepAttribuutInhoud castOther = (BrpDienstbundelGroepAttribuutInhoud) other;
        return new EqualsBuilder().append(historieAttribuutId, castOther.historieAttribuutId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(historieAttribuutId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("historieAttribuutId", historieAttribuutId).toString();
    }
}
