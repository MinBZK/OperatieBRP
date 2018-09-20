/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.logging;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Een logregel.
 */
public final class LogRegel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Element(name = "lo3Herkomst", required = true)
    private final Lo3Herkomst lo3Herkomst;

    @Element(name = "severity", required = true)
    private LogSeverity severity;

    @Element(name = "type", required = true)
    private final LogType type;

    @Element(name = "code", required = false)
    private final String code;

    @Element(name = "omschrijving", required = false)
    private final String omschrijving;

    /**
     * Log regel.
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
    public LogRegel(
            @Element(name = "lo3Herkomst", required = true) final Lo3Herkomst lo3Herkomst,
            @Element(name = "severity", required = true) final LogSeverity severity,
            @Element(name = "type", required = true) final LogType type,
            @Element(name = "code", required = false) final String code,
            @Element(name = "omschrijving", required = false) final String omschrijving) {
        controleerVerplichteVelden(lo3Herkomst, severity, type);
        this.lo3Herkomst = lo3Herkomst;
        this.severity = severity;
        this.type = type;
        this.code = code;
        this.omschrijving = omschrijving;
    }

    private void controleerVerplichteVelden(
            final Lo3Herkomst lo3Herkomst,
            final LogSeverity severity,
            final LogType type) {
        if (lo3Herkomst == null) {
            throw new NullPointerException("lo3Herkomst mag niet null zijn");
        }
        if (severity == null) {
            throw new NullPointerException("severity mag niet null zijn");
        }
        if (type == null) {
            throw new NullPointerException("type mag niet null zijn");
        }
    }

    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    public LogSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(final LogSeverity severity) {
        this.severity = severity;
    }

    public LogType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * @return true als severity het niveau ERROR heeft
     */
    public boolean hasSeverityLevelError() {
        return severity.compareTo(LogSeverity.ERROR) == 0;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LogRegel)) {
            return false;
        }
        final LogRegel castOther = (LogRegel) other;
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
