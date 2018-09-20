/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert een BRP soort document code. Dit is geen enum maar een class omdat het hier een dynamische
 * stamtabel betreft.
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpSoortDocumentCode implements BrpAttribuut, Serializable {

    /**
     * Documentsoort Akte.
     */
    public static final BrpSoortDocumentCode AKTE = new BrpSoortDocumentCode("Akte");
    /**
     * Documentsoort Document.
     */
    public static final BrpSoortDocumentCode DOCUMENT = new BrpSoortDocumentCode("Document");
    /**
     * Documentsoort Migratievoorziening.
     */
    public static final BrpSoortDocumentCode MIGRATIEVOORZIENING = new BrpSoortDocumentCode("Migratievoorziening");

    private static final long serialVersionUID = 1L;

    @Text
    private final String code;

    /**
     * Maakt een BrpSoortDocumentCode object.
     * 
     * @param code
     *            de code die binnen BRP een soort document uniek identficeert, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public BrpSoortDocumentCode(@Text final String code) {
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
        if (!(other instanceof BrpSoortDocumentCode)) {
            return false;
        }
        final BrpSoortDocumentCode castOther = (BrpSoortDocumentCode) other;
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
