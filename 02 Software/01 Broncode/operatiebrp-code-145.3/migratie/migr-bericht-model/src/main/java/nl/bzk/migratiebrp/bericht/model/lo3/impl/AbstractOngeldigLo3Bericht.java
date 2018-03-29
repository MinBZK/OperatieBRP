/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;

/**
 * Bericht om aan te geven dat een bericht ongeldig is.
 */
public abstract class AbstractOngeldigLo3Bericht extends AbstractOngeldigBericht implements Lo3Bericht {
    private static final long serialVersionUID = 1L;

    private String bronPartijCode;
    private String doelPartijCode;

    /**
     * Constructor (gebruikt in ESB).
     * @param bericht bericht inhoud
     * @param melding melding
     */
    public AbstractOngeldigLo3Bericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronPartijCode()
     */
    @Override
    public final String getBronPartijCode() {
        return bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronPartijCode(java.lang.String)
     */
    @Override
    public final void setBronPartijCode(final String bronPartijCode) {
        this.bronPartijCode = bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelPartijCode()
     */
    @Override
    public final String getDoelPartijCode() {
        return doelPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelPartijCode(java.lang.String)
     */
    @Override
    public final void setDoelPartijCode(final String doelPartijCode) {
        this.doelPartijCode = doelPartijCode;
    }

    @Override
    public final void setHeader(final Lo3HeaderVeld veld, final String waarde) {
    }

    @Override
    public final String getHeaderWaarde(final Lo3HeaderVeld veld) {
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
        return new GerelateerdeInformatie(Collections.emptyList(), Arrays.asList(bronPartijCode, doelPartijCode), Collections.emptyList());
    }
}
