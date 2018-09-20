/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;

/**
 * Bericht om aan te geven dat een bericht ongeldig is.
 */
public abstract class OngeldigBericht extends AbstractOngeldigBericht implements Lo3Bericht {
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
    public OngeldigBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronGemeente()
     */
    @Override
    public final String getBronGemeente() {
        return bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronGemeente(java.lang.String)
     */
    @Override
    public final void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelGemeente()
     */
    @Override
    public final String getDoelGemeente() {
        return doelGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelGemeente(java.lang.String)
     */
    @Override
    public final void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    @Override
    public final void setHeader(final Lo3HeaderVeld veld, final String waarde) {
    }

    @Override
    public final String getHeader(final Lo3HeaderVeld veld) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeader()
     */
    @Override
    public final Lo3Header getHeader() {
        return null;
    }

    @Override
    public final void parse(final String lo3Bericht) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(bronGemeente, doelGemeente), null);
    }
}
