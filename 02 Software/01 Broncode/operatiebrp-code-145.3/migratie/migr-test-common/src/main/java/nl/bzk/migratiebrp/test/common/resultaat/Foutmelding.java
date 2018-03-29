/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.resultaat;

import java.io.PrintWriter;
import java.io.StringWriter;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Foutmelding die als XML kan worden geserialiseerd, als terugkoppeling bij een fout tijdens het uitvoeren van de
 * conversie.
 */
@Root(name = "foutmelding")
public final class Foutmelding {

    private final String context;
    private final String message;
    private final String stacktrace;

    /**
     * Constructor.
     * @param context context
     * @param message message
     * @param stacktrace stacktrace
     */
    public Foutmelding(
            @Element(name = "context") final String context,
            @Element(name = "message", required = false) final String message,
            @Element(name = "stacktrace", required = false) final String stacktrace) {
        this.context = context;
        this.message = message;
        this.stacktrace = stacktrace;
    }

    /**
     * Maak een nieuwe foutmelding aan, die evt. weggeschreven kan worden naar XML.
     * @param context De context van de fout
     * @param oorzaak De exception die de fout veroorzaak heeft
     */
    public Foutmelding(final String context, final Throwable oorzaak) {
        if (context == null || oorzaak == null) {
            throw new IllegalArgumentException("Argumenten zijn verplicht");
        }
        this.context = context;
        stacktrace = leesStacktraceUit(oorzaak);
        message = oorzaak.getMessage();
    }

    private String leesStacktraceUit(final Throwable oorzaak) {
        final StringWriter sw = new StringWriter();
        oorzaak.printStackTrace(new PrintWriter(sw));
        return sw.getBuffer().toString();
    }

    /**
     * Geef de waarde van context.
     * @return context
     */
    @Element(name = "context")
    public String getContext() {
        return context;
    }

    /**
     * Geef de waarde van message.
     * @return message
     */
    @Element(name = "message", required = false)
    public String getMessage() {
        return message;
    }

    /**
     * Geef de waarde van stacktrace.
     * @return stacktrace
     */
    @Element(name = "stacktrace", required = false)
    public String getStacktrace() {
        return stacktrace;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Foutmelding)) {
            return false;
        }
        final Foutmelding castOther = (Foutmelding) other;
        return new EqualsBuilder().append(context, castOther.context).append(message, castOther.message).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(context).append(message).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("context", context)
                .append("message", message)
                .toString();
    }

}
