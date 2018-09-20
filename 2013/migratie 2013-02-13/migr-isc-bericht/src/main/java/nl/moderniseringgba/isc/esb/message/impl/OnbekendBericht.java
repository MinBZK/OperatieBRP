/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.impl;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;
import nl.moderniseringgba.isc.esb.message.Bericht;

/**
 * Speciaal bericht om aan te geven dat het ontvangen bericht onbekend is.
 */
public class OnbekendBericht extends AbstractBericht implements Bericht {

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
    protected OnbekendBericht(final String bericht, final String melding) {
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
        return "OnbekendBericht";
    }

    @Override
    public final String getStartCyclus() {
        return null;
    }

    @Override
    public final String format() {
        return null;
    }

}
