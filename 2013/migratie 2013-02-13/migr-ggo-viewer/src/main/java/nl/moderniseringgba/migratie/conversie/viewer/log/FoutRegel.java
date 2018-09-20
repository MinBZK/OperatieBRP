/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.log;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een foutregel voor gebruik bij het melden van fouten met BCM, precondities, etc.
 */
public final class FoutRegel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Lo3Herkomst lo3Herkomst;
    private final LogSeverity severity;
    private final LogType type;
    private final String code;
    private final String omschrijving;

    /**
     * Fout regel.
     * 
     * @param lo3Herkomst
     *            herkomst, mag niet null zijn
     * @param severity
     *            severity, mag niet null zijn
     * @param type
     *            type, mag niet null zijn
     * @param code
     *            code
     * @param omschrijving
     *            omschrijving
     */
    public FoutRegel(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final LogType type,
            final String code,
            final String omschrijving) {
        this.lo3Herkomst = lo3Herkomst;
        this.severity = severity;
        this.type = type;
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * @return the lo3Herkomst
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * @return the severity
     */
    public LogSeverity getSeverity() {
        return severity;
    }

    /**
     * @return the type
     */
    public LogType getType() {
        return type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FoutRegel)) {
            return false;
        }
        final FoutRegel castOther = (FoutRegel) other;
        return new EqualsBuilder().append(lo3Herkomst, castOther.lo3Herkomst).append(severity, castOther.severity)
                .append(type, castOther.type).append(code, castOther.code)
                .append(omschrijving, castOther.omschrijving).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lo3Herkomst).append(severity).append(type).append(code)
                .append(omschrijving).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("herkomst", lo3Herkomst).append("severity", severity).append("type", type)
                .append("code", code).append("omschrijving", omschrijving).toString();
    }

}
