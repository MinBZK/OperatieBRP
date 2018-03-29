/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.io.Serializable;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een foutregel voor gebruik bij het melden van fouten met BCM, precondities, etc.
 */
public final class GgoFoutRegel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final GgoStap stap;
    private final GgoVoorkomen herkomst;
    private final LogSeverity severity;
    private final GgoLogType type;
    private final String code;
    private final String omschrijving;
    private final String htmlFieldId;

    /**
     * Fout regel.
     * @param stap stap
     * @param herkomst herkomst, mag niet null zijn
     * @param severity severity, mag niet null zijn
     * @param type type, mag niet null zijn
     * @param code code
     * @param omschrijving omschrijving
     * @param htmlFieldId het id van het html component waar de fout betrekking op heeft. Deze wordt dan rood gehighlight.
     */
    public GgoFoutRegel(
            final GgoStap stap,
            final GgoVoorkomen herkomst,
            final LogSeverity severity,
            final GgoLogType type,
            final String code,
            final String omschrijving,
            final String htmlFieldId) {
        this.stap = stap;
        this.herkomst = herkomst;
        this.severity = severity;
        this.type = type;
        this.code = code;
        this.omschrijving = omschrijving;
        this.htmlFieldId = htmlFieldId;
    }

    /**
     * Fout regel.
     * @param stap stap
     * @param herkomst herkomst, mag niet null zijn
     * @param severity severity, mag niet null zijn
     * @param type type, mag niet null zijn
     * @param code code
     * @param omschrijving omschrijving
     */
    public GgoFoutRegel(
            final GgoStap stap,
            final GgoVoorkomen herkomst,
            final LogSeverity severity,
            final GgoLogType type,
            final String code,
            final String omschrijving) {
        this(stap, herkomst, severity, type, code, omschrijving, null);

    }

    /**
     * Dezelfde constructor als hierboven maar dan zonder stap.
     * @param herkomst herkomst, mag niet null zijn
     * @param severity severity, mag niet null zijn
     * @param type type, mag niet null zijn
     * @param code code
     * @param omschrijving omschrijving
     */
    public GgoFoutRegel(
            final GgoVoorkomen herkomst,
            final LogSeverity severity,
            final GgoLogType type,
            final String code,
            final String omschrijving) {
        this(null, herkomst, severity, type, code, omschrijving);
    }

    /**
     * Geef de waarde van stap.
     * @return the stap
     */
    public GgoStap getStap() {
        return stap;
    }

    /**
     * Geef de waarde van herkomst.
     * @return the lo3Herkomst
     */
    public GgoVoorkomen getHerkomst() {
        return herkomst;
    }

    /**
     * Geef de waarde van severity.
     * @return the severity
     */
    public LogSeverity getSeverity() {
        return severity;
    }

    /**
     * Geef de waarde van type.
     * @return the type as String
     */
    public String getType() {
        return type == null ? null : type.getLabel();
    }

    /**
     * Geef de waarde van code.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return the omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van html field id.
     * @return the htmlFieldId
     */
    public String getHtmlFieldId() {
        return htmlFieldId;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GgoFoutRegel)) {
            return false;
        }
        final GgoFoutRegel castOther = (GgoFoutRegel) other;
        return new EqualsBuilder().append(herkomst, castOther.herkomst)
                .append(severity, castOther.severity)
                .append(type, castOther.type)
                .append(code, castOther.code)
                .append(omschrijving, castOther.omschrijving)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(herkomst).append(severity).append(type).append(code).append(omschrijving).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("herkomst", herkomst)
                .append("severity", severity)
                .append("type", type)
                .append("code", code)
                .append("omschrijving", omschrijving)
                .toString();
    }

}
