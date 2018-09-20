/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.impl;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;
import nl.moderniseringgba.isc.esb.message.Bericht;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Speciaal bericht om een niet te parsen bericht toch te kunnen verwerken in de processen.
 */
public class OngeldigBericht extends AbstractBericht implements Bericht {

    private static final long serialVersionUID = 1L;

    private final String bericht;
    private final String melding;

    /**
     * Constructor.
     * 
     * @param bericht
     *            ongeparsed bericht
     * @param melding
     *            melding
     */
    protected OngeldigBericht(final String bericht, final String melding) {
        this.bericht = bericht;
        this.melding = melding;
    }

    public final String getBericht() {
        return bericht;
    }

    public final String getMelding() {
        return melding;
    }

    @Override
    public final String getBerichtType() {
        return "OngeldigBericht";
    }

    @Override
    public final String getStartCyclus() {
        return null;
    }

    @Override
    public final String format() {
        return null;
    }

    // CHECKSTYLE:OFF - Extension kan hier wel door gebruik te maken van de ToStringBuilder
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("bericht", bericht).append("melding", melding).toString();
    }
    // CHECKSTYLE:ON

}
