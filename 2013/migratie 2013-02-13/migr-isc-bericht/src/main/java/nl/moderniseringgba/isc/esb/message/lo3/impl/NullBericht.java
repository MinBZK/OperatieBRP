/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Null.
 */
public final class NullBericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private String messageId = BerichtId.generateMessageId();
    private String correlationId;
    private String originator;
    private String recipient;

    /**
     * Default constructor.
     */
    public NullBericht() {
    }

    /**
     * Convenience constructor.
     * 
     * @param correlationId
     *            correlation id
     */
    public NullBericht(final String correlationId) {
        this();
        setCorrelationId(correlationId);
    }

    @Override
    public String getBerichtType() {
        return "Null";
    }

    @Override
    public void setHeader(final Lo3HeaderVeld veld, final String waarde) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getHeader(final Lo3HeaderVeld veld) {
        return null;
    }

    @Override
    public void parse(final String lo3Bericht) {
        // Geen inhoud
    }

    @Override
    public String format() {
        return "";
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String getBronGemeente() {
        return originator;
    }

    @Override
    public void setBronGemeente(final String originator) {
        this.originator = originator;
    }

    @Override
    public String getDoelGemeente() {
        return recipient;
    }

    @Override
    public void setDoelGemeente(final String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NullBericht)) {
            return false;
        }
        final NullBericht castOther = (NullBericht) other;
        return new EqualsBuilder().append(messageId, castOther.messageId)
                .append(correlationId, castOther.correlationId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).append(correlationId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("messageId", messageId).append("correlationId", correlationId).toString();
    }

}
