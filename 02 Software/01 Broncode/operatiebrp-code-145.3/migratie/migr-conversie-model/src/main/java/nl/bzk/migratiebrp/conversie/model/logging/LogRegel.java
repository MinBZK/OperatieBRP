/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.logging;

import java.io.Serializable;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een logregel.
 */
public final class LogRegel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Element(name = "lo3Herkomst", required = true)
    private final Lo3Herkomst lo3Herkomst;

    @Element(name = "severity", required = true)
    private LogSeverity severity;

    @Element(name = "soortMeldingCode", required = true)
    private final SoortMeldingCode soortMeldingCode;

    @Element(name = "lo3ElementNummer", required = false)
    private final Lo3ElementEnum lo3ElementNummer;

    /**
     * Log regel.
     * @param lo3Herkomst herkomst, mag niet null zijn
     * @param severity severity, mag niet null zijn
     * @param soortMeldingCode soortMeldingCode, mag niet null zijn
     * @param lo3ElementNummer het lo3 element nummer
     */
    public LogRegel(
            @Element(name = "lo3Herkomst", required = true) final Lo3Herkomst lo3Herkomst,
            @Element(name = "severity", required = true) final LogSeverity severity,
            @Element(name = "soortMeldingCode", required = true) final SoortMeldingCode soortMeldingCode,
            @Element(name = "lo3ElementNummer", required = false) final Lo3ElementEnum lo3ElementNummer) {
        controleerVerplichteVelden(lo3Herkomst, severity, soortMeldingCode);
        this.lo3Herkomst = lo3Herkomst;
        this.severity = severity;
        this.soortMeldingCode = soortMeldingCode;
        this.lo3ElementNummer = lo3ElementNummer;
    }

    private void controleerVerplichteVelden(
            final Lo3Herkomst lo3HerkomstParam,
            final LogSeverity severityParam,
            final SoortMeldingCode soortMeldingCodeParam) {
        if (lo3HerkomstParam == null) {
            throw new NullPointerException("lo3Herkomst mag niet null zijn");
        }
        if (severityParam == null) {
            throw new NullPointerException("severity mag niet null zijn");
        }
        if (soortMeldingCodeParam == null) {
            throw new NullPointerException("soortMeldingCode mag niet null zijn");
        }
    }

    /**
     * Geef de waarde van lo3 herkomst.
     * @return lo3 herkomst
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Geef de waarde van severity.
     * @return severity
     */
    public LogSeverity getSeverity() {
        return severity;
    }

    /**
     * Zet de waarde van severity.
     * @param severity severity
     */
    public void setSeverity(final LogSeverity severity) {
        this.severity = severity;
    }

    /**
     * Geef de waarde van soort melding code.
     * @return soort melding code
     */
    public SoortMeldingCode getSoortMeldingCode() {
        return soortMeldingCode;
    }

    /**
     * Geef de waarde van lo3 element nummer.
     * @return lo3 element nummer
     */
    public Lo3ElementEnum getLo3ElementNummer() {
        return lo3ElementNummer;
    }

    /**
     * @return true als severity het niveau ERROR heeft
     */
    public boolean hasSeverityLevelError() {
        return severity.compareTo(LogSeverity.ERROR) == 0;
    }

    /**
     * @return true als severity het niveau INFO heeft
     */
    public boolean hasSeverityLevelInfo() {
        return severity.compareTo(LogSeverity.INFO) == 0;
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
        return new EqualsBuilder().append(lo3Herkomst, castOther.lo3Herkomst)
                .append(severity, castOther.severity)
                .append(soortMeldingCode, castOther.soortMeldingCode)
                .append(lo3ElementNummer, castOther.lo3ElementNummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lo3Herkomst).append(severity).append(soortMeldingCode).append(lo3ElementNummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("herkomst", lo3Herkomst)
                .append("severity", severity)
                .append("soortMeldingCode", soortMeldingCode)
                .append("lo3ElementNummer", lo3ElementNummer)
                .toString();
    }

}
