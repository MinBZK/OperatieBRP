/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;

import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Vb01.
 */
public final class Vb01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.LENGTE_BERICHT, Lo3HeaderVeld.BERICHT);

    private String bericht;

    /**
     * Default constructor.
     */
    public Vb01Bericht() {
        super(HEADER);
    }

    /**
     * Convenience constructor.
     * 
     * @param correlationId
     *            correlation id
     */
    public Vb01Bericht(final String correlationId) {
        this();
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor.
     * 
     * @param correlationId
     *            correlation id
     * @param bericht
     *            Het foutmeldingsbericht.
     */
    public Vb01Bericht(final String correlationId, final String bericht) {
        this();
        setCorrelationId(correlationId);
        setBericht(bericht);
    }

    @Override
    public String getBerichtType() {
        return "Vb01";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    /**
     * Zet het bericht.
     * 
     * @param bericht
     *            bericht
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
        setHeader(Lo3HeaderVeld.BERICHT, this.bericht);
        setHeader(Lo3HeaderVeld.LENGTE_BERICHT, this.bericht == null ? "0" : Integer.toString(this.bericht.length()));
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vb01Bericht)) {
            return false;
        }
        return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }

}
