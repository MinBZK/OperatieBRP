/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract bericht.
 */
public abstract class AbstractBericht implements Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private String messageId = BerichtId.generateMessageId();
    private String correlationId;

    @Override
    public final String getMessageId() {
        return messageId;
    }

    @Override
    public final void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    @Override
    public final String getCorrelationId() {
        return correlationId;
    }

    @Override
    public final void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF - Door consequent gebruik van Commons Lang gaat subclassing prima

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("messageId", messageId).append("correlationId", correlationId).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractBericht)) {
            return false;
        }
        final AbstractBericht castOther = (AbstractBericht) other;
        return new EqualsBuilder().append(messageId, castOther.messageId)
                .append(correlationId, castOther.correlationId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).append(correlationId).toHashCode();
    }

    // CHECKSTYLE:ON

}
