/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert het LO3 element gemeente code. Dit is een verwijzing naar de LO3 gemeente tabel (Tabel 33).
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3GemeenteCode implements Lo3Element, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int LENGTE_NEDERLANDSE_CODE = 4;

    @Text
    private final String code;

    /**
     * Maakt een Lo3GemeenteCode object.
     * 
     * @param code
     *            de LO3 code die een gemeente in LO3 uniek identificeert, mag niet null zijn
     * @throws IllegalArgumentException
     *             als de lengte van de code niet tussen de 1 en 40 karakters is
     * @throws NullPointerException
     *             als code null is
     */
    public Lo3GemeenteCode(@Text final String code) {
        if (code == null) {
            throw new NullPointerException("Code mag niet null zijn");
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return true als de gemeente code uit vier cijfers bestaat en niet gelijk is aan 0000, anders false
     */
    @Definitie(Definities.DEF001)
    public boolean isValideNederlandseGemeenteCode() {
        boolean result = false;
        if (code.length() == LENGTE_NEDERLANDSE_CODE) {
            try {
                Integer.parseInt(code);
                result = true;
            } catch (final NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3GemeenteCode)) {
            return false;
        }
        final Lo3GemeenteCode castOther = (Lo3GemeenteCode) other;
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
