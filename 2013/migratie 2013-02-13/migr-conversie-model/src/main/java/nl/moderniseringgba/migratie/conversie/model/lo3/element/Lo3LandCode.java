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
 * Deze class representeert de LO3 land code. Deze identificeert een entry in de LO3 landen tabel (Tabel 34).
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3LandCode implements Lo3Element, Serializable {

    /** De land code voor 'Onbekend'. */
    public static final String CODE_ONBEKEND = "9999";
    /** De land code voor 'Standaard'. */
    public static final String CODE_STANDAARD = "0000";
    /** De land code voor 'Nederland'. */
    public static final String CODE_NEDERLAND = "6030";

    /** Nederland. */
    public static final Lo3LandCode NEDERLAND = new Lo3LandCode(CODE_NEDERLAND);

    private static final long serialVersionUID = 1L;

    @Text
    private final String code;

    /**
     * Maakt een Lo3LandCode object.
     * 
     * @param code
     *            de LO3 code die een land in LO3 uniek identificeert, mag niet null zijn
     * @throws NullPointerException
     *             als code null is
     */
    public Lo3LandCode(@Text final String code) {
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
     * @return true als deze land code gelijk is aan de land code voor Nederland, anders false
     */
    @Definitie(Definities.DEF001)
    public boolean isNederlandCode() {
        return CODE_NEDERLAND.equals(code);
    }

    /**
     * @return true als code <> 0000 en code <> 9999 en code <> 6030, anders false
     */
    @Definitie(Definities.DEF002)
    public boolean isLandCodeBuitenland() {
        return !(CODE_NEDERLAND.equals(code) || CODE_STANDAARD.equals(code) || CODE_ONBEKEND.equals(code));
    }

    /**
     * @return true als code gelijk is aan 0000 of 9999
     */
    @Definitie(Definities.DEF003)
    public boolean isOnbekend() {
        return CODE_STANDAARD.equals(code) || CODE_ONBEKEND.equals(code);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3LandCode)) {
            return false;
        }
        final Lo3LandCode castOther = (Lo3LandCode) other;
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
