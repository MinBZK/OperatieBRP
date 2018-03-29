/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze enum representeert een BRP Soort partij.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpSoortPartijCode implements BrpAttribuut {

    /**
     * Constante voor soort partij GEMEENTE.
     */
    public static final BrpSoortPartijCode GEMEENTE = new BrpSoortPartijCode("Gemeente");
    /**
     * Constante voor soort partij DERDE.
     */
    public static final BrpSoortPartijCode DERDE = new BrpSoortPartijCode("Derde");

    @Element(name = "soortPartij", required = false)
    private final String soortPartij;

    /**
     * Maakt een BrpSoortPartijCode.
     * @param soortPartij BRP soortPartij
     */
    public BrpSoortPartijCode(@Element(name = "soortPartij", required = false) final String soortPartij) {
        this.soortPartij = soortPartij;
    }

    /**
     * Geef de waarde van soortPartij.
     * @return the soortPartij
     */
    public String getSoortPartij() {
        return soortPartij;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpSoortPartijCode)) {
            return false;
        }
        final BrpSoortPartijCode castOther = (BrpSoortPartijCode) other;
        return new EqualsBuilder().append(soortPartij, castOther.soortPartij).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortPartij).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("soortPartij", soortPartij).toString();
    }
}
