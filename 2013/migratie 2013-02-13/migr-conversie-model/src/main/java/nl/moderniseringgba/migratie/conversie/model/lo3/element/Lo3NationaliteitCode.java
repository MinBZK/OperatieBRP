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
 * Deze class representeert de LO3 nationaliteit code. Deze code verwijst binnen LO3 naar een nationaliteit in Tabel 32
 * (zie LO3.7).
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3NationaliteitCode implements Lo3Element, Serializable {

    /** Nationaliteit code voor de Nederlandse nationaliteit. */
    public static final String NATIONALITEIT_CODE_NEDERLANDSE = "0001";
    /** Nationaliteit code voor de indicatie Statenloos. */
    public static final String NATIONALITEIT_CODE_STATENLOOS = "0499";

    private static final long serialVersionUID = 1L;

    @Text
    private final String code;

    /**
     * Maakt een Lo3NationaliteitCode object.
     * 
     * @param code
     *            de LO3 code, deze waarde mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public Lo3NationaliteitCode(@Text final String code) {
        if (code == null) {
            throw new NullPointerException("Code mag niet null zijn");
        }
        this.code = code;
    }

    /**
     * @return de string representatie van de LO3 nationaliteit code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3NationaliteitCode)) {
            return false;
        }
        final Lo3NationaliteitCode castOther = (Lo3NationaliteitCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", code).toString();
    }

}
