/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert de LO3 Soort Nederlands reisdocument (35.10). Deze soort verwijst binnen LO3 naar een
 * Nederlands reisdocument in Tabel 48 (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3SoortNederlandsReisdocument implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final String soort;

    /**
     * Maakt een Lo3SoortNederlandsReisdocument object.
     * 
     * @param soort
     *            de LO3 Soort Nederlands Reisdocument, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als soort null is
     */
    public Lo3SoortNederlandsReisdocument(@Text final String soort) {
        if (soort == null) {
            throw new NullPointerException("Soort mag niet null zijn");
        }
        this.soort = soort;
    }

    /**
     * @return de string representatie van de LO3 Soort Nederlands Reisdocument
     */
    public String getSoort() {
        return soort;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3SoortNederlandsReisdocument)) {
            return false;
        }
        final Lo3SoortNederlandsReisdocument castOther = (Lo3SoortNederlandsReisdocument) other;
        return new EqualsBuilder().append(soort, castOther.soort).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soort).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soort", soort).toString();
    }

}
