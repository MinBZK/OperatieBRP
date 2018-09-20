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
 * Deze class representeert de LO3 Autoriteit van afgifte Nederlands reisdocument (35.40). De codering verwijst binnen
 * LO3 naar een autoriteit in Tabel 49 (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3AutoriteitVanAfgifteNederlandsReisdocument implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final String autoriteit;

    /**
     * Maakt een Lo3AutoriteitVanAfgifteNederlandsReisdocument object.
     * 
     * @param autoriteit
     *            de LO3 Autoriteit van afgifte Nederlands Reisdocument, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als autoriteit null is
     * 
     */
    public Lo3AutoriteitVanAfgifteNederlandsReisdocument(@Text final String autoriteit) {
        if (autoriteit == null) {
            throw new NullPointerException();
        }
        this.autoriteit = autoriteit;
    }

    /**
     * @return de string representatie van de LO3 Autoriteit van afgifte Nederlands Reisdocument
     */
    public String getAutoriteit() {
        return autoriteit;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3AutoriteitVanAfgifteNederlandsReisdocument)) {
            return false;
        }
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument castOther =
                (Lo3AutoriteitVanAfgifteNederlandsReisdocument) other;
        return new EqualsBuilder().append(autoriteit, castOther.autoriteit).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(autoriteit).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("autoriteit", autoriteit)
                .toString();
    }

}
