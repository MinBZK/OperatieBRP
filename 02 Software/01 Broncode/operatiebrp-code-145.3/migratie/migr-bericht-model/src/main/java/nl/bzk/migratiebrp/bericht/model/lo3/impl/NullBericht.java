/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3EindBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Null.
 */
public final class NullBericht implements Lo3Bericht, Lo3EindBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private String messageId;
    private String correlationId;
    private String originator;
    private String recipient;

    /**
     * Default constructor.
     */
    public NullBericht() {
        // bericht zonder correlatieId.
    }

    /**
     * Convenience constructor.
     * @param correlationId correlation id
     */
    public NullBericht(final String correlationId) {
        this();
        setCorrelationId(correlationId);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getBerichtType()
     */
    @Override
    public String getBerichtType() {
        return "Null";
    }

    @Override
    public void setHeader(final Lo3HeaderVeld veld, final String waarde) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getHeaderWaarde(final Lo3HeaderVeld veld) {
        return null;
    }

    @Override
    public List<String> getHeaderWaarden(final Lo3HeaderVeld veld) {
        return Collections.emptyList();
    }
    
    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeaderWaarde()
     */
    @Override
    public Lo3Header getHeader() {
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

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getMessageId()
     */
    @Override
    public String getMessageId() {
        return messageId;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#setMessageId(java.lang.String)
     */
    @Override
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getCorrelationId()
     */
    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#setCorrelationId(java.lang.String)
     */
    @Override
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronPartijCode()
     */
    @Override
    public String getBronPartijCode() {
        return originator;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronPartijCode(java.lang.String)
     */
    @Override
    public void setBronPartijCode(final String bronPartijCode) {
        originator = bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelPartijCode()
     */
    @Override
    public String getDoelPartijCode() {
        return recipient;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelPartijCode(java.lang.String)
     */
    @Override
    public void setDoelPartijCode(final String doelPartijCode) {
        recipient = doelPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getStartCyclus()
     */
    @Override
    public String getStartCyclus() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(originator, recipient), null);
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
        return new EqualsBuilder().append(messageId, castOther.messageId).append(correlationId, castOther.correlationId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).append(correlationId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("messageId", messageId)
                .append("correlationId", correlationId)
                .toString();
    }

}
