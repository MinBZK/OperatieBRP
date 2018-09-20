/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;

/**
 * Bericht om aan te geven dat een bericht onbekend is.
 */
public final class OnbekendBericht extends nl.moderniseringgba.isc.esb.message.impl.OnbekendBericht implements
        Lo3Bericht {
    private static final long serialVersionUID = 1L;

    private String bronGemeente;
    private String doelGemeente;

    /**
     * Constructor (gebruikt in ESB).
     * 
     * @param bericht
     *            bericht inhoud
     * @param melding
     *            melding
     */
    public OnbekendBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    /**
     * Constructor (gebruikt in processen en ESB om onverwacht bericht te wrappen).
     * 
     * @param lo3Bericht
     *            onverwacht bericht
     */
    public OnbekendBericht(final Lo3Bericht lo3Bericht) {
        super(safeFormat(lo3Bericht), "Onbekend bericht");
        setMessageId(lo3Bericht.getMessageId());
        setCorrelationId(lo3Bericht.getCorrelationId());
        setBronGemeente(lo3Bericht.getBronGemeente());
        setDoelGemeente(lo3Bericht.getDoelGemeente());
    }

    private static String safeFormat(final Lo3Bericht lo3Bericht) {
        try {
            return lo3Bericht.format();
        } catch (final BerichtInhoudException e) {
            return null;
        }
    }

    @Override
    public String getBronGemeente() {
        return bronGemeente;
    }

    @Override
    public void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    @Override
    public String getDoelGemeente() {
        return doelGemeente;
    }

    @Override
    public void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    @Override
    public void setHeader(final Lo3HeaderVeld veld, final String waarde) {
    }

    @Override
    public String getHeader(final Lo3HeaderVeld veld) {
        return null;
    }

    @Override
    public void parse(final String lo3Bericht) {
    }

}
