/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Betrokkenheid/Ouderlijk gezag.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpOuderlijkGezagInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "ouderHeeftGezag", required = false)
    private final BrpBoolean ouderHeeftGezag;

    /**
     * Maakt een BrpOuderlijkGezagInhoud object.
     * @param ouderHeeftGezag true als de ouder gezag heeft, false indien geen gezag, null indien onbekend
     */
    public BrpOuderlijkGezagInhoud(@Element(name = "ouderHeeftGezag", required = false) final BrpBoolean ouderHeeftGezag) {
        this.ouderHeeftGezag = ouderHeeftGezag;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(ouderHeeftGezag);
    }

    /**
     * Geef de waarde van ouder heeft gezag van BrpOuderlijkGezagInhoud.
     * @return de waarde van ouder heeft gezag van BrpOuderlijkGezagInhoud
     */
    public BrpBoolean getOuderHeeftGezag() {
        return ouderHeeftGezag;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOuderlijkGezagInhoud)) {
            return false;
        }
        final BrpOuderlijkGezagInhoud castOther = (BrpOuderlijkGezagInhoud) other;
        return new EqualsBuilder().append(ouderHeeftGezag, castOther.ouderHeeftGezag).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ouderHeeftGezag).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("ouderHeeftGezag", ouderHeeftGezag)
                .toString();
    }
}
