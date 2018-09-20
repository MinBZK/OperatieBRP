/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * De class representeert een BRP Reisdocument soort.
 * 
 * Deze code verwijst naar de BRP stamtabel Soort Nederlands reisdocument. Dit is geen enum maar een class omdat het
 * hier een dynamische stamtabel betreft.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class BrpReisdocumentSoort implements BrpAttribuut {

    @Text
    private final String code;

    /**
     * Maakt een BrpReisdocumentSoort object.
     * 
     * @param code
     *            de code, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpReisdocumentSoort(@Text final String code) {
        if (code == null) {
            throw new NullPointerException();
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpReisdocumentSoort)) {
            return false;
        }
        final BrpReisdocumentSoort castOther = (BrpReisdocumentSoort) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("code", code).toString();
    }
}
