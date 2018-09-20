/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;

/**
 * Bericht om aan te geven dat een bericht onbekend is.
 */
public final class OnbekendBericht extends AbstractOnbekendBericht implements Lo3Bericht {
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

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronGemeente()
     */
    @Override
    public String getBronGemeente() {
        return bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronGemeente(java.lang.String)
     */
    @Override
    public void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelGemeente()
     */
    @Override
    public String getDoelGemeente() {
        return doelGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelGemeente(java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeader()
     */
    @Override
    public Lo3Header getHeader() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(bronGemeente, doelGemeente), null);
    }

    @Override
    public void parse(final String lo3Bericht) {
    }

}
